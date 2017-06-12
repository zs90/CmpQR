package com.QRCloud.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.QRCloud.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/index.html")
	public String loginPage(){
		return "index";
	}
	
	@RequestMapping(value="/welcome.do")
	public String welcomePage(){
		return "welcome_page";
	}
	
	@RequestMapping(value="/check_project.do")
	public String checkPage(){
		return "check_project_page";
	}
	
	@RequestMapping(value="/logout.do")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:index.html";
	}
	
	@RequestMapping(value="/Login.do", method=RequestMethod.POST)
	@ResponseBody
	public String loginCheck(HttpServletRequest request, String username, String password){
		HttpSession session;
		int userGroup;
		
		boolean isValidUser = userService.hasMatchUser(username, password);
		
		if(!isValidUser)
			return "1";
		else{
			session = request.getSession(true);
			session.setMaxInactiveInterval(900);
			session.setAttribute("username", username);
			userGroup = userService.getUserGroup(username);
			session.setAttribute("usergroup", userGroup);
			if(userGroup == 0)
				return "welcome.do";
			else
				return "check_project.do";
		}
		
	}
}
