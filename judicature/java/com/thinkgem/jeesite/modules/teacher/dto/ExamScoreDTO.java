package com.thinkgem.jeesite.modules.teacher.dto;

import com.thinkgem.jeesite.modules.student.entity.StudentTask;

public class ExamScoreDTO {
	private String examId;
	private String examTitle;
	private StudentTask studentTask;
	private String score;
	private String ranking;	//学生的名次
	public String getExamTitle() {
		return examTitle;
	}
	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public StudentTask getStudentTask() {
		return studentTask;
	}
	public void setStudentTask(StudentTask studentTask) {
		this.studentTask = studentTask;
	}
	public ExamScoreDTO() {
		super();
	}
	public ExamScoreDTO(String examId, String examTitle, StudentTask studentTask, String score, String ranking) {
		super();
		this.examId = examId;
		this.examTitle = examTitle;
		this.studentTask = studentTask;
		this.score = score;
		this.ranking = ranking;
	}
	@Override
	public String toString() {
		return "ExamScoreDTO [examId=" + examId + ", examTitle=" + examTitle + ", studentTask=" + studentTask
				+ ", score=" + score + ", ranking=" + ranking + "]";
	}
	
}
