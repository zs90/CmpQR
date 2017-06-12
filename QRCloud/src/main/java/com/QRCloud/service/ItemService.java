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

@Service
public class ItemService {
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private AmazonS3Util s3Util;
	
	public List<Item> getItemList(long projectId){
		return itemMapper.getItemList(projectId);
	}
	
	public void uploadItem(User user, Item item, String fileName, String savePath, String prefixPath) throws WriterException, AmazonServiceException, AmazonClientException, InterruptedException, IOException{
		//将文件上传至s3
		String bucketName = userMapper.getBucketName(user.getUserName());
		
    	TransferManager tx = s3Util.getS3Tranfer();
    	Upload myUpload = null;	

    	String keyPrefix = "qrmanager/" + item.getProjectId() + '/';
    	myUpload = tx.upload(bucketName, keyPrefix + fileName, new File(savePath, fileName));
		myUpload.waitForCompletion();

		//更新数据库item部分信息，主要是获取自增的ID
		Pattern p = Pattern.compile("([\\s\\S]+)\\.([a-zA-Z0-9]+)");
		Matcher m = p.matcher(fileName);
		m.matches();
		
		item.setObjectType(m.group(2));
		item.setItemName( m.group(1));
		item.setItemComment("");
		
		itemMapper.addItem(item, user);
		
		//获取item更新时间和创建时间
		Item tmpItem = itemMapper.selectOneItem(item.getItemId());
		item.setCreateTime(tmpItem.getCreateTime());
		item.setUpdateTime(tmpItem.getUpdateTime());
		
		//生成资源原始在s3上的地址		
		item.setResourceUrl("https://s3.cn-north-1.amazonaws.com.cn/" 
				+ bucketName + "/" + keyPrefix + "res_" + item.getItemId() + '.' + item.getObjectType());
		
		//删除服务器暂存文件
		File resFile = new File(savePath, fileName);
		if(resFile.exists())
			resFile.delete();
		
		//对资源重命名，精简网址，便于生成二维码
		String res_key = "res_" + item.getItemId() + '.' + item.getObjectType();
		
		AmazonS3 s3Client = s3Util.getS3Client();
		s3Client.copyObject(bucketName, keyPrefix + fileName, bucketName, keyPrefix + res_key);
		s3Client.deleteObject(bucketName, keyPrefix + fileName);
		
		//获取文件大小
		long objectSize = s3Client.getObjectMetadata(bucketName, keyPrefix + res_key).getContentLength();
		item.setObjectSize(objectSize);		
		
		//生成下载界面和预览界面地址所转化二维码图片的地址
		String QRKeyRes = "qr_res_" + item.getItemId() + ".jpg";
		String QRKeyPre = "qr_pre_" + item.getItemId() + ".jpg";
        
		//Linux
		String QRFileNameRes = prefixPath + "/qr/" + QRKeyRes;
		String QRFileNamePre = prefixPath + "/qr/" + QRKeyPre;
        
		File qrPathFile = new File(prefixPath + "/qr/");		
		
		if(!qrPathFile.exists()){
        	qrPathFile.mkdirs();
        }
        
        String format = "jpg";
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();      
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        
        //支持jpg png gif mp4格式的预览  
        //String ot = item.getObjectType();
        //boolean needPreQR = ot.equals("jpg") || ot.equals("png") || ot.equals("gif") || ot.equals("mp4") || ot.equals("mp3") || ot.equals("pdf") || ot.equals("bmp") ;
 
    	BitMatrix bitMatrixPresentation = null, bitMatrixResource = null;
    	File outputFile = null;
    	
    	//if(needPreQR){
        	outputFile = new File(QRFileNamePre);
        	String presentation_url = "http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=" + item.getItemId();
    		bitMatrixPresentation = new MultiFormatWriter().encode(presentation_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    		QRCodeGen.writeToFile(bitMatrixPresentation, format, outputFile);
    	//}
    	        	
    	//bitMatrixResource = new MultiFormatWriter().encode(res_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    	String download_url = "http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=" + item.getItemId();
    	bitMatrixResource = new MultiFormatWriter().encode(download_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    	outputFile = new File(QRFileNameRes);
    	QRCodeGen.writeToFile(bitMatrixResource, format, outputFile);

		//上传二维码至s3
    	myUpload = tx.upload(bucketName, keyPrefix + QRKeyRes, new File(QRFileNameRes));
    	myUpload.waitForCompletion();
    	item.setDownloadQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyRes);
    	
    	//if(needPreQR){
    		myUpload = tx.upload(bucketName, keyPrefix + QRKeyPre, new File(QRFileNamePre));
    		myUpload.waitForCompletion();
    		item.setPreviewQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyPre);
    	//}
		//else//无需预览，则以空字符串填充
			//item.setPreviewQRUrl("");
		
		//删除服务器二维码图片
		File qrFile = new File(QRFileNameRes);
		if(qrFile.exists())
			qrFile.delete();
		
		//if(needPreQR){
		qrFile = new File(QRFileNamePre);
		if(qrFile.exists())
			qrFile.delete();
		//}

		//更新资源大小和类型和URL
		itemMapper.updateItemSizeTypeUrl(item);	
	}
	
	public void replaceItem(Item item, String savePath, String fileName) throws AmazonServiceException, AmazonClientException, InterruptedException{
		//获取资源url
		//String res_url = itemDAO.FindUrl(item_id).get(2);
		Item oldItem = itemMapper.selectOneItem(item.getItemId());
		String resUrl = oldItem.getResourceUrl();
		String oldType = oldItem.getObjectType();
		
		//提取bucket名称和文件名
		String[] dump = resUrl.split("/");
		String bucketName = dump[3];
		String s3FileName = resUrl.split(bucketName + '/')[1];
		
		//初始化s3 client
    	AmazonS3 s3Client = s3Util.getS3Client();   	
    	TransferManager tx = s3Util.getS3Tranfer();
		
		//删除已有的文件
		s3Client.deleteObject(bucketName, s3FileName);
		
		//上传新文件
		String keyPrefix = "qrmanager/" + item.getProjectId() + '/';
		Upload myUpload = tx.upload(bucketName, keyPrefix + fileName, new File(savePath, fileName));
		myUpload.waitForCompletion();
		
		//修改文件名
		Pattern p = Pattern.compile("([\\s\\S]+)\\.([a-zA-Z0-9]+)");
		Matcher m = p.matcher(fileName);
		m.matches();
		item.setItemName(m.group(1));
		item.setObjectType(m.group(2));
		//String itemName = m.group(1);
		//String objectType = m.group(2);
		//itemDAO.UpdateName(itemName, item_id);
		
		//更新数据库resource_s3_url字段
		String news3FileName = s3FileName.replaceAll("\\." + oldType, "." + item.getObjectType());
		item.setResourceUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + news3FileName);
		itemMapper.updateItemResourceUrl(item);
		
		//修改到新的名字
		s3Client.copyObject(bucketName, keyPrefix + fileName, bucketName, news3FileName);
		s3Client.deleteObject(bucketName, keyPrefix + fileName);
		//s3Client.setObjectAcl(bucketName, s3FileName, CannedAccessControlList.PublicRead);
		
		//获取文件大小
		long objectSize = s3Client.getObjectMetadata(bucketName, news3FileName).getContentLength();
		item.setObjectSize(objectSize);
			
		//删除服务器暂存文件
		File resFile = new File(savePath, fileName);
		if(resFile.exists())
			resFile.delete();
		
		//修改数据库，获取最新修改时间
		//result.add(itemDAO.UpdateObjectInfo(objectType, (int)objectSize, Integer.valueOf(item_id)));
		//itemDAO.UpdateObjectInfo(objectType, (int)objectSize, Integer.valueOf(item_id));
		itemMapper.updateItemNameSizeType(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());
		
		
		//result.add(itemDAO.GetTime(item_id).get(1));
		//result.add(itemName);
		//result.add(objectType);
		//result.add(objectSize);
	}
	
	private void deleteAttachedResource(AmazonS3 s3Client, String ... list){
		for(int i = 0; i < list.length; ++i){
			if(!list[i].equals("") && list[i].indexOf("s3") != -1){
				String[] str = list[i].split("/");
				String bucketname = str[3];
				String key = list[i].split(bucketname + '/')[1];
				s3Client.copyObject(bucketname, key, bucketname, "qrtrash/" + key);
				s3Client.deleteObject(bucketname, key);
			}
		}		
	}
	
	public void deleteItem(Item item){		
		//list = itemDAO.FindUrl(item_id);
		Item oldItem = itemMapper.selectOneItem(item.getItemId());
		
		AmazonS3 s3Client = s3Util.getS3Client();
		
		deleteAttachedResource(s3Client, oldItem.getResourceUrl(), oldItem.getDownloadQRUrl(), oldItem.getPreviewQRUrl());

		//itemDAO.Del(item_id);
		itemMapper.deleteItem(item);
	}
	
	public Item checkItem(long itemId){
		return itemMapper.selectOneItem(itemId);
	}
	
	public void updateItem(Item item){
		itemMapper.updateItemNameComment(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());
	}
	
	public void addLinkItem(Item item, User user, String prefixPath) throws WriterException, IOException, AmazonServiceException, AmazonClientException, InterruptedException{
		itemMapper.addLinkItem(item, user);
		String bucketName = userMapper.getBucketName(user.getUserName());
		
		String keyPrefix = "qrmanager/" + item.getProjectId() + '/';
		
		//生成下载界面和预览界面地址所转化二维码图片的地址
		String QRKeyRes = "qr_res_" + item.getItemId() + ".jpg";
		String QRKeyPre = "qr_pre_" + item.getItemId() + ".jpg";
        
		//Linux
		String QRFileNameRes = prefixPath + "/qr/" + QRKeyRes;
		String QRFileNamePre = prefixPath + "/qr/" + QRKeyPre;
        
		File qrPathFile = new File(prefixPath + "/qr/");		
		
		if(!qrPathFile.exists()){
        	qrPathFile.mkdirs();
        }
        
        String format = "jpg";
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();      
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        
        //支持jpg png gif mp4格式的预览  
        //String ot = item.getObjectType();
        //boolean needPreQR = ot.equals("jpg") || ot.equals("png") || ot.equals("gif") || ot.equals("mp4") || ot.equals("mp3") || ot.equals("pdf") || ot.equals("bmp") ;
 
    	BitMatrix bitMatrixPresentation = null, bitMatrixResource = null;
    	File outputFile = null;
    	
    	//if(needPreQR){
        	outputFile = new File(QRFileNamePre);
        	String presentation_url = "http://qr.cmpedu.com/CmpBookResource/show_resource.do?id=" + item.getItemId();
    		bitMatrixPresentation = new MultiFormatWriter().encode(presentation_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    		QRCodeGen.writeToFile(bitMatrixPresentation, format, outputFile);
    	//}
    	        	
    	//bitMatrixResource = new MultiFormatWriter().encode(res_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    	String download_url = "http://qr.cmpedu.com/CmpBookResource/download_resource.do?id=" + item.getItemId();
    	bitMatrixResource = new MultiFormatWriter().encode(download_url, BarcodeFormat.QR_CODE, 300, 300, hints);
    	outputFile = new File(QRFileNameRes);
    	QRCodeGen.writeToFile(bitMatrixResource, format, outputFile);

		//上传二维码至s3  	
    	TransferManager tx = s3Util.getS3Tranfer();
    	Upload myUpload = tx.upload(bucketName, keyPrefix + QRKeyRes, new File(QRFileNameRes));
    	myUpload.waitForCompletion();
    	item.setDownloadQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyRes);
    	
    	//if(needPreQR){
    		myUpload = tx.upload(bucketName, keyPrefix + QRKeyPre, new File(QRFileNamePre));
    		myUpload.waitForCompletion();
    		item.setPreviewQRUrl("https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/" + keyPrefix + QRKeyPre);
    	//}
		//else//无需预览，则以空字符串填充
			//item.setPreviewQRUrl("");
		
		//删除服务器二维码图片
		File qrFile = new File(QRFileNameRes);
		if(qrFile.exists())
			qrFile.delete();
		
		//if(needPreQR){
		qrFile = new File(QRFileNamePre);
		if(qrFile.exists())
			qrFile.delete();
		//}
		
		itemMapper.updateItemSizeTypeUrl(item);	
		
		//获取item更新时间和创建时间
		Item tmpItem = itemMapper.selectOneItem(item.getItemId());
		item.setCreateTime(tmpItem.getCreateTime());
		item.setUpdateTime(tmpItem.getUpdateTime());		
	}
	
	public void updateLinkItem(Item item){
		itemMapper.updateLinkItem(item);
		item.setUpdateTime(itemMapper.selectOneItem(item.getItemId()).getUpdateTime());
	}
	
	public void getVisitData(VisitStatistic visitStatistic){
		visitStatistic.setProvinceDataMap(itemMapper.getVisitData(visitStatistic));
	}
}
