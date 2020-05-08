package com.thinkgem.jeesite.modules.web.entity;

/**
 * 用户记录基础实体
 */
public class UserRecord {
	
	
	/**
	 * 唯一标识
	 */
	private String id;
	
	/**
	 * app 用户答题json 记录
	 */
	private String json;
	
	/**
	 * 用户Id
	 */
	private String userId;
	
	/**
	 * 课程Id
	 */
	private String  courseId;
	/**
	 * 知识点一级Id
	 */
	private String  knowFirst;
	/**
	 * 知识点二级Id
	 */
	private String  knowSecond;
	
	private String watchTime;
	private String sourceId;
	
	
	public String getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(String watchTime) {
		this.watchTime = watchTime;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getKnowFirst() {
		return knowFirst;
	}
	public void setKnowFirst(String knowFirst) {
		this.knowFirst = knowFirst;
	}
	public String getKnowSecond() {
		return knowSecond;
	}
	public void setKnowSecond(String knowSecond) {
		this.knowSecond = knowSecond;
	}
     
}
