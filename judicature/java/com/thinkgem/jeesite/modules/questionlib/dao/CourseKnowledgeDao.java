/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.teacher.entity.TeacherKnowledge;

/**
 * 知识点DAO接口
 * @author webcat
 * @version 2016-08-16
 */
@MyBatisDao
public interface CourseKnowledgeDao extends TreeDao<CourseKnowledge> {
	
	/**
	 * 查询所有子节点的id
	 * @param courseKnowledge
	 * @return
	 */
	List<String> findIdListByParentIds(CourseKnowledge courseKnowledge);

	/**
	 * 根据知识点名称查找知识点
	 * @param title
	 * @return
	 */
	CourseKnowledge getByTitle(CourseKnowledge courseKnowledge);

	/**
	 * 根据知识点编号查找知识点
	 * @param courseKnowledge
	 * @return
	 */
	CourseKnowledge getByKnowledgeCode(CourseKnowledge courseKnowledge);
	
	
	int HadQuestionsCount(CourseKnowledge courseKnowledge);

	
	Integer countCourseKnowledgeByCourseVesionId(CourseKnowledge courseKnowledge);
	
	
	Integer selectMaxSort(CourseKnowledge courseKnowledge);
	
	int recommendKnowledge(@Param("id")String id,@Param("recommend")int recommend);
	
	int getRecommend(String id);
	
	int sortZT(@Param("sort")int sort,@Param("id")String id);
}