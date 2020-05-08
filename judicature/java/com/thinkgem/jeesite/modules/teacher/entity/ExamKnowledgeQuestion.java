package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;


/**
 * 手动出题时用到
 * 试卷、知识点、试题类
 */
public class ExamKnowledgeQuestion extends DataEntity<ExamKnowledgeQuestion> {

	private static final long serialVersionUID = 1L;

	private Examination exam; //试卷
	private CourseKnowledge courseKnowledge; //知识点
	
	private VersionQuestion  question; //试题表
	
	private List<VersionQuestion>  questions=new ArrayList<VersionQuestion>();//试题集合
	
	
	public Examination getExam() {
		return exam;
	}
	public void setExam(Examination exam) {
		this.exam = exam;
	}
	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}
	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}
	
	public VersionQuestion getQuestion() {
		return question;
	}
	public void setQuestion(VersionQuestion question) {
		this.question = question;
	}
	
	public List<VersionQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<VersionQuestion> questions) {
		this.questions = questions;
	}
	
}
