package com.CmpBookResource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CmpBookResource.mappers.ProjectMapper;
import com.CmpBookResource.model.Project;

/**
 * projectService服务层实现类
 * @author Shane
 * @version 1.0
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	/**
	 * 根据project的Id获取对应的project对象
	 */
	@Override
	public Project getProjectById(String projectId) {
		// TODO Auto-generated method stub
		return projectMapper.selectProject(projectId);
	}

}
