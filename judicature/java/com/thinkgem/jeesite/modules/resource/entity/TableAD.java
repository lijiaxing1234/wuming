package com.thinkgem.jeesite.modules.resource.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class TableAD extends DataEntity<TableAD> {
	
	private static final long serialVersionUID = 1L;
	
	private String colId; // 广告栏目id
	private String imageUrl; // 图标
	private String target; // 目标对象(电子书或URL)
	private String verId; // 版本ID(只针对ad_type=1时有效)
	private int adType; // 广告类型(1-电子书,2-URL)
	private int seq; // 排序

	public String getColId() {
		return colId;
	}

	public void setColId(String colId) {
		this.colId = colId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public int getAdType() {
		return adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

}
