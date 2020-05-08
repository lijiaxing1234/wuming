/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.School;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.sys.entity.Office;

/**
 * 学校管理DAO接口
 * @author webcat
 * @version 2016-08-15
 */
@MyBatisDao
public interface SchoolDao extends CrudDao<School> {

}