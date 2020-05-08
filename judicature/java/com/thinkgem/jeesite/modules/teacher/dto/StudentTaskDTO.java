package com.thinkgem.jeesite.modules.teacher.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

public class StudentTaskDTO extends DataEntity<StudentTaskDTO>{
	private static final long serialVersionUID = 1L;
	private String studentId;
	@ExcelField(title = "学生姓名")
	private String name; // 学生姓名
	private String examClassId;//班级id
	@ExcelField(title = "学号")
	private String stdCode; // 学号
	@ExcelField(title = "电话号码")
	private String stdPhone; // 电话号
	private String examid; // 测验ID
	private String title;// 测试名称
	private String exam_detail_id; //考试详情
	@ExcelField(title = "总成绩")
	private Float score; // 总成绩
	//为了在线考试成绩的导出
	private String onLineScore;//线上成绩
	private Integer unSubmitCount;//未提交作业的次数
	private Integer unClassWork;//未随堂测试的次数
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExamClassId() {
		return examClassId;
	}
	public void setExamClassId(String examClassId) {
		this.examClassId = examClassId;
	}
	public String getStdCode() {
		return stdCode;
	}
	public void setStdCode(String stdCode) {
		this.stdCode = stdCode;
	}
	public String getStdPhone() {
		return stdPhone;
	}
	public void setStdPhone(String stdPhone) {
		this.stdPhone = stdPhone;
	}
	public String getExamid() {
		return examid;
	}
	public void setExamid(String examid) {
		this.examid = examid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExam_detail_id() {
		return exam_detail_id;
	}
	public void setExam_detail_id(String exam_detail_id) {
		this.exam_detail_id = exam_detail_id;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getOnLineScore() {
		return onLineScore;
	}
	public void setOnLineScore(String onLineScore) {
		this.onLineScore = onLineScore;
	}
	public Integer getUnSubmitCount() {
		return unSubmitCount;
	}
	public void setUnSubmitCount(Integer unSubmitCount) {
		this.unSubmitCount = unSubmitCount;
	}
	public Integer getUnClassWork() {
		return unClassWork;
	}
	public void setUnClassWork(Integer unClassWork) {
		this.unClassWork = unClassWork;
	}
}
