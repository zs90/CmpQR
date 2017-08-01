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

/**
 * 访问控制拦截类
 * 
 * @author Shane
 * @version 1.0
 */
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

	/**
	 * 前置处理各类http请求，主要负责判定访问是否合法。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// 获取item的id
		String item_id = ((HttpServletRequest) request).getParameter("id");

		// 如果不是合法的item，则不受访问控制，例如如果是project的访问，就可以直接跳过
		if (item_id == null)
			return true;

		// 如果该item没有通过审核发布，并且来源请求ip不是机工社，则直接重定向至资源尚未发布页面
		if (!itemService.isPublished(request.getParameter("id")) && !request.getRemoteAddr().equals("218.247.15.51")) {
			response.sendRedirect("/CmpBookResource/error/not_published.jsp");
			return false;
		}

		// 获取该item对应的project的每日单个用户访问上限
		Map<String, Object> resource_info = itemService.getAccessLimitById(item_id);

		// 如果为空，可以直接跳过
		if (resource_info == null)
			return true;

		// 获取对应的project的id和访问上限值
		String project_id = String.valueOf((int) resource_info.get("project_id"));
		int limit = (int) resource_info.get("access_threshold");

		// 获取cookies
		Cookie cookies[] = ((HttpServletRequest) request).getCookies();

		// 如果cookies不为空
		if (cookies != null) {
			// 遍历所有的cookie。cookie的格式为timestamp&count。timestamp字段记录了上次访问的时间，count字段记录了累积的访问次数
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				String value = cookie.getValue();
				String[] str = value.split("&");
				long timeStamp = Long.parseLong(str[0]);
				int accessTimes = Integer.parseInt(str[1]);

				// 如果发现该cookie中的project_id和请求的project_id一样，则进行访问次数判定
				if (cookie.getName().equals(project_id)) {
					// 如果上次访问的时间和当下时刻位于同一天
					if (TimeUtil.isSameDayOfMillis(timeStamp, System.currentTimeMillis())) {
						// 将累积访问次数加一
						accessTimes++;

						// 如果累积访问次数超过了设定的上限值，重定向至超过访问限制的jsp视图页面
						if (accessTimes > limit) {
							response.sendRedirect("/CmpBookResource/error/exceed_access.jsp");
							return false;
						} else {
							// 如果没有超出限制，则更新cookie的字段值，重新设置cookie的过期时间并返回给用户
							cookie.setValue(System.currentTimeMillis() + "&" + Integer.toString(accessTimes));
							cookie.setMaxAge(24 * 60 * 60);
							response.addCookie(cookie);
							return true;
						}
					} else {
						// 如果上次访问的时间和当下时刻不是同一天，则一天已过去，访问累积次数重置为1
						cookie.setValue(System.currentTimeMillis() + "&1");
						cookie.setMaxAge(24 * 60 * 60);
						response.addCookie(cookie);
						return true;
					}
				}
			}
		}

		// 如果cookie为null，或者没有找到对应project_id的cookie，则说明用户第一次访问网站，或者第一次访问该project。
		// 那么就新建一个记录访问次数的cookie就好。
		Cookie newCookie = new Cookie(project_id, System.currentTimeMillis() + "&1");
		newCookie.setMaxAge(24 * 60 * 60);
		response.addCookie(newCookie);
		return true;
	}
}
