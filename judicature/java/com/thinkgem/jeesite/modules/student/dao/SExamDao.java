package com.thinkgem.jeesite.modules.student.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.student.entity.SExam;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;

@MyBatisDao
public interface SExamDao{
	
	List<SExam> getStudentNotExam(Map<String, String> paraMap);

	List<String> getExamDetailIdByExamId(String examId);

	SExam getSExamEntityById(String examId);

	List<CourseVesion> getCourseVersionByStudentId(String studentId);

	List<SExam> getNotExamByStudentIdVersionIdExamType(Map<String, String> paraMap);

	List<StudentTask> selectStudentTask(Map<String, String> paraMap);

	Long getLatelyExampleQuestionsCount(Map<String, String> paraMap);

}
