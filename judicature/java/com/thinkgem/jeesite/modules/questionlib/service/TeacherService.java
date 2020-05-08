/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.TeacherDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 教师Service
 * @author hgh
 * @version 2016-09-14
 */
@Service
@Transactional(readOnly = true)
public class TeacherService extends CrudService<TeacherDao, Teacher> {

	
	public Teacher get(String id) {
		Teacher teacher = super.get(id);
		return teacher;
	}
	
	public List<Teacher> findList(Teacher teacher) {
		return super.findList(teacher);
	}
	
	public Page<Teacher> findPage(Page<Teacher> page, Teacher teacher) {
		return super.findPage(page, teacher);
	}
	
	@Transactional(readOnly = false)
	public void save(Teacher teacher) {
		teacher.setSchool(new Office(UserUtils.getUser().getCompany().getId()));
		teacher.setUserType("5");
		teacher.setLoginFlag("0");
		super.save(teacher);
	}
	
	@Transactional(readOnly = false)
	public void delete(Teacher teacher) {
		super.delete(teacher);
	}

	/**
	 * 导入老师
	 * @param teacherList
	 * @return
	 */
	@Transactional(readOnly = false)
	public int importTeachers(List<Teacher> teacherList) {
		return dao.insertTeachers(teacherList);
	}

	/**
	 * 根据机构码获取学校id list
	 * @param officeCode
	 * @return
	 */
	public List<String> getOfficeIdByOfficeCode(String officeCode) {
		return dao.getOfficeIdByOfficeCode(officeCode);
	}

	public Page<Teacher> findTeacherPageByCompanyId(Page<Teacher> page, Teacher teacher) {
		Map<String, String> sqlMap = teacher.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		teacher.setPage(page);
		return page.setList(dao.findTeacherPageByCompanyId(teacher));
	}

	/**
	 * 查询所有的组织机构
	 * @return
	 */
	public List<Map<String, String>> getOfficeIdAndCodeList() {
		return dao.getOfficeIdAndCodeList();
	}

	public List<Teacher> getTeacherListBySchoolId() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		return dao.getTeacherListBySchoolId(schoolId);
	}

	public List<Teacher> getOfficeIdAndOfficeNameBySchoolId() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		return dao.getOfficeIdAndOfficeNameBySchoolId(schoolId);
	}

	public User getUserByLoginName(String loginName) {
		return dao.getUserByLoginName(loginName);
	}
	
}