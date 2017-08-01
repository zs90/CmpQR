package com.QRCloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.LogMapper;
import com.QRCloud.domain.Log;

/**
 * 日志记录服务层，主要用于记录关于审核状态的一些操作记录
 * 
 * @author Shane
 * @version 1.0
 */
@Service
public class LogService {
	@Autowired
	LogMapper logMapper;

	/**
	 * 插入一条日志，当某个project的审核状态发生改变时，就会触发新增日志的操作
	 * 
	 * @param userName
	 *            当前操作的用户
	 * @param action
	 *            操作的行为，分为："未审核", "提交审核", "通过审核", "审核未通过", "提交修改"。对应的值为0,1,2,3,4
	 * @param projectId
	 *            操作的对象，也是project的id
	 * @param checkInfo
	 *            审核状态发生改变时的一些备注说明
	 */
	public void insertLog(String userName, int action, long projectId, String checkInfo) {
		Log log = new Log();

		log.setPredicate(action);
		log.setObject(projectId);
		log.setLogInfo(checkInfo);

		logMapper.insertLog(log, userName);
	}

	/**
	 * 获取日志列表信息
	 * 
	 * @param projectId
	 *            需要获取的日志的project的id
	 * @return 封装了log对象的列表
	 */
	public List<Log> getLogInfo(long projectId) {
		return logMapper.selectLogs(projectId);
	}
}
