/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.questionlib.dao.EdresourcesDao;


/**
 * 教学资源Service
 * @author yfl
 * @version 2016-09-14
 */
@Service
@Transactional(readOnly = true)
public class EdresourcesService extends CrudService<EdresourcesDao, Edresources> {

	public Edresources get(String id) {
		return super.get(id);
	}
	
	public List<Edresources> findList(Edresources edresources) {
		return super.findList(edresources);
	}
	
	public Page<Edresources> findPage(Page<Edresources> page, Edresources edresources) {
		return super.findPage(page, edresources);
	}
	@Transactional(readOnly = false)
	public void save(Edresources edresources) {
		super.save(edresources);
	}
	
	@Transactional(readOnly = false)
	public void delete(Edresources edresources) {
		super.delete(edresources);
	}

	/**
	 * 根据学校id查询该学校所有资源
	 * @param page
	 * @param edresources
	 * @return
	 */
	public Page<Edresources> findSchoolResourcePageByCompanyId(Page<Edresources> page, Edresources edresources) {
		Map<String, String> sqlMap = edresources.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		edresources.setPage(page);
		page.setList(dao.findSchoolResourcePageByCompanyId(edresources));
		return page;
	}
}