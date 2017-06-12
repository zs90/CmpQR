package com.CmpBookResource.Interceptor;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.CmpBookResource.Util.TimeUtil;
import com.CmpBookResource.service.ItemService;

public class AccessControlInterceptor implements HandlerInterceptor {
	@Value("#{accessControlConfig['accessThreshold']}")
	int accessThreshold;

	@Autowired
	private ItemService itemService;
	
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
		if ( !itemService.isPublished(request.getParameter("id")) && !request.getRemoteAddr().equals("218.247.15.51")){
			response.sendRedirect("/CmpBookResource/error/not_published.jsp");
			return false;
		}
		
		//extract access limit from database
		String item_id = ((HttpServletRequest)request).getParameter("id");
		
		if(item_id == null)
			return true;
		
		Map<String, Object> resource_info = itemService.getAccessLimitById(item_id);
		
		if(resource_info == null)
			return true;
		
		String project_id = String.valueOf((int) resource_info.get("project_id"));
		int limit = (int) resource_info.get("access_threshold");

		// pass the request along the filter chain
		Cookie cookies[] = ((HttpServletRequest)request).getCookies();

		if(cookies != null){
			//iterate through every cookie.
			for(int i = 0; i < cookies.length; i++){
				Cookie cookie = cookies[i];
				String value = cookie.getValue();
				String[] str = value.split("&");
				long timeStamp = Long.parseLong(str[0]);
				int accessTimes = Integer.parseInt(str[1]);	
				
				//if we found the cookie with the targeted project id
				if(cookie.getName().equals(project_id)){				
					//if within the same day
					if(TimeUtil.isSameDayOfMillis(timeStamp, System.currentTimeMillis())){
						//exceed visit limit
						accessTimes++;
						
						if( accessTimes > limit){
							response.sendRedirect("/CmpBookResource/error/exceed_access.jsp");
							return false;
						}
						else{//within the limit
							cookie.setValue(System.currentTimeMillis() + "&" + Integer.toString(accessTimes));
							cookie.setMaxAge(24*60*60);
						    response.addCookie(cookie);
						    return true;
						}
					}
					else{//not within the same day
						cookie.setValue(System.currentTimeMillis() + "&1");
						cookie.setMaxAge(24*60*60);
					    response.addCookie(cookie);
					    return true;
					}
				}
			}
		}
		//if cookie is null, or we don't find those ones we want, then create a new one...
		Cookie newCookie = new Cookie(project_id,  System.currentTimeMillis() + "&1");
		newCookie.setMaxAge(24*60*60);
		response.addCookie(newCookie);	
		return true;

		
		
//		if(cookies != null){
//			for(int i = 0; i < cookies.length; i++){
//				Cookie cookie = cookies[i];
//				if(cookie.getName().equals("resourceInfo")){
//					String resourceInfo = cookie.getValue();
//					
//					String[] str = resourceInfo.split("&");
//					
//					long timeStamp = Long.parseLong(str[0]);
//					int accessTimes = Integer.parseInt(str[1]) + 1;
//					
//					//System.out.println(accessTimes);
//		
//					//同一天时间内
//					if(TimeUtil.isSameDayOfMillis(timeStamp, System.currentTimeMillis())){
//						//访问次数超过10次，重定向拒绝
//						if( accessTimes > accessThreshold){
//							response.sendRedirect("/CmpBookResource/error/exceed_access.jsp");
//							return false;
//						}
//						//不到10次，访问次数加1
//						else{
//							Cookie newCookie = new Cookie("resourceInfo", System.currentTimeMillis() + "&" + Integer.toString(accessTimes));
//							newCookie.setMaxAge(24*60*60);
//							response.addCookie(newCookie);
//							return true;
//						}
//					}
//				}		
//			}
//		}
//		
//		//不在一天之间，或者没有找到指定的cookie，或者cookie为空
//		Cookie newCookie = new Cookie("resourceInfo",  System.currentTimeMillis() + "&1");
//		newCookie.setMaxAge(24*60*60);
//		response.addCookie(newCookie);
		
//		return true;
	}

}
