/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.TableBookVersion;
import com.thinkgem.jeesite.modules.questionlib.dao.TableBookVersionDao;

/**
 * 书籍检查Service
 * @author 书籍检查
 * @version 2016-09-26
 */
@Service
@Transactional(readOnly = true)
public class TableBookVersionService extends CrudService<TableBookVersionDao, TableBookVersion> {

	public TableBookVersion get(String id) {
		return super.get(id);
	}
	
	public List<TableBookVersion> findList(TableBookVersion tableBookVersion) {
		return super.findList(tableBookVersion);
	}
	
	public Page<TableBookVersion> findPage(Page<TableBookVersion> page, TableBookVersion tableBookVersion) {
		return super.findPage(page, tableBookVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(TableBookVersion tableBookVersion) {
		super.save(tableBookVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(TableBookVersion tableBookVersion) {
		super.delete(tableBookVersion);
	}
	
}