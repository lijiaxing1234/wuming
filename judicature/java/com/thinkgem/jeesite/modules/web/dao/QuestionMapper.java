package com.thinkgem.jeesite.modules.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;

@MyBatisDao
public interface QuestionMapper {

	List<Questions> getQuestionsByknowledgeId(String knowledgeId);
	boolean addUserAnswer(List<Questions> list);
	
	boolean addUserCollection(@Param("userId") String userId,@Param("questionId") String questionId);
	
	boolean delUserCollection(@Param("userId") String userId,@Param("questionId") String questionId);
	
	List<Questions> questions15(String versionId);
	
	List<Questions> getWrongQuestionByUser(@Param("knowledgeId") String knowledgeId,@Param("userId") String userId);
	
	List<Questions> getCollectionQuestionsByUser(@Param("knowledgeId") String knowledgeId,@Param("userId") String userId);
	
	int getCollectionQuestionsCount(@Param("knowledgeId") String knowledgeId,@Param("userId") String userId);
	
	Integer userHasCollectionQue(@Param("userId") String userId,@Param("questionId") String questionId);
}
