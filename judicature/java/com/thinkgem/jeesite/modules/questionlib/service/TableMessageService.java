/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.TableMessageDao;
import com.thinkgem.jeesite.modules.questionlib.entity.TableMessage;
import com.thinkgem.jeesite.modules.teacher.dao.ExamDao;
import com.thinkgem.jeesite.modules.teacher.dto.LoginDTO;

/**
 * 系统消息Service
 * @author flychao
 * @version 2016-10-24
 */
@Service
@Transactional(readOnly = true)
public class TableMessageService extends CrudService<TableMessageDao, TableMessage> {
	@Autowired
	TableMessageDao tableMessageDao;
	@Autowired
	ExamDao examDao;
	
	public TableMessage get(String id) {
		return super.get(id);
	}
	
	public List<TableMessage> findList(TableMessage tableMessage) {
		return super.findList(tableMessage);
	}
	
	public Page<TableMessage> findPage(Page<TableMessage> page, TableMessage tableMessage) {
		tableMessage.setPage(page);
		page.setList(tableMessageDao.findList(tableMessage));
		return page;
	}
	
	@Transactional(readOnly = false)
	public void save(TableMessage tableMessage) {
		super.save(tableMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(TableMessage tableMessage) {
		super.delete(tableMessage);
	}
	
	public LoginDTO getTeacherLogin(String userId,String versionId) {
		return examDao.getTeacherLogin(userId,versionId);
	}
}