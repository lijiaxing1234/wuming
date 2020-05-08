/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.SCourseVersion;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;

/**
 * 课程版本DAO接口
 * @author webcat
 * @version 2016-09-16
 */
@MyBatisDao
public interface SCourseVersionDao extends CrudDao<SCourseVersion> {

	List<SCourseVersion> getSchoolCourseVersionPage(SCourseVersion sCourseVersion);

	List<Course> getSchoolNotCourse(String schoolId);

	List<SCourseVersion> getVersionsByCourseId(Map<String, String> paraMap);

	void addSchoolCourseVersion(Map<String, String> paraMap);

	Map<String, String> getSchoolVersionBy2Id(Map<String, String> paraMap);

	void deleteSchoolVersion(String courseVersionId);

	List<SCourseVersion> getVersionsByCourseIdAndSchoolId(Map<String, String> paraMap);

	List<Specialty> getCoursesBySpecialtyId(Map<String, String> paraMap);
	
	
	/*学校专业、课程、课程版本查询*/
	
	List<Map<String,Object>>  findSchoolSpecialty(Parameter param);
	
	List<Map<String,Object>>  findSchoolCourseBySpecialtyId(Parameter param);
	
	List<Map<String,Object>>  findSchoolCourseVersionByCourseId(Parameter param);

	void updateSchoolCourseVersion(Map<String, String> paraMap);
	
}