/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.dto.MessageStudentDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.entity.DateVo;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.teacher.dto.Exam2;

/**
 * 学生DAO接口
 * @author webcat
 * @version 2016-09-14
 */
@MyBatisDao
public interface StudentDao extends CrudDao<Student> {

	Student getStudentByStudentCode(Student s);

	List<Course> getCourseListByStudentId(String studentId);

	List<StudentTask> getMyGradePage(StudentTask studentTask);

	List<Office> getSchoolByOfficeCode(String officeCode);

	List<SchoolClass> getSchoolClassesBySchoolId(String schoolId);

	List<Student> findPageByCompanyId(Student student);

	List<Edresources> findStudentSourcesPage(Edresources edresources);

	List<Map<String, String>> getExamCountByCourseType(String studentId);

	List<Exam2> getStudentGradChange(Map<String, Object> paraMap);

	List<MessageStudentDTO> getStudentMsg(String studentId);

	void updateStudentMsg(List<MessageStudentDTO> list);

	List<Student> getSpecialtyListBySchoolId(String schoolId);

	Map<String, Object> getExercisesCount(String studentId);

	String getSchoolIdByStudentId(String studentId);

	List<DateVo> getStudentPassingRate(String studentId);

	Map<String, Object> courseSchedule(Map<String, String> paraMap);

	Student getStudentByStudentCode2(Student student);

	List<Map<String, Object>> getStudentGradeChange(Map<String, String> paraMap);

	Long getStudentRanking(Map<String, Object> map);

	void updatePasswordById(Map<String, String> paraMap);

	
	
	
	List<Student> selectByStudent(Student student);

}