package com.thinkgem.jeesite.modules.web.entity.pay;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class XOrderNoticeEntity_In extends Object{

	@XStreamAlias("return_code")
	private String return_code;
	@XStreamAlias("return_msg")
	private String return_msg;

    /**
     * 微信开放平台审核通过的应用APPID  String(32)
     */
	@XStreamAlias("appid")
	private String appid;

	/**
	 * 微信支付分配的商户号   	String(32)
	 */
	@XStreamAlias("mch_id")
	private String mch_id;
    /**
     * 微信支付分配的终端设备号，  	String(32)
     */
	@XStreamAlias("device_info")
	private String device_info;
    /**
     * 	随机字符串，不长于32位   	String(32)
     */
	@XStreamAlias("nonce_str")
	private String nonce_str;
    
	/**
	 * 签名   	String(32)
	 */
	@XStreamAlias("sign")
	private String sign;
   
	/**
	 * SUCCESS/FAIL  	String(16)
	 */
	@XStreamAlias("result_code")
	private String result_code;
	
	/**
	 * 错误返回的信息描述   String(32)
	 */
	@XStreamAlias("err_code")
	private String err_code;
	
	/**
	 * 	错误返回的信息描述   	String(128)
	 */
	@XStreamAlias("err_code_des")
	private String err_code_des;
	
	/**
	 * 用户在商户appid下的唯一标识   String(128)
	 */
	@XStreamAlias("openid")
	private String openid;
	
	/**
	 * 是否关注公众账号   String(1)
	 */
	@XStreamAlias("is_subscribe")
	private String is_subscribe;
	/**
	 * 交易类型	 String(16)
	 */
	@XStreamAlias("trade_type")
	private String trade_type;
	/**
	 * 付款银行 String(16)
	 */
	@XStreamAlias("bank_type")
	private String bank_type;
	/**
	 * 总金额    Int
	 */ 
	@XStreamAlias("total_fee")
	private String total_fee;
	/**
	 * 货币种类   	String(8)
	 */
	@XStreamAlias("fee_type")
	private String fee_type;
	
	/**
	 * 现金支付金额  Int
	 */
	@XStreamAlias("cash_fee")
	private String cash_fee;
	/**
	 * 现金支付货币类型  	String(16)	CNY
	 */
	@XStreamAlias("cash_fee_type")
	private String cash_fee_type;
	/**
	 *代金券或立减优惠金额  	Int	10
	 */
	@XStreamAlias("coupon_fee")
	private String coupon_fee;
	/**
	 * 代金券或立减优惠使用数量  Int	1
	 */
	@XStreamAlias("coupon_count")
	private String coupon_count;
	/**
	 * 代金券或立减优惠ID	coupon_id_$n	否	String(20)
	 */
	@XStreamAlias("coupon_id_$n")
	private String coupon_id_$n;
	
	/**
	 * 单个代金券或立减优惠支付金额	coupon_fee_$n	否	Int	100
	 */
	@XStreamAlias("coupon_fee_$n")
	private String coupon_fee_$n;
	/**
	 * 微信支付订单号	transaction_id	是	String(32)
	 */
	@XStreamAlias("transaction_id")
	private String transaction_id;
	/**
	 * 商户订单号	out_trade_no	是	String(32)
	 */
	@XStreamAlias("out_trade_no")
	private String out_trade_no;
	/**
	 * 商家数据包	attach	否	String(128)	123456
	 */
	@XStreamAlias("attach")
	private String attach;
	/**
	 * 支付完成时间	time_end	是	String(14)	20141030133525
	 */
	@XStreamAlias("time_end")
	private String time_end;
	
	
	
	
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getErr_code() {
		return err_code;
	}
	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}
	public String getErr_code_des() {
		return err_code_des;
	}
	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getCash_fee() {
		return cash_fee;
	}
	public void setCash_fee(String cash_fee) {
		this.cash_fee = cash_fee;
	}
	public String getCash_fee_type() {
		return cash_fee_type;
	}
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_id_$n() {
		return coupon_id_$n;
	}
	public void setCoupon_id_$n(String coupon_id_$n) {
		this.coupon_id_$n = coupon_id_$n;
	}
	public String getCoupon_fee_$n() {
		return coupon_fee_$n;
	}
	public void setCoupon_fee_$n(String coupon_fee_$n) {
		this.coupon_fee_$n = coupon_fee_$n;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	
}
