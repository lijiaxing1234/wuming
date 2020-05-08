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
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolClassDao;

/**
 * 班级Service
 *
 */
@Service
@Transactional(readOnly = true)
public class SchoolClassService extends CrudService<SchoolClassDao, SchoolClass> {
	
	@Autowired
	private SchoolClassDao dao;
	

	public SchoolClass get(String id) {
		return super.get(id);
	}
	
	public List<SchoolClass> findList(SchoolClass schoolClass) {
		return super.findList(schoolClass);
	}
	
	public Page<SchoolClass> findPage(Page<SchoolClass> page, SchoolClass schoolClass) {
		return super.findPage(page, schoolClass);
	}
	
	@Transactional(readOnly = false)
	public void save(SchoolClass schoolClass) {
		super.save(schoolClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(SchoolClass schoolClass) {
		super.delete(schoolClass);
	}

	public List<Student> findClassStudentByClassId(Student student) {
		return dao.findClassStudentByClassId(student);
	}

	@Transactional(readOnly = false)
	public int importStudents(List<Student> studentList) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("studentList", studentList);
		return dao.insertStudents(paraMap);
	}

	@Transactional(readOnly = false)
	public int importSchoolClass(List<SchoolClass> classList) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classList", classList);
		return dao.importSchoolClass(paraMap);
	}

	public Page<SchoolClass> findClassPageByCompanyId(Page<SchoolClass> page, SchoolClass schoolClass) {
		Map<String, String> sqlMap = schoolClass.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		schoolClass.setPage(page);
		page.setList(dao.findClassPageByCompanyId(schoolClass));
		return page;
	}

	public List<SchoolClass> getSchoolClassListBySchoolId() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		return dao.getSchoolClassListBySchoolId(schoolId);
	}

	public List<Student> getStudentListBySchoolId() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		return dao.getStudentListBySchoolId(schoolId);
	}
	
	@Transactional(readOnly = false)
	public void deleteStudentByClassId(String classId) {
		//根据班级id删除班级下所有学生
		dao.deleteStudentByClassId(classId);
	}

	public List<SchoolClass> getSchoolClassByName(SchoolClass schoolClass) {
		schoolClass.setSchoolId(UserUtils.getUser().getCompany().getId());
		return dao.getSchoolClassByName(schoolClass);
	}

	public String getClassTitleByTitle(String classTitle) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("schoolId", UserUtils.getUser().getCompany().getId());
		paraMap.put("classTitle", classTitle);
		return dao.getClassTitleByTitle(paraMap);
	}
   
	
	/**
	 * 查询学校专业名称
	 * @param schoolClass 
	 * @return List<SchoolClass>
	 */
	public List<SchoolClass> findSchollClassSpecialtyTitle(String specTitle,String companyId){
		SchoolClass schoolClass=new SchoolClass();
		schoolClass.setSchoolId(companyId);
		schoolClass.setSpecialtyTitle(specTitle);
		
		return dao.findSchollClassSpecialtyTitle(schoolClass);
	}
	/**
	 * 查询学校专业名称下的班级
	 * @param specTitle
	 * @param companyId
	 * @return
	 */
	public List<SchoolClass> findSchoolClassBySpecTitle(String specTitle,String companyId) {
		SchoolClass schoolClass=new SchoolClass();
		schoolClass.setSchoolId(companyId);
		schoolClass.setSpecialtyTitle(specTitle);
		return dao.findSchoolClassBySpecTitle(schoolClass);
	}
	
	
}