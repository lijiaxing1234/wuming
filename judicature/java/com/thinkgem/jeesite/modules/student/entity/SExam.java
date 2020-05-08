package com.thinkgem.jeesite.modules.student.entity;

import java.util.Date;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseVesion;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 考试entity
 * 
 * @author .36
 *
 */
public class SExam extends DataEntity<SExam> {

	private static final long serialVersionUID = 1L;
	private String title;
	private String examType;
	private User teacher;
	private CourseVesion version;
	private String examPlace;
	private Date beginTime;
	private Date endTime;
	private String isab;
	private String totalScore;
	private String isNew;
	private String examMode;
	private Float questionRadio;
	private Float questionMultiple;
	private Float questionBlank;
	private Float questionShortAnswer;
	private Float questionCompute;
	private String remarks;
	private String difficult;
	private String simple;
	private String general;
	private String examHours;
	private String score;
	private String examDetailId;
	private String rightCount;
	private String totalCount;
	private String isMark;

	public String getIsMark() {
		return isMark;
	}

	public void setIsMark(String isMark) {
		this.isMark = isMark;
	}

	public String getRightCount() {
		return rightCount;
	}

	public void setRightCount(String rightCount) {
		this.rightCount = rightCount;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getExamDetailId() {
		return examDetailId;
	}

	public void setExamDetailId(String examDetailId) {
		this.examDetailId = examDetailId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	// =========以下为前台页面需要展示的字段=========
	private String residueTime; // 现在距离考试结束所剩余时间 若计算结果为负数则显示已经错过考试时间 单位：分钟
	private String testPaperType;
	private String durationTime; // 考试时长 endTime - beginTime
	private String courseName;
	// 传值需要
	private Date firstTime;
	private Date secondTime;
	private String versionId;
	private String studentId;
	private String examStatus; // 2:錯過考試 3：可以正常參加考試
	private String quizExamStatus;

	public String getQuizExamStatus() {
		return quizExamStatus;
	}

	public void setQuizExamStatus(String quizExamStatus) {
		this.quizExamStatus = quizExamStatus;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(String durationTime) {
		this.durationTime = durationTime;
	}

	public String getTestPaperType() {
		return testPaperType;
	}

	public void setTestPaperType(String testPaperType) {
		this.testPaperType = testPaperType;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public CourseVesion getVersion() {
		return version;
	}

	public void setVersion(CourseVesion version) {
		this.version = version;
	}

	public String getExamPlace() {
		return examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsab() {
		return isab;
	}

	public void setIsab(String isab) {
		this.isab = isab;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getExamMode() {
		return examMode;
	}

	public void setExamMode(String examMode) {
		this.examMode = examMode;
	}

	public Float getQuestionRadio() {
		return questionRadio;
	}

	public void setQuestionRadio(Float questionRadio) {
		this.questionRadio = questionRadio;
	}

	public Float getQuestionMultiple() {
		return questionMultiple;
	}

	public void setQuestionMultiple(Float questionMultiple) {
		this.questionMultiple = questionMultiple;
	}

	public Float getQuestionBlank() {
		return questionBlank;
	}

	public void setQuestionBlank(Float questionBlank) {
		this.questionBlank = questionBlank;
	}

	public Float getQuestionShortAnswer() {
		return questionShortAnswer;
	}

	public void setQuestionShortAnswer(Float questionShortAnswer) {
		this.questionShortAnswer = questionShortAnswer;
	}

	public Float getQuestionCompute() {
		return questionCompute;
	}

	public void setQuestionCompute(Float questionCompute) {
		this.questionCompute = questionCompute;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDifficult() {
		return difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public String getSimple() {
		return simple;
	}

	public void setSimple(String simple) {
		this.simple = simple;
	}

	public String getGeneral() {
		return general;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public String getExamHours() {
		return examHours;
	}

	public void setExamHours(String examHours) {
		this.examHours = examHours;
	}

	public String getResidueTime() {
		return residueTime;
	}

	public void setResidueTime(String residueTime) {
		this.residueTime = residueTime;
	}

}
