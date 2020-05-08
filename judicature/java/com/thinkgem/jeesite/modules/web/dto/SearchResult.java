package com.thinkgem.jeesite.modules.web.dto;

import com.thinkgem.jeesite.modules.web.entity.Resource;

/**
 * app 视频资源搜索返回实体
 *
 */
public class SearchResult extends Resource {
	
	
	/* 视频资源所属的分类 */
	
	private String catId;
	private String catName;
	private String catImage;

	/* 视频资源所属的分类的上级 */
	
	private String parentId;
	private String parentName;
	private String parentImage;
	
	
	private Integer start;
	private Integer pageSize;

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getCatImage() {
		return catImage;
	}

	public void setCatImage(String catImage) {
		this.catImage = catImage;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentImage() {
		return parentImage;
	}

	public void setParentImage(String parentImage) {
		this.parentImage = parentImage;
	}
   
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
