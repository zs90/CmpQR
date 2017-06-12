package com.QRCloud.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
	@Select("select count(*) from users where user_name=#{userName} and password=#{password}")
	int getMatchCount(@Param("userName") String userName, @Param("password") String password);
	
	@Select("select bucket_name from users where user_name =#{userName}")
	String getBucketName(@Param("userName") String userName);
	
	@Select("select user_group from users where user_name =#{userName}")
	int getUserGroup(@Param("userName") String userName);
}
