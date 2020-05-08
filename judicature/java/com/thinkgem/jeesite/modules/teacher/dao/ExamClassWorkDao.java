package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.dto.StudentQuestionDTO;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;
@MyBatisDao
public interface ExamClassWorkDao extends CrudDao<Exam> {
	
	List<Exam> publishExamLimit(Exam exam);
	List<Exam> unpublishExamLimit(Exam exam);
	
	List<Exam> publishExam(Exam exam);
	List<Exam> unpublishExam(Exam exam);
	
	List<TeacherVersionQuestion> publishExamDetail( TeacherVersionQuestion teacherVersionQuestion);
	
	List<StudentQuestionDTO> publishExamPersonDetail(StudentQuestionDTO examDTO);
	//判断学生是否回答正确
	String getErrorStudent(@Param(value="studentId")String studentId, @Param(value="examId")String examId, @Param(value="questionId")String questionId);
	
	
}
