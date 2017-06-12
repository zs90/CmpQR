package com.QRCloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.QRCloud.dao.LogMapper;
import com.QRCloud.domain.Log;

@Service
public class LogService {
	@Autowired
	LogMapper logMapper;
	
	public void insertLog(String userName, int action, long projectId, String checkInfo){
		Log log = new Log();
		
		log.setPredicate(action);
		log.setObject(projectId);
		log.setLogInfo(checkInfo);
	
		logMapper.insertLog(log, userName);
	}
	
	public List<Log> getLogInfo(long projectId){
		return logMapper.selectLogs(projectId);
	}
}
