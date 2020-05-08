/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseQuestionlibDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 题库Service
 * @author webcat
 * @version 2016-08-16
 */
@Service
@Transactional(readOnly = true)
public class CourseQuestionlibService extends CrudService<CourseQuestionlibDao, CourseQuestionlib> {

	@Autowired
	protected CourseQuestionlibDao courseQuestionlibDao;
	
	public CourseQuestionlib get(String id) {
		return super.get(id);
	}
	
	public List<CourseQuestionlib> findList(CourseQuestionlib courseQuestionlib) {
		return super.findList(courseQuestionlib);
	}
	
	public Page<CourseQuestionlib> findPage(Page<CourseQuestionlib> page, CourseQuestionlib courseQuestionlib) {
		return super.findPage(page, courseQuestionlib);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseQuestionlib courseQuestionlib) {
		super.save(courseQuestionlib);
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseQuestionlib courseQuestionlib) {
		super.delete(courseQuestionlib);
	}
	@Transactional(readOnly = false)
	public void deleteSchoolLib(CourseQuestionlib courseQuestionlib) {
		courseQuestionlibDao.deleteSchoolLib(courseQuestionlib);
		
	}

	public CourseQuestionlib getQuestionlibByLibCode(CourseQuestionlib courseQuestionlib) {
		return courseQuestionlibDao.getQuestionlibByLibCode(courseQuestionlib);
	}

	public List<CourseQuestionlib> findListByView(CourseQuestionlib courseQuestionlib) {
		return courseQuestionlibDao.findListByView(courseQuestionlib);
	}

	public List<CourseQuestionlib> getByTitle(CourseQuestionlib courseQuestionlib) {
		return  courseQuestionlibDao.getByTitle(courseQuestionlib);
	}
	
	/**
	 * 教师端验证 题库名称唯一
	 * @param title
	 * @return
	 */
	public CourseQuestionlib getByTitle(String title) {
		
		CourseQuestionlib courseQuestionlib=new CourseQuestionlib();
		courseQuestionlib.setTitle(title);
		
		 User teacher=TearcherUserUtils.getUser();
		 Map<String,String> map=TearcherUserUtils.getTeacherIdAndCourseVersionId();
		 courseQuestionlib.setUser(teacher);
		 courseQuestionlib.setVersionId(map.get("versionId"));
		 courseQuestionlib.setSchoolId(teacher.getCompany().getId());
		 courseQuestionlib.setOwnerType("3");
		 List<CourseQuestionlib> list= getByTitle(courseQuestionlib);
		if(list!=null&& list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	
	
}