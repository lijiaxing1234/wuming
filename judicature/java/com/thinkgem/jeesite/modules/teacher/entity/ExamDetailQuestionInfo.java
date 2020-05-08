package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.List;

import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;

/**
 *试卷统计 (主要用于导出word 使用)
 */
public class ExamDetailQuestionInfo extends VersionQuestion{

	private static final long serialVersionUID = 1L;
	
	
	private  String  quesTypes;  //试卷类型
	private  String  quesScore;  //单题分数
	private  String  totalScore; //总分数
	private  Long    quesCount;  //该题型的下有多少试题
	private  String  quesIds;    //试题的id集合
	
	
	public String getQuesTypes() {
		return quesTypes;
	}
	public void setQuesTypes(String quesTypes) {
		this.quesTypes = quesTypes;
	}
	public String getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(String quesScore) {
		this.quesScore = quesScore;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	public Long getQuesCount() {
		return quesCount;
	}
	public void setQuesCount(Long quesCount) {
		this.quesCount = quesCount;
	}
	public String getQuesIds() {
		return quesIds;
	}
	public void setQuesIds(String quesIds) {
		this.quesIds = quesIds;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((quesCount == null) ? 0 : quesCount.hashCode());
		result = prime * result + ((quesIds == null) ? 0 : quesIds.hashCode());
		result = prime * result
				+ ((quesScore == null) ? 0 : quesScore.hashCode());
		result = prime * result
				+ ((quesTypes == null) ? 0 : quesTypes.hashCode());
		result = prime * result
				+ ((totalScore == null) ? 0 : totalScore.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExamDetailQuestionInfo other = (ExamDetailQuestionInfo) obj;
		if (quesCount == null) {
			if (other.quesCount != null)
				return false;
		} else if (!quesCount.equals(other.quesCount))
			return false;
		if (quesIds == null) {
			if (other.quesIds != null)
				return false;
		} else if (!quesIds.equals(other.quesIds))
			return false;
		if (quesScore == null) {
			if (other.quesScore != null)
				return false;
		} else if (!quesScore.equals(other.quesScore))
			return false;
		if (quesTypes == null) {
			if (other.quesTypes != null)
				return false;
		} else if (!quesTypes.equals(other.quesTypes))
			return false;
		if (totalScore == null) {
			if (other.totalScore != null)
				return false;
		} else if (!totalScore.equals(other.totalScore))
			return false;
		return true;
	}
	
	
	
}
