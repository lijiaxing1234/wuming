package com.thinkgem.jeesite.modules.student.utils;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;

public class StudentUserUtils {
	private static String CACHE_KEY = "student_";
	
	//public static String USER_COOKIE_USER_ID ="student_id";
	//public static String USER_COOKIE_course ="student_course";
	//public static String USER_COOKIE_USER_PASSWORD ="student_password"; 
	
	//public static String CACHE_USER_STUDENT_COURSEVERSION="cache_user_student_courseversion_";
	
	
	
	
	public static void putCache(Student user) {
		CacheUtils.put(CACHE_KEY + user.getId(), user);
	}
	
	public static void removeCache(Student user){
		if(user!=null){
			CacheUtils.remove(CACHE_KEY+ user.getId());
		}
	}
	
	public static Student getUserByID(String userId) {
		if (null != CacheUtils.get(CACHE_KEY + userId)) {
			return (Student)CacheUtils.get(CACHE_KEY + userId);
		}
		return null;
	}
	
	/**
	 * 得到当前登录用户
	 * 
	 * @param request
	 * @param response
	 */
//	public static User getUser(HttpServletRequest request,HttpServletResponse response) {
	public static Student getUser() {
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		//HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();
		/*String userId = CookieUtils.getCookie(request, USER_COOKIE_USER_ID);
		if (StringUtils.isNoneBlank(userId)) {
			if (null != CacheUtils.get(CACHE_KEY + userId)) {
				return (Student)CacheUtils.get(CACHE_KEY + userId);
			}
		}*/
		
		
		OnlineStudentUtils.Subject  obj=(OnlineStudentUtils.Subject)request.getSession().getAttribute(OnlineStudentUtils.SESSION_STUDENT_LOGIN_KEY);
		if(obj !=null){
			String userId =obj.getId();
			if (StringUtils.isNoneBlank(userId)) {
				if (null != CacheUtils.get(CACHE_KEY + userId)) {
					return (Student)CacheUtils.get(CACHE_KEY + userId);
				}
			}
		}
		
		
		return null;
	}
	
	
	/*public static void putCacheForCourseVesion(CourseVesion  courseVesion){
		CacheUtils.put(CACHE_USER_STUDENT_COURSEVERSION + getUser().getId(), courseVesion);
	}
	public static void removeCacheForCourseVesion(){
	    CacheUtils.remove(CACHE_USER_STUDENT_COURSEVERSION+ getUser().getId());
	}
	
	public static CourseVesion getCacheForCourseVesion(){
		CourseVesion courseVesion=(CourseVesion)CacheUtils.get(CACHE_USER_STUDENT_COURSEVERSION+getUser().getId());
		if(courseVesion !=null){
			return courseVesion;
		}else{
			return new CourseVesion();
		}
	}
	
    public static String getVersionId(){
    	return getCacheForCourseVesion().getId();
    }*/

}
