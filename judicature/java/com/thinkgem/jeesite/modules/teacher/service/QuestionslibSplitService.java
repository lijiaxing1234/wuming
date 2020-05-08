package com.thinkgem.jeesite.modules.teacher.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.zxing.Result;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.modules.questionlib.entity.VersionQuestion;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.teacher.dao.QuestionslibSplitDao;
import com.thinkgem.jeesite.modules.teacher.entity.QuestionslibSplit;
import com.thinkgem.jeesite.modules.teacher.entity.StatisticsQuestionNumber;
import com.thinkgem.jeesite.modules.teacher.utils.TearcherUserUtils;

/**
 * 试题分库逻辑
 * @author flychao
 */
@Service
public class QuestionslibSplitService {
   
	@Autowired
	QuestionslibSplitDao splitDao;
	
	 
	  /**
	   * 获得练习题的百分比
	   * @return Double 当前老师设置练习题的百分比
	   */
	 public Double  getPracticePercentByTeacher(){
		 return getPracticePercentByTeacher(getQuestionslibSplitWithTeacherInfo());
	 }
	 /**
	  * 获得练习题的百分比
	  * @return Double 当前老师设置练习题的百分比
	  */
	 private Double  getPracticePercentByTeacher(QuestionslibSplit qls){
		// QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		 return splitDao.getPracticePercentByTeacher(qls);
	 }
	 
	 
	 
	 /**
	   * 插入练习题的百分比
	   * @param percent 百分比
	   * @return int 数据库受影响行数
	   */
	public  int  insertPracticePercentByTeacher(String percent){
	    QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
	    qls.getSqlMap().put("percent", percent);
		return insertPracticePercentByTeacher(qls);
	}
	
	/**
	 * 插入练习题的百分比
	 * @param percent 百分比
	 * @return int 数据库受影响行数
	 */
	private  int  insertPracticePercentByTeacher(QuestionslibSplit qls){
		int result=splitDao.insertPracticePercentByTeacher(qls);
		if(result>0){ 
			initQuestionslibSplitByTeacher(qls);  //修改百分比成功后 初始化分库表
		}
		return result;
	}
	
	
	
	  /**
	   * 修改练习题的百分比
	   * @param percent 百分比
	   * @return int 数据库受影响行数
	   */
	 public  int  updatePracticePercentByTeacher(String percent){
		  QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		  qls.getSqlMap().put("percent", percent);
		return updatePracticePercentByTeacher(qls);
	  }
	  /**
	   * 修改练习题的百分比
	   * @param percent 百分比
	   * @return int 数据库受影响行数
	   */
	  private int  updatePracticePercentByTeacher(QuestionslibSplit qls){
		  int result=splitDao.updatePracticePercentByTeacher(qls);
		  if(result>0){
			 initQuestionslibSplitByTeacher(qls); //修改百分比成功后 初始化分库表
		  }
		  return result;
	  }
	
	 
	 /**
	  * 保存百分比
	  * @param percent  练习题的百分比
	  * @return int  数据库受影响行数
	  */
	 public int saveQuestonPercent(String percent){
		int result=0;
		 QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		  qls.getSqlMap().put("percent", percent);
		
		Double number=getPracticePercentByTeacher(qls);
		if(number !=null){
			result=updatePracticePercentByTeacher(qls);
		}else{
			result=insertPracticePercentByTeacher(qls);
		}
	    return result;
	 }
	
	
	
	
	/**
	 * 根据老师设置的练习题的百分比把老师所在学校的题库和老师自己的题库进行分库
	 * <br/>
	 * 例如: 30%是练习库 那么剩下的70%就是在线考试和组卷库。
	 * @return
	 */
	public void  initQuestionslibSplitByTeacher(){
	    initQuestionslibSplitByTeacher(getQuestionslibSplitWithTeacherInfo());
	}
	/**
	 * 根据老师设置的练习题的百分比把老师所在学校的题库和老师自己的题库进行分库
	 * <br/>
	 * 例如: 30%是练习库 那么剩下的70%就是在线考试和组卷库。
	 */
	
	public void  initQuestionslibSplitByTeacher(QuestionslibSplit qls){
		
		initQuestionslibSplitByTeacherWithSingleQuestionType(qls);
		
		/*
		Map<String,Long> map=getQuestionsSliptArr(qls);
		
		if(map!=null&&map.size()>0){
			
			deleteQuestionslibSplitByTeacher(qls);
			
			//System.out.println(totalNumber+","+entry.getValue());
			Long totalNumber=0L;
			for(Map.Entry<String, Long> entry:map.entrySet()){
				
			   qls.setType(entry.getKey());
		       Parameter param=qls.getSqlParam();
		       param.put("limitNumber", totalNumber+","+entry.getValue());
		       splitDao.initQuestionslibSplitByTeacher(qls);
		       
		       totalNumber+=entry.getValue();
			}
			
		}*/
	}
	
	/**
	 * 根据老师设置的练习题的百分比把老师所在学校的题库和老师自己的<B><strong>题库中某种题型</strong></B>进行分库
	 * <br/>
	 * 例如: 30%是练习库 那么剩下的70%就是在线考试和组卷库。
	 */
	public void  initQuestionslibSplitByTeacherWithSingleQuestionType(QuestionslibSplit qls){
		
		Map<String,Map<String,Long>> mapAll=getQuestionsSliptArrInfo(qls);
		
		if(mapAll!=null&&mapAll.size()>0){
			
		  deleteQuestionslibSplitByTeacher(qls); //清除数据库数据
			
		 for(Map.Entry<String, Map<String,Long>>  map:mapAll.entrySet()){
			
				Map<String,Long>  mapValus=map.getValue();
				
				if(mapValus!=null&&mapValus.size()>0){
				
					
					
					
					Long totalNumber=0L;
					for(Map.Entry<String, Long> entry:mapValus.entrySet()){
						
						Map<String,Object> sqlParam= qls.getSqlParam();
						sqlParam.put("quesType", map.getKey());
						qls.setType(entry.getKey());
						sqlParam.put("limitNumber", totalNumber+","+entry.getValue());
						
						splitDao.initQuestionslibSplitByTeacher(qls);
						
						totalNumber+=entry.getValue();
					}
				}
		  }
		}
	}
	
	
	/**
	 * 删除分库表当前老师所设百分比的所有试题
	 */
    public void  deleteQuestionslibSplitByTeacher(){
    	deleteQuestionslibSplitByTeacher(getQuestionslibSplitWithTeacherInfo());
    }
	/**
	 * 删除分库表当前老师所设百分比的所有试题
	 */
    public void  deleteQuestionslibSplitByTeacher(QuestionslibSplit qls){
    	splitDao.deleteQuestionslibSplitByTeacher(qls);
    }
	
	
	
	/**
	 * 获得所有可用试题的数量
	 * @return Long 当前老师所有可用试题的数量
	 */
	public Long getAllQuestionsNumber(QuestionslibSplit qls){
		return splitDao.getAllQuestionsNumber(qls);
	}
	
	public List<StatisticsQuestionNumber>  getAllQuestionsNumberInfo(QuestionslibSplit qls){
		return splitDao.getAllQuestionsNumberInfo(qls);
	}
	
	
	
	
	
	
	/**
	 * 计算分库的数量
	 * <ul>
	 * <li>map总key代表分库表（table_teacher_questionslib_split 中type字段）中的类型</li>
	 * <li>map总value代表数据库可用试题计算后数量</li>
	 * </ul>
	 * @param qls 
	 * @return Map<String,Long>  
	 */
	public Map<String,Long> getQuestionsSliptArr(QuestionslibSplit qls){
		
		Map<String,Long> map=Maps.newHashMap();
		 
		Double percent=getPracticePercentByTeacher(qls);
		Long number=getAllQuestionsNumber(qls);
		
		if(number==null){
			number=0L;
		}
		
		if(percent==null){
			insertPracticePercentByTeacher("100");
			percent=100D;
			map.put("0", number);
		}else{
			Double percentNumber=(percent/100);
			//练习题数量
			Long practiceCount=Math.round(number*percentNumber);
		    if(practiceCount.equals(number)){
		    	map.put("0", number);
		    }else{
				map.put("1", number-practiceCount);
				map.put("2", practiceCount);
		    }
		}
		return map;
	}
	
	public Map<String,Map<String,Long>> getQuestionsSliptArrInfo(QuestionslibSplit qls){
		 
		  Map<String,Map<String,Long>> allMap=Maps.newTreeMap();
		  
		  Double percent=getPracticePercentByTeacher(qls);
		  
		  if(percent==null){
			insertPracticePercentByTeacher("100");
			percent=100D;
		  }
		  
		  
		  List<StatisticsQuestionNumber> sourceList= getAllQuestionsNumberInfo(qls);
		  
		  Map<String,Long> map=null;
		  
		  for(StatisticsQuestionNumber item:sourceList){
			  
			   map=Maps.newHashMap();
			   Long quesNumber=item.getNumber();
			   
			   if(quesNumber==null){
				   quesNumber=0L;
			   }
			   
			   
			   Double percentNumber=(percent/100);
			   
				//练习题数量
				Long practiceCount=Math.round(quesNumber*percentNumber);
				
			    if(practiceCount.equals(quesNumber)){
			    	map.put("0", quesNumber);
			    	
			    }else{
			    	
					map.put("1", quesNumber-practiceCount);
					map.put("2", practiceCount);
			    }
			    
			  allMap.put(item.getQuesType(), map);
		  }
		  
		 return allMap;
	}
	
	
	
	
	
	/**
	 * 
	 * 获得带有老师所教 (前提老师必须登录)
	 * <h2>课程版本Id<h2></br>
	 * <h2>老师Id<h2></br>
	 * <h2>老师所在学校Id<h2></br>
	 * 的QuestionslibSplit 类
	 * @return QuestionslibSplit 类
	 */
	public QuestionslibSplit getQuestionslibSplitWithTeacherInfo(){
		User teacher=TearcherUserUtils.getUser();
		QuestionslibSplit qls=new QuestionslibSplit();
		if(teacher !=null){
			qls.setTeacherId(teacher.getId());
			qls.setVersionId(TearcherUserUtils.getCourseVesion().getId());
			qls.setSchoolId(teacher.getCompany().getId());
		}
		return qls;
	}
	
	
	/**
	 * 获得在线组卷、在线考试已选中知识点的试题
	 * @param examId 在线考试或组卷在线 、随堂练习、课后作业、课堂例题 Id
	 * @return List<QuestionslibSplit> 分库下考试题
	 */
	public List<VersionQuestion> findExamQuestionslibSplitByteacher(String examId){
		
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","1")); 
		
		List<VersionQuestion> list=Lists.newArrayList();
		List<QuestionslibSplit> dataList=splitDao.findQuestionslibSplitByteacher(qls);
		for(QuestionslibSplit item:dataList){
			list.add((VersionQuestion)item);
		}
		return list;
		
	}	
	/**
	 * 获得练习题已选中知识点的试题
	 * @param examId 练习题Id
	 * @return List<QuestionslibSplit> 分库下练习题
	 */
	public List<VersionQuestion> findPracticeQuestionslibSplitByteacher(String examId){
		
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","2")); 
		
		List<VersionQuestion> list=Lists.newArrayList();
		List<QuestionslibSplit> dataList=splitDao.findQuestionslibSplitByteacher(qls);
		for(QuestionslibSplit item:dataList){
			list.add((VersionQuestion)item);
		}
		return list;
		
	}	
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的考试试题 <h3>按题类型分组统计自动出题试题信息</h3>
	 * <br> 例如：可用分数： 30.0 题的个数： 30 题的分数： 1(30(题的个数)) 
	 * @param examId  在线考试或组卷在线 、随堂练习、课后作业、课堂例题 Id
	 * @return List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> statisticsExamQuestionslibSplitScoreInfoByteacher(String examId){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","1")); 
		
		return  statisticsQuestionslibSplitScoreInfoByteacher(qls);
	}
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的 练习试题 <h3>按题类型分组统计自动出题试题信息</h3>
	 * <br> 例如：可用分数： 30.0 题的个数： 30 题的分数： 1(30(题的个数)) 
	 * @param examId  练习组卷id
	 * @return List<Map<String,Object>> 
	 */
	public List<Map<String,Object>> statisticsPracticeQuestionslibSplitScoreInfoByteacher(String examId){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","2")); 
		
		return  statisticsQuestionslibSplitScoreInfoByteacher(qls);
	}
	
	
	
	
	/**
	 * 查询分库后某种类型是<h2>在线考试或组卷在线 、随堂练习、课后作业、课堂例题 Id</h2>可以补题的数量
	 * @param map 中的key 必须有<ul>
	 *  <li>examId  在线考试和组卷Id</li>
	 *  <li>examDetailId  AB表Id</li>
	 *  <li>quesType  试卷的类型</li>
	 * </ul>
	 * @return Integer 可用的数量
	 */
	public int  countExamQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(Map<String,Object> map){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		//sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","1")); 
		sqlParam.putAll(map);
		Integer result=null;
		result= splitDao.countQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
		return result==null? 0: result.intValue();
	}
	
	/**
	 * 查询分库后某种类型是<h2>在线考试或组卷在线 、随堂练习、课后作业、课堂例题 Id</h2>随机指定数量的的集合
	  * @param map 中的key 必须有<ul>
	 *  <li>examId  在线考试和组卷Id</li>
	 *  <li>examDetailId  AB表Id</li>
	 *  <li>quesType  试卷的类型</li>
	 *  <li>countNumber  随机出题的数量</li>
	 * </ul>
	 * @return List<VersionQuestion> 随机后的集合
	 */
	public List<VersionQuestion>  findRandomExamQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(Map<String,Object> map){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		//sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","1")); 
		sqlParam.putAll(map);
		return splitDao.findRandomQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
	}
	
	/**
	 * 查询分库后某种类型是<h2>练习题</h2>可以补题的数量
	 * @param map 中的key 必须有<ul>
	 *  <li>examId  在线考试和组卷Id</li>
	 *  <li>examDetailId  AB表Id</li>
	 *  <li>quesType  试卷的类型</li>
	 * </ul>
	 * @return Integer 可用的数量
	 */
	public Integer  countPracticeQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(Map<String,Object> map){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		//sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","2")); 
		sqlParam.putAll(map);
		return splitDao.countQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
	}
	
	/**
	 * 查询分库后某种类型是<h2>练习题</h2>随机指定数量的的集合
	 * @param map 中的key 必须有<ul>
	 *  <li>examId  在线考试和组卷Id</li>
	 *  <li>examDetailId  AB表Id</li>
	 *  <li>quesType  试卷的类型</li>
	 *  <li>countNumber  随机出题的数量</li>
	 * </ul>
	 * @return List<VersionQuestion> 随机后的集合
	 */
	public List<VersionQuestion>  findRandomPracticeQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(Map<String,Object> map){
		QuestionslibSplit qls=getQuestionslibSplitWithTeacherInfo();
		Map<String,Object> sqlParam=qls.getSqlParam();
		//sqlParam.put("examId", examId);
		sqlParam.put("typeArr",Arrays.asList("0","2")); 
		sqlParam.putAll(map);
		return splitDao.findRandomQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
	}
	
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题
	 * @param qls
	 * @return List<QuestionslibSplit> 分库下全部试题
	 */
	public List<QuestionslibSplit> findQuestionslibSplitByteacher(QuestionslibSplit qls){
		
		return splitDao.findQuestionslibSplitByteacher(qls);
	}	

	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题的数量
	 * @param qls
	 * @return Long 试题的数量
	 */
	 public  Long countQuestionslibSplitByteacher(QuestionslibSplit qls){
		 
		 return splitDao.countQuestionslibSplitByteacher(qls);
	 }
	
	/**
	 * 获得老师按百分比分库下所在学校及所教版本下的全部试题 <h3>按题类型分组统计自动出题试题信息</h3>
	 * <br> 例如：可用分数： 30.0 题的个数： 30 题的分数： 1(30(题的个数)) 
	 * @param qls
	 * @return List<QuestionslibSplit> Long
	 */
	public List<Map<String,Object>> statisticsQuestionslibSplitScoreInfoByteacher(QuestionslibSplit qls){
		return  splitDao.statisticsQuestionslibSplitScoreInfoByteacher(qls);
	}
	
	
	/**
	 * 查询分库后某种类型可以补题的数量
	 * @param qls
	 * @return Integer
	 */
	public Integer  countQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(QuestionslibSplit qls){
		return splitDao.countQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
	}
	
	/**
	 * 查询分库后某种类型随机指定数量的的集合
	 * @param qls
	 * @return List<VersionQuestion>
	 */
	public List<VersionQuestion>  findRandomQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(QuestionslibSplit qls){
		return splitDao.findRandomQuestionslibSplitByTeacherAndExamIdAndExamDetailIdAndQuesType(qls);
	}
	
}
