package com.thinkgem.jeesite.modules.web.dto;

import com.thinkgem.jeesite.modules.resource.entity.LVCourse;

/**
 * 欢拓开放平台 
 * 课程实体
 */
public class MTCourse {
	
	private String course_id; // 课程id
	private String partner_id; // 合作方id
	private String course_name; // 课程名
	private String start_time; // 课程开始时间
	private String end_time; // 课程结束时间
	private String add_time; // 课程创建时间
	private String live_stime; // 课程直播开始时间
	private String live_etime; // 课程直播结束时间
	private String duration; // 时长(秒)
	private String status; // 状态 0正常，-1已删除
	private String bid; // 欢拓系统的主播id
	private String chatTotal; // 聊天总数
	private String zhubo_key; // 主播登录秘钥
	private String admin_key; // 助教登录秘钥
	private String user_key; // 学生登录秘钥
	private String questionTotal; // 问题总数
	private String voteTotal; // 投票总数
	private String flowerTotal; // 鲜花总数
	private String lotteryTotal; // 抽奖总数
	private String livePv; // 直播观看次数
	private String pbPv; // 回放观看次数
	private String liveUv; // 直播观看人数
	private String pbUv; // 回放观看人数
	private Integer liveStatus; // 直播状态：1 未开始；2 正在直播；3 已结束
	private String playbackUrl; // 回放地址

	
	private LVCourse  lvCourse; //本地课程对应直播表
	
	private MTZhubo  mtZhubo;  //接口主播
	
	private MTZhubo  zhubo;  //接口主播
	
	private int recommend;
	
	private String iconUrl;
	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}

	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getAdd_time() {
		return add_time;
	}

	public void setAdd_time(String add_time) {
		this.add_time = add_time;
	}

	public String getLive_stime() {
		return live_stime;
	}

	public void setLive_stime(String live_stime) {
		this.live_stime = live_stime;
	}

	public String getLive_etime() {
		return live_etime;
	}

	public void setLive_etime(String live_etime) {
		this.live_etime = live_etime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getChatTotal() {
		return chatTotal;
	}

	public void setChatTotal(String chatTotal) {
		this.chatTotal = chatTotal;
	}

	public String getZhubo_key() {
		return zhubo_key;
	}

	public void setZhubo_key(String zhubo_key) {
		this.zhubo_key = zhubo_key;
	}

	public String getAdmin_key() {
		return admin_key;
	}

	public void setAdmin_key(String admin_key) {
		this.admin_key = admin_key;
	}

	public String getUser_key() {
		return user_key;
	}

	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	public String getQuestionTotal() {
		return questionTotal;
	}

	public void setQuestionTotal(String questionTotal) {
		this.questionTotal = questionTotal;
	}

	public String getVoteTotal() {
		return voteTotal;
	}

	public void setVoteTotal(String voteTotal) {
		this.voteTotal = voteTotal;
	}

	public String getFlowerTotal() {
		return flowerTotal;
	}

	public void setFlowerTotal(String flowerTotal) {
		this.flowerTotal = flowerTotal;
	}

	public String getLotteryTotal() {
		return lotteryTotal;
	}

	public void setLotteryTotal(String lotteryTotal) {
		this.lotteryTotal = lotteryTotal;
	}

	public String getLivePv() {
		return livePv;
	}

	public void setLivePv(String livePv) {
		this.livePv = livePv;
	}

	public String getPbPv() {
		return pbPv;
	}

	public void setPbPv(String pbPv) {
		this.pbPv = pbPv;
	}

	public String getLiveUv() {
		return liveUv;
	}

	public void setLiveUv(String liveUv) {
		this.liveUv = liveUv;
	}

	public String getPbUv() {
		return pbUv;
	}

	public void setPbUv(String pbUv) {
		this.pbUv = pbUv;
	}

	public Integer getLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(Integer liveStatus) {
		this.liveStatus = liveStatus;
	}

	public String getPlaybackUrl() {
		return playbackUrl;
	}

	public void setPlaybackUrl(String playbackUrl) {
		this.playbackUrl = playbackUrl;
	}

	public LVCourse getLvCourse() {
		return lvCourse;
	}
	public void setLvCourse(LVCourse lvCourse) {
		this.lvCourse = lvCourse;
	}
	
	public MTZhubo getMtZhubo() {
		return mtZhubo;
	}
	public void setMtZhubo(MTZhubo mtZhubo) {
		this.mtZhubo = mtZhubo;
	}

	public MTZhubo getZhubo() {
		return zhubo;
	}

	public void setZhubo(MTZhubo zhubo) {
		this.zhubo = zhubo;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
}
