package com.QRCloud.service;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.ItemMapper;
import com.QRCloud.dao.UserMapper;
import com.QRCloud.domain.Item;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.QRCloud.util.AmazonS3Util;
import com.QRCloud.util.QRCodeGen;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Item的服务层，用于提供各类针对Item的服务
 * 
 * @author Shane
 * @version 1.0
 */
@Service
public class ItemService {
	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AmazonS3Util s3Util;

	/**
	 * 返回item列表
	 * 
	 * @param projectId
	 *            item所属的project的Id
	 * @return 封装了Item对象的列表
	 */
	public List<Item> getItemList(long projectId) {
		return itemMapper.getItemList(projectId);
	}

	/**
	 * 将文件上传至S3，并更新对应的数据库数据
	 * 
	 * @param user
	 *            当前登陆用户
	 * @param item
	 *            Item相关信息，既是入参，也是出参。
	 * @param fileName
	 *            文件名，例如，格式为test.mp4
	 * @param savePath
	 *            文件在服务器的暂存路径，一般情况下为uploads/username/files
	 * @param prefixPath
	 *            暂存路径的公共前缀部分，参见ItemController类，一般情况下为uploads/username
	 * @throws WriterException
	 *             写异常
	 * @throws AmazonServiceException
	 *             Amazon服务异常
	 * @throws AmazonClientException
	 *             Amazon客户端异常
	 * @throws InterruptedException
	 *             中断异常
	 * @throws IOException
	 *             IO异常
	 */
	public void uploadItem(User user, Item item, String fileName, String savePath, String prefixPath)
			throws WriterException, AmazonServiceException, AmazonClientException, InterruptedException, IOException {
		// 将文件上传至s3
		String bucketName = userMapper.getBucketName(user.getUserName());

		// 初始化s3上传对象
		TransferManager tx = s3Util.getS3Tranfer();
		Upload myUpload = null;

		// 在s3上的保存路径的格式为：s3.cn-north-1.amazonaws.com.cn/bucketname/qrmanager/projectId/
		String keyPrefix = "qrmanager/" + item.getProjectId() + '/';
		myUpload = tx.upload(bucketName, keyPrefix + fileName, new File(savePath, fileName));

		// 采用同步机制，而不采用异步，因为需要保证数据库和文件的一致性。如此，可能会损失部分性能，但考虑文件尺寸
		// 并不大，而且网络带宽较大，时间上不会有太多耗损，故采用同步而非异步
		myUpload.waitForCompletion();

		// 更新数据库item部分信息，主要是获取自增的ID
		Pattern p = Pattern.compile("([\\s\\S]+)\\.([a-zA-Z0-9]+)");
		Matcher m = p.matcher(fileName);
		m.matches();

		// 设置item对象的相关属性，如文件类型，文件名
		item.setObjectType(m.group(2));
		item.setItemName(m.group(1));
		item.setItemComment("");

		// 调用数据库服务，向数据库中新增条目
		itemMapper.addItem(item, user);

		// 获取item更新时间和创建时间
		Item tmpItem = itemMapper.selectOneItem(item.getItemId());
		item.setCreateTime(tmpItem.getCreateTime());
		item.setUpdateTime(tmpItem.getUpdateTime());

		// 初步设定原始资源在s3上的地址
		// 地址格式为s3.cn-north-1.amazonaws.com.cn/bucketname/qrmanager/res_IdNumber.suffix
		// 原始资源统一命名格式为res_IdNumber.suffix，例如某个新文件在数据库中的id为32，格式为mp4，那么最后的文件名为res_32.mp4
		item.setResourceUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + "res_"
				+ item.getItemId() + '.' + item.getObjectType());

		// 删除服务器暂存文件，否则会占用大量空间
		File resFile = new File(savePath, fileName);
		if (resFile.exists())
			resFile.delete();

		// 对资源重命名，精简网址，便于生成二维码
		String res_key = "res_" + item.getItemId() + '.' + item.getObjectType();

		// s3没有提供重命名机制，所以只能先复制，再删除
		AmazonS3 s3Client = s3Util.getS3Client();
		s3Client.copyObject(bucketName, keyPrefix + fileName, bucketName, keyPrefix + res_key);
		s3Client.deleteObject(bucketName, keyPrefix + fileName);

		// 获取并设置文件大小
		long objectSize = s3Client.getObjectMetadata(bucketName, keyPrefix + res_key).getContentLength();
		item.setObjectSize(objectSize);

		// 生成下载界面和预览界面地址所转化二维码的图片的地址
		// 注意，是二维码本身图片存储在s3上的地址，不是二维码所对应的编码的地址
		// 二维码有两种，一种扫描后下载，另一种是扫描后直接预览，前者存储格式为qr_res_43.jpg，后者为qr_pre_43.jpg
		String QRKeyRes = "qr_res_" + item.getItemId() + ".jpg";
		String QRKeyPre = "qr_pre_" + item.getItemId() + ".jpg";

		// 初始化二维码图片文件在本地的存储路径，例如可以为uploads/username/qr/qr_res_89.jpg
		String QRFileNameRes = prefixPath + "/qr/" + QRKeyRes;
		String QRFileNamePre = prefixPath + "/qr/" + QRKeyPre;

		File qrPathFile = new File(prefixPath + "/qr/");
		if (!qrPathFile.exists()) {
			qrPathFile.mkdirs();
		}

		// 设置二维码相关参数
		String format = "jpg";
		Hashtable<EncodeHintType, String> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		// 初始化两种二维码矩阵
		BitMatrix bitMatrixPresentation = null, bitMatrixResource = null;

		// 新建文件
		File outputFile = null;
		outputFile = new File(QRFileNamePre);

		// 初始化预览二维码的待编码的地址
		String presentation_url = "http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=" + item.getItemId();

		// 设置矩阵参数，这里只是简单设置，可以有更加复杂的配置，此处ErrorCorrectionLevel默认级别为L，容错率为7%
		bitMatrixPresentation = new MultiFormatWriter().encode(presentation_url, BarcodeFormat.QR_CODE, 300, 300,
				hints);
		QRCodeGen.writeToFile(bitMatrixPresentation, format, outputFile);

		// 初始化下载二维码的待编码的地址
		String download_url = "http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=" + item.getItemId();

		// 同上上，生成二维码
		bitMatrixResource = new MultiFormatWriter().encode(download_url, BarcodeFormat.QR_CODE, 300, 300, hints);
		outputFile = new File(QRFileNameRes);
		QRCodeGen.writeToFile(bitMatrixResource, format, outputFile);

		// 上传二维码至s3
		myUpload = tx.upload(bucketName, keyPrefix + QRKeyRes, new File(QRFileNameRes));
		myUpload.waitForCompletion();
		item.setDownloadQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyRes);

		myUpload = tx.upload(bucketName, keyPrefix + QRKeyPre, new File(QRFileNamePre));
		myUpload.waitForCompletion();
		item.setPreviewQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyPre);

		// 删除本地服务器二维码图片
		File qrFile = new File(QRFileNameRes);
		if (qrFile.exists())
			qrFile.delete();

		qrFile = new File(QRFileNamePre);
		if (qrFile.exists())
			qrFile.delete();

		// 更新数据库中的资源大小和类型和URL
		itemMapper.updateItemSizeTypeUrl(item);
	}

	/**
	 * 替换资源服务，在二维码不变动的情况下，替换所对应的实体资源
	 * 
	 * @param item
	 *            新的item，既是入参也是出参
	 * @param savePath
	 *            新替换文件的本地服务器的存储路径
	 * @param fileName
	 *            新替换文件的文件名
	 * @throws AmazonServiceException
	 *             亚马逊服务异常
	 * @throws AmazonClientException
	 *             亚马逊客户端异常
	 * @throws InterruptedException
	 *             中断异常
	 */
	public void replaceItem(Item item, String savePath, String fileName)
			throws AmazonServiceException, AmazonClientException, InterruptedException {
		// 获取旧资源的url
		Item oldItem = itemMapper.selectOneItem(item.getItemId());
		String resUrl = oldItem.getResourceUrl();
		String oldType = oldItem.getObjectType();

		// 提取bucket名称和s3上的文件名
		String[] dump = resUrl.split("/");
		String bucketName = dump[3];
		String s3FileName = resUrl.split(bucketName + '/')[1];

		// 初始化s3 client
		AmazonS3 s3Client = s3Util.getS3Client();
		TransferManager tx = s3Util.getS3Tranfer();

		// 删除旧文件
		s3Client.deleteObject(bucketName, s3FileName);

		// 上传新文件
		String keyPrefix = "qrmanager/" + item.getProjectId() + '/';
		Upload myUpload = tx.upload(bucketName, keyPrefix + fileName, new File(savePath, fileName));
		myUpload.waitForCompletion();

		// 设置新的item的名称以及对应的文件类型
		Pattern p = Pattern.compile("([\\s\\S]+)\\.([a-zA-Z0-9]+)");
		Matcher m = p.matcher(fileName);
		m.matches();
		item.setItemName(m.group(1));
		item.setObjectType(m.group(2));

		// 更新item在s3上的资源的新的url，新url和旧url相比，只有后缀名可能发生变化，例如原先的res_123.mp4变化为res_123.pdf，其余不变
		String news3FileName = s3FileName.replaceAll("\\." + oldType, "." + item.getObjectType());
		item.setResourceUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + news3FileName);

		// 将刚刚上传的文件修改到新的名字
		s3Client.copyObject(bucketName, keyPrefix + fileName, bucketName, news3FileName);
		s3Client.deleteObject(bucketName, keyPrefix + fileName);

		// 将新的item信息更新到数据库中
		itemMapper.updateItemResourceUrl(item);

		// 获取并设置新文件大小
		long objectSize = s3Client.getObjectMetadata(bucketName, news3FileName).getContentLength();
		item.setObjectSize(objectSize);

		// 删除服务器上的暂存文件
		File resFile = new File(savePath, fileName);
		if (resFile.exists())
			resFile.delete();

		// 更新数据库中的item大小和类型数据，并获取和设置最新修改时间
		itemMapper.updateItemNameSizeType(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());

	}

	/**
	 * 私有工具类，删除item对应的所有的资源，包括资源实体，二维码图片。
	 * 需要说明的是，这里并没有彻底删除所有的资源，而是将其平移到qrtrash目录下。
	 * 这样做的目的，是防止有人破解系统恶意删除数据，从而可以最大程度对数据进行恢复。
	 * 
	 * @param s3Client
	 *            s3客户端
	 * @param list
	 *            可变参数，待删除的资源列表
	 */
	private void deleteAttachedResource(AmazonS3 s3Client, String... list) {
		for (int i = 0; i < list.length; ++i) {
			if (!list[i].equals("") && list[i].indexOf("s3") != -1) {
				String[] str = list[i].split("/");
				String bucketname = str[3];
				String key = list[i].split(bucketname + '/')[1];
				s3Client.copyObject(bucketname, key, bucketname, "qrtrash/" + key);
				s3Client.deleteObject(bucketname, key);
			}
		}
	}

	/**
	 * 删除item
	 * 
	 * @param item
	 *            待删除的item
	 */
	public void deleteItem(Item item) {
		// 找到待删除的item对象
		Item oldItem = itemMapper.selectOneItem(item.getItemId());

		// 删除对应的存储在s3上的各类资源
		AmazonS3 s3Client = s3Util.getS3Client();

		deleteAttachedResource(s3Client, oldItem.getResourceUrl(), oldItem.getDownloadQRUrl(),
				oldItem.getPreviewQRUrl());

		// 删除数据库中的记录
		itemMapper.deleteItem(item);
	}

	/**
	 * 获取单个item的相关信息
	 * 
	 * @param itemId
	 *            待获取的item的id
	 * @return 查询到的item对象
	 */
	public Item checkItem(long itemId) {
		return itemMapper.selectOneItem(itemId);
	}

	/**
	 * 更新某个item
	 * 
	 * @param item
	 *            待更新的item对象，既是入参也是出参，入参用于更新数据，出参用于获取更新后的日期
	 */
	public void updateItem(Item item) {
		itemMapper.updateItemNameComment(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());
	}

	/**
	 * 新增链接类型资源，注意，链接类型背后并没有对应的实体资源文件，只有二维码文件而已。
	 * resourceUrl字段原本用于存储实体资源的url，但对于链接类型资源而言，将用来存储目标链接的http地址。
	 * 
	 * @param item
	 *            新增的item，既是入参也是出参
	 * @param user
	 *            所属用户
	 * @param prefixPath
	 *            暂存路径的公共前缀部分，参见ItemController类，一般情况下为uploads/username
	 * @throws WriterException
	 *             写异常
	 * @throws IOException
	 *             IO异常
	 * @throws AmazonServiceException
	 *             亚马逊服务异常
	 * @throws AmazonClientException
	 *             亚马逊客户端异常
	 * @throws InterruptedException
	 *             中断异常
	 */
	public void addLinkItem(Item item, User user, String prefixPath)
			throws WriterException, IOException, AmazonServiceException, AmazonClientException, InterruptedException {
		// 更新数据库，获取itemId
		itemMapper.addLinkItem(item, user);
		String bucketName = userMapper.getBucketName(user.getUserName());

		String keyPrefix = "qrmanager/" + item.getProjectId() + '/';

		// 为两种类型的二维码初始化服务器本地生成路径和文件名
		String QRKeyRes = "qr_res_" + item.getItemId() + ".jpg";
		String QRKeyPre = "qr_pre_" + item.getItemId() + ".jpg";

		String QRFileNameRes = prefixPath + "/qr/" + QRKeyRes;
		String QRFileNamePre = prefixPath + "/qr/" + QRKeyPre;

		File qrPathFile = new File(prefixPath + "/qr/");
		if (!qrPathFile.exists()) {
			qrPathFile.mkdirs();
		}

		// 初始化二维码设置信息
		String format = "jpg";
		Hashtable<EncodeHintType, String> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		// 初始化二维码矩阵
		BitMatrix bitMatrixPresentation = null, bitMatrixResource = null;
		File outputFile = null;

		// 生成二维码图片
		outputFile = new File(QRFileNamePre);
		String presentation_url = "http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=" + item.getItemId();
		bitMatrixPresentation = new MultiFormatWriter().encode(presentation_url, BarcodeFormat.QR_CODE, 300, 300,
				hints);
		QRCodeGen.writeToFile(bitMatrixPresentation, format, outputFile);

		outputFile = new File(QRFileNameRes);
		String download_url = "http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=" + item.getItemId();
		bitMatrixResource = new MultiFormatWriter().encode(download_url, BarcodeFormat.QR_CODE, 300, 300, hints);
		QRCodeGen.writeToFile(bitMatrixResource, format, outputFile);

		// 上传二维码至s3，并设置更新后的url
		TransferManager tx = s3Util.getS3Tranfer();
		Upload myUpload = tx.upload(bucketName, keyPrefix + QRKeyRes, new File(QRFileNameRes));
		myUpload.waitForCompletion();
		item.setDownloadQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyRes);

		myUpload = tx.upload(bucketName, keyPrefix + QRKeyPre, new File(QRFileNamePre));
		myUpload.waitForCompletion();
		item.setPreviewQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyPre);

		// 删除服务器二维码图片
		File qrFile = new File(QRFileNameRes);
		if (qrFile.exists())
			qrFile.delete();

		qrFile = new File(QRFileNamePre);
		if (qrFile.exists())
			qrFile.delete();

		// 将更新后的数据同步到数据库
		itemMapper.updateItemSizeTypeUrl(item);

		// 获取和设置item更新时间和创建时间
		Item tmpItem = itemMapper.selectOneItem(item.getItemId());
		item.setCreateTime(tmpItem.getCreateTime());
		item.setUpdateTime(tmpItem.getUpdateTime());
	}

	/**
	 * 更新链接类型资源相关信息，并获取更新时间
	 * 
	 * @param item
	 *            待更新的item，既是入参也是出参，入参用于更新数据，出参用于获取更新后的日期
	 */
	public void updateLinkItem(Item item) {
		itemMapper.updateLinkItem(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());
	}

	/**
	 * 获取指定时间段内不同省份读者用户访问某个item的累积次数的分布情况
	 * 
	 * @param visitStatistic
	 *            查询封装对象，包括起始日期和item的id
	 */
	public void getVisitData(VisitStatistic visitStatistic) {
		visitStatistic.setProvinceDataMap(itemMapper.getVisitData(visitStatistic));
	}

	/**
	 * 修改item的change字段，change为1表示新修改，change为0表示无修改
	 * 
	 * @param item
	 *            需要修改的item对象
	 * @param change
	 *            change的值
	 */
	public void setChanged(Item item, int change, String info) {
		itemMapper.setItemChanged(item, change, info);
	}
	
}
