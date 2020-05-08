package com.thinkgem.jeesite.modules.teacher.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.test.SpringTransactionalContextTests;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.entity.StatisticsQuestionNumber;

public class TestQuestionslibSplitService extends SpringTransactionalContextTests {
	
	@Autowired
	QuestionslibSplitService splitService;
	
	
	@Test
	public void testGetAllQuestionsNumber(){
		
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("cf9f8e9ea9654bb8afed8053a5e77c23");
		qls.setSchoolId("d8a4a151878e41d4b538b4da1c9095d6");
		Long number=splitService.getAllQuestionsNumber(qls);
		System.out.println(number);
		
		//74a1708f024b4a259cf09bc43f50d632(String), cf9f8e9ea9654bb8afed8053a5e77c23(String), d8a4a151878e41d4b538b4da1c9095d6(String)
	}
	
	@Test
	public void testGetAllQuestionsNumberInfo(){
		
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("cf9f8e9ea9654bb8afed8053a5e77c23");
		qls.setSchoolId("d8a4a151878e41d4b538b4da1c9095d6");
		
		
		List<StatisticsQuestionNumber>  sourceLists=splitService.getAllQuestionsNumberInfo(qls);
		
		
		Long totalNumber=0L;
		
		for(StatisticsQuestionNumber item:sourceLists){
			
			System.out.print(item.getQuesType()+"===");
			System.out.println(item.getNumber());
			totalNumber+=item.getNumber();
		}
		
		System.out.println("总共："+totalNumber);
		
	}
	
	
	
	
	
	@Test
	public void testGetQuestionsSliptArr(){
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("cf9f8e9ea9654bb8afed8053a5e77c23");
		qls.setSchoolId("d8a4a151878e41d4b538b4da1c9095d6");
		qls.setTeacherId("74a1708f024b4a259cf09bc43f50d632");
		
		Map<String,Long> map=splitService.getQuestionsSliptArr(qls);
		Long totalNumber=0L;
		for(Map.Entry<String, Long> entry:map.entrySet()){
			System.out.print(entry.getKey()+"		");
			System.out.println(totalNumber+","+entry.getValue());
			totalNumber+=entry.getValue();
		}
		System.out.println(totalNumber);
		
	}
	
	@Test
	public void testGetQuestionsSliptArrInfo(){
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("cf9f8e9ea9654bb8afed8053a5e77c23");
		qls.setSchoolId("d8a4a151878e41d4b538b4da1c9095d6");
		qls.setTeacherId("74a1708f024b4a259cf09bc43f50d632");
		
		Map<String,Map<String,Long>>  AllMap=splitService.getQuestionsSliptArrInfo(qls);
		
		Long totalNumberAll=0L;
		
	    for(Map.Entry<String, Map<String,Long>>  map:AllMap.entrySet()){
			
	    	System.out.println(map.getKey()+"================= 题型");
	    	
			Long totalNumber=0L;
			for(Map.Entry<String, Long> entry:map.getValue().entrySet()){
				System.out.print(entry.getKey()+"		");
				System.out.println(totalNumber+","+entry.getValue());
				totalNumber+=entry.getValue();
			}
			System.out.println(totalNumber);
			
			totalNumberAll+=totalNumber;
		}
	    
	    
	    System.out.println("总计：="+totalNumberAll);
	}
	
	
	
	
	
	
	
	
	@Test
	public void testInitQuestionslibSplitByTeacher(){
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("f01b54fa8a2e4726be9ed3698361fe6a");
		qls.setSchoolId("c002456dc3644ab8860ae4e2dc358c6a");
		qls.setTeacherId("a062f85adf8e4c169688da7666190ee1");
		
		splitService.initQuestionslibSplitByTeacher(qls);
		
		
	}
	@Test
	public void  testFindQuestionslibSplitByteacher(){
		QuestionslibSplit qls=splitService.getQuestionslibSplitWithTeacherInfo();
		qls.setVersionId("f01b54fa8a2e4726be9ed3698361fe6a");
		qls.setSchoolId("c002456dc3644ab8860ae4e2dc358c6a");
		qls.setTeacherId("a062f85adf8e4c169688da7666190ee1");
		Map<String,Object> sqlParam=qls.getSqlParam();
		sqlParam.put("examId", "4aae8b6ce8c54ca7964e63901f3b917e");
		sqlParam.put("typeArr",Arrays.asList("0","1")); 
		
		 List<QuestionslibSplit> list=splitService.findQuestionslibSplitByteacher(qls);
		 /*for(QuestionslibSplit item:list){
			 System.out.print(item.getTitle()+"  ");
			 System.out.print(item.getQuesType()+"  ");
			  
		 }*/
		
	}
	

}
