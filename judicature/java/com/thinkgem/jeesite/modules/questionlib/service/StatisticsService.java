package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.questionlib.dao.StatisticsDao;
import com.thinkgem.jeesite.modules.questionlib.dto.SchoolStaticsDTO;
import com.thinkgem.jeesite.modules.questionlib.dto.StaticDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 
 * 数据统计服务层
 */

@Service
@Transactional(readOnly = true)
public class StatisticsService {

	@Autowired
	protected StatisticsDao dao;

	public List<StaticDTO> getQuestionStatics(StaticDTO staticDTO) {
		return dao.getQuestionStatics(staticDTO);
	}
	
	public List<SchoolStaticsDTO> getSchoolStatics(SchoolStaticsDTO schoolStaticsDTO) {
		return dao.getSchoolStatics(schoolStaticsDTO);
	}
	public List<CourseKnowledge> getKnowledgePointsStatics(CourseKnowledge courseKnowledge)
	{
		return dao.getKnowledgePointsStatics(courseKnowledge);
	}

	public Page<SchoolQuestionlib> accreditStatistics(Page<SchoolQuestionlib> page, SchoolQuestionlib schoolQuestionlib) {
		schoolQuestionlib.setPage(page);
		Map<String, String> sqlMap = schoolQuestionlib.getSqlMap();
		sqlMap.put("userId", UserUtils.getUser().getId());
		page.setList(dao.accreditStatistics(schoolQuestionlib));
		return page;
	}
	 
}
