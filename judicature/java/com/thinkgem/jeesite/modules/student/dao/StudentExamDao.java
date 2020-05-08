package com.thinkgem.jeesite.modules.student.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.sys.entity.User;

@MyBatisDao
public interface StudentExamDao extends CrudDao<SExam>{

	List<Course> getCourseByStudentId(String studentId);

	List<User> getTeachersByStudentId(String studentId);

	List<VersionQuestion> findMyQuestionLib(VersionQuestion question);

	StudentAnswer getStudentAnswer(StudentAnswer studentAnswer);

	List<StudentAnswer> getStudentAnswerByQuestionIdAndStudentId(StudentAnswer studentAnswer);

	List<String> getJoinAndSubmitExamIdList(String studentId);

	List<String> getNotJoinNotOverTimeExamIdList(String studentId);

	List<String> getNotJoinOverTimeExamIdList(String studentId);

	List<VersionQuestion> findMyWrongQuestionLib(VersionQuestion question);
	
	//根据学生id获取学生所学的课程版本
	List<CourseVesion> getCourseVersionListByStudentId(String studentId);

	List<SExam> getStudentJoinedExam(SExam exam);

	List<String> getCongenericQuestionIdList(String questionId);

	List<VersionQuestion> getCongenericQuestions(Map<String, Object> paraMap);

	String getResourceFilePath(String resourceId);

	List<Student> getAllSchool();

}
