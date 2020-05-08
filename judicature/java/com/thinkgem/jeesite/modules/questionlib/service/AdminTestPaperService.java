package com.thinkgem.jeesite.modules.questionlib.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.AdminTestPaperDao;
import com.thinkgem.jeesite.modules.questionlib.entity.TestPaper;

@Service
@Transactional(readOnly = true)
public class AdminTestPaperService extends CrudService<AdminTestPaperDao, TestPaper> {
	
}
