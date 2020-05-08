package com.thinkgem.jeesite.modules.teacher.dto;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;

public class ExamStudentDTO extends DataEntity<ExamStudentDTO>{
	//private StudentAnswer studentAnswer;//学生的回答情况
	private Exam exam;				//测试信息
	private Student student;		//学生情况
	private Integer totalTitle;		//总题数
	private Integer totalRight;		//该学生的答对数
	private String rightPercent;	//正确率
	private Integer totalError;		//错误数
	private Date submitTime;		//该学生的作业提交时间
	private String detailQuestion;	//该学生该题的回答情况
	private Integer totalPerson;	//总人数
	private String tableClassId;   //学生班级Id
	
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Integer getTotalTitle() {
		return totalTitle;
	}
	public void setTotalTitle(Integer totalTitle) {
		this.totalTitle = totalTitle;
	}
	public Integer getTotalRight() {
		return totalRight;
	}
	public void setTotalRight(Integer totalRight) {
		this.totalRight = totalRight;
	}
	
	public String getRightPercent() {
		return rightPercent;
	}
	public void setRightPercent(String rightPercent) {
		this.rightPercent = rightPercent;
	}
	public Integer getTotalError() {
		return totalError;
	}
	public void setTotalError(Integer totalError) {
		this.totalError = totalError;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getDetailQuestion() {
		return detailQuestion;
	}
	public void setDetailQuestion(String detailQuestion) {
		this.detailQuestion = detailQuestion;
	}
	public Integer getTotalPerson() {
		return totalPerson;
	}
	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}
	public String getTableClassId() {
		return tableClassId;
	}
	public void setTableClassId(String tableClassId) {
		this.tableClassId = tableClassId;
	}
	public ExamStudentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ExamStudentDTO(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public ExamStudentDTO(Exam exam, Student student, Integer totalTitle, Integer totalRight, String rightPercent,
			Integer totalError, Date submitTime, String detailQuestion, Integer totalPerson, String tableClassId) {
		super();
		this.exam = exam;
		this.student = student;
		this.totalTitle = totalTitle;
		this.totalRight = totalRight;
		this.rightPercent = rightPercent;
		this.totalError = totalError;
		this.submitTime = submitTime;
		this.detailQuestion = detailQuestion;
		this.totalPerson = totalPerson;
		this.tableClassId = tableClassId;
	}
	@Override
	public String toString() {
		return "ExamStudentDTO [exam=" + exam + ", student=" + student + ", totalTitle=" + totalTitle + ", totalRight="
				+ totalRight + ", rightPercent=" + rightPercent + ", totalError=" + totalError + ", submitTime="
				+ submitTime + ", detailQuestion=" + detailQuestion + ", totalPerson=" + totalPerson + ", tableClassId="
				+ tableClassId + "]";
	}
	
	
}
