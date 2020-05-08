package com.thinkgem.jeesite.modules.teacher.dto;

import java.util.Date;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.questionlib.entity.Course;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;

public class Exam2{
	
	private String id;
	private String title;//作业名称
	private String examType;//类型：1随堂测 2组卷考试 3课后作业 4例题  5在线考试
	
	private String teacherId;//教师id
	private String versionId;//课程版本id
	
	private String examPlace;//考点
	private Date beginTime;//开始时间
	private Date endTime;//结束时间
	private Date publishAnswerTime;//公布答案时间
	private String isab;//是否是ab卷
	private String total_score;//总分数
	private String level;//难易级别
	private Date time;//最后操作的时间
	
	private TableClass examClass;//班级
	private Course examCourse;//所属课程
	private String status; //状态

	private String  isNew;  //是否是新建 1:新建0:调用模板  2:保存为模板
	private String  examMode; //组卷方式1:手动组卷0:自动组卷
	
	private Integer totalPerson;	//随堂管理中的预答题人数
	private Integer unSubmitPerson; //未答题人数
	private String isTeacher; //是否判卷
	private String istemplate; //0:不是模板；1：是模板；默认为0
	private String useTemplatId;//调用模板的id
	
	private String ranking;//统计分析中学生的名次
	protected Map<String, String> sqlMap;
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
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
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
	public String getTotal_score() {
		return total_score;
	}
	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public TableClass getExamClass() {
		return examClass;
	}
	public void setExamClass(TableClass examClass) {
		this.examClass = examClass;
	}
	public Course getExamCourse() {
		return examCourse;
	}
	public void setExamCourse(Course examCourse) {
		this.examCourse = examCourse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Integer getTotalPerson() {
		return totalPerson;
	}
	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}
	public Integer getUnSubmitPerson() {
		return unSubmitPerson;
	}
	public void setUnSubmitPerson(Integer unSubmitPerson) {
		this.unSubmitPerson = unSubmitPerson;
	}
	public String getIstemplate() {
		return istemplate;
	}
	public void setIstemplate(String istemplate) {
		this.istemplate = istemplate;
	}
	public String getUseTemplatId() {
		return useTemplatId;
	}
	public void setUseTemplatId(String useTemplatId) {
		this.useTemplatId = useTemplatId;
	}
	public Date getPublishAnswerTime() {
		return publishAnswerTime;
	}
	public void setPublishAnswerTime(Date publishAnswerTime) {
		this.publishAnswerTime = publishAnswerTime;
	}
	public String getIsTeacher() {
		return isTeacher;
	}
	public void setIsTeacher(String isTeacher) {
		this.isTeacher = isTeacher;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@XmlTransient
	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = Maps.newHashMap();
		}
		return sqlMap;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}
	
}
