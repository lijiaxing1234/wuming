/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.teacher.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailDao;
import com.thinkgem.jeesite.modules.teacher.dao.ExamdetailQuestionDao;
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;
import com.thinkgem.jeesite.modules.teacher.entity.ExamdetailQuestion;

/**
 * 测试问题类型详情Service
 */
@Service
@Transactional(readOnly = true)
public class ExamDetailQuestionService {
	@Autowired
	ExamdetailQuestionDao examDao;
	
	public ExamdetailQuestion get(String id) {
		return examDao.getOnlyOne(id);
	}

}

