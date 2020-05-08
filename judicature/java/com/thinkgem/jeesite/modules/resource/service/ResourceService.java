package com.thinkgem.jeesite.modules.resource.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resource.dao.TablePublicationResourceDao;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;
import com.thinkgem.jeesite.modules.resource.entity.TablePublicationResource;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.teacher.entity.Exam;

/**
 *分类Service
 */
@Service(value="tableResourceService")
@Transactional(readOnly = true)
public class ResourceService extends CrudService<TablePublicationResourceDao,TablePublicationResource> {
	@Autowired
	private TablePublicationResourceDao resourceDao;
	
	/**
	 * 根据条件查询资源
	 * @param page
	 * @param resource
	 * @return
	 */
	public Page<TablePublicationResource> getResourceList(Page<TablePublicationResource> page,
			TablePublicationResource resource) {
		resource.setPage(page);
	    List<TablePublicationResource> list = resourceDao.getResourceList(resource);
		page.setList(list);
		return page;
	}
	/**
	 * 根据id查询资源
	 * @param resId
	 * @return
	 */
	public TablePublicationResource selectResourceById(String resId) {
		return resourceDao.selectResourceById(resId);
	}
	@Transactional(readOnly=false)
	public void insertCategory(TablePublicationResource resource) {
		TablePublicationResource cate = resourceDao.selectResourceById(resource.getId());
		if(cate!=null){
			resource.setUpdateDate(new Date());
			resourceDao.update(resource);
		}else{
			resource.setCreateTime(new Date());
			resource.setUpdateDate(new Date());
			resource.setCreator(UserUtils.getUser().getId());
			resource.setId(UUID.randomUUID().toString());
			resourceDao.insert(resource);
		}
	}
	@Transactional(readOnly=false)
	public void delete(String resId) {
		resourceDao.delete(resId);
	}
}

