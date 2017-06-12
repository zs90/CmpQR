package com.QRCloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.DaoUtil;
import com.QRCloud.dao.UserMapper;

@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;
	
	public boolean hasMatchUser(String userName, String password){
		int matchCount = userMapper.getMatchCount(userName, DaoUtil.MD5Encode(password));
		return matchCount > 0;
	}
	
	public String getBucketName(String userName){
		return userMapper.getBucketName(userName);
	}
	
	public int getUserGroup(String userName){
		return userMapper.getUserGroup(userName);
	}
}
