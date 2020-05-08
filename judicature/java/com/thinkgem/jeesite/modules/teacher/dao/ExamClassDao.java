package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.dto.ExamStudentDTO;
import com.thinkgem.jeesite.modules.teacher.entity.ExamClass;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

/**
 * 试卷适用班级Dao
 */
@MyBatisDao
public interface ExamClassDao extends CrudDao<ExamStudentDTO> {
	
	/**
	 * 查询试卷适用的专业
	 */
	List<ExamClass> getExamClassByExamId(String examId);

	
	/**
	 * 先删除后
	 * 批量添加班级表
	 * 
	 * 参数列子：
	 * Parameter sParam=new Parameter(new Object[][]{
			{"examId",exam.getId()}, 
			{"classArr",classArr}//数组","分割
		 });
	 */
	int batchAdd(Parameter sParam);
	/**
	 * 先删除后
	 * 批量添加班级表
	 * 
	 * 参数列子：
	 * Parameter sParam=new Parameter(new Object[][]{
			{"examId",exam.getId()}, 
			{"classArr",classArr}//数组","分割
		 });
	 */
	int addClassExample(Parameter sParam);
	
	/**
	 * 查询该随堂练习的详情
	 */
	public List<TeacherVersionQuestion> selectStudentByExamId(TeacherVersionQuestion question);
	/**
	 * 试卷适用的班级内学生的列表
	 */
	public List<ExamStudentDTO> findExamStudent(ExamStudentDTO examStudent);
	
	
	/**
	 * 查询某个学生对应某个测试的提交时间
	 */
	public Date findStudnetSubmitTime(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	
	/**
	 * 查询某个学生在某个测试时需回答的总题数
	 */
	public Integer findStudentRight(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	
	/**
	 * 查询某个学生对应某个测试各个题目的正确数
	 */
	public Integer findStudentTotalTitle(@Param(value="studentId")String studentId,@Param(value="examId")String examId);
	/**
	 * 随堂测试的保存到examDetail表中
	 */
	public void addExerciseExam(@Param(value="examId")String examId,@Param(value="detailId")String detailId);
	public String queryIdByExamId(String examId);
	
	int addExercise(Parameter sParam);
	
	
	//获取学生的答案
	public List<String> findDetailQuestion(@Param(value="studentId")String studentId,@Param(value="questionId")String questionId,@Param(value="examId")String examId);
	//添加随堂测试的问题
	public void insertQuestion(@Param(value="questionId")String questionId,@Param(value="examDetailId")String examDetailId,@Param(value="sort")int sort,@Param(value="questionType")String questionType);
	//添加随堂测试的知识点
	public void insertKnowId(Parameter sParam);
	//先删除随堂测试的问题
	void deleteExamClass(String examDetailId);
	
	
	int updateExamClassStatusByExamId(ExamClass examClass);
}
