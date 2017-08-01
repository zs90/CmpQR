package com.CmpBookResource.mappers;

import com.CmpBookResource.model.Project;

/**
 * projectMapper数据库访问接口类
 * 
 * @author Shane
 * @version 1.0
 */
public interface ProjectMapper {
	/**
	 * 根据project的id查询对应的project
	 * 
	 * @param pid
	 *            project的id
	 * @return project对象
	 */
	Project selectProject(String pid);
}
