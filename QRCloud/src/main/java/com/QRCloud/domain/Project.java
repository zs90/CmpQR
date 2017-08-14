package com.QRCloud.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Project类定义，对应于每一个项目，也就是每一本书，每一个项目可以包含若干个Item（资源）
 * 
 * @author Shane
 * @version 1.0
 */
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	private long projectId; // 项目Id
	private String projectName; // 项目名
	private long ownerId; // 所属用户ID
	private String projectComment; // 项目描述和评论
	private long isbn; // isbn
	private Date createTime; // 项目创建时间
	private Date updateTime; // 项目更新时间
	private String bookName; // 书名
	private String editor; // 编辑
	private int edition; // 版次
	private String writer; // 作者
	private String candidateNum; // 选题号
	private Date pubDate; // 发行日期
	private String owner; // 所属用户名
	private int pubNum; // 印刷数
	private int accessThreshold; // 单个用户每日访问上线
	private String resourceUrl; // 原始文件URL，这个字段暂时无效
	private String previewQRUrl; // 预览二维码在s3上的存储url，扫描后，页面将会以列表方式展现该项目下所有Item文件。注意，是二维码图片文件的存储路径，而非二维码本身解码出来的URL
	private String downloadQRUrl; // 下载二维码在s3上的存储url，扫描后，页面将会以列表方式展现该项目下所有Item文件，并提供下载和预览两种选择。注意，这是二维码图片文件的存储路径，而非二维码本身解码出来的URL
	private int checkStatus; // 审核状态：未审核，已提交审核，已通过审核，未通过审核，已提交修改。分别对应数值0-4
	private String checkInfo; // 审核状态发生变化时，所记录的备注信息
	private String firstChecker; //初审编辑
	private String secondChecker; //复审编辑
	
	public int getPubNum() {
		return pubNum;
	}

	public void setPubNum(int pubNum) {
		this.pubNum = pubNum;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getProjectComment() {
		return projectComment;
	}

	public void setProjectComment(String projectComment) {
		this.projectComment = projectComment;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {
		this.isbn = isbn;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public int getAccessThreshold() {
		return accessThreshold;
	}

	public void setAccessThreshold(int accessThreshold) {
		this.accessThreshold = accessThreshold;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getPreviewQRUrl() {
		return previewQRUrl;
	}

	public void setPreviewQRUrl(String previewQRUrl) {
		this.previewQRUrl = previewQRUrl;
	}

	public String getDownloadQRUrl() {
		return downloadQRUrl;
	}

	public void setDownloadQRUrl(String downloadQRUrl) {
		this.downloadQRUrl = downloadQRUrl;
	}

	public int getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public String getCandidateNum() {
		return candidateNum;
	}

	public void setCandidateNum(String candidateNum) {
		this.candidateNum = candidateNum;
	}
	
	public void setEdition(int edition){
		this.edition = edition;
	}
	
	public int getEdition(){
		return edition;
	}

	public String getFirstChecker() {
		return firstChecker;
	}

	public void setFirstChecker(String firstChecker) {
		this.firstChecker = firstChecker;
	}

	public String getSecondChecker() {
		return secondChecker;
	}

	public void setSecondChecker(String secondChecker) {
		this.secondChecker = secondChecker;
	}
	
}
