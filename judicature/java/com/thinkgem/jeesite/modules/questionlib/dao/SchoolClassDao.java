/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;

/**
 * 班级DAO接口
 */
@MyBatisDao
public interface SchoolClassDao extends CrudDao<SchoolClass> {

	//根据学校id查询所有班级
	List<SchoolClass> getClassBySchoolId(SchoolClass schoolClass);

	//根据班级id查询所有学生
	List<Student> findClassStudentByClassId(Student student);

	//批量插入学生
	int insertStudents(Map<String, Object> paraMap);

	//批量导入班级
	int importSchoolClass(Map<String, Object> paraMap);

	List<SchoolClass> findClassPageByCompanyId(SchoolClass schoolClass);

	List<SchoolClass> findListByCompanyId(SchoolClass schoolClass);

	List<SchoolClass> getSchoolClassListBySchoolId(String schoolId);

	List<Student> getStudentListBySchoolId(String schoolId);

	void deleteStudentByClassId(String classId);

	List<SchoolClass> getSchoolClassByName(SchoolClass schoolClass);

	String getClassTitleByTitle(Map<String, String> paraMap);
	
	/**
	 * 查询学校专业名称
	 * @param schoolClass 
	 * @return List<SchoolClass>
	 */
	List<SchoolClass> findSchollClassSpecialtyTitle(SchoolClass schoolClass);
    
	/**
	 *查询学校专业名称下的班级
	 * @param schoolClass
	 * @return
	 */
	List<SchoolClass> findSchoolClassBySpecTitle(SchoolClass schoolClass);

}