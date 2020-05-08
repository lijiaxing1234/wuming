/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.sys.entity.User;

/**
 * 学生评论Entity
 * @author yfl
 * @version 2016-09-10
 */
public class StudentReview extends DataEntity<StudentReview> {
	
	private static final long serialVersionUID = 1L;
	private String teacherId;		// 老师id
	private String studentId;		// 学生id
	private String content;		// 评论内容
	private Date crateTime;		// 创建时间
	private String isShow;		// 是否显示：0不显示 1显示
	private User teacher;
	
	public StudentReview() {
		super();
	}

	public StudentReview(String id){
		super(id);
	}

	@Length(min=1, max=32, message="老师id长度必须介于 1 和 32 之间")
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	@Length(min=1, max=32, message="学生id长度必须介于 1 和 32 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=0, max=1000, message="评论内容长度必须介于 0 和 1000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCrateTime() {
		return crateTime;
	}

	public void setCrateTime(Date crateTime) {
		this.crateTime = crateTime;
	}
	
	@Length(min=0, max=1, message="是否显示：0不显示 1显示长度必须介于 0 和 1 之间")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
}