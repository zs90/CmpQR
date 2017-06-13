package com.CmpBookResource.controller;

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
	
	@RequestMapping(value="/show_resource.do", method=RequestMethod.GET)
	public String showItem(@RequestParam(value="id") String itemId, Model model){		
		Item item = itemService.getItemById(itemId);
		
		//没找到资源
		if(item == null){
			return "/error/resource_not_found";
		}
		
		//找到了资源，但是不可预览
		if(!validFileType.isShowable(item.getItemType())){
			return "/error/not_support_show";
		}
		
		//link资源不需要预签名
		if(!validFileType.isLinkType(item.getItemType())){
			String[] temp = item.getItemUrl().split("/");
			String bucketName = temp[3];
			String key = item.getItemUrl().split(bucketName + '/')[1];
			item.setItemUrl(s3client.getUrl(bucketName, key));
		}
		
		model.addAttribute("item", item);
		
		String objectType = item.getItemType();
		
		if(objectType.equals("mp4"))
			return "/WEB-INF/jsp/show_detail/show_video";
		else if(objectType.equals("mp3"))
			return "/WEB-INF/jsp/show_detail/show_audio";
		else if(objectType.equals("png") || objectType.equals("jpg") || objectType.equals("gif") || objectType.equals("bmp"))
			return "/WEB-INF/jsp/show_detail/show_img";		
		else if(objectType.equals("web"))
			return "/WEB-INF/jsp/show_detail/show_web_link";
		else if(objectType.equals("paper"))
			return "/WEB-INF/jsp/show_detail/show_paper_link";
		else if(objectType.equals("pdf"))
			return "/WEB-INF/jsp/show_detail/show_pdf";	
		
		return "/error/resource_not_found";
	}
	
	@RequestMapping(value="/show_project.do", method=RequestMethod.GET)
	public String showProject(@RequestParam(value="pid") String projectId, Model model){
		Project project = projectService.getProjectById(projectId);
		
		//项目不存在
		if(project == null){
			return "/error/resource_not_found";
		}
		
		//去除不可预览的资源
		Iterator<Item> it = project.getItems().iterator();
		while(it.hasNext()){
			Item item = it.next();
			if(!validFileType.isShowable(item.getItemType())){
				it.remove();
			}
		}
		
		model.addAttribute("project", project);
		
		return "/WEB-INF/jsp/show_project";
	}
	
	@RequestMapping(value="/download_resource.do", method=RequestMethod.GET)
	public String downloadItem(@RequestParam(value="id") String itemId, Model model){
		Item item = itemService.getItemById(itemId);
		//资源不存在
		if(item == null){
			return "/error/resource_not_found";
		}
		
		//资源不可下载
		if(!validFileType.isDownable(item.getItemType())){
			return "/error/not_support_download";
		}
				
		String[] temp = item.getItemUrl().split("/");
		String bucketName = temp[3];
		String key = item.getItemUrl().split(bucketName + '/')[1];
		item.setItemUrl(s3client.getUrl(bucketName, key));
				
		model.addAttribute("item", item);
		return "/WEB-INF/jsp/download_resource";
	}
	
	@RequestMapping(value="/down_project.do", method=RequestMethod.GET)
	public String downloadProject(@RequestParam(value="pid") String projectId, Model model){
		Project project = projectService.getProjectById(projectId);
		//项目不存在
		if(project == null){
			return "/error/resource_not_found";
		}
		
		model.addAttribute("project", project);
		return "/WEB-INF/jsp/download_project";
	}
	
	@ExceptionHandler(Throwable.class)
	public String handleRunTimeException(Throwable e){
		e.printStackTrace();
		return "/error/500_error";
	}
}
