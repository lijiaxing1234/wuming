package com.thinkgem.jeesite.modules.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.persistence.Parameter;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.web.dto.SearchResult;
import com.thinkgem.jeesite.modules.web.entity.AD;
import com.thinkgem.jeesite.modules.web.entity.IndexList;
import com.thinkgem.jeesite.modules.web.entity.Knowledge;
import com.thinkgem.jeesite.modules.web.entity.Questions;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.service.AppIndexService;
import com.thinkgem.jeesite.modules.web.service.ResService;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;


/**
 * app首页 历年真题、每日一练、智能练习 、发现栏目 接口
 * 
 */
@Controller
@RequestMapping("/web/index")
public class AppIndexController extends BaseController {
	
	
	
	@Autowired
	AppIndexService appIndexService;
	
	@Autowired
	ResService  resService;
	
	
	
	
	
	/**
	 * 历年真题列表
	 * @param treelevel 知识点树的级别
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("pastYears/list")
	public String  pastYearslist(@RequestParam(value="treelevel",required=false,defaultValue="2") Integer treelevel){
		
		ReturnData  result=new ReturnData();
		List<Knowledge>  list=Lists.newArrayList();
		//历年真题的课程Id
		String courseId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "pastYears.courseid");
		try{
			List<Knowledge>  knowList=appIndexService.selectKnowledgeByCourseIdList(courseId,treelevel);
			if(knowList!=null && knowList.size()>0){
				list.addAll(knowList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("recomYear")
	public String  recomYear(@RequestParam(value="treelevel",required=false,defaultValue="2") Integer treelevel){
		
		ReturnData  result=new ReturnData();
		List<Knowledge>  list=Lists.newArrayList();
		//历年真题的课程Id
		String courseId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "pastYears.courseid");
		try{
			List<Knowledge>  knowList=appIndexService.selectRecomZhentiList(courseId,treelevel);
			if(knowList!=null && knowList.size()>0){
				list.addAll(knowList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	@ResponseBody
	@RequestMapping("pastYearsQues/list")
	public String  pastYearsQueslist(@RequestParam(value="knowId",required=true) String knowId){
		
		ReturnData  result=new ReturnData();
		List<Questions>  list=Lists.newArrayList();
		//历年真题的课程Id
		String courseId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "pastYears.courseid");
		try{
			List<Questions>  knowList=appIndexService.selectQuestionByKnowIdList(courseId,knowId);
			if(knowList!=null && knowList.size()>0){
				list.addAll(knowList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 每日一练
	 * @param courseId 课程Id
	 */
	@ResponseBody
	@RequestMapping("dailypractice")
	public  String  dailypractice(@RequestParam(value="courseId",required=true)String courseId){
		
		String key=String.format("dailypractice_%s",courseId);
		
		String fileName=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "dailyPractice.fileName");
     	File file=new File(SystemPath.getClassPath(),fileName);
     	if(!file.exists()){
     		appIndexService.initeDailyPracticeProperties();
     	}
     	
		String quesId=GlobalConfigUtils.readValue(file.getAbsolutePath(), key);
		
		ReturnData  result=new ReturnData();
		Questions  ques=null;
		try{
			result.setStatus(true);
			if(!StringUtils.isNoneBlank(quesId)){
				appIndexService.initeDailyPracticeProperties();
				quesId=GlobalConfigUtils.readValue(file.getAbsolutePath(), key);
			}
			
			if(StringUtils.isNoneBlank(quesId)){
				ques=appIndexService.getQuestionById(quesId);
				if(ques!=null){
					result.setData(ques);
				}
			}
			
		}catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(false);
			result.setData(new Questions());
		}
		
		return JsonUtil.toJson(result);
	}

	
	/**
	 * 智能练习
	 * @param courseId 课程Id
	 */
	@ResponseBody
	@RequestMapping("smartpractice")
	public  String  smartpractice(@RequestParam(value="courseId",required=true)String courseId){
		
		
		ReturnData  result=new ReturnData();
		List<Questions>  list=Lists.newArrayList();
		try{
			String smartpractice=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "smartpractice");
			Integer randomNumber=Integer.valueOf(smartpractice);
			
			result.setStatus(true);
			if(StringUtils.isNoneBlank(courseId)){
				List<Questions> quesInfoList=appIndexService.getRandomQuestionListByCourseId(courseId, randomNumber);
				if(quesInfoList!=null&&quesInfoList.size()>0){
					list.addAll(quesInfoList);
				}
			}
			result.setData(list);
		}catch (Exception e) {
			result.setMessage(e.getMessage());
			result.setStatus(false);
			result.setData(list);
		}
		
		return JsonUtil.toJson(result);
	}
	
	
	
	
	/***
	 * 视频发现 ->为您推荐
	 * @param treelevel 分类树级别
	 * @param  courseId  多个以","分割
	 * @return
	 */
	@ResponseBody
	@RequestMapping("video/cateRecommendList")
	public String  cateRecommendList(String courseId,@RequestParam(value="treelevel",required=false,defaultValue="2") Integer treelevel){
		
		ReturnData  result=new ReturnData();
		List<ResCategory>  list=Lists.newArrayList();
		//为您推荐
		String cateId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.recommendId");
		try{
			List<ResCategory>  resCateList=appIndexService.selectResCategoryByCourseIdList(courseId,cateId,treelevel);
			if(resCateList!=null && resCateList.size()>0){
				list.addAll(resCateList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	/***
	 * 视频发现 ->免费视频
	 * @param treelevel 分类树级别
	 * @param  courseId  多个以","分割
	 * @return
	 */
	@ResponseBody
	@RequestMapping("video/cateFreeVideoList")
	public String  cateFreeVideoList(String courseId,@RequestParam(value="treelevel",required=false,defaultValue="2") Integer treelevel){
		
		ReturnData  result=new ReturnData();
		List<ResCategory>  list=Lists.newArrayList();
		//为您推荐
		String cateId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.freeVideoId");
		try{
			List<ResCategory>  resCateList=appIndexService.selectResCategoryByCourseIdList(courseId,cateId,treelevel);
			if(resCateList!=null && resCateList.size()>0){
				list.addAll(resCateList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	
	/**
	 * 通过父Id 查询其下所有分类
	 * @param parentId
	 * @param treelevel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("video/categoryListByParentId")
	public String  categoryListByParentId(@RequestParam(value="parentId",required=true)String parentId ,@RequestParam(value="treelevel",required=false,defaultValue="3") Integer treelevel){
		
		ReturnData  result=new ReturnData();
		List<ResCategory>  list=Lists.newArrayList();
		//为您推荐
		//String cateId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.freeVideoId");
		try{
			List<ResCategory>  resCateList=appIndexService.selectResCategoryByCourseIdList(null,parentId,treelevel);
			if(resCateList!=null && resCateList.size()>0){
				list.addAll(resCateList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	
	/**
	 * 通Id 查询其下所有分类
	 * @param parentId
	 * @param treelevel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("video/categoryListById")
	public String  categoryListById(@RequestParam(value="id",required=true)String id ){
		
		ReturnData  result=new ReturnData();
		List<ResCategory>  list=Lists.newArrayList();
		//为您推荐
		//String cateId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.freeVideoId");
		try{
			List<ResCategory>  resCateList=appIndexService.categoryListById(id);
			if(resCateList!=null && resCateList.size()>0){
				list.addAll(resCateList);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
		    e.printStackTrace();
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}

	
	@ResponseBody
	@RequestMapping("indexList")
	public String  indexList(@RequestParam(value="treelevel",required=false,defaultValue="2") Integer treelevel){
		IndexList indexList = new IndexList();
		String courseId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "pastYears.courseid");
		String cateId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.recommendId");
		String cateId1=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "video.cate.live");
		//String arouselId=GlobalConfigUtils.readValue(GlobalConfigUtils.profilepath, "index.arousel");
		ReturnData  returnData = new ReturnData();
		List<Knowledge> knowList = new ArrayList<Knowledge>();
		List<ResCategory> resCateList = new ArrayList<ResCategory>();
		List<ResCategory> resCateList1 = new ArrayList<ResCategory>();
		List<AD> indexAd = new ArrayList<AD>();
		try {
			knowList = appIndexService.selectKnowledgeByCourseIdListPage(courseId,treelevel);
			resCateList = appIndexService.selectResCategoryByCourseIdList(null,cateId,treelevel);
			resCateList1 = appIndexService.selectResCategoryByCourseIdList(null,cateId1,treelevel);
			indexAd = appIndexService.getIndexAd();
		} catch (Exception e) {
			returnData.setData(new ArrayList());
			returnData.setStatus(false);
			e.printStackTrace();
		}
		indexList.setAdList(indexAd);
		indexList.setKnowledgeList(knowList);
		indexList.setVideoList(resCateList);
		indexList.setLiveList(resCateList1);
		returnData.setData(indexList);
		returnData.setStatus(true);
		return JsonUtil.toJson(returnData);
	}

	
	
	
	/**
	 * app
	 * 首页搜索
	 */
	@ResponseBody
	@RequestMapping("search")
	public String search(String searchKey,Integer start,Integer end ){
		
      /*
		ReturnData  result=new ReturnData();
		List<SearchResult>  list=Lists.newArrayList();
		try{
			List<SearchResult>  reslist=resService.searchResource(search);
			if(reslist!=null && reslist.size()>0){
				list.addAll(reslist);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);*/
		
		
		//新修改为 根据课程名称 、资源名称、分类名称、查询 数据 （2017-08-21）
		ReturnData  result=new ReturnData();
		
		List<Parameter>  list=Lists.newArrayList();
		
		try{
			List<Parameter>  reslist=resService.search(searchKey,start,end);
			if(reslist!=null && reslist.size()>0){
				list.addAll(reslist);
			}
			result.setStatus(true);
			result.setData(list);
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	
	
	
}
