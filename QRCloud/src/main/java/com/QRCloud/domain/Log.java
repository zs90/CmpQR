package com.QRCloud.domain;

import java.util.Date;

/**
 * Log类定义，对应于具体的日志对象，日志用于记录审核状态改变时的相关信息。
 * 主要包含“主谓宾”的逻辑结构，例如，A用户对a项目提出了审核请求，那么，主就是A，谓就是提出申请，宾就是a
 * 
 * @author Shane
 * @version 1.0
 */
public class Log {
	private String logId; // 日志的ID
	private long subject; // 日志中的发起审核状态改变的主体
	private String subjectName;// 主体名称
	private int predicate;// 谓语，也是具体的改变了审核状态的操作，例如“提出申请”，“审核通过”等等
	private long object; // 客体，也就是被改变审核状态的对象，也是一个ProjectId
	private Date logTime;// 日志生成的时间
	private String logInfo;// 改变审核状态时，附加的备注和说明

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
