package com.thinkgem.jeesite.modules.resource.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class TableADColumns extends DataEntity<TableADColumns> {

	private static final long serialVersionUID = 1L;

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

}
