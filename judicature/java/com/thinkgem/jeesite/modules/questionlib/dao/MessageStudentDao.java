/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.MessageStudent;

/**
 * 提醒DAO接口
 * @author flychao
 * @version 2016-12-06
 */
@MyBatisDao
public interface MessageStudentDao extends CrudDao<MessageStudent> {
	
}