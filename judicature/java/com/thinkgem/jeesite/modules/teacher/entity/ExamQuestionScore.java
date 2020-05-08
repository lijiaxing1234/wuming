package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class ExamQuestionScore  extends DataEntity<ExamQuestionScore> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1320150061245373620L;
	
	
	private String examId;
	private String quesType;
	private String quesScore;
	private Double  sort;
	
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getQuesType() {
		return quesType;
	}
	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}
	public String getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(String quesScore) {
		this.quesScore = quesScore;
	}
	public Double getSort() {
		return sort;
	}
	public void setSort(Double sort) {
		this.sort = sort;
	}
	
	
	
	
	
}
