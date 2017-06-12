package com.QRCloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.QRCloud.domain.Log;

public interface LogMapper {
	public void insertLog(@Param("log") Log log, @Param("userName") String userName);

	public List<Log> selectLogs(@Param("projectId") long projectId);
}
