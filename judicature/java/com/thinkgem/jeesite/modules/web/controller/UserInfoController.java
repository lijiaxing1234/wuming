package com.thinkgem.jeesite.modules.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Page;
import com.thinkgem.jeesite.modules.web.entity.CourseInformation;
import com.thinkgem.jeesite.modules.web.entity.PublicationResourceVisitors;
import com.thinkgem.jeesite.modules.web.entity.UserDeliveryAddress;
import com.thinkgem.jeesite.modules.web.entity.UserInfo;
import com.thinkgem.jeesite.modules.web.entity.UserIntegral;
import com.thinkgem.jeesite.modules.web.entity.UserPayCourse;
import com.thinkgem.jeesite.modules.web.entity.UserSign;
import com.thinkgem.jeesite.modules.web.service.PayService;
import com.thinkgem.jeesite.modules.web.service.UserInfoService;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.PropertieUtils;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

@Controller
@RequestMapping("/web/userInfo")
public class UserInfoController {

	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	PayService payService;
	
	@RequestMapping("hasUser")
	@ResponseBody
	public String hasUser(String phone){
		ReturnData returnData = new ReturnData();
		boolean hasUser = userInfoService.hasUser(phone);
		returnData.setStatus(hasUser);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("Login")
	@ResponseBody
	public String Login(String phone,String password){
		ReturnData returnData = new ReturnData();
		boolean b = userInfoService.getUserPWD(phone, password);
		returnData.setStatus(b);
		if(b){
			UserInfo userInfo = userInfoService.getUserInfoByPhone(phone);
			returnData.setData(userInfo);
		}else {
			returnData.setMessage("密码不正确!");
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("UserInfo")
	@ResponseBody
	public String getUser(String id){
		ReturnData returnData = new ReturnData();
		try {
			UserInfo userInfo = userInfoService.getUserInfoById(id);
			if(userInfo!=null){
				returnData.setData(userInfo);
				returnData.setStatus(true);
			}else {
				returnData.setStatus(true);
				returnData.setData(new UserInfo());
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("userHasPWD")
	@ResponseBody
	public String userHasPWD(String id){
		ReturnData returnData = new ReturnData();
	    boolean userHasPWD = userInfoService.userHasPWD(id);
	    returnData.setStatus(userHasPWD);
	    String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("addUser")
	@ResponseBody
	public String addUser(String phone){
		ReturnData returnData = new ReturnData();
		boolean addUser = userInfoService.addUser(phone);
		UserInfo userInfo2 = userInfoService.getUserInfoByPhone(phone);
		if(userInfo2!=null){
			returnData.setData(userInfo2);
		}
		returnData.setStatus(addUser);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("setPWD")
	@ResponseBody
	public String setPW(UserInfo userInfo){
		ReturnData returnData = new ReturnData();
		boolean setPW = userInfoService.setPW(userInfo);
		returnData.setStatus(setPW);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("forgetPWD")
	@ResponseBody
	public String forgetPWD(UserInfo userInfo){
		ReturnData returnData = new ReturnData();
		boolean setPW = userInfoService.forgetPWD(userInfo);
		returnData.setStatus(setPW);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	//记录资源访问
	@RequestMapping("playRes")
	@ResponseBody
	public String playRes(String userId,String resId){
	 
		 //记录资源访问
		PublicationResourceVisitors prv=new PublicationResourceVisitors();
		prv.setUserId(userId);
		prv.setResourceId(resId);
		userInfoService.addPlayLog(prv);
		 
		return "{\"log\":\"OK\"}";
	}
	
	
	@RequestMapping("qqGroup")
	@ResponseBody
	public String qqGroup(){
	 
		String qqGroup = Global.getConfig("qqGroup"); //qq群
		
		qqGroup ="{\"qq\":\""+qqGroup+"\"}";
		return qqGroup;
	}
	
	
	@RequestMapping("updateUserInfo")
	@ResponseBody
	public String updateUserInfo(UserInfo userInfo){
		ReturnData returnData = new ReturnData();
		boolean updateUserInfo = userInfoService.updateUserInfo(userInfo);
		returnData.setStatus(updateUserInfo);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping(value = "updateUserIcon", method = RequestMethod.POST)
   	@ResponseBody
	public String updateUserIcon(@RequestParam(value = "uploadFile", required = false) MultipartFile file,
						@RequestParam("userId") String userId,
						HttpServletRequest request) {
		String name = "";
		UserInfo userInfo = new UserInfo();
	   	ReturnData returnData = new ReturnData();
	   //	String serverPath = PropertieUtils.getProperty("icon.server");
	   	//String filePath = PropertieUtils.getProperty("icon");
	   	
	   	String serverPath =request.getRequestURL().toString().replace("web/userInfo/updateUserIcon", "userIcon/");
	   	String filePath = request.getSession().getServletContext().getRealPath("\\userIcon\\");
		System.err.println(filePath);
	 
	   	SimpleDateFormat sdf = new SimpleDateFormat("HHmmssS");
	   	Date date = new Date();
	   			String originalFileName = file.getOriginalFilename();
	   			File file1 = new File(filePath);
	   			if (!file1.exists()) {
	   				file1.mkdir();
	   			}
	   			try {
	   				UUID uuid = UUID.randomUUID();
	   				String string = uuid.toString();
	   				String substring = string.substring(0, 5);
	   				name = sdf.format(date)+substring+originalFileName.substring(originalFileName.lastIndexOf('.'),originalFileName.length());
	   				String iconUrl = filePath+"/"+name;
	   				FileOutputStream fileOutputStream = new FileOutputStream(iconUrl);
	   				fileOutputStream.write(file.getBytes());
	   				fileOutputStream.flush();
	   				fileOutputStream.close();
	   				userInfo.setIcon(serverPath+name);
	   				userInfo.setId(userId);
	   			} catch (FileNotFoundException e) {
	   				e.printStackTrace();
	   				
	   			} catch (IOException e) {
	   				e.printStackTrace();
	   			}
	   			boolean b = userInfoService.updateUserIcon(userInfo);
	   			if(b){
	   				returnData.setData(serverPath+name);
	   			}
	   	returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("userDeliveryAddress")
	@ResponseBody
	public String  getDeliveryAddress(String userId){
		ReturnData returnData = new ReturnData();
		try {
			UserDeliveryAddress deliveryAddress = userInfoService.getDeliveryAddress(userId);
			if(deliveryAddress!=null){
				returnData.setData(deliveryAddress);
				returnData.setStatus(true);
			}else{
				returnData.setStatus(true);
				returnData.setData(new UserDeliveryAddress());
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("updateDeliveryAddress")
	@ResponseBody
	public String  updateDeliveryAddress(UserDeliveryAddress userDeliveryAddress){
		ReturnData returnData = new ReturnData();
		boolean b = userInfoService.updateDeliveryAddress(userDeliveryAddress);
		returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("updateUserIntegral")
	@ResponseBody
	public String  updateUserIntegral(UserIntegral userIntegral){
		ReturnData returnData = new ReturnData();
		boolean b = userInfoService.updateUserIntegral(userIntegral);
		returnData.setStatus(b);
		if (b) {
			UserIntegral integral = userInfoService.getUserIntegral(userIntegral.getUserId(),DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
			returnData.setData(integral);
		}
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("userIntegralByDate")
	@ResponseBody
	public String  userIntegralByDate(String userId, String date){
		ReturnData returnData = new ReturnData();
	    try {
			UserIntegral integral = userInfoService.getUserIntegral(userId,date);
			if(integral == null){
			returnData.setStatus(true);
			returnData.setData(new UserIntegral());
			}
			returnData.setStatus(true);
			returnData.setData(integral);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setStatus(false);
		}
		
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("centerIndex")
	@ResponseBody
	public String  centerIndex(String userId,String date){
		ReturnData returnData = new ReturnData();
	    try {
			UserInfo centerIndex = userInfoService.centerIndex(userId,date);
			if(centerIndex == null){
			returnData.setStatus(true);
			returnData.setData(new UserInfo());
			}
			returnData.setStatus(true);
			returnData.setData(centerIndex);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setStatus(false);
		}
		
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("updateStudyTime")
	@ResponseBody
	public String  updateStudyTime(UserInfo userInfo){
		ReturnData returnData = new ReturnData();
		boolean b = userInfoService.updateStudyTime(userInfo);
		returnData.setStatus(b);
		String json = JsonUtil.toJson(returnData);
		return json;
	}
	
	@RequestMapping("getUserSign")
	@ResponseBody
	public String getUserSign(String userId){
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ReturnData returnData = new ReturnData();
		try {
			List<UserSign> userSign = userInfoService.getUserSign(userId);
			Integer i = userInfoService.getUserSignPumpkinCountByDate(userId,format.format(new Date()));
			if (i == null) {
				i = 0;
			}
			map.put("userSign",userSign);
			map.put("todayPumpkinCount",i);
			returnData.setData(map);
			returnData.setStatus(true);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setData(map);
			returnData.setStatus(false);
		}
		
		return JsonUtil.toJson(returnData);
	}
	
	@RequestMapping("userTodaySign")
	@ResponseBody
	public String userTodaySign(String userId,String date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		ReturnData returnData = new ReturnData();
		try {
			boolean b = userInfoService.userTodaySign(userId,date);
			Integer i = userInfoService.getUserSignPumpkinCountByDate(userId,format.format(new Date()));
			returnData.setData(i);
			returnData.setStatus(b);
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setStatus(false);
		}
		return JsonUtil.toJson(returnData);
	}
	
	@RequestMapping("courseInformation")
	@ResponseBody
	public String courseInformation(String pageNB,int pageSize){
		ReturnData returnData = new ReturnData();
		try {
			Page<CourseInformation> courseInformationByPage = userInfoService.CourseInformationByPage(pageNB,pageSize);
			if (courseInformationByPage.getContent().size()>0) {
				returnData.setData(courseInformationByPage);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<String, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnData.setStatus(false);
		}
		return JsonUtil.toJson(returnData);
	}
	
	@RequestMapping("userPayCourse")
	@ResponseBody
	public String userPayCourse(String userId){
		ReturnData returnData = new ReturnData();
		try {
			List<UserPayCourse> payCourse = payService.getAllUserHasPayCourse(userId);
			if (payCourse.size()>0) {
				returnData.setData(payCourse);
				returnData.setStatus(true);
			}else {
				returnData.setData(new HashMap<String, Object>());
				returnData.setStatus(true);
			}
		} catch (Exception e) {
			returnData.setStatus(false);
			e.printStackTrace();
		}
		return JsonUtil.toJson(returnData);
	}
}
