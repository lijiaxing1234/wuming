package com.thinkgem.jeesite.modules.web.entity;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 发现栏目
 */
public class ResCategory {
	
	
	private String id;
	private String name;
	private String description; //描述
	private String persenter;//主讲者
	private Double price;//价格
	private String image;//封面
	private Integer type;//分类类型：0 免费  1 为你推荐  2直播课堂
	private String parentId;//父级id
	private String parentIds;//父级id集合
	private Long playNum;//播放次数
	
	private ResCategory parent;//资源父级
	private Integer resCount;//该分类下共多少资源
	
	private List<Resource> res=Lists.newArrayList();
	private List<ResCategory> resCategory=Lists.newArrayList();
	
    private String  courseName ;  //课程名
    
    private String author ; //作者
    
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPersenter() {
		return persenter;
	}
	public void setPersenter(String persenter) {
		this.persenter = persenter;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public Long getPlayNum() {
		return playNum;
	}
	public void setPlayNum(Long playNum) {
		this.playNum = playNum;
	}
	public ResCategory getParent() {
		return parent;
	}
	public void setParent(ResCategory parent) {
		this.parent = parent;
	}
	public Integer getResCount() {
		return resCount;
	}
	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}
	public List<Resource> getRes() {
		return res;
	}
	public void setRes(List<Resource> res) {
		this.res = res;
	}
	public List<ResCategory> getResCategory() {
		return resCategory;
	}
	public void setResCategory(List<ResCategory> resCategory) {
		this.resCategory = resCategory;
	}
	
	
	

}
