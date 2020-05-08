package com.thinkgem.jeesite.modules.web.entity;

import java.util.List;

/**
 * 用户答题记录实体
 */
public class UserRecordAnswer  extends UserRecord{
	
	
	/**
	 * 用户记录基础表标识
	 */
	private  String  recordId;
	
	/**
	 * 问题Id
	 */
	private String  quesId;
	
	/**
	 * 0:错误;1正确
	 */
	private Integer answerStatus;
	
	/**
	 * 用户答题选项集合
	 * 用户答题选项 多个选项以"#$_$#"分割
	 */
	private String  answerOption;
	
	
	/**
	 * app 用户答题 Json 选项答案
	 * 
	 * 用户答题选中的项集合
	 */
	private List<String>  userAnswer;


	public Integer getAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(Integer answerStatus) {
		this.answerStatus = answerStatus;
	}
	
	public String getQuesId() {
		return quesId;
	}
	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}
	
	public List<String> getUserAnswer() {
		return userAnswer;
	}
	
	public void setUserAnswer(List<String> userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	public String getAnswerOption() {
		return answerOption;
	}
	
	public void setAnswerOption(String answerOption) {
		this.answerOption = answerOption;
	}
	
	public String getRecordId() {
		return recordId;
	}
	
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
}
