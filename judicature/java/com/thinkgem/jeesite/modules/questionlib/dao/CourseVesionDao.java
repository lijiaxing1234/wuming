/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.ClassCourseListVo;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;

/**
 * 课程版本DAO接口
 * @author webcat
 * @version 2016-08-16
 */
@MyBatisDao
public interface CourseVesionDao extends CrudDao<CourseVesion> {
	//获取教师所在的版本信息
	/*public List<ClassVesionCourseDTO> getCourseVesionDaoByUserId(@Param(value="userID")String userID);*/
	/*//获取通过id版本信息
	public CourseVesion getCourseVesionById(@Param(value="courseVesionId")String courseVesionId);*/
	/*第一步：根据教师id获取版本id 
	第二步：根据版本id获取课程id
	第三步：根据课程id获取课程详情 */
	public List<ClassCourseListVo> getCourseVesionByUserId(@Param(value="userID")String userID);
	//一个版本只能对应一门课程
	public CourseVesion getCourseIdByVesionId(@Param(value="vesionId")String vesionId);
	
	public Course getCourseByCourseId(@Param(value="courseId")String courseId);
	
	/**
	 * 根据版本代码获取版本
	 * @param courseVesion
	 * @return
	 */
	public CourseVesion getVersionByVersionCode(CourseVesion courseVesion);
	
	/**
	 * 根据专业id获取版本列表
	 * @param courseVesion
	 * @return
	 */
	public List<CourseVesion> findListBySpecialtyId(CourseVesion courseVesion);
	
	/**
	 * 查询课程下有多少个版本
	 * @param courseVesion 
	 * @return Integer  版本的数量
	 */
	Integer countCourseVesionByCourseId(CourseVesion courseVesion);
	
}