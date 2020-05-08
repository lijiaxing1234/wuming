package com.thinkgem.jeesite.modules.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.web.dto.AppRequestJson;
import com.thinkgem.jeesite.modules.web.entity.UserRecordAnswer;
import com.thinkgem.jeesite.modules.web.service.UserAnswerService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;


/**
 * 用户相关
 * 用户答题记录
 */
@Controller
@RequestMapping("/web/userRelated")
public class UserAnswerController extends BaseController {
	
	
	@Autowired
	UserAnswerService  userAnswerService;
	
	
	/**
	 * 保存用户答题记录
	 * @param userAnswer
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveUserAnswer")
	public String  saveUserAnswer(HttpServletRequest  request,HttpServletResponse  response){
		ReturnData  result=new ReturnData();
		
		
		
		StringBuilder  reqJson=new StringBuilder();
		BufferedReader reader=null;
		try{
			
			    reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	            String reqResult = "";
	            while ((reqResult = reader.readLine()) != null) {
	            	reqJson.append(reqResult);
	            }
	          
	         String  jsonResult=reqJson.toString();
	     	 System.out.println(jsonResult);
	     	 
	         AppRequestJson appJson=null;
	         
	         if(StringUtils.isNoneBlank(jsonResult)){
	        	 appJson=(AppRequestJson)JSON.parseObject(jsonResult, new TypeReference<AppRequestJson>() {});
	         }
	         
	         if(appJson!=null){
	        	 for(UserRecordAnswer userAnswer:appJson.getJson()){
	        	     userAnswer.setJson(jsonResult);
	        		 userAnswerService.saveUserAnswer(userAnswer);
	        	 }
	         }
		    result.setStatus(true);
			result.setMessage("保存成功");
		}catch (Exception e) {
			e.printStackTrace();
			result.setStatus(false);
			result.setMessage("保存失败："+e.getMessage());
		}finally {
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return JsonUtil.toJson(result);
	}
    
	
	
	
}
