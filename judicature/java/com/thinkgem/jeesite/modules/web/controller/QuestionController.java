package com.thinkgem.jeesite.modules.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.questionlib.entity.CourseKnowledge;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.questionlib.service.VersionQuestionService;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.service.QuestionService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

@Controller
@RequestMapping("/web/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;
	@Autowired
	VersionQuestionService versionQuestionService;
	@RequestMapping("questionsByknowledgeId")
	@ResponseBody
	public String questionsByknowledgeId(String knowledgeId){
		
		ReturnData returnData = new ReturnData();
		try {
			List<Questions> list = questionService.getQuestionsByknowledgeId(knowledgeId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("addUserAnswer")
	@ResponseBody
	public String addUserAnswer(String userId,String questionIds,String answers,String isCorrect){
		boolean addUserAnswer = questionService.addUserAnswer(userId, questionIds, answers, isCorrect);
		String json = JsonUtil.toJson(addUserAnswer);
		return json;
	}
	
	@RequestMapping("rootKnow")
	@ResponseBody
	public String rootKnow(String userId,String versionId){
		ReturnData returnData = new ReturnData();
		try {
			List<Knowledge> list = questionService.getRootKnow(userId, versionId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("wrongQuestionByUser")
	@ResponseBody
	public String getWrongQuestionByUser(String knowledgeId,String userId){
		ReturnData returnData = new ReturnData();
		try {
			List<Questions> list = questionService.getWrongQuestionByUser(knowledgeId, userId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("userCollectionKnow")
	@ResponseBody
	public String getUserCollectionKnow(String userId,String courseId){
		ReturnData returnData = new ReturnData();
		try {
			List<Knowledge> list = questionService.getUserCollectionKnow(userId, courseId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("collectionQuestionByUser")
	@ResponseBody
	public String getCollectionQuestionsByUser(String knowledgeId,String userId){
		ReturnData returnData = new ReturnData();
		try {
			List<Questions> list = questionService.getCollectionQuestionsByUser(knowledgeId, userId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("questions15")
	@ResponseBody
	public String questions15(String courseId){
		ReturnData returnData = new ReturnData();
		try {
			List<Questions> list = questionService.questions15(courseId);
			if (list.size()>0) {
				returnData.setData(list);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<Object, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("addUserCollection")
	@ResponseBody
	public String addUserCollection(String userId,String questionId){
		ReturnData returnData = new ReturnData();
		  
		boolean b = questionService.addUserCollection(userId, questionId);
		returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("delUserCollection")
	@ResponseBody
	public String delUserCollection(String userId,String questionId){
		ReturnData returnData = new ReturnData();
		  
		boolean b = questionService.delUserCollection(userId, questionId);
		returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("userHasCollectionQue")
	@ResponseBody
	public String userHasCollectionQue(String userId,String questionId){
		ReturnData returnData = new ReturnData();
		boolean b = questionService.userHasCollectionQue(userId, questionId);
		returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
}
