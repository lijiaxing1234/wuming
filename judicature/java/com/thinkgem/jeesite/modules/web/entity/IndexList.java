package com.thinkgem.jeesite.modules.web.entity;

import java.util.List;

public class IndexList {

	private List<AD> adList;
	private List<Knowledge> KnowledgeList;
	private List<ResCategory> videoList;
	private List<ResCategory> liveList;
	public List<AD> getAdList() {
		return adList;
	}
	public void setAdList(List<AD> adList) {
		this.adList = adList;
	}
	public List<Knowledge> getKnowledgeList() {
		return KnowledgeList;
	}
	public void setKnowledgeList(List<Knowledge> knowledgeList) {
		KnowledgeList = knowledgeList;
	}
	public List<ResCategory> getVideoList() {
		return videoList;
	}
	public void setVideoList(List<ResCategory> videoList) {
		this.videoList = videoList;
	}
	public List<ResCategory> getLiveList() {
		return liveList;
	}
	public void setLiveList(List<ResCategory> liveList) {
		this.liveList = liveList;
	}
	
	
}
