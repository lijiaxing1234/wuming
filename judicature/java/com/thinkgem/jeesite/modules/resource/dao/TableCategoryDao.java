package com.thinkgem.jeesite.modules.resource.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;

@MyBatisDao
public interface TableCategoryDao extends CrudDao<TableCategory> {
	
	List<TableCategory> getCategoryAll();

	TableCategory getTableCategoryById(String cateId);
	
	int insert(TableCategory category);
	
	int update(TableCategory category);
	
	int delete(String cateId);
 }
