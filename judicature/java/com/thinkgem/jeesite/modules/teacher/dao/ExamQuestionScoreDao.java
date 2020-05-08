package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.ExamQuestionScore;

@MyBatisDao
public interface ExamQuestionScoreDao {

	
	 /**
	  * 试题出卷的分数 集合
	  * @param examId
	  */
	 List<ExamQuestionScore> findExamQuestionScoreExamId(String examId);
	 
	 /**
	  * 先删除后
	  * 批量插入试题出卷的分数
	  * 参数格式：
	  * Parameter param=new Parameter(new Object[][]{
			{"examId",examId}, //试卷Id
			{"list",List<ExamQuestionScore>} //各种题型分数集合
		});
	  */
	 int batchExamQuestionScore(Parameter param);
	
}
