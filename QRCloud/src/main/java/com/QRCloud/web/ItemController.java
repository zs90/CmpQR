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

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ServletContext servletContext;
	
	private String formatFromListToJson(Object ...args){
		return JSONArray.fromObject(args).toString();		
	}
	
	@RequestMapping(value="/item_page.do")
	public String itemPage(String project_id, HttpSession session){
		session.setAttribute("current_project", project_id);
		return "item_page";
	}
	
	@RequestMapping(value="/check_item.do")
	public String checkItemPage(String project_id, HttpSession session, Model model){
		session.setAttribute("current_project", project_id);
		int userGroup = (int)session.getAttribute("usergroup");
		
		if(userGroup == 0)
			model.addAttribute("from", "/QRCloud/project_page.do");
		else
			model.addAttribute("from", "/QRCloud/check_project.do");
			
		return "check_item_page";

	}
	
	@RequestMapping(value="/GetItemList.do", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getItemList(HttpSession session, HttpServletRequest request){
		long projectId = Integer.valueOf((String)session.getAttribute("current_project"));

		List<Object> retList = new ArrayList<>();
		List<Item> itemList = itemService.getItemList(projectId);
			
		String ownerName = projectService.getUserName(projectId);
		for(int i = 0; i < itemList.size(); i++){
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
			retList.add(tmp);
		}
		
		JSONArray jsonArray = JSONArray.fromObject(retList); 
		
		return jsonArray.toString();		
	}
	
	@RequestMapping(value="/upload_item.do", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String uploadItem(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try{
			String username = (String)session.getAttribute("username");		
			String project_id = (String)session.getAttribute("current_project");
			
			//初始化POJO
			User user = new User();
			Item item = new Item();
			
			user.setUserName(username);
			item.setProjectId(Integer.valueOf(project_id));
			item.setPageView(0);
			
			//开始接受文件
			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;
			
			String savePath = prefixPath + "/files/";   
			
	        File savePathFile = new File(savePath);
	        if(!savePathFile.exists()){
	        	savePathFile.mkdirs();
	        }
	        	
	    	ServletFileUpload upload = new ServletFileUpload();
	    	
	    	//解析表单，上传文件至服务器
	   		String fileName = null;
	
			FileItemIterator iter = upload.getItemIterator(request);  		
		
			while(iter.hasNext()){
				FileItemStream it = iter.next();
				InputStream stream = it.openStream();
				if(!it.isFormField()){
	    			fileName = it.getName();
					BufferedOutputStream buf_out = new BufferedOutputStream(new FileOutputStream(new File(savePath, fileName)));
					BufferedInputStream buf_in = new BufferedInputStream(stream);
					byte[] buffer = new byte[1024];
					int len = -1;
					while((len = buf_in.read(buffer)) != -1){
						buf_out.write(buffer, 0, len);
					}
					buf_in.close();
					buf_out.flush();
					buf_out.close();		
				}
			}
			
			itemService.uploadItem(user, item, fileName, savePath, prefixPath);
			
			return formatFromListToJson(item.getItemId(),
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()),
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()),
										username,
										item.getObjectType(),
										item.getObjectSize(),
										item.getPageView()
										);
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/replace_item.do", produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String replaceItem(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try{
			//获取表单参数
			String username = (String)session.getAttribute("username");	
			String project_id = (String)session.getAttribute("current_project");
			
			//初始化pojo
			Item item = new Item();
			item.setProjectId(Integer.valueOf(project_id));
			
			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;
	        
			String savePath = prefixPath + "/files/";  
			
	        File savePathFile = new File(savePath);
	        if(!savePathFile.exists()){
	        	savePathFile.mkdirs();
	        }
	
	    	ServletFileUpload upload = new ServletFileUpload();
	    	
	   		String fileName = null, fieldName = null;
	
			FileItemIterator iter = upload.getItemIterator(request);  		
			//解析表单，上传文件至服务器
			while(iter.hasNext()){
				FileItemStream it = iter.next();
				InputStream stream = it.openStream();
				if(!it.isFormField()){
	    			fileName = it.getName();
					BufferedOutputStream buf_out = new BufferedOutputStream(new FileOutputStream(new File(savePath, fileName)));
					BufferedInputStream buf_in = new BufferedInputStream(stream);
					byte[] buffer = new byte[1024];
					int len = -1;
					while((len = buf_in.read(buffer)) != -1){
						buf_out.write(buffer, 0, len);
					}
					buf_in.close();
					buf_out.flush();
					buf_out.close();
				}
				else{
					fieldName = it.getFieldName();
	
					if(fieldName.equals("item_id"))
						item.setItemId(Integer.valueOf(Streams.asString(stream)));
				}
			}
			
			itemService.replaceItem(item, savePath, fileName);
			
			return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()),
										item.getItemName(),
										item.getObjectType(),
										item.getObjectSize()
										);		
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/DeleteItem.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String deleteItem(String item_id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		Item item = new Item();
		item.setItemId(Integer.valueOf(item_id));
		
		itemService.deleteItem(item);
		
		return "1";
	}
	
	@RequestMapping(value="/GetItemQR.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String checkItem(String item_id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {		
		Item item = itemService.checkItem(Integer.valueOf(item_id));
		
		return formatFromListToJson(item.getPreviewQRUrl(), item.getDownloadQRUrl());
	}	
	
	@RequestMapping(value="/UpdateItem.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String updateItem(@ModelAttribute("Item") Item item, HttpSession session) {		
		itemService.updateItem(item);
		
		return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
	}
	
	@RequestMapping(value="/UploadLink.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String addLinkItem(@ModelAttribute("Item") Item item, HttpSession session) {	
		try{
			String username = (String)session.getAttribute("username");		
			String project_id = (String)session.getAttribute("current_project");
			
			//初始化POJO
			User user = new User();
			user.setUserName(username);
			
			item.setProjectId(Integer.valueOf(project_id));
			item.setPageView(0);
			
			String prefixPath = servletContext.getRealPath("/") + "uploads/" + username;
			
			itemService.addLinkItem(item, user, prefixPath);
			
			return formatFromListToJson(item.getItemId(),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()),
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()),
					username,
					item.getObjectType(),
					0,
					0
					);
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/ReplaceLink.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String updateLinkItem(@ModelAttribute("Item") Item item, HttpSession session) {		
		itemService.updateLinkItem(item);
		
		return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
	}
	
	@RequestMapping(value="/GetItemPV.do", method=RequestMethod.GET)
	public String getItemPV(String id, String name, HttpSession session, Model model) {	
		session.setAttribute("pv_item_id", Integer.valueOf(id));
		model.addAttribute("name", name);
		return "item_pv_statistic";
	}
	
	@RequestMapping(value="/GetItemPVDetail.do", method=RequestMethod.GET, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String getPVDetail(String startDate, String endDate, HttpSession session) throws ParseException {	
		VisitStatistic vs = new VisitStatistic();
		
		vs.setId((int)session.getAttribute("pv_item_id"));	
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vs.setStartDate(df.parse(startDate + " 00:00:00"));
		vs.setEndDate(df.parse(endDate + " 00:00:00"));
		itemService.getVisitData(vs);
		
		return JSONArray.fromObject(vs.getProvinceDataMap()).toString();
	}
}
