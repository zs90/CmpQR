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

public class IPVisitLogInterception implements HandlerInterceptor {	
//	@Autowired
//	private ApplicationContext appContext;
//	@Autowired
//	private IPLoggerFactory ipLoggerFactory;
	
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

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		//IPLoggerService ips = (IPLoggerService)appContext.getBean("IPLoggerService");
		String id = ((HttpServletRequest)request).getParameter("id");
		if(id == null)
			return true;
		
		String day = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		cmpLogger.LogInfo(time + " " + request.getRemoteAddr() + "  " + id, "access_log_" + day + ".log");
		
		itemService.incrementPVByOne(id);
			
		return true;
	}
}
