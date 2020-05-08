package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.Examknowledge;
import com.thinkgem.jeesite.modules.teacher.entity.KnowledgeQuestionDetail;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;

@MyBatisDao
public interface ExamknowledgeDao {
	
	 /**
	  * 得到知识点Id集合
	  * @param examId
	  */
	 List<String> getknowledgeIdsByExamId(String examId);
	 
	 
	 /**
	  * 先删除后
	  * 批量插入考的知识点
	  * 参数格式：
	  * Parameter param=new Parameter(new Object[][]{
			{"examId",exam.getId()}, //试卷Id
			{"ckIds",ids} //课程知识点Ids数组
		});
	  */
	 int batchAddExamKnowledge(Parameter param);
	 /**
	     * 课后作业手动生成组卷的确认细节
	     * 根据id罗列所选中的知识点
     */
	  List<Examknowledge> queryExamKnowleByExamId(@Param(value="examId")String examId);
	  
	  /**
	   * 根据知识点id查询知识点所对应的题目
	   */
	  List<TeacherVersionQuestion> selectQuestionById(@Param(value="question")TeacherVersionQuestion question);
	  
	  /**
	   * 手动添加课后作业
	   */
	  void addKnowledgeQuestionDetail(@Param(value="know")KnowledgeQuestionDetail know);
	  
	  
	  
	/**
	 * 自动组卷时 选中知识点的第一级父节点的知识
	 * @param courseKnowledge
	 */
	List<CourseKnowledge> getSelectKnowledgeFirstParentByExamId(CourseKnowledge courseKnowledge);
}
