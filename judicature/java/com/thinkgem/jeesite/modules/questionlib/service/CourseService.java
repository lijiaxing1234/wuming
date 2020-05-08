/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;
import com.thinkgem.jeesite.modules.questionlib.entity.TestDate;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseDao;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;

/**
 * 课程Service
 * @author webcat
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class CourseService extends CrudService<CourseDao, Course> {

	@Autowired
	private CourseDao courseDao;
	
	public Course get(String id) {
		return super.get(id);
	}
	
	public List<Course> findList(Course course) {
		return super.findList(course);
	}
	
	public Page<Course> findPage(Page<Course> page, Course course) {
		return super.findPage(page, course);
	}
	
	@Transactional(readOnly = false)
	public void save(Course course) {
		super.save(course);
	}
	
	@Transactional(readOnly = false)
	public void delete(Course course) {
		super.delete(course);
	}

	public Course getCourseByCourseCode(Course course) {
		return courseDao.getCourseByCourseCode(course);
	}
	
	/**
	 * 查询专业下有多少门课程
	 * @param specId 专业Id
	 * @return Integer 课程的数量
	 */
	public Integer  countCourseBySpecialtyId(Course course){
//		Course course=new Course();
//		course.setSpecialtyId(specId);
		return courseDao.countCourseBySpecialtyId(course);
	}
	
	@Transactional(readOnly = false)
	public boolean addExamDate(TestDate td){
		int hasTestDate = courseDao.hasTestDate();
		if(hasTestDate == 1){
			int i = courseDao.updateTestDate(td);
			return i == 1;
		}else{
			int i = courseDao.addTestDate(td.getExamDate());
			return i == 1;
		}
	}
	
	public TestDate getTestDate(){
		int hasTestDate = courseDao.hasTestDate();
		if(hasTestDate == 1){
			TestDate testDate = courseDao.getTestDate();
			return testDate;
		}else{
			TestDate testDate = new TestDate();
			testDate.setExamDate("");
			return testDate;
		}
	}
	
	public com.thinkgem.jeesite.common.utils.Page<CourseInformation> CourseInformationByPage(String pageNB){
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNO;
		try {
			pageNO = Integer.parseInt(pageNB);
		} catch (Exception e) {
			pageNO = 1;
		}
		int pageSize = 20;
		pageNO = (pageNO-1)*pageSize;
		map.put("pageNO", pageNO);
		map.put("pageSize", pageSize);
		int allciCount = courseDao.getAllciCount();
		com.thinkgem.jeesite.common.utils.Page<CourseInformation> page = new com.thinkgem.jeesite.common.utils.Page<CourseInformation>(pageNB, allciCount);
		List<CourseInformation> allCourseInformation = courseDao.getAllCourseInformation(map);
		page.setContent(allCourseInformation);
		return page;
	}
	
	@Transactional(readOnly = false)
	public boolean addci(CourseInformation courseInformation){
		int i = courseDao.addCourseInformation(courseInformation);
		return i ==1;
	}
	@Transactional(readOnly = false)
	public boolean delci(int id){
		int i = courseDao.delCourseInformation(id);
		return i == 1;
	}
}