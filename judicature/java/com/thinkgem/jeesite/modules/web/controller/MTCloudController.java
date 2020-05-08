package com.thinkgem.jeesite.modules.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.questionlib.entity.Student;
import com.thinkgem.jeesite.modules.questionlib.service.StudentService;
import com.thinkgem.jeesite.modules.resource.entity.ColumnKey;
import com.thinkgem.jeesite.modules.resource.entity.LVCourse;
import com.thinkgem.jeesite.modules.resource.entity.LVCourseImplement;
import com.thinkgem.jeesite.modules.resource.entity.LVFeedback;
import com.thinkgem.jeesite.modules.resource.service.LVCourseService;
import com.thinkgem.jeesite.modules.resource.service.LVFeedbackService;
import com.thinkgem.jeesite.modules.web.dto.MTCourse;
import com.thinkgem.jeesite.modules.web.dto.MTJSON;
import com.thinkgem.jeesite.modules.web.dto.MTZhubo;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.mtcloud.MTCloud;


@Controller
@RequestMapping("/web/mtcloud")
public class MTCloudController extends  BaseController {
	
	@Autowired
	LVCourseService  courseService;
	
	@Autowired
	LVFeedbackService  feedbackService;
	
	@Autowired
	StudentService  studentService; //用户
	
	/**
	 * 直播课程列表
	 * @return
	 */
	@SuppressWarnings("serial")
	@ResponseBody
	@RequestMapping("courseList")
	public String  anchorList(@RequestParam(value="pageNum",required=true)Integer pageNum,@RequestParam(value="pageSize",required=true)Integer pageSize,String userId){
	
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		MTJSON<List<MTCourse>>  mtJson=null;
		try {
			  
			  String start_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.start_time");
		      String end_time=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "mtcloud.courselist.end_time");
			
			 //status：课程状态(0为正常状态，-1为已删除)，expire:回放地址有效期(如传3600表示小时)
			  HashMap<Object,Object> options = new HashMap<Object,Object>();
			  options.put("status", 0);
			 
			  String  result= client.courseList(start_time, end_time, pageNum, pageSize, options);
			  
			   mtJson = JSON.parseObject(result, new TypeReference<MTJSON<List<MTCourse>>>() {});
			  
			  for(MTCourse course:mtJson.getData()){
				  LVCourse lvCourse= courseService.getCourseByLVCourseId(course.getCourse_id(),userId);
				  
				  try{
				      MTJSON<MTZhubo>  mtZhubo=new MTJSON<MTZhubo>();
				      String  mtzhuboResult= client.zhuboGet(course.getBid(), null);
				      mtZhubo=JSON.parseObject(mtzhuboResult, new TypeReference<MTJSON<MTZhubo>>(){});
				      course.setMtZhubo(mtZhubo.getData());
				      
				  }catch (Exception e) {}
				  
				  course.setLvCourse(lvCourse);
			  }
			  
			  map.put("mtstatus",true);
			  map.put("json", mtJson);
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
	}
	
	
	/**
	 * 生成进入直播课程 access_token
	 * @return
	 */
	@ResponseBody
	@RequestMapping("courseAccess")
	public String  courseAccess(@RequestParam(value="courseId",required=true)String courseId,
			                    @RequestParam(value="userId",required=true)String userId,
			                    @RequestParam(value="userNickName",required=true)String userNickName){
	
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		try {
			
			 /*
			  * 
			  * 
				     course_id	String	Y	课程ID
					uid	String	Y	合作方观看用户唯一ID
					nickname	String	Y	合作方观看用户昵称
					role	String	Y	用户身份(user/admin/spadmin/guest，分别对应普通用户/管理员/超级管理员/游客)
					expire	int	N	有效期,默认:3600(单位:秒)
			  * 
			  * 	其它可选项，gender：性别(1为男性，2为女性)，
			  *               avatar：头像链接(默认头像可不传，由欢拓来配置)，
			  *                gid：分组ID，
			  *                ssl：是否使用https(true为使用，false为不使用)
			  *                
			  *                
			  *   
			  * */
			  HashMap<Object,Object> options = new HashMap<Object,Object>();
			  String  result=client.courseAccess(courseId, userId, userNickName, MTCloud.ROLE_USER, 3600, options);
			  map.put("mtstatus", true);
			  map.put("json",JSONObject.parse(result));
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
	}
	
	/**
	 * 课程或老师 介绍
	 * @param columnKey
	 * @param model
	 * @return
	 */
	@RequestMapping({ "getpreViewByID" })
	public String getpreViewByID(ColumnKey columnKey, final Model model){
		
		if(StringUtils.isNotBlank(columnKey.getColId())){
		   /*直播对应本系统数据*/
		   LVCourse  lvCourse= courseService.getCourseByLVCourseId(columnKey.getColId());
		   
		   if(lvCourse !=null){
			   LVCourseImplement implement=new LVCourseImplement();
			   implement.setLvCourseId(columnKey.getColId());
			   implement.setLvCourseCate(Short.valueOf(columnKey.getColCate()));
			   List<LVCourseImplement>  implementList=courseService.selectCourseImplementList(implement);
			   
			   model.addAttribute("lvCourse", lvCourse);  
			   model.addAttribute("implementList", implementList);
		   }
		   
		}
    	return "modules/lv/lvCoursePreView";
	}
	
	
	

	/**
	 *课程回放列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("clipListCourseId")
	public String  clipListByCid(@RequestParam(value="courserId",required=true)Integer courserId,
			                     @RequestParam(value="pageNum",required=true)Integer pageNum,
			                     @RequestParam(value="pageSize",required=true)Integer pageSize){
	
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		try {
			 //status：课程状态(0为正常状态，-1为已删除)，expire:回放地址有效期(如传3600表示小时)
			  HashMap<Object,Object> options = new HashMap<Object,Object>();
			  options.put("status", 0);
			 
			  String  result= client.clipListByCid(courserId, pageNum, pageSize);
			  
			  map.put("mtstatus",true);
			  map.put("json", JSONObject.parse(result));
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
	}
	
	/**
	 * 课程回放 生成 token
	 * @param courserId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("clipAccess")
	public String  clipAccess(@RequestParam(value="clipid",required=true)Integer clipid,
			                     @RequestParam(value="userId",required=true)String userId,
			                     @RequestParam(value="userNickName",required=true)String userNickName,
			                     @RequestParam(value="expire",required=false)Integer expire){
	
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		try {
			  if(expire ==null ){
				  expire=3600;
			  }
			  String  result=client.clipAccess(clipid, userId, userNickName,expire);
			  map.put("mtstatus",true);
			  map.put("json", JSONObject.parse(result));
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
	}
    
	
	
	 
	@ResponseBody
	@RequestMapping("scanByCourseId")
	public String  clipListByCid(@RequestParam(value="courserId",required=true)String courserId,@RequestParam(value="userId",required=true)String userId){
	
	    Map<String,Object> map=Maps.newHashMap();
	    if(org.apache.commons.lang3.StringUtils.isNotBlank(courserId)){
	    	
			MTCloud client = new MTCloud(); 
			
			MTJSON<MTCourse>  mtJson=null;
			try {
				  String  result= client.courseGet(courserId);
				  
				  mtJson = JSON.parseObject(result, new TypeReference<MTJSON<MTCourse>>() {});
				  
				  if(mtJson !=null){
					  
					  MTCourse  course=mtJson.getData();
					  
					  if(course !=null ){
						  
					      LVCourse lvCourse= courseService.getCourseByLVCourseId(course.getCourse_id(),userId);
						/*  try{
						      MTJSON<MTZhubo>  mtZhubo=new MTJSON<MTZhubo>();
						      String  mtzhuboResult= client.zhuboGet(course.getBid(), null);
						      mtZhubo=JSON.parseObject(mtzhuboResult, new TypeReference<MTJSON<MTZhubo>>(){});
						      course.setMtZhubo(mtZhubo.getData());
						      
						  }catch (Exception e) {}*/
						  
						  course.setLvCourse(lvCourse);
					  }
					  
					  map.put("mtstatus",true);
					  map.put("json", course);
					  
				  }
				  
		
			} catch (Exception e) {
				e.printStackTrace();
				  map.put("mtstatus", true);
				  map.put("json",new Object());
			}
			
	    }
	    return JSON.toJSONString(map);
	}
	
	
	
	
	
   
    	

	/**
	 * 课程评论接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping("feedbackList")
	public String  feedbackList(@RequestParam(value="courseId",required=true)String courseId,
			                    @RequestParam(value="start",required=true)Integer start,
			                    @RequestParam(value="pageSize",required=true)Integer pageSize){
	
		
		 Map<String,Object> map=Maps.newHashMap();
	     List<Map<String,Object>> resultList=Lists.newArrayList();
        
		try {
			   LVFeedback  feedback = new LVFeedback();
			   feedback.setLvCourseId(courseId);
			   List<LVFeedback> list= feedbackService.frontSelectFeedBackList(feedback, start, pageSize);
			   for(LVFeedback item:list){
				   Map<String,Object> jsonMap=Maps.newHashMap();
				   
				   List<Student>  userlist= studentService.selectByStudent(new Student(item.getUserId()));
                   if (userlist != null && userlist.size() >0) {
                   	Student student=userlist.iterator().next();
                       item.setPhone(student.getStdPhone());
                       item.setUserName(student.getName());
                   }  
				   
				   jsonMap.put("score", item.getScore());
				   jsonMap.put("lv_course_id", item.getLvCourseId());
				   jsonMap.put("content", item.getContent());
				   jsonMap.put("phone",item.getPhone());
				   jsonMap.put("userName",item.getUserName());
				   resultList.add(jsonMap);
			   }
	
			  map.put("mtstatus",true);
			  map.put("json", resultList);
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new ArrayList<LVFeedback>());
		}
	    return JSON.toJSONString(map);
	}
	
	
	@ResponseBody
	@RequestMapping("addfeedback")
	public String  addfeedback(LVFeedback feedback){
		
		 Map<String,Object> map=Maps.newHashMap();
        
		try {
		      feedbackService.insertSelective(feedback);
			  map.put("mtstatus",true);
			  map.put("json", "成功");
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json","失败");
		}
	    return JSON.toJSONString(map);
		
	}
	

	
	
	
	
	
	
	
	
	
	@ResponseBody
	@RequestMapping("recomLv")
	public String  getRecomLv(String userId){
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		MTJSON<MTCourse>  mtJson=null;
		List<MTJSON<MTCourse>> list = new ArrayList<MTJSON<MTCourse>>();
		try {
			  
			List<String> recomLvId = courseService.getRecomLvId();
			for (int i = 0; i < recomLvId.size(); i++) {
				String courseGet = client.courseGet(recomLvId.get(i));
				mtJson = JSON.parseObject(courseGet, new TypeReference<MTJSON<MTCourse>>() {});
				//for(MTCourse course:mtJson.getData()){
					LVCourse recomLv = courseService.getRecomLv(mtJson.getData().getCourse_id(),userId);
					//LVCourse lvCourse= courseService.getCourseByLVCourseId(course.getCourse_id());
					
					try{
						MTJSON<MTZhubo>  mtZhubo=new MTJSON<MTZhubo>();
						String  mtzhuboResult= client.zhuboGet(mtJson.getData().getBid(), null);
						mtZhubo=JSON.parseObject(mtzhuboResult, new TypeReference<MTJSON<MTZhubo>>(){});
						mtJson.getData().setMtZhubo(mtZhubo.getData());
						
					}catch (Exception e) {}
					
					mtJson.getData().setLvCourse(recomLv);
					list.add(mtJson);
				//}
			}
			  map.put("mtstatus",true);
			  map.put("json", list);
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
			
	}
	
	@ResponseBody
	@RequestMapping("hasBuyLv")
	public String  hasBuyLv(String userId){
	    Map<String,Object> map=Maps.newHashMap();
		MTCloud client = new MTCloud(); 
		MTJSON<MTCourse>  mtJson=null;
		List<MTJSON<MTCourse>> list = new ArrayList<MTJSON<MTCourse>>();
		try {
			List<String> ppCourseId = courseService.getPPCourseId(userId);
			if (ppCourseId.size()>0) {
				for (int i = 0; i < ppCourseId.size(); i++) {
					String courseGet = client.courseGet(ppCourseId.get(i));
					mtJson = JSON.parseObject(courseGet, new TypeReference<MTJSON<MTCourse>>() {});
					LVCourse recomLv = courseService.getRecomLv(mtJson.getData().getCourse_id(),userId);
					mtJson.getData().setLvCourse(recomLv);
					list.add(mtJson);
				}
			}
			  map.put("mtstatus",true);
			  map.put("json", list);
		} catch (Exception e) {
			e.printStackTrace();
			  map.put("mtstatus", true);
			  map.put("json",new Object());
		}
	    return JSON.toJSONString(map);
			
	}
	
	
	
	/**
	 * 插入用户观看 免费课程 关联表
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("adFreeMTCourse")
	public String  hasBuyLv(String userId,String courseId){
		
		 if(StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(courseId)){
		    Map<String,Object> map=Maps.newHashMap();
			try {
					int result=0;
					result=courseService.insertIgnoreFreeMTCourse(userId, courseId);
					
					if(result>0){
					 map.put("mtstatus",true);
					 map.put("json", "成功");
					}else{
						 map.put("mtstatus",true);
						 map.put("json", "数据已经存在");
					}
				} catch (Exception e) {
					  e.printStackTrace();
					 map.put("mtstatus", true);
					  map.put("json","失败");
				}
			    return JSON.toJSONString(map);
		 }
		 
		return null;
	}
	
}
