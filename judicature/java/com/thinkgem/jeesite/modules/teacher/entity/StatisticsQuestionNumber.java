package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 *  统计 某个学校和版本下所有可以使用的试题
 */
public class StatisticsQuestionNumber extends DataEntity<StatisticsQuestionNumber> {

	private static final long serialVersionUID = 1L;
	

	private String  quesType;
	private Long    number;
	
	public String getQuesType() {
		return quesType;
	}
	
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}

    
}
