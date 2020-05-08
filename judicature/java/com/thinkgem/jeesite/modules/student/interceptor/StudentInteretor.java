package com.thinkgem.jeesite.modules.student.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.Servlets;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.utils.OnlineStudentUtils;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;

public class StudentInteretor  implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		boolean  flag=Servlets.isAjaxRequest(request);
		
		
		
		/*String userId = CookieUtils.getCookie(request, StudentUserUtils.USER_COOKIE_USER_ID);
		if (StringUtils.isNotBlank(userId)) {//没有登录过或cookie已经过期
			Student user = StudentUserUtils.getUserByID(userId);//是否在缓存中
			if (user == null) {
				if(flag){
					PrintWriter out = response.getWriter();
					out.println("{\"statusCode\":\"301\", \"message\":\"由于您长时间未登录。请重新登录！\"}");
				}else{
				   response.sendRedirect(request.getContextPath()+Global.getStudentPath() + "/login");
				}
				return false;
			}
		} else {
			if(flag){
				PrintWriter out = response.getWriter();
				out.println("{\"statusCode\":\"301\", \"message\":\"由于您产时间未登录。请重新登录！!\"}");
			}else{
			   response.sendRedirect(request.getContextPath()+Global.getStudentPath() + "/login");
			}
			return false;
		}*/
		
		
		HttpSession session=request.getSession(false);
		
		OnlineStudentUtils.Subject  obj=session==null ? null:(OnlineStudentUtils.Subject)session.getAttribute(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY);
		
		if(obj!=null){
			String userId =obj.getId();
			
			if (StringUtils.isNotBlank(userId)) {//没有登录过或cookie已经过期
				Student user = StudentUserUtils.getUserByID(userId);//是否在缓存中
				if (user == null) {
					if(flag){
						PrintWriter out = response.getWriter();
						out.println("{\"statusCode\":\"301\", \"message\":\"由于您长时间未登录。请重新登录！\"}");
					}else{
					   response.sendRedirect(request.getContextPath()+Global.getStudentPath() + "/login");
					}
					return false;
				}
			} else {
				if(flag){
					PrintWriter out = response.getWriter();
					out.println("{\"statusCode\":\"301\", \"message\":\"由于您长时间未登录。请重新登录！!\"}");
				}else{
				   response.sendRedirect(request.getContextPath()+Global.getStudentPath() + "/login");
				}
				return false;
			}
			
		}else{
			
			if(flag){
				PrintWriter out = response.getWriter();
				out.println("{\"statusCode\":\"301\", \"message\":\"由于您长时间未登录。请重新登录！!\"}");
			}else{
			   response.sendRedirect(request.getContextPath()+Global.getStudentPath() + "/login");
			}
			return false;
			
		}
		
		return true;
	}
	
	

}
