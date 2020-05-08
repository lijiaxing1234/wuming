/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dto;

import java.util.Date;

import net.sourceforge.jtds.jdbc.DateTime;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;

/**
 * 知识点 
 * @author cq
 * @version 2016-10-11
 */
public class KnowledgePointsDTO extends TreeEntity<KnowledgePointsDTO> {
	
	

	private static final long serialVersionUID = 1L;
	private CourseKnowledge  courseKnowledge; //课程知识点Id
	

	private int count1;
	public int getCount1() {
		return count1;
	}

	public CourseKnowledge getCourseKnowledge() {
		return courseKnowledge;
	}

	public void setCourseKnowledge(CourseKnowledge courseKnowledge) {
		this.courseKnowledge = courseKnowledge;
	}
	public void setCount1(int count1) {
		this.count1 = count1;
	}

	private Date startDt;
	private Date endDt;
 

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
	
	public KnowledgePointsDTO() {
		super();
	}

	public KnowledgePointsDTO(String id){
		super(id);
	}

 
	
	@JsonBackReference
	public KnowledgePointsDTO getParent() {
		return parent;
	}

	public void setParent(KnowledgePointsDTO parent) {
		this.parent = parent;
	}
	
	 
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}