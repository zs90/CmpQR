package com.QRCloud.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QRCloud.domain.Item;
import com.QRCloud.domain.User;
import com.QRCloud.domain.VisitStatistic;
import com.QRCloud.service.ItemService;
import com.QRCloud.service.ProjectService;

import net.sf.json.JSONArray;

/**
 * Item类控制层，用于处理针对Item类的相关的HTTP请求
 * 
 * @author Shane
 * @version 1.0
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ServletContext servletContext;

	/**
	 * 工具类函数，负责将一系列的对象转化为json字符串
	 * 
	 * @param args
	 *            可变参数，待转化的对象
	 * @return 转化后的json字符串
	 */
	private String formatFromListToJson(Object... args) {
		return JSONArray.fromObject(args).toString();
	}

	/**
	 * 处理item_page.do的http请求
	 * 
	 * @param project_id
	 *            请求的project_id
	 * @param session
	 *            注入的session
	 * @return 返回视图item_page.jsp
	 */
	@RequestMapping(value = "/item_page.do")
	public String itemPage(String project_id, HttpSession session) {
		session.setAttribute("current_project", project_id);
		return "item_page";
	}

	/**
	 * 处理view_item.do的http请求
	 * 
	 * @param project_id
	 *            请求的project_id
	 * @param session
	 *            注入的session
	 * @param model
	 *            注入的model
	 * @return 返回视图view_item_page.jsp
	 */
	@RequestMapping(value = "/view_item.do")
	public String viewItemPage(String project_id, HttpSession session, Model model) {
		session.setAttribute("current_project", project_id);
		int userGroup = (int) session.getAttribute("usergroup");

		// 不同的用户组会进入同一个view_item_page页面，但是在返回上一页的时候，目标网址不一样，所以加入一个from属性。
		if (userGroup == 0)
			model.addAttribute("from", "/QRCloud/project_page.do");
		else
			model.addAttribute("from", "/QRCloud/check_project.do");

		return "view_item_page";

	}

	/**
	 * 响应check_item.do的http请求
	 * 
	 * @param project_id
	 *            project的id
	 * @param session
	 *            注入的session
	 * @param model
	 *            注入的model
	 * @return 返回check_item_page视图页面
	 */
	@RequestMapping(value = "/check_item.do")
	public String checkItemPage(String project_id, HttpSession session, Model model) {
		session.setAttribute("current_project", project_id);
		return "check_item_page";
	}

	/**
	 * 响应itemChange.do的请求，用于设置item的修改状态
	 * 
	 * @param item_id
	 *            待修改的item_id
	 * @param changed
	 *            change的值，1为新修改，0为未修改
	 * @return 成功返回1
	 */
	@ResponseBody
	@RequestMapping(value = "/itemChange.do", produces = "text/plain; charset=UTF-8")
	public String changeItem(String item_id, String changed, String info) {
		Item item = new Item();
		item.setItemId(Integer.valueOf(item_id));

		itemService.setChanged(item, Integer.valueOf(changed), info);
		return "1";
	}

	/**
	 * 处理GetItemList.do的http请求，主要负责加载当前project下的所有item信息
	 * 
	 * @param session
	 *            注入的session
	 * @param request
	 *            注入的request
	 * @return 返回的json字符串
	 */
	@RequestMapping(value = "/GetItemList.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getItemList(HttpSession session, HttpServletRequest request) {
		long projectId = Integer.valueOf((String) session.getAttribute("current_project"));

		List<Object> retList = new ArrayList<>();
		List<Item> itemList = itemService.getItemList(projectId);

		String ownerName = projectService.getUserName(projectId);
		for (int i = 0; i < itemList.size(); i++) {
			List<Object> tmp = new ArrayList<>();
			tmp.add(String.valueOf(i));
			tmp.add(itemList.get(i).getItemName());
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(itemList.get(i).getCreateTime()));
			tmp.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(itemList.get(i).getUpdateTime()));
			tmp.add(ownerName);
			tmp.add(itemList.get(i).getObjectType());
			tmp.add(itemList.get(i).getObjectSize());
			tmp.add(itemList.get(i).getItemId());
			tmp.add(itemList.get(i).getItemComment());
			tmp.add(String.valueOf(itemList.get(i).getPageView()));
			tmp.add(String.valueOf(itemList.get(i).getChanged()));
			retList.add(tmp);
		}

		JSONArray jsonArray = JSONArray.fromObject(retList);

		return jsonArray.toString();
	}

	/**
	 * 处理针对/upload_item.do的http请求
	 * 
	 * @param session
	 *            注入的session
	 * @param request
	 *            注入的request
	 * @param response
	 *            注入的response
	 * @return 返回的json字符串
	 */
	@RequestMapping(value = "/upload_item.do", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String uploadItem(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取请求参数
			String username = (String) session.getAttribute("username");
			String project_id = (String) session.getAttribute("current_project");

			// 初始化POJO
			User user = new User();
			Item item = new Item();

			// 设置POJO
			user.setUserName(username);
			item.setProjectId(Integer.valueOf(project_id));
			item.setPageView(0);

			// 准备开始接收文件
			// 在当前路径下建立uploads/username的文件夹，每个用户拥有一个单独的文件夹，以username区分
			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;

			// uploads/username文件夹下再建立一个子文件夹，名为files，用于暂存上传的文件
			String savePath = prefixPath + "/files/";

			// 检查文件夹是否存在，不存在则新建一个文件夹
			File savePathFile = new File(savePath);
			if (!savePathFile.exists()) {
				savePathFile.mkdirs();
			}

			// 初始化上传对象
			ServletFileUpload upload = new ServletFileUpload();

			// 解析表单，将http流数据进行解析并写入文件
			String fileName = null;

			FileItemIterator iter = upload.getItemIterator(request);

			while (iter.hasNext()) {
				FileItemStream it = iter.next();
				InputStream stream = it.openStream();
				if (!it.isFormField()) {
					fileName = it.getName();
					BufferedOutputStream buf_out = new BufferedOutputStream(
							new FileOutputStream(new File(savePath, fileName)));
					BufferedInputStream buf_in = new BufferedInputStream(stream);
					byte[] buffer = new byte[1024];
					int len = -1;
					while ((len = buf_in.read(buffer)) != -1) {
						buf_out.write(buffer, 0, len);
					}
					buf_in.close();
					buf_out.flush();
					buf_out.close();
				}
			}

			// 上传该文件至s3
			itemService.uploadItem(user, item, fileName, savePath, prefixPath);

			// 将结果转为json数组
			// 字段依次为：itemId，创建时间，修改时间，用户名，item格式，item大小，item的PV
			return formatFromListToJson(item.getItemId(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()), username,
					item.getObjectType(), item.getObjectSize(), item.getPageView());
		} catch (Exception e) {
			// 如果出现异常，返回error字符串到前端
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 处理replace_item.do的http请求
	 * 
	 * @param session
	 *            注入的session
	 * @param request
	 *            注入的request
	 * @param response
	 *            注入的response
	 * @return 返回的json字符串
	 */
	@RequestMapping(value = "/replace_item.do", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String replaceItem(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取表单参数
			String username = (String) session.getAttribute("username");
			String project_id = (String) session.getAttribute("current_project");

			// 初始化pojo
			Item item = new Item();
			item.setProjectId(Integer.valueOf(project_id));

			// 初始化服务器本地存储路径和文件
			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;

			String savePath = prefixPath + "/files/";

			File savePathFile = new File(savePath);
			if (!savePathFile.exists()) {
				savePathFile.mkdirs();
			}

			// 初始化上传对象
			ServletFileUpload upload = new ServletFileUpload();

			// 解析表单，将http流数据进行解析并写入文件
			String fileName = null, fieldName = null;

			FileItemIterator iter = upload.getItemIterator(request);
			while (iter.hasNext()) {
				FileItemStream it = iter.next();
				InputStream stream = it.openStream();
				if (!it.isFormField()) {
					fileName = it.getName();
					BufferedOutputStream buf_out = new BufferedOutputStream(
							new FileOutputStream(new File(savePath, fileName)));
					BufferedInputStream buf_in = new BufferedInputStream(stream);
					byte[] buffer = new byte[1024];
					int len = -1;
					while ((len = buf_in.read(buffer)) != -1) {
						buf_out.write(buffer, 0, len);
					}
					buf_in.close();
					buf_out.flush();
					buf_out.close();
				} else {
					fieldName = it.getFieldName();

					if (fieldName.equals("item_id"))
						item.setItemId(Integer.valueOf(Streams.asString(stream)));
				}
			}

			// 更新数据库
			itemService.replaceItem(item, savePath, fileName);

			// 返回部分更新的数据，修改时间，item名称，item类型，item大小
			return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()),
					item.getItemName(), item.getObjectType(), item.getObjectSize());
		} catch (Exception e) {
			// 如果出错，就返回"error"字符串
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 处理DeleteItem.do的http请求，负责删除item
	 * 
	 * @param item_id
	 *            待删除的item_id
	 * @param session
	 *            注入的session
	 * @param request
	 *            注入的request
	 * @param response
	 *            注入的response
	 * @return 删除成功就返回1，否则返回0
	 */
	@RequestMapping(value = "/DeleteItem.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String deleteItem(String item_id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Item item = new Item();
		item.setItemId(Integer.valueOf(item_id));

		int ret = 1;
		try {
			itemService.deleteItem(item);
		} catch (Exception e) {
			// 如果删除出错，返回值置0
			e.printStackTrace();
			ret = 0;
		}
		return String.valueOf(ret);
	}

	/**
	 * 处理GetItemQR.do的http请求，主要负责获取二维码的url链接用于展示
	 * 
	 * @param item_id
	 *            所需要的item_id
	 * @param session
	 *            注入的session
	 * @param request
	 *            注入的request
	 * @param response
	 *            注入的response
	 * @return 两种二维码的url链接（json字符串表示）
	 */
	@RequestMapping(value = "/GetItemQR.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getItemQR(String item_id, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Item item = itemService.checkItem(Integer.valueOf(item_id));

		return formatFromListToJson(item.getPreviewQRUrl(), item.getDownloadQRUrl());
	}

	/**
	 * 处理UpdateItemd.do的http请求，主要负责更新Item相关信息
	 * 
	 * @param item
	 *            注入的item，绑定了请求参数
	 * @param session
	 *            注入的session
	 * @return 返回更新时间（json字符串表示）
	 */
	@RequestMapping(value = "/UpdateItem.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String updateItem(@ModelAttribute("Item") Item item, HttpSession session) {
		itemService.updateItem(item);

		return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
	}

	/**
	 * 处理UploadLink.do的http请求，主要负责新增链接类型的资源
	 * 
	 * @param item
	 *            待新增的item
	 * @param session
	 *            注入的session
	 * @return 返回的json字符串，依次包含item_id，创建时间，更新时间，用户名，类型，0，0
	 */
	@RequestMapping(value = "/UploadLink.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String addLinkItem(@ModelAttribute("Item") Item item, HttpSession session) {
		try {
			String username = (String) session.getAttribute("username");
			String project_id = (String) session.getAttribute("current_project");

			User user = new User();
			user.setUserName(username);

			item.setProjectId(Integer.valueOf(project_id));
			item.setPageView(0);

			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;

			itemService.addLinkItem(item, user, prefixPath);

			return formatFromListToJson(item.getItemId(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()), username,
					item.getObjectType(), 0, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 处理replaceLink.do的http请求
	 * 
	 * @param item
	 *            待替换的item
	 * @param session
	 *            注入的session
	 * @return 返回json字符串，即最新更新时间
	 */
	@RequestMapping(value = "/ReplaceLink.do", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String updateLinkItem(@ModelAttribute("Item") Item item, HttpSession session) {
		itemService.updateLinkItem(item);

		return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
	}

	/**
	 * 处理GetItemPV.do的http请求，返回统计展示页面
	 * 
	 * @param id
	 *            item_id
	 * @param name
	 *            item的名称
	 * @param session
	 *            注入的session
	 * @param model
	 *            注入的model
	 * @return 返回视图item_pv_statistic.jsp
	 */
	@RequestMapping(value = "/GetItemPV.do", method = RequestMethod.GET)
	public String getItemPV(String id, String name, HttpSession session, Model model) {
		session.setAttribute("pv_item_id", Integer.valueOf(id));
		model.addAttribute("name", name);
		return "item_pv_statistic";
	}

	/**
	 * 处理GetItemPVDetail.do的http请求，主要负责获取读者用户扫码的累积次数及其地理分布情况
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
	@RequestMapping(value = "/GetItemPVDetail.do", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String getPVDetail(String startDate, String endDate, HttpSession session) throws ParseException {
		VisitStatistic vs = new VisitStatistic();

		vs.setId((int) session.getAttribute("pv_item_id"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vs.setStartDate(df.parse(startDate + " 00:00:00"));
		vs.setEndDate(df.parse(endDate + " 00:00:00"));
		itemService.getVisitData(vs);

		return JSONArray.fromObject(vs.getProvinceDataMap()).toString();
	}

	/**
	 * 响应getItemCheckLog.do的http请求，主要负责获取item的审核信息
	 * 
	 * @param item_id
	 *            需要获取的item的id
	 * @return 审核信息字符串
	 */
	@RequestMapping(value = "/getItemCheckLog.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getItemCheckLog(long item_id) {
		Item item = itemService.checkItem(item_id);
		return item.getCheckInfo();
	}
}
