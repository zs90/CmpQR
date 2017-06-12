package com.QRCloud.service;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletContext;

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
	
	public List<Project> getProjectList(String userName, String checkStatus, int userGroup, int page, int pageLen){
		if(userGroup == 0)
			return projectMapper.listAllProjects(userName, checkStatus, (page-1)*pageLen, pageLen);
		else 
			return projectMapper.listEveryProject(checkStatus, (page-1)*pageLen, pageLen);
	}
	
	public String getUserName(long projectId){
		return projectMapper.getUserName(projectId);
	}
	
	public long addProject(Project project, User user, String servletContextPath) throws WriterException, IOException, AmazonServiceException, AmazonClientException, InterruptedException {		
		//添加项目信息
		projectMapper.addProject(project, user);
		
		String projectId = String.valueOf(project.getProjectId());
		String userName = user.getUserName();
		
        String format = "jpg";
        Hashtable<EncodeHintType, String> hints = new Hashtable<>();
        
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        String showUrl = "http://qr.cmpedu.com/CmpBookResource/show_project.do?pid=" + projectId;
        String downUrl = "http://qr.cmpedu.com/CmpBookResource/down_project.do?pid=" + projectId;
        
        String prefixPath = servletContextPath + "uploads/" + userName;
        
        String QRShowKey = "proj_show_" + projectId + ".jpg";
        String QRDownKey = "proj_down_" + projectId + ".jpg";
        
        String QRFileNameShow = prefixPath + "/qr/" + QRShowKey;
        String QRFileNameDown = prefixPath + "/qr/" + QRDownKey;
        
		File qrPathFile = new File(prefixPath + "/qr/");		
		
		if(!qrPathFile.exists()){
        	qrPathFile.mkdirs();
        }   
		
    	BitMatrix bitMatrix = null;
    	File outputFile = null;
	
    	bitMatrix = new MultiFormatWriter().encode(showUrl, BarcodeFormat.QR_CODE, 300, 300, hints);
    	outputFile = new File(QRFileNameShow);
    	QRCodeGen.writeToFile(bitMatrix, format, outputFile);

    	bitMatrix = new MultiFormatWriter().encode(downUrl, BarcodeFormat.QR_CODE, 300, 300, hints);
    	outputFile = new File(QRFileNameDown);
    	QRCodeGen.writeToFile(bitMatrix, format, outputFile);

		//获取bucketname
    	String bucketName = userMapper.getBucketName(userName);
    	
    	TransferManager tx = s3Util.getS3Tranfer();
    	Upload myUpload = null;
		
    	//上传二维码至s3
    	myUpload = tx.upload(bucketName, "qrmanager/" + projectId + '/' + QRShowKey, new File(QRFileNameShow));
    	myUpload.waitForCompletion();        	
    	myUpload = tx.upload(bucketName, "qrmanager/" + projectId + '/' + QRDownKey, new File(QRFileNameDown));
    	myUpload.waitForCompletion();
        
		//更新数据库字段
		String s3QRShowUrl = "https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/qrmanager/" + projectId + '/' + QRShowKey;
		String s3QRDownUrl = "https://s3.cn-north-1.amazonaws.com.cn/" + bucketName + "/qrmanager/" + projectId + '/' + QRDownKey;
		
		project.setPreviewQRUrl(s3QRShowUrl);
		project.setDownloadQRUrl(s3QRDownUrl);
		
		projectMapper.updateProjectUrl(project);
		
		//删除服务器二维码图片
		File qrFile = new File(QRFileNameShow);
		if(qrFile.exists())
			qrFile.delete();
			
		qrFile = new File(QRFileNameDown);
		if(qrFile.exists()){
			qrFile.delete();
		}	
		
		return project.getProjectId();
	}
	
	public void setProjectTime(Project project){
		Project projectTime = projectMapper.getTime(project.getProjectId());
		
		project.setCreateTime(projectTime.getCreateTime());
		project.setUpdateTime(projectTime.getUpdateTime());
	}
	
	
	public int deleteProject(long projectId, String userName){
		int itemNumber = projectMapper.checkEmpty(projectId);
		//如果还有项目还有资源，则返回-1
		if ( itemNumber > 0)
			return -1;
		
		AmazonS3 s3Client = s3Util.getS3Client();
		String bucketName = userMapper.getBucketName(userName);
	
		String projectStr = String.valueOf(projectId);
		s3Client.deleteObject(bucketName, "qrmanager/" + projectStr + "/proj_show_" + projectStr + ".jpg");
		s3Client.deleteObject(bucketName, "qrmanager/" + projectStr + "/proj_down_" + projectStr + ".jpg");
		
		//如果没有资源，则删除后返回成功值，此处应该为1
		return projectMapper.deleteProject(projectId);
	}
	
	public int updateProject(Project project){
		//如果项目名称重复
		if(projectMapper.countProject(project) == 1)
			return 2;
		
		//不重复则返回1
		return projectMapper.updateProject(project);
	}
	
	public Project getOneProject(long projecId){
		return projectMapper.selectOneProject(projecId);
	}
	
	public void getProjectQR(long projectId, String srcPath){
		List<Item> itemList = itemMapper.getItemList(projectId);
		
        File savePathFile = new File(srcPath);
        if(!savePathFile.exists()){
        	savePathFile.mkdirs();
        }
        		
        //ready to fetch qr pics
		String itemName, itemUrl;

		AmazonS3 s3Client = s3Util.getS3Client();
		for(int i = 0; i < itemList.size(); i++){
			itemName = itemList.get(i).getItemName();
			itemUrl = itemList.get(i).getPreviewQRUrl();
			
			//extract bucket and key
			if(!itemUrl.isEmpty() && itemUrl != null){
				String[] dump = itemUrl.split("/");
				String bucketName = dump[3];
				String keyName = itemUrl.split(bucketName + '/')[1];
				s3Client.getObject(new GetObjectRequest(bucketName, keyName), new File(srcPath + "/" + itemName + ".jpg"));
			}
		}			
	}
	
	public void setCheckStatus(int checkStatus, long projectId, String userName, String checkInfo){
		Project project = new Project();
		project.setCheckStatus(checkStatus);
		project.setProjectId(projectId);
		
		projectMapper.updateProjectCheckStatus(project);
		logService.insertLog(userName, checkStatus, projectId, checkInfo);
	}
	
	public List<Project> getProjects(String projectName, int userGroup, String userName, int checkStatus){
		if(userGroup == 0)
			return projectMapper.getProjectsWithUser(projectName, userName, checkStatus);
		else
			return projectMapper.getProjects(projectName, checkStatus);
	}
	
	public void getVisitData(VisitStatistic visitStatistic){
		visitStatistic.setProvinceDataMap(projectMapper.getVisitData(visitStatistic));
	}
	
}
