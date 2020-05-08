package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class KnowledgeQuestionDetail extends DataEntity<KnowledgeQuestionDetail>{
	
	private static final long serialVersionUID = 1L;
	
	private String knowledgeId;		//知识点的id
	private String questionType;	//题目的类型：1单选题，5多选题，2填空题，4简答题，3计算题
	private String questionDegree;	//问题的难易程度，1易，2中，3难
	private String questionNumber;	//对应有多少道题
	private String questionScore;	//每题多少分
	private String examId;			//对应的考试Id
	private String teacherId;		//由哪个老师创建的（保留字段）
	
	
	public String getKnowledgeId() {
		return knowledgeId;
	}
	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestionDegree() {
		return questionDegree;
	}
	public void setQuestionDegree(String questionDegree) {
		this.questionDegree = questionDegree;
	}
	public String getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public KnowledgeQuestionDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	public KnowledgeQuestionDetail(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public KnowledgeQuestionDetail(String knowledgeId, String questionType, String questionDegree,
			String questionNumber, String questionScore, String examId, String teacherId) {
		super();
		this.knowledgeId = knowledgeId;
		this.questionType = questionType;
		this.questionDegree = questionDegree;
		this.questionNumber = questionNumber;
		this.questionScore = questionScore;
		this.examId = examId;
		this.teacherId = teacherId;
	}
	@Override
	public String toString() {
		return "KnowledgeQuestionDetail [knowledgeId=" + knowledgeId + ", questionType=" + questionType
				+ ", questionDegree=" + questionDegree + ", questionNumber=" + questionNumber + ", questionScore="
				+ questionScore + ", examId=" + examId + ", teacherId=" + teacherId + "]";
	}
	
	
}
