package com.CmpBookResource.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.CmpBookResource.Util.AWSS3Client;
import com.CmpBookResource.Util.ValidFileType;
import com.CmpBookResource.model.Item;
import com.CmpBookResource.model.Project;
import com.CmpBookResource.service.ItemService;
import com.CmpBookResource.service.ProjectService;

/**
 * DisplayServlet控制器，负责集中处理所有的读者用户扫码产生的http请求
 * 
 * @author Shane
 * @version 1.0
 */
@Controller
public class DisplayServlet {
	@Autowired
	private ItemService itemService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private AWSS3Client s3client;

	@Autowired
	private ValidFileType validFileType;

	/**
	 * 处理show_resource.do的http请求，该请求负责展示具体的在线预览的界面
	 * 
	 * @param itemId
	 *            请求的item的id
	 * @param model
	 *            注入的model
	 * @return 返回对应的jsp视图
	 */
	@RequestMapping(value = "/show_resource.do", method = RequestMethod.GET)
	public String showItem(@RequestParam(value = "id") String itemId, Model model) {
		// 根据item的id获取item对象
		Item item = itemService.getItemById(itemId);

		// 如果该id所对应的item不存在，则返回资源不存在的提示界面
		if (item == null) {
			return "/error/resource_not_found";
		}

		// 找到了对应的item，但是该item是不可预览的类型，则返回该资源不支持预览的提示界面
		if (!validFileType.isShowable(item.getItemType())) {
			return "/error/not_support_show";
		}

		// 如果该item不是链接类型，则需要对对应的实体资源的s3的url进行加密签名
		// 如果是链接类型则不需要
		if (!validFileType.isLinkType(item.getItemType())) {
			String[] temp = item.getItemUrl().split("/");
			String bucketName = temp[3];
			String key = item.getItemUrl().split(bucketName + '/')[1];
			item.setItemUrl(s3client.getUrl(bucketName, key));
		}

		// 将item对象添加至model中
		model.addAttribute("item", item);

		// 获取item的类型
		String objectType = item.getItemType();

		// 根据item的类型返回对应的jsp页面
		if (objectType.equals("mp4"))
			return "/WEB-INF/jsp/show_detail/show_video";
		else if (objectType.equals("mp3"))
			return "/WEB-INF/jsp/show_detail/show_audio";
		else if (objectType.equals("png") || objectType.equals("jpg") || objectType.equals("gif")
				|| objectType.equals("bmp"))
			return "/WEB-INF/jsp/show_detail/show_img";
		else if (objectType.equals("web"))
			return "/WEB-INF/jsp/show_detail/show_web_link";
		else if (objectType.equals("paper"))
			return "/WEB-INF/jsp/show_detail/show_paper_link";
		else if (objectType.equals("pdf"))
			return "/WEB-INF/jsp/show_detail/show_pdf";

		// 这一条永远不会执行，为了保证编译通过，占位处理
		return "/error/resource_not_found";
	}

	/**
	 * 处理show_project.do的http请求，主要负责返回project在线预览页面。
	 * 该页面包含了一个列表，每一个列表项都可以被点击，之后跳转至对应的item的在线预览的页面
	 * 
	 * @param projectId
	 *            project的id
	 * @param model
	 *            注入的model
	 * @return 对应的jsp视图页面
	 */
	@RequestMapping(value = "/show_project.do", method = RequestMethod.GET)
	public String showProject(@RequestParam(value = "pid") String projectId, Model model) {
		// 根据project的id获取project对象
		Project project = projectService.getProjectById(projectId);

		// 如果project不存在，则返回资源不存在的提示页面
		if (project == null) {
			return "/error/resource_not_found";
		}

		// 对于那些不可以在线预览的item，要将其从列表项中剔除
		Iterator<Item> it = project.getItems().iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (!validFileType.isShowable(item.getItemType())) {
				it.remove();
			}
		}

		// 对project对象添加至model对象中
		model.addAttribute("project", project);

		// 返回对应的jsp视图页面
		return "/WEB-INF/jsp/show_project";
	}

	/**
	 * 处理download_resource.do的http请求，主要负责返回item下载页面。 用户通过点击对应的按钮，可以下载对应的实体资源。
	 * 
	 * @param itemId
	 *            item的id
	 * @param model
	 *            注入的model
	 * @return 对应的jsp视图页面
	 */
	@RequestMapping(value = "/download_resource.do", method = RequestMethod.GET)
	public String downloadItem(@RequestParam(value = "id") String itemId, Model model) {
		// 获取item对象
		Item item = itemService.getItemById(itemId);

		// 如果item不存在，则返回资源不存在的提示页面
		if (item == null) {
			return "/error/resource_not_found";
		}

		// 如果item不可以下载，则返回资源不支持下载的提示页面
		if (!validFileType.isDownable(item.getItemType())) {
			return "/error/not_support_download";
		}

		// 其余的可下载的item，其对应的s3的url链接将会进行加密签名，之后供用户下载
		String[] temp = item.getItemUrl().split("/");
		String bucketName = temp[3];
		String key = item.getItemUrl().split(bucketName + '/')[1];
		item.setItemUrl(s3client.getUrl(bucketName, key));

		// 将item添加至model
		model.addAttribute("item", item);

		// 返回对应的jsp视图页面
		return "/WEB-INF/jsp/download_resource";
	}

	/**
	 * 处理down_project.do的http请求，主要负责返回project下载页面。
	 * 该页面包含了一个列表，每一个列表项都提供了两种选择：预览和下载。用户可以根据需求进行选择。 选择后进入对应的item预览或者下载页面
	 * 
	 * @param projectId
	 *            project的id
	 * @param model
	 *            注入的model
	 * @return 对应的jsp视图页面
	 */
	@RequestMapping(value = "/down_project.do", method = RequestMethod.GET)
	public String downloadProject(@RequestParam(value = "pid") String projectId, Model model) {
		// 获取project对象
		Project project = projectService.getProjectById(projectId);

		// 如果project不存在，则返回资源不存在的提示页面
		if (project == null) {
			return "/error/resource_not_found";
		}

		// 将project对象添加至model对象中
		model.addAttribute("project", project);

		// 返回对应的jsp视图页面
		return "/WEB-INF/jsp/download_project";
	}

	/**
	 * 异常处理函数，负责处理运行时异常。 主要完成两件事，一是对异常进行日志记录，二是导向至自定义的错误页面。
	 * 
	 * @param e
	 *            异常
	 * @return 返回自定义的错误页面
	 */
	@ExceptionHandler(Throwable.class)
	public String handleRunTimeException(Throwable e) {
		// 日志记录时间和调用栈
		Date date = new Date();
		String dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		System.err.println("[ " + dateString + " ]");
		e.printStackTrace();

		// 返回自定义的错误页面
		return "/error/500_error";
	}
}
