package com.QRCloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登陆拦截类，对用户进行登陆状态判定
 * 
 * @author Shane
 * @version 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 如果用户没有登陆，或者登陆的session已经失效，那么重定向至网站首页
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		if (request.getSession(false) != null || request.getRequestURI().equals("/QRCloud/Login.do")
				|| request.getRequestURI().equals("/QRCloud/index.html")) {
			return true;
		} else {
			response.sendRedirect("/QRCloud/index.html");
			return false;
		}
	}

}
