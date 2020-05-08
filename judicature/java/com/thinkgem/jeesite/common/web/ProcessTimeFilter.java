package com.thinkgem.jeesite.common.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.thinkgem.jeesite.common.utils.DateUtils;

public class ProcessTimeFilter implements Filter {
	
	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		
		long time = System.currentTimeMillis();
		
		request.setAttribute(START_TIME, time);
		
		chain.doFilter(request, response);
		
		time = System.currentTimeMillis() - time;
		
		System.out.println(request.getRequestURI()+"=========================="+DateUtils.formatDateTime(time));
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
