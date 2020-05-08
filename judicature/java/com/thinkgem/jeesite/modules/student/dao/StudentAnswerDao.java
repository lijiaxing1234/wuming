package com.thinkgem.jeesite.modules.student.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;

@MyBatisDao
public interface StudentAnswerDao {

	void addStudentTask(StudentTask task);

	List<VersionQuestion> getQuestionListByExamDetailIdAndQuesType(Map<String, String> paraMap);

	StudentAnswer isAnswered(StudentAnswer studentAnswer);

	void insertAnswer(StudentAnswer studentAnswer);

	void updateAnswer(StudentAnswer studentAnswer);

	void updateStudentTask(StudentTask studentTask);
	
	String getCorrectAnswerByCompare(StudentAnswer studentAnswer);

	void updateAnswrByCorrentOrNot(StudentAnswer studentAnswer);

	String getStudentTaskIdByStudentIdAndExamDetailId(Map<String, String> paraMap);

	List<VersionQuestion> getQuestionList(Map<String, String> paraMap);

	List<VersionQuestion> getExampleQuestionList(Map<String, String> paraMap);

	List<VersionQuestion> getMissQuestions(Map<String, String> paraMap);

	String getStudentTaskIdByStudentIdAndExamDetailIdAndIsSubmit(Map<String, String> paraMap);

	String getTestPaperStatus(StudentAnswer studentAnswer);

	List<VersionQuestion> getQuestionListByExamDetailIdAndQuesType2(Map<String, String> paraMap);

	String getExamDetailIdByExamDeatilId(Map<String, String> paraMap);
	

	/**
	 * 查询当前登录学生的答案
	 * @param studentAnswer
	 * @return List<StudentAnswer> 答案集合
	 */
	List<StudentAnswer>  getStudentAnswerByExample(StudentAnswer studentAnswer);

	String isExistAnswer(StudentAnswer studentAnswer);
	


}
