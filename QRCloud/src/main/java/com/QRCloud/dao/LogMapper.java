package com.QRCloud.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.QRCloud.domain.Log;

/**
 * LogMapper接口类，集合了审核模块中与日志记录相关的数据库操作
 * 
 * @author Shane
 * @version 1.0
 */
public interface LogMapper {
	/**
	 * 插入新的日志对象
	 * 
	 * @param log
	 *            日志对象
	 * @param userName
	 *            用于提取user_id字段插入到新的Log行中
	 */
	public void insertLog(@Param("log") Log log, @Param("userName") String userName);

	/**
	 * 根据ProjectId选择最近20条审核日志记录
	 * 
	 * @param projectId
	 *            需要查询的项目Id
	 * @return 包含了Log对象的日志列表
	 */
	public List<Log> selectLogs(@Param("projectId") long projectId);
}
