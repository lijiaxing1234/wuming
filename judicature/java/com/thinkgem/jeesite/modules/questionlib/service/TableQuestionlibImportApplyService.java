/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolClass;
import com.thinkgem.jeesite.modules.questionlib.entity.TableQuestionlibImportApply;
import com.thinkgem.jeesite.modules.questionlib.dao.TableQuestionlibImportApplyDao;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 申请入平台Service
 * @author flychao
 * @version 2016-12-06
 */
@Service
@Transactional(readOnly = true)
public class TableQuestionlibImportApplyService extends CrudService<TableQuestionlibImportApplyDao, TableQuestionlibImportApply> {

	
	static String CACHE_LIST_KEY="findQuesImportApplys";
	
	public TableQuestionlibImportApply get(String id) {
		return super.get(id);
	}
	
	public List<TableQuestionlibImportApply> findList(TableQuestionlibImportApply tableQuestionlibImportApply) {
		return super.findList(tableQuestionlibImportApply);
	}
	
	public Page<TableQuestionlibImportApply> findPage(Page<TableQuestionlibImportApply> page, TableQuestionlibImportApply tableQuestionlibImportApply) {
		return super.findPage(page, tableQuestionlibImportApply);
	}
	
	@Transactional(readOnly = false)
	public void save(TableQuestionlibImportApply tableQuestionlibImportApply) {
		super.save(tableQuestionlibImportApply);
		CacheUtils.remove(CACHE_LIST_KEY);
	}
	
	@Transactional(readOnly = false)
	public void delete(TableQuestionlibImportApply tableQuestionlibImportApply) {
		super.delete(tableQuestionlibImportApply);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<TableQuestionlibImportApply>  findQuesImportApplys(){
		List<TableQuestionlibImportApply> list = (List<TableQuestionlibImportApply>)CacheUtils.get(CACHE_LIST_KEY);
		if(null == list){
			TableQuestionlibImportApply entity=new TableQuestionlibImportApply();
			entity.setSchoolId(UserUtils.getUser().getCompany().getId());
			list=dao.findList(entity);
		}
		CacheUtils.put(CACHE_LIST_KEY, list);
		return list;
	}

	@Transactional(readOnly = false)
	public void audit(TableQuestionlibImportApply quesImportApply) {
		dao.update(quesImportApply);
		CacheUtils.remove(CACHE_LIST_KEY);
	}
	
}