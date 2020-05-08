package com.thinkgem.jeesite.modules.questionlib.dto;

import org.hibernate.validator.constraints.Length;

public class MessageStudentDTO {
	private static final long serialVersionUID = 1L;
	private String id;
	private String message; // message
	private String teacherId; // teacher_id
	private String studentId; // student_id
	String examId;
	private Integer status; // 0:未读;1:已读

	private String examTitle;
	private String teacherName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	@Length(min = 0, max = 32, message = "teacher_id长度必须介于 0 和 32 之间")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	@Length(min = 0, max = 32, message = "student_id长度必须介于 0 和 32 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Length(min = 0, max = 11, message = "0:未读;1:已读长度必须介于 0 和 11 之间")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
