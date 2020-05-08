package com.thinkgem.jeesite.modules.teacher.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseVesionDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.service.CourseVesionService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.dto.ClassVesionCourseDTO;
import com.thinkgem.jeesite.modules.teacher.entity.ExamQuestionScore;

public class TearcherUserUtils {
	private static String CACHE_KEY = "user_";
	public static String USER_COOKIE_USER_ID ="tearcher_id";
	public static String USER_COOKIE_USER_PASSWORD ="tearcher_password";
	
	public static String USER_COURSEVERSION_CACHE ="teacher_course_";
	
	
	static CourseVesionDao courseVesionDao = SpringContextHolder.getBean(CourseVesionDao.class);
	static CourseVesionService courseVesionService = SpringContextHolder.getBean(CourseVesionService.class);
	
	public static void putCache(User user) {
		CacheUtils.put(CACHE_KEY + user.getId(), user);
	}
	
	public static void removeCache(User user){
		if(user!=null){
			CacheUtils.remove(CACHE_KEY+ user.getId());
		}
	}
	
	
	public static User getUserByID(String userId) {
		if (null != CacheUtils.get(CACHE_KEY + userId)) {
			return (User)CacheUtils.get(CACHE_KEY + userId);
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
	public static User getUser() {
		RequestAttributes reqA=RequestContextHolder.getRequestAttributes();
		if(reqA!=null){
			HttpServletRequest request = ((ServletRequestAttributes)reqA).getRequest();  
			String userId = CookieUtils.getCookie(request, USER_COOKIE_USER_ID);
			if (StringUtils.isNoneBlank(userId)) {
				if (null != CacheUtils.get(CACHE_KEY + userId)) {
					return (User)CacheUtils.get(CACHE_KEY + userId);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 得到当前登陆用户所任课程的版本号
	 * 
	 * 问题：课程为空怎么办？？？
	 */
	
	public static List<ClassVesionCourseDTO> getTeacherCourseVesion(){
		User user = getUser();
		if(user!=null&&!(("").equals(user))){
			String id = user.getId();
			if(StringUtils.isNoneBlank(id)){
				/*Page<ClassVesionCourseDTO> page=new Page<ClassVesionCourseDTO>();
				String classVesionCourseDTOId = UUID.randomUUID().toString();
				ClassVesionCourseDTO classVesionCourseDTO=new ClassVesionCourseDTO(classVesionCourseDTOId);*/
				/*List<ClassVesionCourseDTO> courseVesionDaoByUserId=courseVesionDao.getCourseVesionDaoByUserId(id);*/
				
				List<ClassVesionCourseDTO> list = courseVesionService.getCourseVesionDaoByUserId(id);
			    return list;
			}
		}
		return null;
	}
	
	
	/**
	 * <h2>获取到教师id<br/>
	 *      版本id</br>
	 *      老师学校Id
	 *  <h2>
	 **/
   public static  Map<String,String>  getTeacherIdAndCourseVersionId(){
	   HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
	   String userId = CookieUtils.getCookie(request, USER_COOKIE_USER_ID);
	   String versionId = versionId();
	   Map<String,String> map=new HashMap<String,String>();
	   map.put("userId", userId);
	   map.put("versionId", versionId);
	   map.put("schoolId", getUser().getCompany().getId());
	   return map;
   }
 
  
	public static CourseVesion getCourseVesion() {
		CourseVesion courseVesion = (CourseVesion) CacheUtils.get(USER_COURSEVERSION_CACHE + getUser().getId());
		return courseVesion;
	}
	public static void setCourseVesion(CourseVesion courseVesion,User user){
		CacheUtils.put(USER_COURSEVERSION_CACHE + user.getId(),courseVesion);
	}
	
	public static void removeCacheCourseVesion(){
       User user=getUser();
		if(user!=null){
			CacheUtils.remove(USER_COURSEVERSION_CACHE+ user.getId());
		}
	}
	public static String versionId() {
		return getCourseVesion().getId();
	}
	
	
	
	
	public static ExamQuestionScore  getExamQuestionScore(List<ExamQuestionScore> list,String value){
		
		if(list !=null && list.size()>0){
			for(ExamQuestionScore examQuesScore:list){
				if(examQuesScore.getQuesType().equals(value)){
					return examQuesScore;
				}
			}
		}
		return  null;
	}
	
	/**
	 * 
	 * @param source 分组源 以‘,’分割
	 * @return
	 */
	public static Map<String,Integer> groupbyString(String source){
		 Map<String,Integer> map=Maps.newHashMap();
		if(StringUtils.isNoneBlank(source)){
			Map<String,List<String>> setMap=Maps.newHashMap();
			String[] strArr=source.split(",");
			for(int i=0,len=strArr.length;i<len;i++){
				if(setMap.containsKey(strArr[i])){
					List<String> set=setMap.get(strArr[i]);
					set.add(strArr[i]);
				}else{
					List<String> list=Lists.newArrayList();
					list.add(strArr[i]);
					setMap.put(strArr[i],list);
				}
			}
			for(Map.Entry<String, List<String>> entry:setMap.entrySet()){
				map.put(entry.getKey(), entry.getValue().size());
			}
		}
		return map;
	}
	
	
	
}
