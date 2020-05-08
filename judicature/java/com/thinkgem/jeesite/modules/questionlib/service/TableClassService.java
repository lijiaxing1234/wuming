/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;
import com.thinkgem.jeesite.modules.questionlib.dao.TableClassDao;

/**
 * 班级Service
 * @author webcat
 * @version 2016-09-09
 */
@Service
@Transactional(readOnly = true)
public class TableClassService extends CrudService<TableClassDao, TableClass> {

	public TableClass get(String id) {
		return super.get(id);
	}
	
	public List<TableClass> findList(TableClass tableClass) {
		return super.findList(tableClass);
	}
	
	public Page<TableClass> findPage(Page<TableClass> page, TableClass tableClass) {
		return super.findPage(page, tableClass);
	}
	
	@Transactional(readOnly = false)
	public void save(TableClass tableClass) {
		super.save(tableClass);
	}
	
	@Transactional(readOnly = false)
	public void delete(TableClass tableClass) {
		super.delete(tableClass);
	}
	
}