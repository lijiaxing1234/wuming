package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 试卷
 * 
 * @author .36
 *
 */
public class TestPaper extends DataEntity<TestPaper> {

	private static final long serialVersionUID = 1L;
	private String title; // 名称
	private Date createTime; // 出题时间
	private String schoolName; // 学校名称
	private String courseName; // 课程名称
	private String testPaperName; // 试卷名称
	private String testPaperType; // 试卷类型 在线考试 组卷考试
	private String state; // 状态 已考 未考
	// 前台所搜所用字段
	private Date firstTime;
	private Date secondTime;

	public String getTestPaperName() {
		return testPaperName;
	}

	public void setTestPaperName(String testPaperName) {
		this.testPaperName = testPaperName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTestPaperType() {
		return testPaperType;
	}

	public void setTestPaperType(String testPaperType) {
		this.testPaperType = testPaperType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	public Date getSecondTime() {
		return secondTime;
	}

	public void setSecondTime(Date secondTime) {
		this.secondTime = secondTime;
	}

}
