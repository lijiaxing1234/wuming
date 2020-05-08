package com.thinkgem.jeesite.modules.resource.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.resource.dao.TableCategoryDao;
import com.thinkgem.jeesite.modules.resource.entity.TableCategory;

/**
 *分类Service
 */
@Service(value="tableCategoryService")
@Transactional(readOnly = true)
public class CategoryService extends CrudService<TableCategoryDao,TableCategory> {
	@Autowired
	private TableCategoryDao categoryDao;
	
	/**
	 * 获取所有分类
	 * @return
	 */
	public List<TableCategory> getCategoryAll() {
		return categoryDao.getCategoryAll();
	}
	/**
	 * 根据id查询分类
	 * @param id
	 * @return
	 */
	public TableCategory getTableCategoryById(String cateId) {
		return categoryDao.getTableCategoryById(cateId);
	}
	@Transactional(readOnly=false)
	public void insertCategory(TableCategory category) {
		TableCategory cate = categoryDao.getTableCategoryById(category.getId());
		if(cate!=null){
			categoryDao.update(category);
		}else{
			if(category.getParent().getId().equals("")||(category.getParent().getId()==null)){
				category.setParentId("1");
				category.setParentIds("0,1");
			}else{
				category.setParentId(category.getParent().getId());
				TableCategory tableCategoryById = categoryDao.getTableCategoryById(category.getParent().getId());
				if(tableCategoryById!=null)
					category.setParentIds(tableCategoryById.getParentIds()+","+category.getParent().getId());
			}
			category.setId(UUID.randomUUID().toString());
			categoryDao.insert(category);
		}
	}
	@Transactional(readOnly=false)
	public void delete(String cateId) {
		categoryDao.delete(cateId);
	}
}

