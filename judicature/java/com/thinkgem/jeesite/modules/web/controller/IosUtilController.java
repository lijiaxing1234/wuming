package com.thinkgem.jeesite.modules.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.PropertieUtils;
import com.thinkgem.jeesite.modules.web.util.ReturnData;

@Controller
@RequestMapping("IOSUtil")
public class IosUtilController {

	@RequestMapping("tORf")
	@ResponseBody
	public String tORf(){
		ReturnData returnData = new ReturnData();
		String property = PropertieUtils.getProperty("iosStatus");
		int i = Integer.parseInt(property);
		if (i == 0) {
			returnData.setStatus(false);
		}
		if (i == 1) {
			returnData.setStatus(true);
		}
		return JsonUtil.toJson(returnData);
	}
	
	
}
