/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.StudentDao;
import com.thinkgem.jeesite.modules.questionlib.dto.MessageStudentDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.entity.DateVo;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;

/**
 * 学生Service
 * @author webcat
 * @version 2016-09-14
 */
@Service
@Transactional(readOnly = true)
public class StudentService extends CrudService<StudentDao, Student> {
	@Autowired
	private StudentDao dao;
	
	public Student get(String id) {
		Student student = super.get(id);
		return student;
	}
	
	public List<Student> findList(Student student) {
		return super.findList(student);
	}
	
	public Page<Student> findPage(Page<Student> page, Student student) {
		return super.findPage(page, student);
	}
	
	@Transactional(readOnly = false)
	public void save(Student student) {
		super.save(student);
	}
	
	@Transactional(readOnly = false)
	public void delete(Student student) {
		super.delete(student);
	}

	public Student getStudentByStudentCode(Student s) {
		if(StringUtils.isBlank(s.getSchoolId())){
			String schoolId = StudentUserUtils.getUser().getSchoolId();
			s.setSchoolId(schoolId);
		}
		Student studentByStudentCode = dao.getStudentByStudentCode(s);
		return studentByStudentCode;
	}
	
	public Student getStudentByStudentCode2(Student student) {
		return dao.getStudentByStudentCode2(student);
	}
	

	public List<Course> getCourseListByStudentId(String studentId) {
		return dao.getCourseListByStudentId(studentId);
	}

	public Page<StudentTask> getMyGradePage(Page<StudentTask> page, StudentTask studentTask) {
		studentTask.setPage(page);
		studentTask.setStudentId(StudentUserUtils.getUser().getId());
		page.setList(dao.getMyGradePage(studentTask));
		return page;
	}

	/**
	 * 根据机构编码查询学校
	 * @param officeCode
	 * @return
	 */
	public List<Office> getSchoolByOfficeCode(String officeCode) {
		return dao.getSchoolByOfficeCode(officeCode);
	}

	/**
	 * 根据学校id查询该学校下的所有班级
	 * @param schoolId
	 * @return
	 */
	public List<SchoolClass> getSchoolClassesBySchoolId(String schoolId) {
		return dao.getSchoolClassesBySchoolId(schoolId);
	}

	public Page<Student> findPageByCompanyId(Page<Student> page, Student student) {
		Map<String, String> sqlMap = student.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		student.setPage(page);
		page.setList(dao.findPageByCompanyId(student));
		return page;
	}

	public Page<Edresources> findStudentSourcesPage(Page<Edresources> page, Edresources edresources) {
		Student student = StudentUserUtils.getUser();
		edresources.setStudentId(student.getId());
		edresources.setPage(page);
		page.setList(dao.findStudentSourcesPage(edresources));
		return page;
	}

	public List<Map<String, String>> getExamCountByCourseType() {
		String studentId = StudentUserUtils.getUser().getId();
		return dao.getExamCountByCourseType(studentId);
	}

	public List<Exam2> getStudentGradChange(String studentId, String versionId, String duration) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("classId", StudentUserUtils.getUser().getSchoolClass().getId());
		if(null == duration){
			paraMap.put("duration", 0);
		}else if("1".equals(duration)){
			paraMap.put("duration", 30);
		}else if("2".equals(duration)){
			paraMap.put("duration", 60);
		}else if("3".equals(duration)){
			paraMap.put("duration", 90);
		}
		return dao.getStudentGradChange(paraMap);
	}

	@Transactional(readOnly = false)
	public List<MessageStudentDTO> getStudentMsg() {
		if(StudentUserUtils.getUser() == null){
			return null;
		}
		List<MessageStudentDTO> list = dao.getStudentMsg(StudentUserUtils.getUser().getId());
		if(!list.isEmpty()){
			dao.updateStudentMsg(list);
		}
		return list;
	}

	public int getStudentMsgCount() {
		if(StudentUserUtils.getUser() == null){
			return 0;
		}
		List<MessageStudentDTO> list = dao.getStudentMsg(StudentUserUtils.getUser().getId());
		return list.size();
	}

	public List<Student> getSpecialtyListBySchoolId() {
		String schoolId = UserUtils.getUser().getCompany().getId();
		return dao.getSpecialtyListBySchoolId(schoolId);
	}

	public Map<String, Object> getExercisesCount() {
		String studentId = StudentUserUtils.getUser().getId();
		return dao.getExercisesCount(studentId);
	}

	public List<DateVo> getStudentPassingRate() {
		String studentId = StudentUserUtils.getUser().getId();
		return dao.getStudentPassingRate(studentId);
	}

	public Map<String, Object> courseSchedule(String versionId) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("versionId", versionId);
		return dao.courseSchedule(paraMap);
	}

	public List<Map<String, Object>> getStudentGradeChange(String versionId) {
		String studentId = StudentUserUtils.getUser().getId();
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", studentId);
		paraMap.put("versionId", versionId);
		return dao.getStudentGradeChange(paraMap);
	}

	public Long getStudentRanking(Map<String, Object> map) {
		String studentId = StudentUserUtils.getUser().getId();
		map.put("studentId", studentId);
		return dao.getStudentRanking(map);
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String newPassword) {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("studentId", id);
		paraMap.put("newPassword", newPassword);
		dao.updatePasswordById(paraMap);
	}

	
	/**
	 * 查询用户表
	 */
	public List<Student>   selectByStudent(Student student){
		return dao.selectByStudent(student);
		
	}
	
	
	
}