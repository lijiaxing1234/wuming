package com.thinkgem.jeesite.modules.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.web.dao.CourseWebMapper;
import com.thinkgem.jeesite.modules.web.dao.KnowledgeWebMapper;
import com.thinkgem.jeesite.modules.web.dao.UserAnswerMapper;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.UserRecordAnswer;
import com.thinkgem.jeesite.modules.web.util.NumberUtils;

@Service
public class CourseWebService {

	@Autowired
	CourseWebMapper courseMapper;
	
	@Autowired
	KnowledgeWebMapper  knowMapper;
	
	@Autowired
	UserAnswerMapper  userAnswerMapper;
	
	public List<Course> getAllCourse(){
		List<Course> list = courseMapper.getAllCourse();
		return list;
	}

	
	/**
	 * 
	 * @param courseId
	 * @param userId
	 * @return
	 */
	public Course getCourseById(String courseId, String userId) {
		
		
		Course course=new Course();
		
		Integer totalQuestion=userAnswerMapper.countQuestionBycourseIdKnowId(courseId, null);
		course.setTotalQuestion(totalQuestion);
		
		//用户答题情况统计
		UserRecordAnswer  recordAnswer=new UserRecordAnswer();
		recordAnswer.setUserId(userId);
		recordAnswer.setCourseId(courseId);
		
		List<Map<String,Object>> userAnswerList=userAnswerMapper.statisUserAnswer(recordAnswer);
		Map<String,Object>  map=userAnswerList.iterator().next();
		
		course.setHasDoneQuestion(new Long(map.get("hasDoneQuestion").toString()).intValue());
		System.out.println("-----------------  userId   ---------  " + userId);
		if (StringUtils.isBlank(userId)) {
			course.setRightQuestion(0);
			course.setWrongQuestion(0);
		  
		}else
		{
			course.setRightQuestion(new Long(map.get("rightQuestion").toString()).intValue());
			course.setWrongQuestion(new Long(map.get("wrongQuestion").toString()).intValue());
			
		}
		
		
		
	  
		if(course.getHasDoneQuestion() !=null && course.getHasDoneQuestion() >0){
		  course.setAccuracy(NumberUtils.div(course.getRightQuestion(), course.getHasDoneQuestion(), 2));
		}
		
		
		List<Knowledge> knowListend= new ArrayList<Knowledge>();
	    List<Knowledge> knowList=knowMapper.getKnowledgeList(courseId,"0");
		int courseCount = 0;
	    for(Knowledge know:knowList){
	    	int fknowCount = 0;
	    	setKnowledge(know,courseId,userId,know.getId(),null);
	    	
	        List<Knowledge> childList=knowMapper.getKnowledgeList(courseId, know.getId());
	        if (childList.size() > 0) {
				for (Knowledge child : childList) {
					Integer totalQuestion2 = child.getTotalQuestion();
					fknowCount += totalQuestion2;
					setKnowledge(child, courseId, userId, know.getId(), child.getId());
				}
				courseCount += fknowCount;
				know.setSonKnowledges(childList);
				know.setTotalQuestion(fknowCount);
				if (fknowCount >0) {
					knowListend.add(know);
		        }
				fknowCount= 0;
	        }
	        else
	        {
	        	fknowCount = know.getTotalQuestion();
	        	if (fknowCount >0) {
	        		ArrayList<Knowledge> arrayList = new ArrayList<Knowledge>();
		        	know.setSonKnowledges(arrayList);
		        	courseCount += fknowCount;
					know.setTotalQuestion(fknowCount);
					knowListend.add(know);
				}
	        	
	        }
	    }
		course.setTotalQuestion(courseCount);
	    course.setKnowledges(knowListend);
		return course;
	}
	
	
	
	/**
	 * 赋值
	 * @param know   
	 * @param courseId
	 * @param userId
	 * @param knowFirst
	 * @param knowSecord
	 */
	private  void  setKnowledge(Knowledge know,String courseId,String userId,String knowFirst,String knowSecord){
		
		UserRecordAnswer  recordAnswer=new UserRecordAnswer();
    	recordAnswer.setCourseId(courseId);
    	recordAnswer.setUserId(userId);
    	
    	if(StringUtils.isNotBlank(knowFirst)){
    		recordAnswer.setKnowFirst(knowFirst);
    	}
    	
    	if(StringUtils.isNoneBlank(knowSecord)){
    		recordAnswer.setKnowSecond(knowSecord);
    	}
    	
    	List<Map<String,Object>>   userAnswerList=userAnswerMapper.statisUserAnswer(recordAnswer);
	    Map<String,Object> map=userAnswerList.iterator().next();
    	
	    
	    if (StringUtils.isBlank(userId)) {
	    	know.setHasDoneQuestion(0);
		    know.setRightQuestion(0);
		    know.setWrongQuestion(0);
		  
		}else
		{
			know.setHasDoneQuestion(new Long(map.get("hasDoneQuestion").toString()).intValue());
		    know.setRightQuestion(new Long(map.get("rightQuestion").toString()).intValue());
		    know.setWrongQuestion(new Long(map.get("wrongQuestion").toString()).intValue());
		}
	    
	    
	    
	    if(know.getHasDoneQuestion()!=null && know.getHasDoneQuestion()>0)
	       know.setAccuracy(NumberUtils.div(know.getRightQuestion(), know.getHasDoneQuestion(), 2));
	}
}
