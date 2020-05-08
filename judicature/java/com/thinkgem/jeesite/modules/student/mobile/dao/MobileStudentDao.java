/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.mobile.dao;

import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.student.entity.StudentTask;
import com.thinkgem.jeesite.modules.student.mobile.domain.Answer;
import com.thinkgem.jeesite.modules.student.mobile.domain.CommentVo;
import com.thinkgem.jeesite.modules.student.mobile.domain.Question;
import com.thinkgem.jeesite.modules.student.mobile.domain.QuestionVo;
import com.thinkgem.jeesite.modules.student.mobile.domain.StudentVo;

/**
 * 课程DAO接口
 * 
 * @author webcat
 * @version 2016-08-15
 */
@MyBatisDao
public interface MobileStudentDao {

	/**
	 * 根据学号获取学生的相关信息
	 * @param loginUser
	 * @return
	 */
	public StudentVo getStudentByCode(StudentVo loginUser);

	/**
	 * 根据学生的id获取该学生学习的课程
	 * @param stdId
	 * @return
	 */
	public List<Map<String, String>> getCoursesByStdId(String stdId);

	/**
	 * 获取学生已经参加的考试
	 * @param paraMap
	 * @return
	 */
	public List<Map<String, Object>> getExamsByTypeAndCourse(Map<String, Object> paraMap);
	
	/**
	 * 获取学生还没有参加的考试
	 * @param pageMap
	 * @return
	 */
	public List<Map<String, Object>> getStudentNotJoinExam(Map<String, Object> pageMap);

	/**
	 * 我的题库（我参加了的所有考试的所有试题）
	 * @param pageMap
	 * @return
	 */
	public List<QuestionVo> getMyQuestionLib(Map<String, Object> pageMap);

	/**
	 * 我做错了的所有的题目
	 * @param pageMap
	 * @return
	 */
	public List<QuestionVo> getWrongQuestionLib(Map<String, Object> pageMap);

	/**
	 * 我的评论
	 * 老师对该学生的评论列表
	 * @param pageMap
	 * @return
	 */
	public List<CommentVo> getMyComment(Map<String, Object> pageMap);

	/**
	 * 获取试卷
	 * @param paraMap
	 * @return
	 */
	public List<QuestionVo> getTestPaper(Map<String, String> paraMap);

	/**
	 * 获取试卷的详细信息
	 * @param paraMap
	 * @return
	 */
	public List<QuestionVo> getTestPaperAllMessage(Map<String, String> paraMap);

	
	public List<Map<String, Object>> getExamsByExamTypeAndCourseIdAndStudentId(Map<String, Object> paraMap);

	public StudentAnswer mobileIsAnswered(Map<String, String> paraMap);

	public void addStudentAnswer(StudentAnswer studentAnswer);

	public void updateStudentAnswer(StudentAnswer studentAnswer);

	public void updateMobileStudentTask(StudentTask studentTask);

	public void updatePassword(Map<String, String> paraMap);

	public StudentVo getStudentById(Map<String, String> paraMap);

	public int addStudentTask(StudentTask studentTask);

	public void addStudentAnswerList(List<StudentAnswer> list);

	public List<QuestionVo> getMissedTestPaper(Map<String, String> paraMap);

	public List<Map<String, String>> getQuestionIdAndAnswerByExamDetilId(String examDetailId);

	public void browseQuestion(Map<String, String> paraMap);

	public List<Question> getQuestionList(Map<String, Object> paraMap);

	public List<Answer> getAnswerList(Map<String, Object> paraMap);

	public void addQuestion(Map<String, Object> paraMap);

	public void submitAnswer(Map<String, Object> paraMap);

	public StudentVo isExistStudent(String studentId);

	public List<Map<String, String>> getAllSchool();

	public List<Map<String, String>> getQuestionLibQuesTypesAndQuesCounts(Map<String, String> paraMap);

	public List<Map<String, String>> getWrongQuestionLibQuesTypeAndQuesCount(Map<String, String> paraMap);

	public String isGradeTestPaper(Map<String, String> paraMap);

	public StudentVo isGraduate(Map<String, String> paraMap);


}