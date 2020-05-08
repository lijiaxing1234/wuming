/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.modules.questionlib.dao.SpecialtyDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;

/**
 * 专业Service
 * @author webcat
 * @version 2016-08-15
 */
@Service
@Transactional(readOnly = true)
public class SpecialtyService extends TreeService<SpecialtyDao, Specialty> {

	@Autowired
	private SpecialtyDao specialtyDao;
	
	public Specialty get(String id) {
		return super.get(id);
	}
	
	public List<Specialty> findList(Specialty specialty) {
		return super.findList(specialty);
	}
	
	public List<Specialty> findAll() {
		return super.findList(new Specialty());
	}
	
	public List<Specialty> findAllByParentIds(Specialty entity) {
		return this.dao.findByParentIdsLike(entity);
	}
	
	public Page<Specialty> findPage(Page<Specialty> page, Specialty specialty) {
		return super.findPage(page, specialty);
	}
	
	@Transactional(readOnly = false)
	public void save(Specialty specialty) {
		super.save(specialty);
	}
	
	@Transactional(readOnly = false)
	public void delete(Specialty specialty) {
		super.delete(specialty);
	}

	
	@Transactional(readOnly = false)
	public boolean deleteAllChild (Specialty specialty) {
		return specialtyDao.deleteAllChild(specialty);
	}
	
	public Specialty getSpecialtyBySpecialtyCode(Specialty specialty) {
		return specialtyDao.getSpecialtyBySpecialtyCode(specialty);
	}
	public List<String> findIdListByParentIds(Specialty specialty) {
		return specialtyDao.findIdListByParentIds(specialty);
	}
	
	
	public List<Specialty> findListByTitle(Specialty specialty)
	{
		return specialtyDao.findListByTitle(specialty);
	}
}