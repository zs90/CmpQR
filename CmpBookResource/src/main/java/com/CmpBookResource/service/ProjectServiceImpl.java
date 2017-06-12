package com.CmpBookResource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CmpBookResource.mappers.ProjectMapper;
import com.CmpBookResource.model.Project;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	
	@Override
	public Project getProjectById(String projectId) {
		// TODO Auto-generated method stub
		return projectMapper.selectProject(projectId);
	}

}
