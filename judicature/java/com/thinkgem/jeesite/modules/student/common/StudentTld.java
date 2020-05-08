package com.thinkgem.jeesite.modules.student.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dto.StaticDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.dao.StudentExamDao;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class StudentTld implements ApplicationContextAware{
	
	static StudentExamDao studentExamDao = SpringContextHolder.getBean(StudentExamDao.class);
	
	
	public static List<Course> getCourseByStudentId() {
		@SuppressWarnings("unchecked")
		List<Course> dictList = (List<Course>)CacheUtils.get(StudentUserUtils.getUser().getId()+"studentCourses");
		if(dictList == null){
			String studentId = StudentUserUtils.getUser().getId();
			dictList = studentExamDao.getCourseByStudentId(studentId);
			CacheUtils.put(StudentUserUtils.getUser().getId()+"studentCourses", dictList);
		}
		return dictList;
	}
	
	public static List<User> getTeachersByStudentId(){
		@SuppressWarnings("unchecked")
		List<User> dictList = (List<User>)CacheUtils.get(StudentUserUtils.getUser().getId()+"studentTeachers");
		if(dictList == null){
			String studentId = StudentUserUtils.getUser().getId();
			dictList = studentExamDao.getTeachersByStudentId(studentId);
			CacheUtils.put(StudentUserUtils.getUser().getId()+"studentTeachers", dictList);
		}
		return dictList;
	}
	
	public static List<CourseVesion> getCourseVersionListByStudentId(){
		@SuppressWarnings("unchecked")
		List<CourseVesion> dictList = (List<CourseVesion>) CacheUtils.get(StudentUserUtils.getUser().getId()+"studentCourseVersionList");
		if(null == dictList){
			String studentId = StudentUserUtils.getUser().getId();
			dictList = studentExamDao.getCourseVersionListByStudentId(studentId);
			CacheUtils.put(StudentUserUtils.getUser().getId()+"studentCourseVersionList", dictList);
		}
		return dictList;
	}
	
	public static List<Student> getAllSchool(){
		return studentExamDao.getAllSchool();
	}
	
	
	
	public static  Object   getStudentAnswer(StudentAnswer answer,String property){
		
		if(answer !=null&& StringUtils.isNoneBlank(property)){
			try {
				return PropertyUtils.getProperty(answer, property);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public static Object   getClassByProperty(Object obj,String property){
		
		List<Object>  list=null;
        		
		if(obj !=null&& StringUtils.isNoneBlank(property)){
				try {
					list = getClassByPropertys(obj,property);
					if(list !=null)
					  return  StringUtils.join(list, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return null;
		
	}
	public static List<Object>   getClassByPropertys(Object obj,String property){
		
		List<Object>  list=null;
		
		if(obj !=null&& StringUtils.isNoneBlank(property)){
			
			try {
				list = Lists.newArrayList();
				Field[] fs = obj.getClass().getDeclaredFields();
				
				Object val=null;
				
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					
					Reflections.makeAccessible(f);
					
					val=f.get(obj);
					
					if (f.getName().indexOf(property) != -1&&val!=null) {
						list.add(val);
					}
				}
				return list;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	
	
	
	
	
	
	
	
	

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
	}
}
