package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 提问Entity
 */
public class UQuestion extends DataEntity<UQuestion> {

	private static final long serialVersionUID = 1L;
	private String subject; // 标题
	private String detail; // 情详
	private Date createtime; // 建创时间
	private Date modifytime; // 后最修改时间

	private User teacher;
	private Student student;

	private Integer answerCount;

	//
	private String questionGiver;
	private String questionDetail;
	private String answerDetail;
	private String questionAnswer;
	private Date questionCreateTime;
	private Date answerCreateTime;

	public Date getQuestionCreateTime() {
		return questionCreateTime;
	}

	public void setQuestionCreateTime(Date questionCreateTime) {
		this.questionCreateTime = questionCreateTime;
	}

	public Date getAnswerCreateTime() {
		return answerCreateTime;
	}

	public void setAnswerCreateTime(Date answerCreateTime) {
		this.answerCreateTime = answerCreateTime;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String getQuestionGiver() {
		return questionGiver;
	}

	public void setQuestionGiver(String questionGiver) {
		this.questionGiver = questionGiver;
	}

	public String getQuestionDetail() {
		return questionDetail;
	}

	public void setQuestionDetail(String questionDetail) {
		this.questionDetail = questionDetail;
	}

	public String getAnswerDetail() {
		return answerDetail;
	}

	public void setAnswerDetail(String answerDetail) {
		this.answerDetail = answerDetail;
	}

	public UQuestion() {
		super();
	}

	public UQuestion(String id) {
		super(id);
	}

	@Length(min = 0, max = 100, message = "标题长度必须介于 0 和 100 之间")
	@NotNull
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@NotNull
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	@Override
	public void preInsert() {
		this.createtime = new Date();
		this.modifytime = this.createtime;
	}

	@Override
	public void preUpdate() {
		this.modifytime = new Date();
	}

}