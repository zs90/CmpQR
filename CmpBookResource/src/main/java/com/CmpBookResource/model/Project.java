package com.CmpBookResource.model;

import java.util.List;

/**
 * project类
 * 
 * @author Shane
 * @version 1.0
 */
public class Project {
	private long projectId;// project的id
	private String projectName;// project的名称
	private String projectComment;// project的评论
	private List<Item> items;// project所包含的item对象

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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

	public String getProjectComment() {
		return projectComment;
	}

	public void setProjectComment(String projectComment) {
		this.projectComment = projectComment;
	}

}
