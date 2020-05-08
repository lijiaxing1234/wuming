/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 知识点Entity
 * @author webcat
 * @version 2016-08-16
 */
public class CourseKnowledge extends TreeEntity<CourseKnowledge> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 名称
	private String knowledgeCode;	//知识点编号(firstLevel+weightValue+secondLevel+thirdLevel+testLevel: 一级知识点-xxx 00 000, 二级知识点-xxx xx 000,三级知识点-xxx xx xxx)
	private String creditHours;		//一级知识点学时
	private String versionId;		// 版本
	private String level;		// 难度
	
	
	private String firstLevel;		//一级知识点编号
	private String secondLevel;		//二级知识点编号
	private String thirdLevel;		//三级知识点编号
	
	private int count1;

	private Date startDt;
	private Date endDt;
	
	private int count2;
	
 

	private String specialtyId;
	String specialtyName;
	
	private String courseVesionId ;
	String courseVersionName;
	
	private String courseId ;
	String courseName;
	
	
	private Integer sort; 	// 排序
	
	private int recommend;
	
	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	

	public String getCourseVesionId() {
		return courseVesionId;
	}

	public void setCourseVesionId(String courseVesionId) {
		this.courseVesionId = courseVesionId;
	}
	
	public String getCourseVersionName() {
		return courseVersionName;
	}
	public void setCourseVersionName(String courseVersionName) {
		this.courseVersionName = courseVersionName;
	}
	

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCount2() {
		return count2;
	}

	public void setCount2(int count2) {
		this.count2 = count2;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public int getCount1() {
		return count1;
	}

	public void setCount1(int count1) {
		this.count1 = count1;
	}

	public CourseKnowledge() {
		super();
	}

	public CourseKnowledge(String id){
		super(id);
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getKnowledgeCode() {
		if(StringUtils.isBlank(knowledgeCode) && StringUtils.isNotBlank(firstLevel) && StringUtils.isNotBlank(secondLevel) && StringUtils.isNotBlank(thirdLevel)){
			knowledgeCode = firstLevel+secondLevel+thirdLevel;
		}
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		if(StringUtils.isBlank(knowledgeCode) && StringUtils.isNotBlank(firstLevel) && StringUtils.isNotBlank(secondLevel) && StringUtils.isNotBlank(thirdLevel)){
			knowledgeCode = firstLevel+secondLevel+thirdLevel;
		}
		this.knowledgeCode = knowledgeCode;
	}

	public String getCreditHours() {
		return creditHours;
	}

	public void setCreditHours(String creditHours) {
		this.creditHours = creditHours;
	}

	public String getFirstLevel() {
		if(StringUtils.isBlank(firstLevel) && StringUtils.isNotBlank(knowledgeCode) &&knowledgeCode.length()==6){
			firstLevel = knowledgeCode.substring(0, 2);
		}
		return firstLevel;
	}

	public void setFirstLevel(String firstLevel) {
		if(StringUtils.isBlank(firstLevel)){
			firstLevel = "00";
		}else if(firstLevel.toCharArray().length==1){
			firstLevel = "0" + firstLevel;
		}
		this.firstLevel = firstLevel;
	}

	/*public String getWeightValue() {
		if(StringUtils.isBlank(weightValue) && StringUtils.isNotBlank(knowledgeCode)){
			weightValue = knowledgeCode.substring(2, 3);
		}
		return weightValue;
	}

	public void setWeightValue(String weightValue) {
		if("1".equals(weightValue)){
			weightValue = "A";
		}else if("2".equals(weightValue)){
			weightValue = "B";
		}else if("3".equals(weightValue)){
			weightValue = "C";
		}else{
			//weightValue = "0";
		}
		this.weightValue = weightValue;
	}*/

	public String getSecondLevel() {
		if(StringUtils.isBlank(secondLevel) && StringUtils.isNotBlank(knowledgeCode) &&knowledgeCode.length()==6){
			secondLevel = knowledgeCode.substring(2, 4);
		}
		return secondLevel;
	}

	public void setSecondLevel(String secondLevel) {
		if(StringUtils.isBlank(secondLevel)){
			secondLevel = "00";
		}else if(secondLevel.toCharArray().length==1){
			secondLevel = "0" + secondLevel;
		}
		this.secondLevel = secondLevel;
	}

	public String getThirdLevel() {
		if(StringUtils.isBlank(thirdLevel) && StringUtils.isNotBlank(knowledgeCode) &&knowledgeCode.length()==6){
			thirdLevel = knowledgeCode.substring(4, 6);
		}
		return thirdLevel;
	}

	public void setThirdLevel(String thirdLevel) {
		if(StringUtils.isBlank(thirdLevel)){
			thirdLevel = "00";
		}else if(thirdLevel.toCharArray().length==1){
			thirdLevel = "0" + thirdLevel;
		}
		this.thirdLevel = thirdLevel;
	}

	/*public String getTestLevel() {
		if(StringUtils.isBlank(testLevel) &&StringUtils.isNotBlank(knowledgeCode)){
			testLevel = knowledgeCode.substring(7, 8);
		}
		return testLevel;
	}

	public void setTestLevel(String testLevel) {
		if("1".equals(testLevel)){
			testLevel = "X";
		}else if("2".equals(testLevel)){
			testLevel = "Y";
		}else if("3".equals(testLevel)){
			testLevel = "Z";
		}else{
			//testLevel = "4";
		}
		this.testLevel = testLevel;
	}
*/
	@Length(min=0, max=32, message="版本长度必须介于 0 和 32 之间")
	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	
	@Length(min=0, max=10, message="难度长度必须介于 0 和 10 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		if("X".equals(level) || "A".equals(level) || "掌握".equals(level)){
			level = "1";
		}else if("Y".equals(level) || "B".equals(level) || "熟悉".equals(level)){
			level = "2";
		}else if("Z".equals(level) || "C".equals(level) || "了解".equals(level)){
			level = "3";
		}else{
			
		}
		this.level = level;
	}
	
	@JsonBackReference
	public CourseKnowledge getParent() {
		return parent;
	}

	public void setParent(CourseKnowledge parent) {
		this.parent = parent;
	}
	
	/*@Length(min=0, max=2000, message="parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	*/
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}