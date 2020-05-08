/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 教师DAO接口
 * @author hgh
 * @version 2016-09-14
 */
@MyBatisDao
public interface TeacherDao extends CrudDao<Teacher> {

	//导入老师
	int insertTeachers(List<Teacher> teacherList);

	List<String> getOfficeIdByOfficeCode(String officeCode);

	List<Teacher> findTeacherPageByCompanyId(Teacher teacher);

	List<Map<String, String>> getOfficeIdAndCodeList();

	List<Teacher> getTeacherListBySchoolId(String schoolId);

	List<Teacher> getOfficeIdAndOfficeNameBySchoolId(String schoolId);

	User getUserByLoginName(String loginName);
	
}