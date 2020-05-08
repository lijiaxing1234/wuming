package com.thinkgem.jeesite.modules.web.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.sdk.util.l;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.modules.web.dao.AppIndexMapper;
import com.thinkgem.jeesite.modules.web.entity.AD;
import com.thinkgem.jeesite.modules.web.entity.Course;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;


/**
 *  app首页 历年真题、每日一练、智能练习 接口 Service
 * 
 */
@Service
public class AppIndexService {
	
	/**
	 * 每日一练 以 “dailypractice_” 加课程Id
	 */
	private static final  String dailypractice="dailypractice";
	
	@Autowired
	AppIndexMapper appIndexMapper;
	
	@Autowired
	CourseWebService courseService;
	 
	
	
	 /**
	  * 根据课程Id 随机查询试题
	  * @param courseId 课程Id
	  * @param randomNumber 随机个数
	  * @return List<Questions>  试题集合
	  */
     public  List<Questions>  getRandomQuestionListByCourseId(String courseId,Integer randomNumber){
   	   return  getRandomQuestionListByCourseId(courseId,null,randomNumber);
     }
	 /**
	  * 根据课程Id 随机查询试题
	  * @param courseId 课程Id
	  * @param quesType  题型（可为空）
	  * @param randomNumber 随机个数
	  * @return List<Questions>  试题集合
	  */
     public  List<Questions>  getRandomQuestionListByCourseId(String courseId,String quesType,Integer randomNumber){
    	 return  appIndexMapper.getRandomQuestionListByCourseId(courseId,quesType,randomNumber);
     }
     
     
	
     /**
	  * 根据试题Id查询试题
	  * @param courseId 课程Id
	  * @return Questions  试题信息
	  */
     public  Questions  getQuestionById(String quesId){
    	 List<Questions> list=  appIndexMapper.getQuestionById(quesId);
    	 if(list!=null && list.size()>0){
    		 return list.iterator().next();
    	 }
    	 return null;
     }
     
 	/**
 	 * 查询随机出的试题的题型
 	 * @param courseId 课程Id
 	 * @return List<Map<String,String>>题型集合 <h4>Map<String,String> key(题型Id)->id,value(题型label)->value</h4> 
 	 */
     public List<Map<String,String>> getRandomQuestionTypeListByCourseId(String courseId){
    	 return  appIndexMapper.getRandomQuestionTypeListByCourseId(courseId);
     }
     
     
     /**
      * 根据题型随机取试题
      * @param courseId 课程Id
	  * @param randomNumber 每种题型随机个数
      * @return List<Questions> 试题集合
      */
     public  List<Questions> getRandomQuestionListByCourseIdOrderByQuesType(String courseId,Integer randomNumber){
    	 
    	 List<Questions>  list=Lists.newArrayList();
    	 
    	 List<Map<String,String>> quesTypeList=getRandomQuestionTypeListByCourseId(courseId);
    	 
    	 for(Map<String,String> quesTypeMap:quesTypeList){
    		 
    		 String  key=quesTypeMap.get("id");
    		// String  value=quesTypeMap.get("value");
    		 
    		 List<Questions> quesList= getRandomQuestionListByCourseId(courseId,key,randomNumber);
    		 list.addAll(quesList);
    	 }
    	 return  list;
     }
     
     
     
    /**
     *  根据课程Id 查询课程下所有知识点
     * @param courseId  课程Id
     * @param treelevel 树的级别 （可为null）
     * @return List<Knowledge> 课程下所有知识点
     */
 	public List<Knowledge> selectKnowledgeByCourseIdList(String courseId,Integer treelevel) {
 		List<Knowledge> selectKnowledgeByCourseIdList = appIndexMapper.selectKnowledgeByCourseIdList(courseId,treelevel);
 		List<Knowledge> list = new ArrayList<Knowledge>();
 		for (int i = 0; i < selectKnowledgeByCourseIdList.size(); i++) {
			if (selectKnowledgeByCourseIdList.get(i).getTotalQuestion()>0) {
				list.add(selectKnowledgeByCourseIdList.get(i));
			}
		}
		return list;
	}
    
 	public List<Knowledge> selectKnowledgeByCourseIdListPage(String courseId,Integer treelevel) {
		return appIndexMapper.selectKnowledgeByCourseIdListPage(courseId,treelevel);
	}
    /**
     * 根据课程Id和知识点id 查询所有试题
     * @param courseId  课程Id
     * @param knowId   知识点id
     * @return List<Questions> 试题
     */
	public List<Questions> selectQuestionByKnowIdList(String courseId, String knowId) {
		List<Questions> list = appIndexMapper.selectQuestionByKnowIdList(courseId,knowId);
		for (int i = 0; i < list.size(); i++) {
			String trueVersionId = appIndexMapper.getTrueVersionId(list.get(i).getId());
			list.get(i).setTrueVersionId(trueVersionId);
		}
		
		return list;
	}
  
     
     
    /**
     * 根据分类父id查询所有下级分类
     * @param courseId  课程id
     * @param parentCateId  分类父id
     * @param treelevel  分类树级别
     * @return List<ResCategory> 分类列表
     */
	public List<ResCategory> selectResCategoryByCourseIdList(String courseId, String parentCateId, Integer treelevel) {
		
		String[] courseIds={};
		if(StringUtils.isNotBlank(courseId)){
			courseIds=courseId.split(",");
		}else{
			courseIds=null;
		}
		
		return appIndexMapper.selectResCategoryByCourseIdList(courseIds,parentCateId,treelevel);
	}
	
	
	public List<ResCategory> categoryListById(String id) {
		
		 
		return appIndexMapper.categoryListById(id);
	}
	
	
     
     
     
     
     
     /**
      * 初始化
      * 每日一练 随机一个试题 保存到properties 文件
      */
     public  void  initeDailyPracticeProperties(){
     	
     	System.out.println("/*********************************************任务开始*******************************************************/");
     	
     	String currentDate=DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
     	System.out.println("每日一练 任务开始->开始时间"+currentDate);
     	
     	String fileName=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "dailyPractice.fileName");
     	File file=new File(SystemPath.getClassPath(),fileName);
     	if(!file.exists()){
     		try {
				file.createNewFile();
				//确保文件创建成功
				Thread.sleep(2000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
     		
     	}
     	
     	String filePath=file.getAbsolutePath();
     	System.out.println(filePath);
     	
     	List<Course> list = courseService.getAllCourse();
     	
     	Map<String,String> mapKey=Maps.newHashMap();
     	
     	for(Course course:list){
     	    
     		String key=String.format(dailypractice+"_%s",course.getId());
     		
     		String id=GlobalConfigUtils.readValue(filePath, key);
 		
     		System.out.println("每日一练修改->{之前}的值:"+(StringUtils.isNotBlank(id) ? id:"为空"));
     		
     		List<Questions> quesList=getRandomQuestionListByCourseId(course.getId(),1);
     		
     		if(quesList.size()>0){
     		 id=quesList.iterator().next().getId();
     		}else{
     			id="";
     		}
     		
     		mapKey.put(key, id);
     		
 	        GlobalConfigUtils.clearAfterSaveProperties(filePath,mapKey,"修改app每日一练时间："+currentDate);
 	        
     	    System.out.println("每日一练修改->{之后}的值:"+(StringUtils.isNotBlank(id) ? id:"为空"));
     	
     	}
     	
     	System.out.println("/*********************************************任务结束*******************************************************/");
     }

     public List<AD> getIndexAd(){
    	 List<AD> indexAd = appIndexMapper.getIndexAd();
    	 return indexAd;
     }
     
     public List<Knowledge> selectRecomZhentiList(String courseId,Integer treelevel){
    	 return appIndexMapper.selectRecomZhentiList(courseId, treelevel);
     }
}
