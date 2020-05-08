package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolRescourceDao;
import com.thinkgem.jeesite.modules.questionlib.entity.Edresources;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
public class SchoolRescourceService {
	private 
	SchoolRescourceDao dao;

	public Page<Edresources> getSchoolResourceList(Page<Edresources> page, Edresources edresources) {
		Map<String, String> sqlMap = edresources.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		page.setList(dao.getSchoolResourceList(edresources));
		edresources.setPage(page);
		return page;
	}
}
