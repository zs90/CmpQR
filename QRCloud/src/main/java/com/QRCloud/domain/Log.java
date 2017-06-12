package com.QRCloud.domain;

import java.util.Date;

public class Log {
	private String logId;
	private long subject;
	private String subjectName;
	private int predicate;
	private long object;
	private Date logTime;
	private String logInfo;
	
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public long getSubject() {
		return subject;
	}
	public void setSubject(long subject) {
		this.subject = subject;
	}
	public int getPredicate() {
		return predicate;
	}
	public void setPredicate(int predicate) {
		this.predicate = predicate;
	}
	public long getObject() {
		return object;
	}
	public void setObject(long object) {
		this.object = object;
	}
	public Date getLogTime() {
		return logTime;
	}
	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getLogInfo() {
		return logInfo;
	}
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}	
}
