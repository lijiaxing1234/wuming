package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;
import com.thinkgem.jeesite.modules.teacher.entity.Examination;

/**
 * 试卷Dao
 */
@MyBatisDao
public interface ExaminationDao {

	/**
	 * 新增试卷
	 */
	int addExaminationSelect(Examination exam);
	/**
	 * 字段不为 null 且 不为 '' 就修改
	 * 修改试卷
	 */
	int updateExamSelect(Examination exam);
	int updateHomeWorkExamSelect(Examination exam);
	
	 /**
	 * 单条试卷
	 */
	Examination getExam(Examination exam);
	

   
	/**
	 * 查询试卷集合
	 * @param exam 试卷对象
	 * @return 符合条件的集合
	 */
	List<Examination> findList(Examination exam);
	List<Examination> findExam(Examination exam);
	
	/**
	 * 查询固定题型的分值 
	 */
	List<Map<String,Object>> findQuesScore();
	//获取试卷对应的知识点
	List<String> getKnowIds(@Param(value="examId")String examId);
	//查询试卷中对应的习题
	List<String> getQuestionIds(@Param(value="examId")String examId);
	//查询课堂例题列表
	List<Exam> getExampleList(Exam exam);
	//保存试卷的主副标题
	void saveMainTitleAndSubTitle(@Param(value="examId")String examId, @Param(value="examtype")String examtype, 
			@Param(value="mainTitle_A")String mainTitle_A, @Param(value="subTitle_A")String subTitle_A);
	
	void updateExam(@Param(value="exam")Examination exam);
	
}
