package com.QRCloud.web;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QRCloud.domain.Item;
import com.QRCloud.service.ItemService;
import com.QRCloud.service.ProjectService;

import net.sf.json.JSONArray;

@Controller
public class StatisticController {
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ProjectService projectService;
	
	private String formatFromListToJson(Object ...args){
		return JSONArray.fromObject(args).toString();		
	}
	
	@RequestMapping(value="/project_visit_detail.do", method=RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	@ResponseBody
	public String updateLinkItem(@ModelAttribute("Item") Item item, HttpSession session) {		
		itemService.updateLinkItem(item);
		
		return formatFromListToJson(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getUpdateTime()));
	}
}
