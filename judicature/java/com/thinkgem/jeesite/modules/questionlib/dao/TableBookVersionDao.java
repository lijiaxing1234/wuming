/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.TableBookVersion;

/**
 * 书籍检查DAO接口
 * @author 书籍检查
 * @version 2016-09-26
 */
@MyBatisDao
public interface TableBookVersionDao extends CrudDao<TableBookVersion> {
	
}