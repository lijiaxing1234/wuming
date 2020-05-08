package com.thinkgem.jeesite.modules.resource.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class TableCategory extends DataEntity<TableCategory>{

	private static final long serialVersionUID = 1L;
	private String name;
	private String description; //描述
	private String persenter;//主讲者
	private Double price;//价格
	private String image;//封面
	private Integer type;//分类类型：0 免费  1 为你推荐  2直播课堂
	private String parentId;//父级id
	private String parentIds;//父级id集合
	private Long playNum;//播放次数
	private String courseId;//栏目所属课程
	
	private TableCategory parent;//资源父级
	private Integer resCount;//该分类下共多少资源

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

	public Integer getResCount() {
		return resCount;
	}

	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}

	public TableCategory getParent() {
		return parent;
	}

	public void setParent(TableCategory parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "TableCategory [name=" + name + ", description=" + description + ", persenter=" + persenter + ", price="
				+ price + ", image=" + image + ", type=" + type + ", parentId=" + parentId + ", parentIds=" + parentIds
				+ ", playNum=" + playNum + ", parent=" + parent + ", resCount=" + resCount + "]";
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	
	
}
