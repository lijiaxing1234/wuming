/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 教师Entity
 * 
 * @author hgh
 * @version 2016-09-14
 */
public class Teacher extends DataEntity<Teacher> {

	private static final long serialVersionUID = 1L;
	// 学校编码
	private Office school; // 归属学校
	private String schoolName;
	private Office office; // 归属部门（该老师所属部门）
	@ExcelField(title = "登录名")
	private String loginName; // 登录名
	@ExcelField(title = "登录密码")
	private String password; // 密码
	@ExcelField(title = "工号")
	private String no; // 工号
	@ExcelField(title = "教师姓名")
	private String name; // 姓名
	private String officeId; // 归属部门的id
	@ExcelField(title = "所属部门")
	private String officeName;// 所属部门名称
	@ExcelField(title = "邮箱")
	private String email; // 邮箱
	@ExcelField(title = "电话号码")
	private String phone; // 电话
	@ExcelField(title = "手机号码")
	private String mobile; // 手机
	private String userType; // 用户类型
	private String photo; // 用户头像
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆时间
	private String loginFlag; // 是否可登录
	@ExcelField(title = "备注信息")

	private String remarks;

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Teacher() {
		super();
	}

	public Teacher(String id) {
		super(id);
	}

	@NotNull(message = "归属学校不能为空")
	public Office getSchool() {
		return school;
	}

	public void setSchool(Office school) {
		this.school = school;
	}

	@NotNull(message = "归属部门（该老师所属部门）不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Length(min = 1, max = 100, message = "密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 0, max = 100, message = "工号长度必须介于 0 和 100 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 200, message = "邮箱长度必须介于 0 和 200 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 0 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "手机长度必须介于 0 和 200 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min = 0, max = 1, message = "用户类型长度必须介于 0 和 1 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Length(min = 0, max = 1000, message = "用户头像长度必须介于 0 和 1000 之间")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Length(min = 0, max = 100, message = "最后登陆IP长度必须介于 0 和 100 之间")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@Length(min = 0, max = 64, message = "是否可登录长度必须介于 0 和 64 之间")
	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@Override
	public boolean equals(Object obj) {
		// 登录名 姓名 工号
		if (!(obj instanceof Teacher)) {
			return false;
		}
		Teacher teacher = (Teacher) obj;
		return this.loginName.equals(teacher.getLoginName());
	}

	@Override
	public int hashCode() {
		return this.loginName.hashCode();
	}

}