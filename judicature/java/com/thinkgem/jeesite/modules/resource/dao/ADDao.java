package com.thinkgem.jeesite.modules.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.org.glassfish.gmbal.ParameterNames;
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.TableAD;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;

@MyBatisDao
public interface ADDao extends CrudDao<TableAD> {
	
	TableAD selectADById(String adId);
	
	List<TableAD> getADList(@Param(value="colId")String colId);
	
	int insert(TableAD tableAd);
	
	int update(TableAD tableAd);
	
	void deleteAD(String adId);
	
	int selectADCountById(String adColumnsId);
	
	int getMaxADseq(String colId);
 }
