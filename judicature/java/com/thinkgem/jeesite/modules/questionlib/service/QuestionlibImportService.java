package com.thinkgem.jeesite.modules.questionlib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.questionlib.dao.QuestionlibImportDao;
import com.thinkgem.jeesite.modules.questionlib.entity.QuestionlibImport;

/**
 * 试题导入题库-批量导入试题Service
 * @author ryz
 * @version 2016-09-26
 */
@Service
@Transactional(readOnly = true)
public class QuestionlibImportService extends CrudService<QuestionlibImportDao, QuestionlibImport> {
	@Autowired
	private QuestionlibImportDao questionlibImportDao;
	public String queryExamQuestionlibId(String versionId){
		return questionlibImportDao.queryExamQuestionlibId(versionId);
	}
}