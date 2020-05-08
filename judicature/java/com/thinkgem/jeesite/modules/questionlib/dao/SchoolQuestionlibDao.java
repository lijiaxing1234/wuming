/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;

/**
 * 学校数据库DAO接口
 * @author ryz
 * @version 2016-09-14
 */
@MyBatisDao
public interface SchoolQuestionlibDao extends CrudDao<SchoolQuestionlib> {
	
	
    List<SchoolQuestionlib> findAuthorizedList(SchoolQuestionlib sql);

	List<SchoolQuestionlib> getQuestionLibs();
	
}