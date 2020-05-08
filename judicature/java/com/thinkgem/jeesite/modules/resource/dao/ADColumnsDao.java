package com.thinkgem.jeesite.modules.resource.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.TableAD;
import com.thinkgem.jeesite.modules.resource.entity.TableADColumns;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;

@MyBatisDao
public interface ADColumnsDao extends CrudDao<TableADColumns> {
	
	TableADColumns selectADColumnsById(String adId);
	
	List<TableADColumns> getADColumnsList();
	
	int insert(TableADColumns tableAd);
	
	int update(TableADColumns tableAd);
	
	int delete(String adColumnsId);

	TableADColumns getADColumnByCode(String adCode);

 }
