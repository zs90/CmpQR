package com.QRCloud.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import com.QRCloud.domain.Project;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;

/**
 * ProjectMapper类，集合了所有关于Project相关的数据库操作
 * 
 * @author Shane
 * @version 1.0
 */
public interface ProjectMapper {
	/**
	 * 根据指定的查询条件，列出所有的Project行。按时间排序，由近及远，排序时，更新时间一级优先，创建时间二级优先
	 * 
	 * @param userName
	 *            Project所属的用户
	 * @param checkStatus
	 *            Project所处的审核状态
	 * @param offset
	 *            列表偏移量
	 * @param num
	 *            本次查询的数量
	 * @return 查询到的包含Project对象的列表
	 */
	List<Project> listAllProjects(@Param("userName") String userName, @Param("checkStatus") String checkStatus,
			@Param("offset") int offset, @Param("num") int num);

	/**
	 * 获取属于某个user的project总长度
	 * 
	 * @param checkStatus
	 *            审核状态
	 * @param userName
	 *            用户名
	 * @return 长度值
	 */
	int countProjectLengthWithUser(@Param("checkStatus") String checkStatus, @Param("userName") String userName);

	/**
	 * 列出所有的Project,与listAllProject不一样的是，列出的是所有用户的Project列表，而非某一个用户
	 * 
	 * @param checkStatus
	 *            Project所处的审核状态
	 * @param offset
	 *            select偏移量
	 * @param num
	 *            本次查询所要返回的数量
	 * @return 查询到的包含Project对象的列表
	 */
	List<Project> listEveryProject(@Param("checkStatus") String checkStatus, @Param("offset") int offset,
			@Param("num") int num);

	/**
	 * 获取所有user的project总长度
	 * 
	 * @param checkStatus
	 *            审核状态
	 * @return 长度值
	 */
	int countProjectLength(@Param("checkStatus") String checkStatus);

	/**
	 * 根据projectId获取userName
	 * 
	 * @param projectId
	 *            Project的Id
	 * @return 此Project所属的user
	 */
	String getUserName(@Param("projectId") long projectId);

	/**
	 * 插入新的project行
	 * 
	 * @param project
	 *            新增的Project对象
	 * @param user
	 *            新增的Project对象所属的user
	 * @return 更新结果
	 */
	int addProject(@Param("project") Project project, @Param("user") User user);

	/**
	 * 根据ProjectId获取对应的Project的时间信息
	 * 
	 * @param projectId
	 *            需要查询的Project
	 * @return 返回时间信息，包含在一个Project对象里面
	 */
	Project getTime(@Param("projectId") long projectId);

	/**
	 * 检查该Project是否为空（是否包含任何Item）
	 * 
	 * @param projectId
	 *            需要查询的Project
	 * @return 所包含的Item数目，如果没有Item，就返回0
	 */
	int checkEmpty(@Param("projectId") long projectId);

	/**
	 * 删除一个Project
	 * 
	 * @param projectId
	 *            待删除的Project的Id
	 * @return 删除结果
	 */
	int deleteProject(@Param("projectId") long projectId);

	/**
	 * 查询与指定Project同名的Project，并返回其数量
	 * 
	 * @param project
	 *            需要查询的Project
	 * @return 同名的Project数量，0为无，1为存在
	 */
	int countProject(@Param("project") Project project);

	/**
	 * 更新某个指定的Project
	 * 
	 * @param project
	 *            待更新的Project
	 * @return 更新的结果
	 */
	int updateProject(@Param("project") Project project);

	/**
	 * 更新某个指定Project的url相关字段
	 * 
	 * @param project
	 *            待更新的Project
	 * @return 更新的结果
	 */
	int updateProjectUrl(@Param("project") Project project);

	/**
	 * 查询某一个Project的相关信息
	 * 
	 * @param projectId
	 *            待查询的Project的Id
	 * @return 查询到的Project信息，封装至一个Project对象
	 */
	Project selectOneProject(@Param("projectId") long projectId);

	/**
	 * 更新某个Project的CheckStatus字段
	 * 
	 * @param project
	 *            待更新的Project
	 * @return 更新结果
	 */
	int updateProjectCheckStatus(@Param("project") Project project);

	/**
	 * 通过User，candidateNum以及checkStatus获取对应的Project信息。
	 * 主要用于分社用户的基于选题号的模糊搜索服务，该搜索是局部搜索，搜索范围限制在某单一分社用户所属Project。
	 * 
	 * 
	 * @param candidateNum
	 *            选题号
	 * @param userName
	 *            Project所属的User
	 * @param checkStatus
	 *            Project所处的审核状态
	 * @param offset
	 *            页面偏移量
	 * @param len
	 *            页长
	 * @return 符合条件的所有的Project，封装至一个列表中
	 */
	List<Project> getProjectsWithUser(@Param("candidateNum") String candidateNum, @Param("userName") String userName,
			@Param("checkStatus") String checkStatus, @Param("offset") int offset, @Param("len") int len);

	/**
	 * 获取搜索结果的长度
	 * 
	 * @param candidateNum
	 *            选题号
	 * @param userName
	 *            用户名
	 * @param checkStatus
	 *            审核状态
	 * @return 结果长度
	 */
	int countSearchedLengthWithUser(@Param("candidateNum") String candidateNum, @Param("userName") String userName,
			@Param("checkStatus") String checkStatus);

	/**
	 * 通过candidateNum,checkStatus信息获取对应的Project信息
	 * 主要用于审核用户的基于选题号的模糊搜索服务，该搜索是全局搜索，搜索范围限制在所有分社用户所属Project
	 * 
	 * @param candidateNum
	 *            选题号
	 * @param checkStatus
	 *            Project所处的审核状态
	 * @param offset
	 *            页面偏移量
	 * @param len
	 *            页长
	 * @return 符合条件的所有的Project，封装至一个列表中
	 */
	List<Project> getProjects(@Param("candidateNum") String candidateNum, @Param("checkStatus") String checkStatus,
			@Param("offset") int offset, @Param("len") int len);

	/**
	 * 获取搜索的结果的长度
	 * 
	 * @param candidateNum
	 *            选题号
	 * @param checkStatus
	 *            审核状态
	 * @return 结果长度
	 */
	int countSearchedLength(@Param("candidateNum") String candidateNum, @Param("checkStatus") String checkStatus);

	/**
	 * 根据vs对象所提供的查询条件，查询和统计指定时间段不同城市访问某个Project次数的分布情况
	 * 
	 * @param vs
	 *            查询条件包装对象
	 * @return 查询结果，为一个List列表，每一个列表项包含省份和PV两个字段
	 */
	List<Map<String, Integer>> getVisitData(@Param("vs") VisitStatistic vs);

}