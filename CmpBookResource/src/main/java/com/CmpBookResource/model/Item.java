package com.CmpBookResource.model;

/**
 * item类
 * 
 * @author Shane
 * @version 1.0
 */
public class Item {
	private long itemId;// item的id
	private String itemName;// item的名称
	private String itemComment;// item的评论
	private String itemUrl;// item对应的实体资源的s3的url，或者是item对应的链接url
	private String itemType;// item的类型

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemComment() {
		return itemComment;
	}

	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

}
