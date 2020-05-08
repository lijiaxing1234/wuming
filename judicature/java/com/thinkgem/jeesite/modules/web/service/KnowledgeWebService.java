package com.thinkgem.jeesite.modules.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.LocatorEx.Snapshot;
import com.thinkgem.jeesite.modules.web.dao.KnowledgeWebMapper;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;

@Service
public class KnowledgeWebService {

	@Autowired
	KnowledgeWebMapper knowledgeWebMapper;
	
	@Deprecated
	public Course getKnowledgeByCourseId(String courseId,String userId){
		List<Knowledge> knowledgeByCourseId = knowledgeWebMapper.getKnowledgeByCourseId(courseId);
		Course course = new Course();
		int coursehasDoneQue = 0;
		int coursetotalright = 0;
		int coursetotalwrong = 0;
		int coursetotalQue = 0;
		int hasDoneQue = 0;
		int totalright = 0;
		int totalwrong = 0;
		int totalhasDone = 0;
		//double accuracy = 0; 
		int ktotalQue = 0;
		for (int i = 0; i < knowledgeByCourseId.size(); i++) {
			List<Knowledge> sonKnowledge = knowledgeWebMapper.getKnowledgeByParentId(knowledgeByCourseId.get(i).getId(),userId);
			if (sonKnowledge.size()>0) {
				for (int j = 0; j < sonKnowledge.size(); j++) {
					Knowledge  sonKnow=sonKnowledge.get(j);
					/*int totalQuestion =sonKnowledge.get(j).getTotalQuestion();
					int rightQuestion = sonKnowledge.get(j).getRightQuestion();
					int wrongQuestion = sonKnowledge.get(j).getWrongQuestion();*/
					int totalQuestion =0;
					int rightQuestion =0;
					int wrongQuestion =0;
					
					if(sonKnow!=null){
						totalQuestion=(sonKnow.getTotalQuestion() !=null) ? sonKnow.getTotalQuestion():0;
						rightQuestion=(sonKnow.getRightQuestion() !=null) ? sonKnow.getRightQuestion():0;
						wrongQuestion=(sonKnow.getWrongQuestion() !=null) ? sonKnow.getWrongQuestion():0;
					}
					
				
					hasDoneQue = rightQuestion + wrongQuestion;
					ktotalQue += totalQuestion;
					totalright += rightQuestion;
					totalwrong += wrongQuestion;
					totalhasDone += hasDoneQue;
					if(hasDoneQue>0){
						sonKnowledge.get(j).setAccuracy(new Double(rightQuestion/hasDoneQue));
						sonKnowledge.get(j).setHasDoneQuestion(hasDoneQue);
					}else {
						sonKnowledge.get(j).setAccuracy(0D);
						sonKnowledge.get(j).setHasDoneQuestion(0);
					}
					hasDoneQue = 0;
					totalright = 0;
					totalwrong = 0;
				}
			}
			if (totalhasDone>0) {
				knowledgeByCourseId.get(i).setAccuracy(new Double(totalright/totalhasDone));
			}else {
				knowledgeByCourseId.get(i).setAccuracy(0D);
			}
			knowledgeByCourseId.get(i).setTotalQuestion(ktotalQue);
			knowledgeByCourseId.get(i).setHasDoneQuestion(totalhasDone);
			knowledgeByCourseId.get(i).setSonKnowledges(sonKnowledge);
			coursehasDoneQue += totalhasDone;
			coursetotalright += totalright;
			coursetotalwrong += totalwrong;
			coursetotalQue += ktotalQue;
			totalhasDone = 0;
			ktotalQue = 0;
		}
		if (coursehasDoneQue>0) {
			course.setAccuracy(new Double(coursetotalright/coursehasDoneQue));
		}else {
			course.setAccuracy(0D);
		}
		course.setTotalQuestion(coursetotalQue);
		course.setRightQuestion(coursetotalright);
		course.setWrongQuestion(coursetotalwrong);
		course.setKnowledges(knowledgeByCourseId);
		return course;
	}
	
	
	
	public boolean addUserKnowledgeInfo(Knowledge knowledge){
		int b = knowledgeWebMapper.addUserKnowledgeInfo(knowledge);
		return b == 1;
		
	}
	public boolean hasUserKnowledgeInfo(Knowledge knowledge){
		int b = knowledgeWebMapper.hasUserKnowledgeInfo(knowledge);
		return b == 1;
		
	}
	public boolean updateUserKnowledgeInfo(Knowledge knowledge){
		int b = knowledgeWebMapper.updateUserKnowledgeInfo(knowledge);
		return b == 1;
	}
}
