package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;

@MyBatisDao
public interface SchoolKnowledgeDao {

	List<CourseKnowledge> getSchoolKnowledgeList(CourseKnowledge knowledge);

}
