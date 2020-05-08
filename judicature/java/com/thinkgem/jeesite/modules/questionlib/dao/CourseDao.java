/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.TestDate;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;

/**
 * 课程DAO接口
 * @author webcat
 * @version 2016-08-15
 */
@MyBatisDao
public interface CourseDao extends CrudDao<Course> {

	/**
	 * 根据课程代码获取课程
	 * @param course
	 * @return
	 */
	public Course getCourseByCourseCode(Course course);
	
	/**
	 * 查询专业下有多少门课程
	 * @param course
	 * @return
	 */
	Integer countCourseBySpecialtyId(Course course);
	
	
	int addTestDate(String examDate);
	int hasTestDate();
	int updateTestDate(TestDate td);
	TestDate getTestDate();
	
	List<CourseInformation> getAllCourseInformation(Map<String, Object> map);
	int addCourseInformation(CourseInformation courseInformation);
	int delCourseInformation(int id);
	int getAllciCount();
}