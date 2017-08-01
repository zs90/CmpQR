package com.QRCloud.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 接口类UserMapper，集合了所有与User相关的数据库操作
 * 
 * @author Shane
 * @version 1.0
 */
public interface UserMapper {
	/**
	 * 获取密码和用户名匹配的行的数目
	 * 
	 * @param userName
	 *            待查询的用户名
	 * @param password
	 *            待查询的密码
	 * @return 匹配的行数
	 */
	@Select("select count(*) from users where user_name=#{userName} and password=#{password}")
	int getMatchCount(@Param("userName") String userName, @Param("password") String password);

	/**
	 * 根据用户名获取桶名
	 * 
	 * @param userName
	 *            用户名
	 * @return 属于该用户的桶名
	 */
	@Select("select bucket_name from users where user_name =#{userName}")
	String getBucketName(@Param("userName") String userName);

	/**
	 * 根据用户名获取所属用户组
	 * 
	 * @param userName
	 *            用户名
	 * @return 所属用户组
	 */
	@Select("select user_group from users where user_name =#{userName}")
	int getUserGroup(@Param("userName") String userName);
}
