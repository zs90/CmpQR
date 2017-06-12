package com.QRCloud.domain;
import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long itemId;
	private String itemName;
	private long projectId;
	private Date createTime;
	private Date updateTime;
	private String ownerId;
	private String itemComment;
	private long objectSize;
	private String objectType;
	private int pageView;
	private String resourceUrl;
	private String previewQRUrl;
	private String downloadQRUrl;
	
	public long getObjectSize(){
		return objectSize;
	}
	
	public void setObjectSize(long objectSize){
		 this.objectSize = objectSize;
	}
	
	public String getObjectType(){
		return objectType;
	}
	
	public void setObjectType(String objectType){
		this.objectType = objectType;
	}
	
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerName) {
		this.ownerId = ownerName;
	}
	public String getItemComment() {
		return itemComment;
	}
	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}
	public int getPageView(){
		return pageView;
	}
	public void setPageView(int pageView){
		this.pageView = pageView;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
}
