package com.thinkgem.jeesite.modules.web.dto;

import java.util.List;

import com.thinkgem.jeesite.modules.web.entity.UserRecordAnswer;

/**
 * 用于接受app用户答题json
 * @author Administrator
 *
 */
public class AppRequestJson {
	
	
	private  List<UserRecordAnswer>  json;
	
	public List<UserRecordAnswer> getJson() {
		return json;
	}
	public void setJson(List<UserRecordAnswer> json) {
		this.json = json;
	}

}
