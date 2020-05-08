/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.TableClass;

/**
 * 班级DAO接口
 * @author webcat
 * @version 2016-09-09
 */
@MyBatisDao
public interface TableClassDao extends CrudDao<TableClass> {
	
}