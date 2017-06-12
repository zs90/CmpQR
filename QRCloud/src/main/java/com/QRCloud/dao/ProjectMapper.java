package com.QRCloud.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.QRCloud.domain.Project;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;

public interface ProjectMapper {
	List<Project> listAllProjects(@Param("userName") String userName, 
								@Param("checkStatus") String checkStatus, 
								@Param("offset") int offset,
								@Param("num") int num
								);
	
	List<Project> listEveryProject(@Param("checkStatus") String checkStatus, 
								@Param("offset") int offset,
								@Param("num") int num								
								);
	
	String getUserName(@Param("projectId") long projectId);

	int addProject(@Param("project") Project project, @Param("user") User user);

	Project getTime(@Param("projectId") long projectId);
	
	int checkEmpty(@Param("projectId") long projectId);

	int deleteProject(@Param("projectId") long projectId);
	
	int countProject(@Param("project") Project project);
	
	int updateProject(@Param("project") Project project);
	
	int updateProjectUrl(@Param("project") Project project);
	
	Project selectOneProject(@Param("projectId") long projectId);
	
	int updateProjectCheckStatus(@Param("project") Project project);
	
	List<Project> getProjectsWithUser(@Param("projectName") String projectName, @Param("userName") String userName, @Param("checkStatus") int checkStatus);
	
	List<Project> getProjects(@Param("projectName") String projectName, @Param("checkStatus") int checkStatus);

	//
	List<Map<String, Integer>> getVisitData(@Param("vs") VisitStatistic vs);
}