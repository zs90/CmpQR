package com.QRCloud.domain;

import java.io.Serializable;

/**
 * 用户类定义，对应于每一个平台的用户，用户大致分为分社用户和审核用户
 * 
 * @author Shane
 * @version 1.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private long user_id; // 用户ID
	private String user_name; // 用户名
	private long depart_id; // 部门ID
	private String depart_name; // 部门名
	private String password; // 密码
	private long bucket_id; // 桶Id
	private String bucket_name; // 桶名
	private int user_group; // 用户组

	public Long getUserId() {
		return user_id;
	}

	public void setUserId(long id) {
		this.user_id = id;
	}

	public String getUserName() {
		return user_name;
	}

	public void setUserName(String name) {
		this.user_name = name;
	}

	public Long getDepartId() {
		return depart_id;
	}

	public void setDepartId(long id) {
		this.depart_id = id;
	}

	public String getDepartName() {
		return depart_name;
	}

	public void setDepartName(String name) {
		this.depart_name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pw) {
		this.password = pw;
	}

	public Long getBucketId() {
		return bucket_id;
	}

	public void setBucketId(long id) {
		this.bucket_id = id;
	}

	public String getBucketName() {
		return bucket_name;
	}

	public void setBucketName(String name) {
		this.bucket_name = name;
	}

	public int getUser_group() {
		return user_group;
	}

	public void setUser_group(int user_group) {
		this.user_group = user_group;
	}

}
