package com.thinkgem.jeesite.modules.teacher.dto;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;


public class StudentQuestionDTO extends DataEntity<StudentQuestionDTO>{

	private static final long serialVersionUID = 1L;
	
	private Student student;
	private StudentAnswer studentAnswer;
	private TeacherVersionQuestion teacherVersionQuestion;
	private String totalScore;//总成绩
	private String onLineScore;//线上成绩
	private Integer unSubmitCount;//未提交作业的次数
	private Integer unClassWork;//未随堂测试的次数
	private String errorPercent;//某道题的本班错误率
	private Integer classPerson;//班级共有多少人
	private Integer classError;//该题班级共有多少人答错
	private Date submitTime;//提交作业时间
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public StudentAnswer getStudentAnswer() {
		return studentAnswer;
	}
	public void setStudentAnswer(StudentAnswer studentAnswer) {
		this.studentAnswer = studentAnswer;
	}
	public TeacherVersionQuestion getTeacherVersionQuestion() {
		return teacherVersionQuestion;
	}
	public void setTeacherVersionQuestion(TeacherVersionQuestion teacherVersionQuestion) {
		this.teacherVersionQuestion = teacherVersionQuestion;
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
	
	public Integer getClassPerson() {
		return classPerson;
	}
	public void setClassPerson(Integer classPerson) {
		this.classPerson = classPerson;
	}
	public Integer getClassError() {
		return classError;
	}
	public void setClassError(Integer classError) {
		this.classError = classError;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
	
	public String getErrorPercent() {
		return errorPercent;
	}
	public void setErrorPercent(String errorPercent) {
		this.errorPercent = errorPercent;
	}
	public StudentQuestionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentQuestionDTO(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public StudentQuestionDTO(Student student, StudentAnswer studentAnswer,
			TeacherVersionQuestion teacherVersionQuestion, String totalScore, String onLineScore, Integer unSubmitCount,
			Integer unClassWork, String errorPercent, Integer classPerson, Integer classError, Date submitTime) {
		super();
		this.student = student;
		this.studentAnswer = studentAnswer;
		this.teacherVersionQuestion = teacherVersionQuestion;
		this.totalScore = totalScore;
		this.onLineScore = onLineScore;
		this.unSubmitCount = unSubmitCount;
		this.unClassWork = unClassWork;
		this.errorPercent = errorPercent;
		this.classPerson = classPerson;
		this.classError = classError;
		this.submitTime = submitTime;
	}
	@Override
	public String toString() {
		return "StudentQuestionDTO [student=" + student + ", studentAnswer=" + studentAnswer
				+ ", teacherVersionQuestion=" + teacherVersionQuestion + ", totalScore=" + totalScore + ", onLineScore="
				+ onLineScore + ", unSubmitCount=" + unSubmitCount + ", unClassWork=" + unClassWork + ", errorPercent="
				+ errorPercent + ", classPerson=" + classPerson + ", classError=" + classError + ", submitTime="
				+ submitTime + "]";
	}
}
