package com.thinkgem.jeesite.modules.web.entity;

public class ADColumn{

	private String colId;

	private String adCode; //唯一编码
	
	private String colName; //广告栏名称

	public String getAdCode() {
		return adCode;
	}

	public void setAdCode(String adCode) {
		this.adCode = adCode;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

}
