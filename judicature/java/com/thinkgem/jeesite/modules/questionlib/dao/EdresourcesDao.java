/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;
import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;


/**
 * 学生DAO接口
 * @author yfl
 * @version 2016-09-14
 */
//教学资源
@MyBatisDao
public interface EdresourcesDao extends CrudDao<Edresources> {

	List<Edresources> findSchoolResourcePageByCompanyId(Edresources edresources);
	
}