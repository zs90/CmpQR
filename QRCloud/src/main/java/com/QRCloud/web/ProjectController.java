package com.QRCloud.web;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QRCloud.domain.Log;
import com.QRCloud.domain.Project;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;
import com.QRCloud.service.LogService;
import com.QRCloud.service.ProjectService;
import com.QRCloud.util.ZipUtils;

import net.sf.json.JSONArray;

/**
 * Project控制层，集中处理针对project的相关http请求
 * 
 * @author Shane
 * @version 1.0
 */
@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/project_page.do")
	public String projectPage() {
		return "project_page";
	}

	/**
	 * 处理GetProjectList.do的http请求，负责获取project的列表
	 * 
	 * @param session
	 *            注入的session
	 * @param checkStatus
	 *            处于某种审核状态的project列表
	 * @param page
	 *            当前页
	 * @param pageLen
	 *            页长
	 * @return json字符串，包含了project的列表，以及总的列表长度值，前端页面以此绘制表格
	 */
	@RequestMapping(value = "/GetProjectList.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getProjectList(HttpSession session, String checkStatus, int page, int pageLen) {
		String userName = (String) session.getAttribute("username");
		int userGroup = (int) session.getAttribute("usergroup");

		List<Project> projectList = projectService.getProjectList(userName, checkStatus, userGroup, page, pageLen);
		
		int projectLen = projectService.getProjectLength(userName, checkStatus, userGroup);

		List<Object> list = new ArrayList<>();

		for (int i = 0; i < projectList.size(); i++) {
			List<String> tmp = new ArrayList<>();
			tmp.add(String.valueOf(i));
			tmp.add(projectList.get(i).getProjectName());
			tmp.add(projectList.get(i).getProjectComment());
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectList.get(i).getCreateTime()));
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectList.get(i).getUpdateTime()));
			tmp.add(projectService.getUserName(projectList.get(i).getProjectId()));
			tmp.add(String.valueOf(projectList.get(i).getPubNum()));
			tmp.add(String.valueOf(projectList.get(i).getAccessThreshold()));
			tmp.add(String.valueOf(projectList.get(i).getProjectId()));
			list.add(tmp);
		}

		list.add(projectLen);
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return jsonArray.toString();
	}

	/**
	 * 处理AddProject.do的http请求，负责新增project
	 * 
	 * @param project
	 *            待增加的project
	 * @param session
	 *            注入的session
	 * @return json字符串，元素依次为 projectId 创建时间 更新时间 username。如果失败，返回error字符串
	 */
	@RequestMapping(value = "/AddProject.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String addProject(@ModelAttribute("Project") Project project, HttpSession session) {
		try {
			String username = (String) session.getAttribute("username");
			List<Object> rs = new ArrayList<>();

			User user = new User();
			user.setUserName(username);

			// 第一个字段：project id
			projectService.addProject(project, user, servletContext.getRealPath("/"));

			rs.add(project.getProjectId());

			// 第二，第三个字段 create_time和update_time
			projectService.setProjectTime(project);
			rs.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(project.getCreateTime()));
			rs.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(project.getUpdateTime()));

			// 第四个字段username
			rs.add(username);

			JSONArray jsonArray = JSONArray.fromObject(rs);
			return jsonArray.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 处理DeleteProject.do的http请求，负责删除project
	 * 
	 * @param session
	 *            注入的session
	 * @param project_id
	 *            待删除project的id
	 * @return 项目不为空，返回2，删除失败。项目为空，返回1，删除成功
	 */
	@RequestMapping(value = "/DeleteProject.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String deleteProject(HttpSession session, String project_id) {
		int ret = projectService.deleteProject(Integer.valueOf(project_id), (String) session.getAttribute("username"));

		// 项目不为空，删除失败
		if (ret == -1)
			return "2";
		// 项目为空，删除成功
		else
			return "1";
	}

	/**
	 * 处理UpdateProject.do的http请求，负责更新project
	 * 
	 * @param project
	 *            待更新的project
	 * @param session
	 *            注入的session
	 * @return 更新成功，返回时间；项目名称重复，返回2
	 */
	@RequestMapping(value = "/UpdateProject.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String updateProject(@ModelAttribute("Project") Project project, HttpSession session) {
		int ret = projectService.updateProject(project);

		// 项目更新成功,则返回更新时间
		if (ret == 1) {
			projectService.setProjectTime(project);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(project.getUpdateTime());
		}
		// 项目名称重复
		else
			return "2";
	}

	/**
	 * 处理CheckProject.do的http请求，负责预览project
	 * 
	 * @param projectId
	 *            待预览的project
	 * @return 返回item的相关元数据供前端展示
	 */
	@RequestMapping(value = "/CheckProject.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String checkProject(@RequestParam(value = "pid") String projectId) {
		Project project = projectService.getOneProject(Integer.valueOf(projectId));

		List<String> ret = new ArrayList<>();

		ret.add(project.getPreviewQRUrl());
		ret.add(project.getDownloadQRUrl());
		ret.add(project.getWriter());
		ret.add(project.getEditor());
		ret.add(String.valueOf(project.getEdition()));
		ret.add(project.getCandidateNum());

		JSONArray jsonArray = JSONArray.fromObject(ret);
		return jsonArray.toString();
	}

	/**
	 * 处理QRDownloadZip.do的http请求，负责批量下载二维码
	 * 
	 * @param projectId
	 *            需要下载的project的id
	 * @param response
	 *            注入的response
	 */
	@RequestMapping(value = "/QRDownloadZip.do", method = RequestMethod.POST)
	public void batchDownloadQR(@RequestParam(value = "project_id") String projectId, HttpServletResponse response) {
		try {
			// 下载所有二维码到指定路径
			String zipPath = servletContext.getRealPath("/") + "zips/" + projectId;
			String srcPath = zipPath + "/src";
			projectService.getProjectQR(Integer.valueOf(projectId), srcPath);

			// 初始化zip文件存储路径
			String in = srcPath;
			String outPath = zipPath + "/zip";
			String out = outPath + "/" + projectId + ".zip";

			File savePathFile = new File(outPath);
			if (!savePathFile.exists()) {
				savePathFile.mkdirs();
			}

			// 将所有的二维码文件打包进zip
			ZipUtils appZip = new ZipUtils(out, in);
			appZip.generateFileList(new File(in));
			appZip.zipIt(out);

			// 将zip文件写入response输出流
			File zipFile = new File(out);
			response.setContentType("application/zip");
			response.setContentLength((int) zipFile.length());
			response.addHeader("Content-Disposition", "attachment;filename=\"" + projectId + ".zip" + "\"");

			byte[] arBytes = new byte[32768];
			FileInputStream is = new FileInputStream(zipFile);
			ServletOutputStream op = response.getOutputStream();

			int count;
			while ((count = is.read(arBytes)) > 0) {
				op.write(arBytes, 0, count);
			}
			op.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理changeCheckStatus.do的http请求，负责更新project的审核状态
	 * 
	 * @param session
	 *            注入的session
	 * @param checkStatus
	 *            审核状态值
	 * @param projectId
	 *            project的id
	 * @param checkInfo
	 *            改变审核状态时的备注信息
	 * @return 更新成功，返回1
	 */
	@RequestMapping(value = "/changeCheckStatus.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String changeCheckStatus(HttpSession session, int checkStatus, long projectId, String checkInfo) {
		if (checkInfo == null)
			checkInfo = new String("");
		String userName = (String) session.getAttribute("username");

		projectService.setCheckStatus(checkStatus, projectId, userName, checkInfo);

		return "1";
	}

	/**
	 * 处理getCheckLog.do的http请求，负责返回日志信息
	 * 
	 * @param projectId
	 *            待查询的project的id
	 * @return 返回json字符串，记录了所有的审核信息，格式大致为：时间，操作人，审核动作，备注
	 */
	@RequestMapping(value = "/getCheckLog.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getCheckLog(long projectId) {
		List<Log> logList = logService.getLogInfo(projectId);

		List<Object> retList = new ArrayList<>();
		for (int i = 0; i < logList.size(); i++) {
			List<Object> tmp = new ArrayList<>();
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logList.get(i).getLogTime()));
			tmp.add(logList.get(i).getSubjectName());
			tmp.add(logList.get(i).getPredicate());
			tmp.add(logList.get(i).getLogInfo());
			retList.add(tmp);
		}

		JSONArray jsonArray = JSONArray.fromObject(retList);
		return jsonArray.toString();
	}

	/**
	 * 处理searchProject.do的http请求，通过project名称模糊搜索project
	 * 
	 * @param projectName
	 *            project的名称
	 * @param session
	 *            注入的会话
	 * @param checkStatus
	 *            审核状态
	 * @return json字符串，包括搜索到的符合条件的project列表，以及总的列表长度值
	 */
	@RequestMapping(value = "/searchProject.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String searchProject(String projectName, HttpSession session, String checkStatus, int page, int pageLen) {
		String userName = (String) session.getAttribute("username");
		int userGroup = (int) session.getAttribute("usergroup");

		List<Project> projectList = projectService.searchProjects(projectName, userGroup, userName, checkStatus,
				(page - 1) * pageLen, pageLen);

		int length = projectService.getSearchedProjectLength(userName, checkStatus, userGroup, projectName);
		
		List<Object> list = new ArrayList<>();

		for (int i = 0; i < projectList.size(); i++) {
			List<String> tmp = new ArrayList<>();
			tmp.add(String.valueOf(i));
			tmp.add(projectList.get(i).getProjectName());
			tmp.add(projectList.get(i).getProjectComment());
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectList.get(i).getCreateTime()));
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectList.get(i).getUpdateTime()));
			tmp.add(projectService.getUserName(projectList.get(i).getProjectId()));
			tmp.add(String.valueOf(projectList.get(i).getPubNum()));
			tmp.add(String.valueOf(projectList.get(i).getAccessThreshold()));
			tmp.add(String.valueOf(projectList.get(i).getProjectId()));
			list.add(tmp);
		}
		
		list.add(length);
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return jsonArray.toString();
	}

	/**
	 * 处理GetProjectPV.do的http请求，负责返回PV统计页面
	 * 
	 * @param id
	 *            待查询的id，这里是project的id
	 * @param name
	 *            project的名称
	 * @param session
	 *            注入的session
	 * @param model
	 *            注入的model
	 * @return 返回视图project_pv_statistic.jsp
	 */
	@RequestMapping(value = "/GetProjectPV.do", method = RequestMethod.GET)
	public String getItemPV(String id, String name, HttpSession session, Model model) {
		session.setAttribute("pv_project_id", Integer.valueOf(id));
		model.addAttribute("name", name);
		return "project_pv_statistic";
	}

	/**
	 * 处理GetProjectPVDetail.do的http请求，负责返回PV表格绘制所需的详细数据
	 * 
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @param session
	 *            注入的session
	 * @return 用于绘制图表的数据，json格式表示，结构为一个数组，每一个元素为{省份：访问次数}
	 * @throws ParseException
	 */
	@RequestMapping(value = "/GetProjectPVDetail.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getPVDetail(String startDate, String endDate, HttpSession session) throws ParseException {
		VisitStatistic vs = new VisitStatistic();

		vs.setId((int) session.getAttribute("pv_project_id"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vs.setStartDate(df.parse(startDate + " 00:00:00"));
		vs.setEndDate(df.parse(endDate + " 00:00:00"));
		projectService.getVisitData(vs);

		return JSONArray.fromObject(vs.getProvinceDataMap()).toString();
	}

}
