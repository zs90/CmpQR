package com.QRCloud.service;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.ItemMapper;
import com.QRCloud.dao.ProjectMapper;
import com.QRCloud.dao.UserMapper;
import com.QRCloud.domain.Item;
import com.QRCloud.domain.Project;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;
import com.QRCloud.util.AmazonS3Util;
import com.QRCloud.util.QRCodeGen;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * project服务层
 * 
 * @author Shane
 * @version 1.0
 */
@Service
public class ProjectService {
	@Autowired
	private ProjectMapper projectMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AmazonS3Util s3Util;

	@Autowired
	private LogService logService;

	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 获取project的列表
	 * 
	 * @param userName
	 *            当前用户
	 * @param checkStatus
	 *            审核状态
	 * @param userGroup
	 *            用户组
	 * @param page
	 *            当前页
	 * @param pageLen
	 *            页长
	 * @return 封装了Project对象的列表
	 */
	public List<Project> getProjectList(String userName, String checkStatus, int userGroup, int page, int pageLen) {
		if (userGroup == 0)
			return projectMapper.listAllProjects(userName, checkStatus, (page - 1) * pageLen, pageLen);
		else
			return projectMapper.listEveryProject(checkStatus, (page - 1) * pageLen, pageLen);
	}

	/**
	 * 获取username
	 * 
	 * @param projectId
	 *            project的id
	 * @return 用户名
	 */
	public String getUserName(long projectId) {
		return projectMapper.getUserName(projectId);
	}

	/**
	 * 新增project，主要包括生产并存储二维码，更新对应数据库
	 * 
	 * @param project
	 *            待新增的project对象
	 * @param user
	 *            所属user对象
	 * @param servletContextPath
	 *            serlvet上下文
	 * @return 新获取的project_id
	 * @throws WriterException
	 * @throws IOException
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public long addProject(Project project, User user, String servletContextPath)
			throws WriterException, IOException, AmazonServiceException, AmazonClientException, InterruptedException {
		// 新增project，获取projectId
		projectMapper.addProject(project, user);

		String projectId = String.valueOf(project.getProjectId());
		String userName = user.getUserName();

		// 初始化二维码编码设置
		String format = "jpg";
		Hashtable<EncodeHintType, String> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

		// 预览页面url和下载页面url
		// 对于每个project而言，一共有这两类project
		// 一类是预览，扫描后，会出现一个包含了该project所有item的一个列表，点击列表项可以预览item
		// 另一类是下载，扫描后，会出现一个包含了该project所有item的一个列表，每一个列表项提供了两种选择：预览和下载。预览和上述item页面一样，下载则是另一个item下载页面。
		String showUrl = "http://qr.cmpedu.com/CmpBookResource/show_project.do?pid=" + projectId;
		String downUrl = "http://qr.cmpedu.com/CmpBookResource/down_project.do?pid=" + projectId;

		// 本地服务器存储路径
		String prefixPath = servletContextPath + "uploads/" + userName;

		// 二维码文件名，遵守一定的命名规则，例如预览二维码是proj_show_123.jpg，下载二维码为proj_down_123.jpg
		String QRShowKey = "proj_show_" + projectId + ".jpg";
		String QRDownKey = "proj_down_" + projectId + ".jpg";

		// 二维码文件完整路径名
		String QRFileNameShow = prefixPath + "/qr/" + QRShowKey;
		String QRFileNameDown = prefixPath + "/qr/" + QRDownKey;

		// 检查文件夹是否创建
		File qrPathFile = new File(prefixPath + "/qr/");
		if (!qrPathFile.exists()) {
			qrPathFile.mkdirs();
		}

		// 创建二维码矩阵，并将其写入文件
		BitMatrix bitMatrix = null;
		File outputFile = null;

		bitMatrix = new MultiFormatWriter().encode(showUrl, BarcodeFormat.QR_CODE, 300, 300, hints);
		outputFile = new File(QRFileNameShow);
		QRCodeGen.writeToFile(bitMatrix, format, outputFile);

		bitMatrix = new MultiFormatWriter().encode(downUrl, BarcodeFormat.QR_CODE, 300, 300, hints);
		outputFile = new File(QRFileNameDown);
		QRCodeGen.writeToFile(bitMatrix, format, outputFile);

		// 获取bucketname
		String bucketName = userMapper.getBucketName(userName);

		// 初始化上传对象
		TransferManager tx = s3Util.getS3Tranfer();
		Upload myUpload = null;

		// 上传二维码至s3，采用同步而非异步
		myUpload = tx.upload(bucketName, "qrmanager/" + projectId + '/' + QRShowKey, new File(QRFileNameShow));
		myUpload.waitForCompletion();
		myUpload = tx.upload(bucketName, "qrmanager/" + projectId + '/' + QRDownKey, new File(QRFileNameDown));
		myUpload.waitForCompletion();

		// 设置新的project信息
		String s3QRShowUrl = "https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/qrmanager/" + projectId + '/'
				+ QRShowKey;
		String s3QRDownUrl = "https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/qrmanager/" + projectId + '/'
				+ QRDownKey;

		project.setPreviewQRUrl(s3QRShowUrl);
		project.setDownloadQRUrl(s3QRDownUrl);

		// 将新的project信息更新至数据库
		projectMapper.updateProjectUrl(project);

		// 删除服务器本地暂存的二维码图片文件
		File qrFile = new File(QRFileNameShow);
		if (qrFile.exists())
			qrFile.delete();

		qrFile = new File(QRFileNameDown);
		if (qrFile.exists())
			qrFile.delete();

		// 返回projectId
		return project.getProjectId();
	}

	/**
	 * 获取project的时间信息
	 * 
	 * @param project
	 *            获取时间信息的project对象
	 */
	public void setProjectTime(Project project) {
		Project projectTime = projectMapper.getTime(project.getProjectId());

		project.setCreateTime(projectTime.getCreateTime());
		project.setUpdateTime(projectTime.getUpdateTime());
	}

	/**
	 * 删除project
	 * 
	 * @param projectId
	 *            待删除的project的projectID
	 * @param userName
	 *            当前操作用户
	 * @return 删除结果，如果project还包含有item（不为空），则返回-1，删除失败。删除成功返回1。
	 */
	public int deleteProject(long projectId, String userName) {
		// 检查project下属item数量
		int itemNumber = projectMapper.checkEmpty(projectId);

		// 如果project还有item，则返回-1
		if (itemNumber > 0)
			return -1;

		// 删除s3上的存储文件
		AmazonS3 s3Client = s3Util.getS3Client();
		String bucketName = userMapper.getBucketName(userName);

		String projectStr = String.valueOf(projectId);
		s3Client.deleteObject(bucketName, "qrmanager/" + projectStr + "/proj_show_" + projectStr + ".jpg");
		s3Client.deleteObject(bucketName, "qrmanager/" + projectStr + "/proj_down_" + projectStr + ".jpg");

		// 如果project中没有item，则从数据库中将其删除，并返回成功值，此处应该为1
		return projectMapper.deleteProject(projectId);
	}

	/**
	 * 更新project
	 * 
	 * @param project
	 *            待更新的project
	 * @return 更新结果
	 */
	public int updateProject(Project project) {
		// 如果名称重复，返回2
		if (projectMapper.countProject(project) == 1)
			return 2;

		// 不重复则更新条目，并返回1
		return projectMapper.updateProject(project);
	}

	/**
	 * 获取单条project记录
	 * 
	 * @param projecId
	 *            待获取的projectId
	 * @return 获取到的project对象
	 */
	public Project getOneProject(long projecId) {
		return projectMapper.selectOneProject(projecId);
	}

	/**
	 * 批量下载某个project下的所有的item的二维码文件
	 * 
	 * @param projectId
	 *            project的id
	 * @param srcPath
	 *            存储路径
	 */
	public void getProjectQR(long projectId, String srcPath) {
		// 获取item列表
		List<Item> itemList = itemMapper.getItemList(projectId);

		// 初始化本地存储路径
		File saveDownloadPathFile = new File(srcPath + "/downloads");
		File savePreviewPathFile = new File(srcPath + "/previews");

		if (!saveDownloadPathFile.exists()) {
			saveDownloadPathFile.mkdirs();
		}

		if (!savePreviewPathFile.exists()) {
			savePreviewPathFile.mkdirs();
		}

		// 依次从s3上下载二维码图片文件到本地
		String itemName, itemPreviewUrl, itemDownloadUrl;

		AmazonS3 s3Client = s3Util.getS3Client();
		for (int i = 0; i < itemList.size(); i++) {
			// 下载到本地的文件名应该和数据库中的itemname保持一致
			itemName = itemList.get(i).getItemName();
			itemPreviewUrl = itemList.get(i).getPreviewQRUrl();
			itemDownloadUrl = itemList.get(i).getDownloadQRUrl();

			// 考虑兼容性，之前的系统会出现字段缺失的情况，所以需要进行一下判断
			if (itemPreviewUrl != null && !itemPreviewUrl.isEmpty()) {
				String[] dump = itemPreviewUrl.split("/");
				String bucketName = dump[3];
				String keyName = itemPreviewUrl.split(bucketName + '/')[1];
				s3Client.getObject(new GetObjectRequest(bucketName, keyName),
						new File(srcPath + "/previews/" + itemName + ".jpg"));
			}
			if (itemDownloadUrl != null && !itemDownloadUrl.isEmpty()) {
				String[] dump = itemDownloadUrl.split("/");
				String bucketName = dump[3];
				String keyName = itemDownloadUrl.split(bucketName + '/')[1];
				s3Client.getObject(new GetObjectRequest(bucketName, keyName),
						new File(srcPath + "/downloads/" + itemName + ".jpg"));
			}
		}
	}

	/**
	 * 设置project当前的审核状态
	 * 
	 * @param checkStatus
	 *            审核状态标记码
	 * @param projectId
	 *            project的id
	 * @param userName
	 *            用户名
	 * @param checkInfo
	 *            审核备注信息
	 * @see com.QRCloud.domain.Project
	 */
	public void setCheckStatus(int checkStatus, long projectId, String userName, String checkInfo) {
		Project project = new Project();
		project.setCheckStatus(checkStatus);
		project.setProjectId(projectId);

		projectMapper.updateProjectCheckStatus(project);
		logService.insertLog(userName, checkStatus, projectId, checkInfo);
	}

	/**
	 * 搜索指定条件的project，这里会区分不同的用户组，用户组为0表示为分社用户，用户组为1则是审核用户
	 * 
	 * @param projectName
	 *            project名称
	 * @param userGroup
	 *            用户组
	 * @param userName
	 *            用户名
	 * @param checkStatus
	 *            审核状态
	 * @param offset
	 *            页面偏移量
	 * @param len
	 *            页长
	 * @return 封装了project对象的列表
	 */
	public List<Project> searchProjects(String candidateNum, int userGroup, String userName, String checkStatus,
			int offset, int len) {
		if (userGroup == 0)
			return projectMapper.getProjectsWithUser(candidateNum, userName, checkStatus, offset, len);
		else
			return projectMapper.getProjects(candidateNum, checkStatus, offset, len);
	}

	/**
	 * 获取指定时间段内不同省份读者用户访问某个project的累积次数的分布情况
	 * 
	 * @param visitStatistic
	 *            查询条件对象
	 */
	public void getVisitData(VisitStatistic visitStatistic) {
		visitStatistic.setProvinceDataMap(projectMapper.getVisitData(visitStatistic));
	}

	/**
	 * 获取project的长度
	 * 
	 * @param userName
	 *            用户名
	 * @param checkStatus
	 *            审核状态
	 * @param userGroup
	 *            用户组
	 * @return 长度值
	 */
	public int getProjectLength(String userName, String checkStatus, int userGroup) {
		if (userGroup == 0)
			return projectMapper.countProjectLengthWithUser(checkStatus, userName);
		else
			return projectMapper.countProjectLength(checkStatus);
	}

	/**
	 * 获取搜索结果的project的长度
	 * 
	 * @param userName
	 *            用户名
	 * @param checkStatus
	 *            审核状态
	 * @param userGroup
	 *            用户组
	 * @param projectName
	 *            project名称
	 * @return 长度值
	 */
	public int getSearchedProjectLength(String userName, String checkStatus, int userGroup, String candidateNum) {
		if (userGroup == 0)
			return projectMapper.countSearchedLengthWithUser(candidateNum, userName, checkStatus);
		else
			return projectMapper.countSearchedLength(candidateNum, checkStatus);
	}
}
