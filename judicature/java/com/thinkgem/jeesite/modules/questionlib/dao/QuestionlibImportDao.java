/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.QuestionlibImport;

/**
 * 试题导入题库-批量导入试题DAO接口
 * @author ryz
 * @version 2016-09-26
 */
@MyBatisDao
public interface QuestionlibImportDao extends CrudDao<QuestionlibImport> {

	String queryExamQuestionlibId(String versionId);
	
}