/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;

/**
 * 学生任务（在线考试、练习、作业。。。）Entity
 * 
 * @author cq
 * @version 2016-09-20
 */
public class StudentTask extends DataEntity<StudentTask> {

	private static final long serialVersionUID = 1L;
	private Student student;
	private StudentExamDetail examDetail;
	private String examid; // 测验ID
	private String isSubmit; // 提交状态 0：未提交，1：已提交
	private String totalfraction; // 测验总分 满分为多少分
	private Float score; // 分得
	private Date starttime; // 开始作答时间
	private Date submittime; // 交提时间

	// 传值字段
	private String title;// 测试名称
	private String studentId;// 学生id
	private String exam_detail_id;
	private String examType;
	private String examId;
	private String examDetailId;
	private String state;
	private String rightCount;
	private String totalCount;
	private String isMark;

	public String getIsMark() {
		return isMark;
	}

	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}

	public String getRightCount() {
		return rightCount;
	}

	public void setRightCount(String rightCount) {
		this.rightCount = rightCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getExamDetailId() {
		return examDetailId;
	}

	public void setExamDetailId(String examDetailId) {
		this.examDetailId = examDetailId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public String getExam_detail_id() {
		return exam_detail_id;
	}

	public void setExam_detail_id(String exam_detail_id) {
		this.exam_detail_id = exam_detail_id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StudentTask() {
		super();
	}

	public StudentTask(String id) {
		super(id);
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public StudentExamDetail getExamDetail() {
		return examDetail;
	}

	public void setExamDetail(StudentExamDetail examDetail) {
		this.examDetail = examDetail;
	}

	@Length(min = 0, max = 32, message = "测验ID长度必须介于 0 和 32 之间")
	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public String getIsSubmit() {
		return isSubmit;
	}

	public void setIsSubmit(String isSubmit) {
		this.isSubmit = isSubmit;
	}

	@Length(min = 0, max = 11, message = "测验总分长度必须介于 0 和 11 之间")
	public String getTotalfraction() {
		return totalfraction;
	}

	public void setTotalfraction(String totalfraction) {
		this.totalfraction = totalfraction;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSubmittime() {
		return submittime;
	}

	public void setSubmittime(Date submittime) {
		this.submittime = submittime;
	}

}