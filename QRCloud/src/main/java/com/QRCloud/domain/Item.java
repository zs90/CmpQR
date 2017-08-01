package com.QRCloud.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Item类定义，对应于每一个具体的资源
 * 
 * @author Shane
 * @version 1.0
 */
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	private long itemId;// item的id
	private String itemName;// item的名称
	private long projectId;// item所属project的Id
	private Date createTime;// item创建时间
	private Date updateTime;// item更新的时间
	private String ownerId;// item所属的用户的id
	private String itemComment;// item的备注和评论
	private long objectSize;// item所对应的资源的大小
	private String objectType;// item所对应的资源的类型
	private int pageView;// item的访问数，即PV
	private String resourceUrl;// item所对应的实体资源在s3上的存储url。但对于链接类型资源而言，将用来存储目标链接的http地址
	private String previewQRUrl;// item所对应的实体资源的预览二维码在s3上的存储url
	private String downloadQRUrl;// item所对应的实体资源的下载二维码在s3上的存储url
	private int changed;// item是否最近更新过
	private String checkInfo; // item的审核信息
	
	public String getCheckInfo() {
		return checkInfo;
	}

	public void setCheckInfo(String checkInfo) {
		this.checkInfo = checkInfo;
	}

	public long getObjectSize() {
		return objectSize;
	}

	public void setObjectSize(long objectSize) {
		this.objectSize = objectSize;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
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

	public int getPageView() {
		return pageView;
	}

	public void setPageView(int pageView) {
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

	public int getChanged() {
		return changed;
	}

	public void setChanged(int changed) {
		this.changed = changed;
	}

}
