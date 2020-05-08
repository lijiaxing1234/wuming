package com.thinkgem.jeesite.modules.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

public class SystemLoginOut extends org.apache.shiro.web.filter.authc.LogoutFilter {

	
	
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)throws Exception {
		UserUtils.clearCache();
		return super.preHandle(request, response);
	}
	
	
	


	

}
