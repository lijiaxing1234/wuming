package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;

public class TeacherStudent extends Student{
	private static final long serialVersionUID=1L;
	private Exam exam;
	private String teacherId; //任课老师id
	public Exam getExam() {
		return exam;
	}
	public void setExam(Exam exam) {
		this.exam = exam;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public TeacherStudent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TeacherStudent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public TeacherStudent(Exam exam, String teacherId) {
		super();
		this.exam = exam;
		this.teacherId = teacherId;
	}
	@Override
	public String toString() {
		return "TeacherStudent [exam=" + exam + ", teacherId=" + teacherId + "]";
	}
	
	
}
