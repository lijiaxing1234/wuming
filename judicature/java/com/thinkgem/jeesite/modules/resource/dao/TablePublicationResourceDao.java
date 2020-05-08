package com.thinkgem.jeesite.modules.resource.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.resource.entity.TablePublicationResource;

@MyBatisDao
public interface TablePublicationResourceDao extends CrudDao<TablePublicationResource> {

	List<TablePublicationResource> getResourceList(TablePublicationResource resource);

	TablePublicationResource selectResourceById(String resId);
	
	int insert(TablePublicationResource resource);
	
	int update(TablePublicationResource resource);
	
	int delete(String cateId);
 }
