package com.QRCloud.domain;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private long user_id;
	private String user_name;
	private long depart_id;
	private String depart_name;
	private String password;
	private long bucket_id;
	private String bucket_name;
	private int user_group;
	
	public Long getUserId(){
		return user_id;
	}
	
	public void setUserId(long id){
		this.user_id = id;
	}	
	
	public String getUserName(){
		return user_name;
	}
	
	public void setUserName(String name){
		this.user_name = name;
	}
	
	public Long getDepartId(){
		return depart_id;
	}
	
	public void setDepartId(long id){
		this.depart_id = id;
	}
	
	public String getDepartName(){
		return depart_name;
	}
	
	public void setDepartName(String name){
		this.depart_name = name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String pw){
		this.password = pw;
	}
	
	public Long getBucketId(){
		return bucket_id;
	}
	
	public void setBucketId(long id){
		this.bucket_id = id;
	}
	
	public String getBucketName(){
		return bucket_name;
	}
	
	public void setBucketName(String name){
		this.bucket_name = name;
	}

	public int getUser_group() {
		return user_group;
	}

	public void setUser_group(int user_group) {
		this.user_group = user_group;
	}
	
}
