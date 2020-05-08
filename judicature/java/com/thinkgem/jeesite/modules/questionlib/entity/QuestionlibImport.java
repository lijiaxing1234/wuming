/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.entity;

import org.hibernate.validator.constraints.Length;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.entity.User;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * 试题导入题库-批量导入试题Entity
 * @author ryz
 * @version 2016-09-26
 */
public class QuestionlibImport extends DataEntity<QuestionlibImport> {
	
	private static final long serialVersionUID = 1L;
	private String questionlibId;		// 题库id
	private String specialtyId;		// 专业id
	private Specialty specialty;	//专业
	private String title;		// 导入文档时用户填写的名称
	private String docName;		//导入文档文件名
	private String writer;		// 出题人
	private Office office;	//上传者部门
	private User user;		// 上传人user_id
	private String phone;	//上传者电话
	private Office school;		// 设置学校school_id
	private String coursePhase;		// 适用层次course_phase
	private String filePath;		//导入文档保存路径
	
	
    String  quesImportApplyId;  //申请入库的题库id
	
	
	public QuestionlibImport() {
		super();
	}

	public QuestionlibImport(String id){
		super(id);
	}

	@Length(min=1, max=32, message="题库id长度必须介于 1 和 32 之间")
	public String getQuestionlibId() {
		return questionlibId;
	}

	public void setQuestionlibId(String questionlibId) {
		this.questionlibId = questionlibId;
	}
	
	@Length(min=1, max=32, message="专业id长度必须介于 1 和 32 之间")
	public String getSpecialtyId() {
		if(specialty!=null && StringUtils.isBlank(specialtyId)){
			specialtyId = specialty.getId();
		}
		return specialtyId;
	}

	public void setSpecialtyId(String specialtyId) {
		this.specialtyId = specialtyId;
	}
	
	public Specialty getSpecialty() {
		if(specialty==null && StringUtils.isNotBlank(specialtyId)){
			specialty = new Specialty(specialtyId);
		}
		return specialty;
	}
	
	public void setSpecialty(Specialty specialty) {
		this.specialty = specialty;
	}
	
	@Length(min=1, max=100, message="导入文档名称长度必须介于 1 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=1, max=200, message="导入文档名称长度必须介于 1 和 200 之间")
	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	@Length(min=0, max=32, message="出题人长度必须介于 0 和 32 之间")
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@NotNull(message="上传人user_id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=32, message="设置学校school_id长度必须介于 0 和 32 之间")
	public String getSchoolId() {
		return this.school.getId();
	}

	public void setSchoolId(String schoolId) {
		this.school.setId(schoolId);
	}
	
	public void setSchool(Office school){
		this.school = school;
	}
	
	public Office getSchool(){
		return this.school;
	}
	
	@Length(min=0, max=10, message="适用层次course_phase长度必须介于 0 和 10 之间")
	public String getCoursePhase() {
		return coursePhase;
	}

	public void setCoursePhase(String coursePhase) {
		this.coursePhase = coursePhase;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
  
	/*public void setQuesImportApplyId(String quesImportApplyId) {
		this.quesImportApplyId = quesImportApplyId;
	}*/
	public String getQuesImportApplyId() {
		return quesImportApplyId;
	}
	
}