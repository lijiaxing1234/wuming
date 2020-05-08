package com.thinkgem.jeesite.modules.web.dto;

/**
 * 主播对象
 * @author Administrator
 *
 */
public class MTZhubo {
	
	private String bid;   //欢拓系统的主播id
	private String thirdAccount; //发起直播课程的合作方主播唯一账号或ID
	private String nickname; // 主播昵称;
	private String createTime; //创建时间
	private String expireTime; //过期时间
	private String state;      //主播状态
	private String scoreTotal; //获得的评分总分
	private String flower ;    //获得的总鲜花数
	
	private String p_150;    //图片
	private String p_40;     //图片
	
	
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getThirdAccount() {
		return thirdAccount;
	}
	public void setThirdAccount(String thirdAccount) {
		this.thirdAccount = thirdAccount;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getScoreTotal() {
		return scoreTotal;
	}
	public void setScoreTotal(String scoreTotal) {
		this.scoreTotal = scoreTotal;
	}
	public String getFlower() {
		return flower;
	}
	public void setFlower(String flower) {
		this.flower = flower;
	}
	public String getP_150() {
		return p_150;
	}
	public void setP_150(String p_150) {
		this.p_150 = p_150;
	}
	public String getP_40() {
		return p_40;
	}
	public void setP_40(String p_40) {
		this.p_40 = p_40;
	}
}
