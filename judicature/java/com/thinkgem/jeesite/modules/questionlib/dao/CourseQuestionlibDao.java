/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseQuestionlib;

/**
 * 题库DAO接口
 * @author webcat
 * @version 2016-08-16
 */
@MyBatisDao
public interface CourseQuestionlibDao extends CrudDao<CourseQuestionlib> {

	/**
	 * 在删除题库的同时，删除授权给学校的题库
	 * @param courseQuestionlib
	 */
	public void deleteSchoolLib(CourseQuestionlib courseQuestionlib);

	/**
	 * 根据题库代码获取题库
	 * @param courseVesion
	 * @return
	 */
	public CourseQuestionlib getQuestionlibByLibCode(CourseQuestionlib courseQuestionlib);

	/**
	 * 通过视图查询由专业、课程等id查询题库列表
	 * @param courseQuestionlib
	 * @return
	 */
	public List<CourseQuestionlib> findListByView(CourseQuestionlib courseQuestionlib);

	public List<CourseQuestionlib> getByTitle(CourseQuestionlib courseQuestionlib);

	
}