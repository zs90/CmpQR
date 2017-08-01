package com.QRCloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.DaoUtil;
import com.QRCloud.dao.UserMapper;

/**
 * 用户服务层
 * 
 * @author Shane
 * @version 1.0
 */
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	/**
	 * 检查用户名和密码是否匹配
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @return 若匹配，返回true，否则返回false
	 */
	public boolean hasMatchUser(String userName, String password) {
		int matchCount = userMapper.getMatchCount(userName, DaoUtil.MD5Encode(password));
		return matchCount > 0;
	}

	/**
	 * 根据用户名，返回该用户所分配的桶的名称
	 * 
	 * @param userName
	 *            用户名
	 * @return 桶名
	 */
	public String getBucketName(String userName) {
		return userMapper.getBucketName(userName);
	}

	/**
	 * 查询该用户所属用户组
	 * 
	 * @param userName
	 *            用户名
	 * @return 如果属于分社用户，返回0，属于审核用户，返回1
	 */
	public int getUserGroup(String userName) {
		return userMapper.getUserGroup(userName);
	}
}
