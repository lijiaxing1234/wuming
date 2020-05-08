/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.School;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolDao;

/**
 * 学校管理Service
 * @author webcat
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class SchoolService extends CrudService<SchoolDao, School> {

	public School get(String id) {
		return super.get(id);
	}
	
	public List<School> findList(School school) {
		return super.findList(school);
	}
	
	public Page<School> findPage(Page<School> page, School school) {
		return super.findPage(page, school);
	}
	
	@Transactional(readOnly = false)
	public void save(School school) {
		super.save(school);
	}
	
	@Transactional(readOnly = false)
	public void delete(School school) {
		super.delete(school);
	}
	
}