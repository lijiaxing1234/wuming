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
import com.thinkgem.jeesite.modules.teacher.entity.Examdetail;

/**
 * 测试详情Service
 */
@Service
@Transactional(readOnly = true)
public class ExamDetailService {
	@Autowired
	ExamdetailDao examDao;
	
	public List<Examdetail> get(Examdetail examdetail) {
		return examDao.getExamDetailByExamId(examdetail);
	}
	
	
	public Examdetail getExamdetailById(String id){
		List<Examdetail> list=examDao.getExamdetailById(id);
		if(list !=null&&list.size()>0){
			return list.iterator().next();
		}
		return null;
	}
	@Transactional(readOnly = false)
	public int  updateSelectById(Examdetail examdetail){
	    return examDao.updateSelective(examdetail);
	}
}

