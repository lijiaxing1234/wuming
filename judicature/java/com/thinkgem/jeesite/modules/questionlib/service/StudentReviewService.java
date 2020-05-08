/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.StudentReview;
import com.thinkgem.jeesite.modules.student.utils.StudentUserUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.StudentReviewDao;

/**
 * 学生评论Service
 * @author yfl
 * @version 2016-09-10
 */
@Service
@Transactional(readOnly = true)
public class StudentReviewService extends CrudService<StudentReviewDao, StudentReview> {

	public StudentReview get(String id) {
		return super.get(id);
	}
	
	public List<StudentReview> findList(StudentReview studentReview) {
		return super.findList(studentReview);
	}
	
	public Page<StudentReview> findPage(Page<StudentReview> page, StudentReview studentReview) {
		return super.findPage(page, studentReview);
	}
	
	@Transactional(readOnly = false)
	public void save(StudentReview studentReview) {
		super.save(studentReview);
	}
	
	@Transactional(readOnly = false)
	public void delete(StudentReview studentReview) {
		super.delete(studentReview);
	}
	
	/**
	 * 根据条件查询某学生的所有评论
	 * @param page
	 * @param review
	 * @return
	 */
	public Page<StudentReview> getMyReview(Page<StudentReview> page, StudentReview review) {
		review.setPage(page);
		String studentId = StudentUserUtils.getUser().getId();
		review.setStudentId(studentId);
		page.setList(dao.getMyReview(review));
		return page;
	}
	
}