package com.thinkgem.jeesite.modules.web.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.metadata.exif.KodakMakernoteDescriptor;
import com.google.common.collect.Lists;
import com.thinkgem.jeesite.modules.web.dao.KnowledgeWebMapper;
import com.thinkgem.jeesite.modules.web.dao.QuestionMapper;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;

@Service
public class QuestionService {

	@Autowired
	QuestionMapper questionMapper;
	
	@Autowired
	KnowledgeWebMapper knowledgeWebMapper;
	
	 public List<Questions> getQuestionsByknowledgeId(String knowledgeId){
		List<Questions> questionsByknowledgeId = questionMapper.getQuestionsByknowledgeId(knowledgeId);
		Collections.sort(questionsByknowledgeId);
		return questionsByknowledgeId;
	}
	 
	 @Transactional
	 public boolean addUserAnswer(String userId,String questionIds,String answers,String isCorrect){
		 List<Questions> list = new ArrayList<Questions>();
		 if (isCorrect.length()>1) {
			 String[] questionArray = questionIds.split(",");
			 String[] answerArray = answers.split(",");
			 String[] isCorrectArray = isCorrect.split(",");
			 for (int i = 0; i < questionArray.length; i++) {
				 Questions questions = new Questions();
				 questions.setStudentId(userId);
				 questions.setId(questionArray[i]);
				 questions.setAnswer0(answerArray[i]);
				 questions.setIsCorrect(Integer.parseInt(isCorrectArray[i]));
				 list.add(questions);
			 }
		 }else {
			 Questions questions = new Questions();
			 questions.setStudentId(userId);
			 questions.setId(questionIds);
			 questions.setAnswer0(answers);
			 questions.setIsCorrect(Integer.parseInt(isCorrect));
			 list.add(questions);
		}
		 return questionMapper.addUserAnswer(list);
	 }
	 
	 public boolean addUserCollection(String userId,String questionId){
		 return questionMapper.addUserCollection(userId, questionId);
	 }
	 
	 public boolean delUserCollection(String userId,String questionId){
		 return questionMapper.delUserCollection(userId, questionId);
	 }
	 
	 public boolean userHasCollectionQue(String userId,String questionId){
		 return questionMapper.userHasCollectionQue(userId, questionId) > 0;
	 }
	 
	 public List<Questions> questions15(String versionId){
		 return questionMapper.questions15(versionId);
	 }
	 
	 public List<Knowledge> getRootKnow(String userId,String versionId){
		 List<Knowledge> rootKnow2 =new ArrayList<Knowledge>();
		 List<Knowledge> rootKnow = knowledgeWebMapper.getRootKnow1(userId, versionId);
		 
		 if (rootKnow.size() == 0 || rootKnow.get(0).getId() == null || rootKnow.get(0).getId() == "") {
			 List<Knowledge> rootKnow1 = knowledgeWebMapper.getRootKnow2(userId, versionId);
			 /*if ((rootKnow1.get(0).getId() == null || rootKnow.get(0).getId() == "") && rootKnow.get(0).getWrongQuestion() == 0) {
					return new ArrayList<Knowledge>();
			 }*/
			 if (rootKnow1.size() == 0) {
				 return new ArrayList<Knowledge>();
			 }
			 for (int i = 0; i < rootKnow1.size(); i++) {
				 rootKnow1.get(i).setSonKnowledges(new ArrayList<Knowledge>());
				 /*if (!(""+rootKnow1.get(i).getWrongQuestion()).equals( "0")) {
					 List<Knowledge> knowledgeByShelf = knowledgeWebMapper.getKnowledgeByShelf(rootKnow.get(i).getId(), userId);
					 rootKnow1.get(i).setSonKnowledges(knowledgeByShelf);
					 rootKnow2.add(rootKnow.get(i));
				}*/
			 }
			 return rootKnow1;
		 }
		 if ((rootKnow.get(0).getId() == null || rootKnow.get(0).getId() == "") && rootKnow.get(0).getWrongQuestion() == 0) {
				return new ArrayList<Knowledge>();
		 }
		 //List<Questions> userWrongQuestion = questionMapper.getUserWrongQuestion(userId);
		 //List<String> wrongKnowledge = questionMapper.getWrongKnowledge(userId);
		 for (int i = 0; i < rootKnow.size(); i++) {
			 if (!(""+rootKnow.get(i).getWrongQuestion()).equals( "0")) {
				 List<Knowledge> knowledgeByParentId = knowledgeWebMapper.getKnowledgeByParentId(rootKnow.get(i).getId(), userId);
				 rootKnow.get(i).setSonKnowledges(knowledgeByParentId);
				 rootKnow2.add(rootKnow.get(i));
			}
		 }
		 List<Knowledge> rootKnow1 = knowledgeWebMapper.getRootKnow2(userId, versionId);
		 if (rootKnow1.size()>0) {
			 for (int i = 0; i < rootKnow1.size(); i++) {
				 rootKnow1.get(i).setSonKnowledges(new ArrayList<Knowledge>());
			 }
			 rootKnow2.addAll(rootKnow1);
		 }
		 return rootKnow2;
	 }
	 
	 public List<Questions> getWrongQuestionByUser(String knowledgeId,String userId){
		return questionMapper.getWrongQuestionByUser(knowledgeId, userId);
	 }
	 
	 public List<Knowledge> getUserCollectionKnow(String userId,String courseId){
		 int tCount = 0;
		 int count = 0;
		 List<Knowledge> rootKnow = new ArrayList<Knowledge>();
		 rootKnow = knowledgeWebMapper.getUserCollectionKnow1(courseId,userId);
		 if (rootKnow.size() == 0) {
			 rootKnow = knowledgeWebMapper.getUserCollectionKnow2(courseId,userId);
			 for (int i = 0; i < rootKnow.size(); i++) {
					 count = questionMapper.getCollectionQuestionsCount(rootKnow.get(i).getId(), userId);
					 rootKnow.get(i).setTotalQuestion(count);
					 rootKnow.get(i).setSonKnowledges(new ArrayList<Knowledge>());
		 }
			 return rootKnow;
		 }
		 
		 
		 for (int i = 0; i < rootKnow.size(); i++) {
				 List<Knowledge> knowledgeByParentId = knowledgeWebMapper.getCollectionKnowledgeByParentId(rootKnow.get(i).getId(), userId);
				 for (int j = 0; j < knowledgeByParentId.size(); j++) {
					 count = questionMapper.getCollectionQuestionsCount(knowledgeByParentId.get(j).getId(), userId);
					 knowledgeByParentId.get(j).setTotalQuestion(count);
					 tCount += count;
					 count = 0;
				}
				 rootKnow.get(i).setSonKnowledges(knowledgeByParentId);
				 rootKnow.get(i).setTotalQuestion(tCount);
				 tCount = 0;
		 }
		 List<Knowledge> know2 = knowledgeWebMapper.getUserCollectionKnow2(courseId,userId);
		 if (know2.size()>0) {
			 for (int i = 0; i < know2.size(); i++) {
				 count = questionMapper.getCollectionQuestionsCount(know2.get(i).getId(), userId);
				 know2.get(i).setTotalQuestion(count);
				 know2.get(i).setSonKnowledges(new ArrayList<Knowledge>());
			 }
			 rootKnow.addAll(know2);
		 }
		 return rootKnow;
	 }
	 
	 public List<Questions> getCollectionQuestionsByUser(String knowledgeId,String userId){
			return questionMapper.getCollectionQuestionsByUser(knowledgeId, userId);
	 }
}
