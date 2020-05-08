/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.thinkgem.jeesite.common.persistence.TreeEntity;
import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 专业Entity
 * @author webcat
 * @version 2016-10-08
 */
public class Specialty extends TreeEntity<Specialty> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 名称
	private String desciption;		// 描述
	private String specialtyCode;		//专业代码
	
	public Specialty() {
		super();
	}

	public Specialty(String id){
		super(id);
	}

	@ExcelField(title="专业名称", align=2, sort=1)
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=1000, message="描述长度必须介于 0 和 1000 之间")
	public String getDesciption() {
		return desciption;
	}

	public void setDesciption(String desciption) {
		this.desciption = desciption;
	}

	public Specialty getParent() {
		return parent;
	}

	public void setParent(Specialty parent) {
		this.parent = parent;
	}

	public String getSpecialtyCode() {
		return specialtyCode;
	}

	public void setSpecialtyCode(String specialtyCode) {
		this.specialtyCode = specialtyCode;
	}

	public void setParentId(String id){
		if (parent == null){
			parent = new Specialty();
		}
		parent.setId(id);
	}
}