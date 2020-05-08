package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 回答Entity
 */
public class UAnswer extends DataEntity<UAnswer> {
	
	private static final long serialVersionUID = 1L;
	private UQuestion question;		// 问题ID
	private String detail;		// 情详
	private Date createtime;		// 建创时间
	private Date modifytime;		// 后最修改时间
	
	private User teacher;
	private Student student;
	
	public UAnswer() {
		super();
	}

	public UAnswer(String id){
		super(id);
	}
    public UQuestion getQuestion() {
		return question;
	}
    public void setQuestion(UQuestion question) {
		this.question = question;
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
	
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	

	@Override
	public void preInsert() {
		this.createtime=new Date();
		this.modifytime = this.createtime;
	}

	@Override
	public void preUpdate() {
		this.modifytime=new Date();
	}
	
}