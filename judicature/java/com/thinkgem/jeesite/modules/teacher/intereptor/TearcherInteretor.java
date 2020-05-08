package com.thinkgem.jeesite.modules.teacher.intereptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

public class TearcherInteretor  implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse arg1, Object handler, Exception ex)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		String userId = CookieUtils.getCookie(request, TearcherUserUtils.USER_COOKIE_USER_ID);
		if (StringUtils.isNotBlank(userId)) {//没有登录过或cookie已经过期
			User user = TearcherUserUtils.getUserByID(userId);//是否在缓存中
			if (user == null) {
				response.sendRedirect(request.getContextPath()+Global.getTeacherPath() + "/login?ref=login");
				return false;
			}
		} else {
			response.sendRedirect(request.getContextPath()+Global.getTeacherPath() + "/login?ref=login");
			return false;
		}
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.substring(contextPath.length());
		String getQueryString=request.getQueryString();
		String currentUrl=uri+ (StringUtils.isNotBlank(getQueryString) ? "?"+getQueryString : ""  );
		request.setAttribute("currentUrl", Encodes.urlEncode(currentUrl));
		
		return true;
	}

}
