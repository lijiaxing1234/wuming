package com.thinkgem.jeesite.modules.web.controller;

import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.web.entity.AD;
import com.thinkgem.jeesite.modules.web.entity.ResCategory;
import com.thinkgem.jeesite.modules.web.entity.Resource;
import com.thinkgem.jeesite.modules.web.entity.UserRecord;
import com.thinkgem.jeesite.modules.web.service.ADService;
import com.thinkgem.jeesite.modules.web.service.ResService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;


/**
 *发现资源的详情页面
 * 
 */
@Controller
@RequestMapping("/web/res")
public class ResController extends BaseController {
	@Autowired
	private ResService resService;
	@Autowired
	private ADService adService;
	
	//查找子级栏目
	@ResponseBody
	@RequestMapping("selectCategoryByParentId")
	public String  selectCategoryByParentId(@RequestParam(value="cateId",required=true)String cateId){
		
		ReturnData  result=new ReturnData();
		List<ResCategory>  list=Lists.newArrayList();
		try{
			List<ResCategory> resList=resService.selectCategoryByParentId(cateId);
			if(resList!=null && resList.size()>0){
				list.addAll(resList);
			}
			result.setStatus(true);
			result.setData(resList);
		}catch (Exception e) {
			result.setStatus(false);
			result.setMessage(e.getMessage());
			result.setData(list);
		}
		return JsonUtil.toJson(result);
	}
	
	
	//查找栏目下的资源集合
	@ResponseBody
	@RequestMapping("selectResByCateId")
	public String  selectResByCateId(@RequestParam(value="cateId",required=true)String cateId,String userId){
		
		ReturnData  result=new ReturnData();
		List<Resource>  list=Lists.newArrayList();
		try{
			List<Resource>  resList=resService.selectResByCateId(cateId,userId);
			if(resList!=null && resList.size()>0){
				list.addAll(resList);
				Resource r=resList.get(0);
				System.out.println("---------------- shipin message = " +  r.getName() +" -- " +r.getFilePath());
			}else
			{
				System.out.println("---------------- shipin message = " );
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
	
	//广告接口
	@ResponseBody
	@RequestMapping("selectAd")
	public String  selectAd(@RequestParam(value="adCode",required=true)String adCode){
		
		ReturnData  result=new ReturnData();
		List<AD>  list=Lists.newArrayList();
		try{
			List<AD>  resList=adService.selectAD(adCode);
			if(resList!=null && resList.size()>0){
				list.addAll(resList);
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
	
	@RequestMapping("updateUserRecord")
	@ResponseBody
	public String updateUserRecord(UserRecord record){
		ReturnData returnData = new ReturnData();
		boolean b = resService.updateUserRecord(record);
		returnData.setStatus(b);
		return JsonUtil.toJson(returnData);
	}
	
	@RequestMapping("userRecord")
	@ResponseBody
	public String userRecord(String userId,String sourceId){
		ReturnData returnData = new ReturnData();
		try {
			UserRecord time = resService.getUserRecord(userId, sourceId);
			if (time != null) {
				returnData.setData(time);
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
		}
		return JsonUtil.toJson(returnData);
	}
	
	@RequestMapping("addLiveCount")
	@ResponseBody
	public String addLiveCount(UserRecord record){
		ReturnData returnData = new ReturnData();
		boolean b = resService.addLiveCount(record);
		returnData.setStatus(b);
		return JsonUtil.toJson(returnData);
	}
}
