package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.BaseDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.dto.SchoolStaticsDTO;
import com.thinkgem.jeesite.modules.questionlib.dto.StaticDTO;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.SchoolQuestionlib;

/**
 * 统计dao
 */
@MyBatisDao
public interface StatisticsDao extends BaseDao {
	public List<StaticDTO> getQuestionStatics(StaticDTO staticDTO);

	public List<SchoolStaticsDTO> getSchoolStatics(SchoolStaticsDTO schoolStaticsDTO);

	public List<CourseKnowledge> getKnowledgePointsStatics(CourseKnowledge courseKnowledge);

	public List<SchoolQuestionlib> accreditStatistics(SchoolQuestionlib schoolQuestionlib);

}
