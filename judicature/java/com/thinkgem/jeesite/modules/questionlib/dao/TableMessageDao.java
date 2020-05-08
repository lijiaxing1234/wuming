/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.TableMessage;

/**
 * 系统消息DAO接口
 * @author flychao
 * @version 2016-10-24
 */
@MyBatisDao
public interface TableMessageDao extends CrudDao<TableMessage> {
	
}