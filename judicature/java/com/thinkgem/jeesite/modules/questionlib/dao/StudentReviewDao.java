/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.StudentReview;

/**
 * 学生评论DAO接口
 * @author yfl
 * @version 2016-09-10
 */
@MyBatisDao
public interface StudentReviewDao extends CrudDao<StudentReview> {

	List<StudentReview> getMyReview(StudentReview review);
	
}