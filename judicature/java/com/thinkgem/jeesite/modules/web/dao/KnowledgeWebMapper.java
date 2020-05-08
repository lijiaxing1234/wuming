package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;

@MyBatisDao
public interface KnowledgeWebMapper {
    
	/**
	 * 通过课程Id得到当前课程的一级知识点
	 * @param courseId 课程Id
	 * @return
	 */
	List<Knowledge> getKnowledgeByCourseId(@Param("courseId") String courseId);
	
	List<Knowledge> getKnowledgeByParentId(@Param("parentId") String parentId,@Param("userId") String userId);
	List<Knowledge> getKnowledgeByShelf(@Param("id") String id,@Param("userId") String userId);
	int addUserKnowledgeInfo(Knowledge knowledge);
	int hasUserKnowledgeInfo(Knowledge knowledge);
	int updateUserKnowledgeInfo(Knowledge knowledge);
	Knowledge getKnowledgeById(String id);
	List<Knowledge> getRootKnow1(@Param("userId") String userId,@Param("versionId") String versionId);
	List<Knowledge> getRootKnow2(@Param("userId") String userId,@Param("versionId") String versionId);
	List<Knowledge> getUserCollectionKnow1(@Param("courseId") String courseId,@Param("userId") String userId);
	List<Knowledge> getUserCollectionKnow2(@Param("courseId") String courseId,@Param("userId") String userId);
	List<Knowledge> getCollectionKnowledgeByParentId(@Param("parentId") String parentId,@Param("userId") String userId);
    /**
     * 得到知识点集合
     * @param knowledge
     * @return
     */
    List<Knowledge>  getKnowledgeList(@Param("courseId")String courseId,@Param("parentId")String parentId);

}
