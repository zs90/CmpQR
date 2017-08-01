package com.CmpBookResource.Interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.CmpBookResource.Util.CmpLogger;
import com.CmpBookResource.service.ItemService;

/**
 * 日志记录拦截类
 * @author Shane
 * @version 1.0
 */
public class IPVisitLogInterception implements HandlerInterceptor {		
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private CmpLogger cmpLogger;
	
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
	 * 前置处理http请求，用于记录用户ip和访问时间
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//获取id值
		String id = ((HttpServletRequest)request).getParameter("id");
		
		//如果id不合法或者为空，直接跳过
		if(id == null)
			return true;
		
		//记录日期
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		//记录日志
		cmpLogger.LogInfo(time + " " + request.getRemoteAddr() + "  " + id, "access_log_" + day + ".log");
		
		//将访问总次数加1
		itemService.incrementPVByOne(id);
			
		return true;
	}
}
