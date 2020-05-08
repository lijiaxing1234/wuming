package com.thinkgem.jeesite.modules.web.dto;

/**
 * 欢拓开放平台 
 * 公共json格式
 */
public class MTJSON<E>  {

	/**
	 * 状态
	 */
	private Integer code;
	/**
	 * 总记录数
	 */
	private Integer total;
	/**
	 * 数据
	 */
	private E data;
	
	
	private  Boolean  cache;
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	
	public Boolean getCache() {
		return cache;
	}
	public void setCache(Boolean cache) {
		this.cache = cache;
	}
	
	
}
