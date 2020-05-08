package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.ExamDetailQuestionInfo;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;

/**
 *考试试题Dao
 */
@MyBatisDao
public interface ExamdetailQuestionDao {

	/**
	 * 先删除后批量添加考试试题
	 * 
	 * Parameter param=new Parameter(new Object[][]{
				    {"examDetailId",examdetail.getId()},     //试卷详细id
				    {"questionList",examdetailQuestionList}, //试卷试题集合
	   });
	 */
	void  batchDeleAndInsert(Parameter param);
	
	
	/**
	 * 查询试卷中的试题
	 * 	Parameter param=new Parameter(new Object[][]{
    	   {"examdetailId",examdetailId}, 
    	   {"beginTime",beginTime},
    	   {"endTime",endTime},
    	   {"examType",examType},
    	});
	 */
	List<ExamdetailQuestion> getExamdetailQuestionByExamDetailId(Parameter param);

	 /**
     * 查询全部试题
     * @param questionId 不包括这条数据
     * @param beginTime
     * @param endTime
     * @param examType
     * @return
     */
	List<ExamdetailQuestion> getExamdetailQuestions(Parameter param);
	/**
	 * 查询单条数据
	 */
	ExamdetailQuestion getOnlyOne(@Param(value="id")String id);


	/**
	 * 插入补题集合
	 * @param param 适用列子 <br/>
	 * 	 Parameter param=new  Parameter(new Object[][]{
				 {"list",list},  //随机补题集合
				 {"examDetailId",examDetailId} //AorB卷的唯一Id
		  });
	 */
	void InsertSupplementExamDetailQuestion(Parameter param);

    /**
     * 调整排序
     * @param eq
     */
	void updateExamDetailQuesSort(ExamdetailQuestion eq);
	
	/**
	 * 查询试卷A或B的试题 
	 * @param param examDetailId
	 * @return
	 */
	List<ExamDetailQuestionInfo> findQuestionsByExamDetailId(Parameter param);
	
	/**
	 * 查询A或B卷下全部的试题Id
	 * @param examDetailId  A或B卷的Id
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> findList(String examDetailId);
	
}
