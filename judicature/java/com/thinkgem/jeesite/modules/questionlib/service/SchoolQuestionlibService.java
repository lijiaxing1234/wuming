/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolQuestionlibDao;

/**
 * 学校数据库Service
 * @author ryz
 * @version 2016-09-14
 */
@Service
@Transactional(readOnly = true)
public class SchoolQuestionlibService extends CrudService<SchoolQuestionlibDao, SchoolQuestionlib> {

	public SchoolQuestionlib get(String id) {
		return super.get(id);
	}
	
	public List<SchoolQuestionlib> findList(SchoolQuestionlib schoolQuestionlib) {
		return super.findList(schoolQuestionlib);
	}
	
	public Page<SchoolQuestionlib> findPage(Page<SchoolQuestionlib> page, SchoolQuestionlib schoolQuestionlib) {
		return super.findPage(page, schoolQuestionlib);
	}
	
	@Transactional(readOnly = false)
	public void save(SchoolQuestionlib schoolQuestionlib) {
		super.save(schoolQuestionlib);
	}
	
	@Transactional(readOnly = false)
	public void delete(SchoolQuestionlib schoolQuestionlib) {
		super.delete(schoolQuestionlib);
	}

	/**
	 * 查询已授权题库
	 * @param page
	 * @param schoolQuestionlib
	 * @return
	 */
	public Page<SchoolQuestionlib> findAuthorizedPage(Page<SchoolQuestionlib> page, SchoolQuestionlib schoolQuestionlib) {
		schoolQuestionlib.setPage(page);
		page.setList(super.dao.findAuthorizedList(schoolQuestionlib));
		return page;
	}
	
}