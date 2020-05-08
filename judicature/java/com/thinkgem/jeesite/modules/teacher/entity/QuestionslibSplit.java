package com.thinkgem.jeesite.modules.teacher.entity;

import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;

/**
 * 试题分库表
 * @author flychao
 */
public class QuestionslibSplit extends VersionQuestion {
	
	
	private static final long serialVersionUID = 1L;
	
	
	private  String questionId;    //试题的Id
	private String teacherId;      //老师的Id
	private String schoolId;       //学校的
	private String versionId;      //课程的版本
	private String type;           //0:表示全部都可用 1:考试或组卷 2:练习题
	
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
	
