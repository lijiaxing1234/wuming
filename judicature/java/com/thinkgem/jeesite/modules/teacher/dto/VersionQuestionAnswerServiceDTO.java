package com.thinkgem.jeesite.modules.teacher.dto;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.student.entity.StudentAnswer;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherVersionQuestion;


public class VersionQuestionAnswerServiceDTO extends DataEntity<VersionQuestionAnswerServiceDTO>{

	private static final long serialVersionUID = 1L;

	private StudentAnswer studentAnswer;//学生答案
	private VersionQuestion versionQuestion;//问题
	
	
	public StudentAnswer getStudentAnswer() {
		return studentAnswer;
	}
	public void setStudentAnswer(StudentAnswer studentAnswer) {
		this.studentAnswer = studentAnswer;
	}
	public VersionQuestion getVersionQuestion() {
		return versionQuestion;
	}
	public void setVersionQuestion(VersionQuestion versionQuestion) {
		this.versionQuestion = versionQuestion;
	}
	public VersionQuestionAnswerServiceDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public VersionQuestionAnswerServiceDTO(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public VersionQuestionAnswerServiceDTO(StudentAnswer studentAnswer, VersionQuestion versionQuestion) {
		super();
		this.studentAnswer = studentAnswer;
		this.versionQuestion = versionQuestion;
	}
	@Override
	public String toString() {
		return "VersionQuestionAnswerServiceDTO [studentAnswer=" + studentAnswer + ", versionQuestion="
				+ versionQuestion + "]";
	}
	


}
