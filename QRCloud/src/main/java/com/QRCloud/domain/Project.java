package com.QRCloud.domain;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long projectId; 
	private String projectName;
	private long ownerId;
	private String projectComment;
	private long isbn;
	private Date createTime;
	private Date updateTime;
	private String bookName;
	private String editor;
	private int edtion;
	private String writer;
	private Date pubDate;
	private String owner;
	private int pubNum;
	private int accessThreshold;
	private String resourceUrl;
	private String previewQRUrl;
	private String downloadQRUrl;
	private int checkStatus;
	private String checkInfo;
	
	public int getPubNum(){
		return pubNum;
	}
	
	public void setPubNum(int pubNum){
		this.pubNum = pubNum;
	}
	
	public String getOwner(){
		return owner;
	}
	
	public void setOwner(String owner){
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
	public int getEdtion() {
		return edtion;
	}
	public void setEdtion(int edtion) {
		this.edtion = edtion;
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


}
