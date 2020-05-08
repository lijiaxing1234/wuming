/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.questionlib.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.dao.CourseKnowledgeDao;

/**
 * 知识点Service
 * @author webcat
 * @version 2016-08-16
 */
@Service
@Transactional(readOnly = true)
public class CourseKnowledgeService extends TreeService<CourseKnowledgeDao, CourseKnowledge> {

	@Autowired
	protected CourseKnowledgeDao courseKnowledgeDao;
	
	public CourseKnowledge get(String id) {
		return super.get(id);
	}
	
	public List<CourseKnowledge> findList(CourseKnowledge courseKnowledge) {
		/*if (StringUtils.isNotBlank(courseKnowledge.getParentIds())){
			courseKnowledge.setParentIds(","+courseKnowledge.getParentIds()+",");
		}*/
		return super.findList(courseKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void save(CourseKnowledge courseKnowledge) {
		super.save(courseKnowledge);
	}
	
	@Transactional(readOnly = false)
	public void delete(CourseKnowledge courseKnowledge) {
		super.delete(courseKnowledge);
	}

	public List<String> findIdListByParentIds(CourseKnowledge courseKnowledge) {
		return courseKnowledgeDao.findIdListByParentIds(courseKnowledge);
	}

	public CourseKnowledge getByTitle(CourseKnowledge courseKnowledge) {
		return courseKnowledgeDao.getByTitle(courseKnowledge);
	}
	
	public CourseKnowledge getByKnowledgeCode(CourseKnowledge courseKnowledge) {
		return courseKnowledgeDao.getByKnowledgeCode(courseKnowledge);
	}


	public int HadQuestionsCount(CourseKnowledge courseKnowledge) {
		return courseKnowledgeDao.HadQuestionsCount(courseKnowledge);
	}
	
	/**
	 * 获取某个版本下知识点的数量
	 * @param courseVesionId  版本Id
	 * @return Integer  版本下知识点的数量
	 */
	public Integer  countCourseKnowledgeByCourseVesionId(String courseVesionId){
	
		CourseKnowledge courseKnowledge=new CourseKnowledge();
		
		courseKnowledge.setVersionId(courseVesionId);
		
		return courseKnowledgeDao.countCourseKnowledgeByCourseVesionId(courseKnowledge);
	}

	public Integer selectMaxSort(String courseVesionId) {
		CourseKnowledge courseKnowledge=new CourseKnowledge();
		courseKnowledge.setVersionId(courseVesionId);
		return courseKnowledgeDao.selectMaxSort(courseKnowledge);
	}
	
	@Transactional(readOnly = false) 
	public boolean recommendKnowledge(String id,int recommend){
		return courseKnowledgeDao.recommendKnowledge(id,recommend) == 1;
	}
	
	public int getRecommend(String id){
		return courseKnowledgeDao.getRecommend(id);
	}
	@Transactional(readOnly = false) 
	public boolean sortZT(int sort,String id){
		return courseKnowledgeDao.sortZT(sort, id) == 1;
	}
}