/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.MessageStudent;
import com.thinkgem.jeesite.modules.questionlib.dao.MessageStudentDao;

/**
 * 提醒Service
 * @author flychao
 * @version 2016-12-06
 */
@Service
@Transactional(readOnly = true)
public class MessageStudentService extends CrudService<MessageStudentDao, MessageStudent> {

	public MessageStudent get(String id) {
		return super.get(id);
	}
	
	public List<MessageStudent> findList(MessageStudent messageStudent) {
		return super.findList(messageStudent);
	}
	
	public Page<MessageStudent> findPage(Page<MessageStudent> page, MessageStudent messageStudent) {
		return super.findPage(page, messageStudent);
	}
	
	@Transactional(readOnly = false)
	public void save(MessageStudent messageStudent) {
		super.save(messageStudent);
	}
	
	@Transactional(readOnly = false)
	public void delete(MessageStudent messageStudent) {
		super.delete(messageStudent);
	}
	
}