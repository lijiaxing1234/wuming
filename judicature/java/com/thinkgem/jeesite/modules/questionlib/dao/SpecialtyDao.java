/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Specialty;

/**
 * 专业DAO接口
 * @author webcat
 * @version 2016-10-08
 */
@MyBatisDao
public interface SpecialtyDao extends TreeDao<Specialty> {

	/**
	 * 根据专业代码获取专业
	 * @param specialty
	 * @return
	 */
	Specialty getSpecialtyBySpecialtyCode(Specialty specialty);
	
	
	/**
	 * 删除所有的子节点
	 * @param specialty
	 * @return
	 */
	boolean deleteAllChild(Specialty specialty);
	 
	public List<String> findIdListByParentIds(Specialty specialty) ;
	
	public List<Specialty> findListByTitle(Specialty specialty);

	
}