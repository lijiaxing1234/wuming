package com.thinkgem.jeesite.modules.teacher.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.teacher.entity.ExamSpecility;

/**
 * 试卷适用专业Dao
 */
@MyBatisDao
public interface ExamSpecilityDao{

	/**
	 * 查询试卷适用的专业
	 */
	List<ExamSpecility> getExamSpecilitiesByExamId(String examId);

	
	
	/**
	 * 先删除后批量添加适用专业表 
	 * 批量添加专业表
	 * 
	 * 参数列子：
	 * Parameter sParam=new Parameter(new Object[][]{
			{"examId",exam.getId()}, 
			{"specilityArr",specilityArr}//数组","分割
		 });
	 */
	int batchAdd(Parameter sParam);
}
