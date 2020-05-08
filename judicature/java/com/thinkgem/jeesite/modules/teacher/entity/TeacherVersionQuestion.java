/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 试题Entity
 */
public class TeacherVersionQuestion extends DataEntity<TeacherVersionQuestion> {
	
	private static final long serialVersionUID = 1L;
	private String examCode;		// 考点
	private String quesType;		// 题型
	private String quesLevel;		// 难度
	private String quesPoint;		// 分值
	private String title;		// 题目
	private String answer;		// 答案
	private String answer1;		
	private String answer2;		
	private String answer3;		
	private String answer4;		
	private String answer5;		
	private String answer6;		
	private String answer7;	
	private String answer8;		
	private String answer9;		
	private String description;		// 讲解
	private String writer;		// 命题人
	private String checker;		// 审题人
	private Office office;		// 单位
	private String tearchId;		// 所属老师
	private String versionId;		// 版本
	private String questionlibId;  //题库
	private Date createTime; //创建时间                           缺少来源字段？？？？？
	
	private CourseKnowledge courseKnowledge;  //知识点
	
	private Date submitTime;		//学生提交作业时间
	
	private Student student;//学生表包含班级表
	
	//为了随堂练习
	private String rightPercent;//正确率
	private Integer isRight;//是否正确
	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public String getQuesType() {
		return quesType;
	}

	public void setQuesType(String quesType) {
		this.quesType = quesType;
	}

	public String getQuesLevel() {
		return quesLevel;
	}

	public void setQuesLevel(String quesLevel) {
		this.quesLevel = quesLevel;
	}

	public String getQuesPoint() {
		return quesPoint;
	}

	public void setQuesPoint(String quesPoint) {
		this.quesPoint = quesPoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getTearchId() {
		return tearchId;
	}

	public void setTearchId(String tearchId) {
		this.tearchId = tearchId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getQuestionlibId() {
		return questionlibId;
	}

	public void setQuestionlibId(String questionlibId) {
		this.questionlibId = questionlibId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}

	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getRightPercent() {
		return rightPercent;
	}

	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getAnswer5() {
		return answer5;
	}

	public void setAnswer5(String answer5) {
		this.answer5 = answer5;
	}

	public String getAnswer6() {
		return answer6;
	}

	public void setAnswer6(String answer6) {
		this.answer6 = answer6;
	}

	public String getAnswer7() {
		return answer7;
	}

	public void setAnswer7(String answer7) {
		this.answer7 = answer7;
	}

	public String getAnswer8() {
		return answer8;
	}

	public void setAnswer8(String answer8) {
		this.answer8 = answer8;
	}

	public String getAnswer9() {
		return answer9;
	}

	public void setAnswer9(String answer9) {
		this.answer9 = answer9;
	}

	public TeacherVersionQuestion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherVersionQuestion(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public TeacherVersionQuestion(String examCode, String quesType, String quesLevel, String quesPoint, String title,
			String answer, String answer1, String answer2, String answer3, String answer4, String answer5,
			String answer6, String answer7, String answer8, String answer9, String description, String writer,
			String checker, Office office, String tearchId, String versionId, String questionlibId, Date createTime,
			CourseKnowledge courseKnowledge, Date submitTime, Student student, String rightPercent, Integer isRight) {
		super();
		this.examCode = examCode;
		this.quesType = quesType;
		this.quesLevel = quesLevel;
		this.quesPoint = quesPoint;
		this.title = title;
		this.answer = answer;
		this.answer1 = answer1;
		this.answer2 = answer2;
		this.answer3 = answer3;
		this.answer4 = answer4;
		this.answer5 = answer5;
		this.answer6 = answer6;
		this.answer7 = answer7;
		this.answer8 = answer8;
		this.answer9 = answer9;
		this.description = description;
		this.writer = writer;
		this.checker = checker;
		this.office = office;
		this.tearchId = tearchId;
		this.versionId = versionId;
		this.questionlibId = questionlibId;
		this.createTime = createTime;
		this.courseKnowledge = courseKnowledge;
		this.submitTime = submitTime;
		this.student = student;
		this.rightPercent = rightPercent;
		this.isRight = isRight;
	}

	@Override
	public String toString() {
		return "TeacherVersionQuestion [examCode=" + examCode + ", quesType=" + quesType + ", quesLevel=" + quesLevel
				+ ", quesPoint=" + quesPoint + ", title=" + title + ", answer=" + answer + ", answer1=" + answer1
				+ ", answer2=" + answer2 + ", answer3=" + answer3 + ", answer4=" + answer4 + ", answer5=" + answer5
				+ ", answer6=" + answer6 + ", answer7=" + answer7 + ", answer8=" + answer8 + ", answer9=" + answer9
				+ ", description=" + description + ", writer=" + writer + ", checker=" + checker + ", office=" + office
				+ ", tearchId=" + tearchId + ", versionId=" + versionId + ", questionlibId=" + questionlibId
				+ ", createTime=" + createTime + ", courseKnowledge=" + courseKnowledge + ", submitTime=" + submitTime
				+ ", student=" + student + ", rightPercent=" + rightPercent + ", isRight=" + isRight + "]";
	}

	

	
}