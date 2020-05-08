package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.UAnswer;

/**
 * 回答DAO接口
 */
@MyBatisDao
public interface UAnswerDao extends CrudDao<UAnswer> {
	int countAllList(UAnswer uAnswer);
	
}