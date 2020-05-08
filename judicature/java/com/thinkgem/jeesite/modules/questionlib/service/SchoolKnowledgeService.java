package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.SchoolKnowledgeDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

@Service
public class SchoolKnowledgeService {

	@Autowired
	private SchoolKnowledgeDao dao;

	public Page<CourseKnowledge> getSchoolKnowledgeList(Page<CourseKnowledge> page, CourseKnowledge knowledge) {
		Map<String, String> sqlMap = knowledge.getSqlMap();
		sqlMap.put("companyId", UserUtils.getUser().getCompany().getId());
		knowledge.setPage(page);
		page.setList(dao.getSchoolKnowledgeList(knowledge));
		return page;
	}
	
	
	
	
}
