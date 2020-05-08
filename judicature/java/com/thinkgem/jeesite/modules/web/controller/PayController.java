package com.thinkgem.jeesite.modules.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.thinkgem.jeesite.modules.web.entity.AppPayEntity_In;
import com.thinkgem.jeesite.modules.web.entity.PumpkinPay;
import com.thinkgem.jeesite.modules.web.entity.XOrderEntity_In;
import com.thinkgem.jeesite.modules.web.entity.XOrderEntity_Out;
import com.thinkgem.jeesite.modules.web.entity.XOrderNoticeEntity_In;
import com.thinkgem.jeesite.modules.web.entity.XOrderNoticeEntity_Out;
import com.thinkgem.jeesite.modules.web.service.PayService;
import com.thinkgem.jeesite.modules.web.util.HttpUtil;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ParamHandleUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;
import com.thinkgem.jeesite.modules.web.util.WXSignUtil;
import com.thinkgem.jeesite.modules.web.util.XstreamUtils;


@Controller
@RequestMapping("/web/pay")
public class PayController {

	@Autowired
	PayService payService;
	
	//微信
	public static final String generateOrderUrl="https://api.mch.weixin.qq.com/pay/unifiedorder";
    
    public static final String WX_APPID = "wxfdc905359294f144";
    
    public static final String WX_MC_ID = "1418563302";
    
    public static final String WX_API_KEY = "sjnvy699HxsaDaixklqpiy8w1evm9yda";
    
    //支付宝
   
    //public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAt6nbTJBu62kYgTj8dtLRt+1+R5h3oLFYdxba/A6fxDjLAjjTn2TDiJ8LNue6RT2C6wBUXMEuKEIOFQ394ISIyrwcHA0MSF0pWB2weDkQy5wl2oUh8Q+uUvkLRzFNau3tPAU42RL/wDNdUr6C0D8o4XjlTpHdCOMX9y8evATifPuRPV36N83u3IY4emH7sCvAD0qbU1vmFvPT/nc+y/kUSXbbcORrMA9MMrHXtqqB5gs80L2mnKfQTHPuy6g/v2DuXTjRO+IPoNuJUvvzHfxX4+UbYOW1u0otG+p6Aii7quKlf/hO++lldgzzDeVSS1sqnKAk0bB32Rjaq2qUaJYYUwIDAQAB";
    
    // 沙箱正常的
   // public static final String APP_ID="2016073100135181";
   // public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA13pkjEJ4ucJWXGJnjPUJ8FYVO8pk+5o0o6rhyjWruxYtCrKxDtUXkgnO5MdHHvsElsgALxalk9Of/qvF92dU6+BjkINq94izFriOxAf0+c3sN+WZ4geuaVMJdNF3UEF0mwKnIILkATJub9C2Hj6Sy2pyYTRGEAspX6UKNJEHOGN5FO77mWWigjZmOJGUbXtdbvFxjvdaD1/kWKzecf6bnBOB2mhcuc9LbDHYPBTZgL7yyAxGR8S5fnTR5FUmiQp80k2/wwti+oniJWWPU8rr9FZ5h3/ll/9yhAGwZX2WTdng62FpJkpVQJquipBsx/SEsYShCmHUUyHGg26bYUvAXwIDAQAB";
   // public static final String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3qdtMkG7raRiBOPx20tG37X5HmHegsVh3Ftr8Dp/EOMsCONOfZMOInws257pFPYLrAFRcwS4oQg4VDf3ghIjKvBwcDQxIXSlYHbB4ORDLnCXahSHxD65S+QtHMU1q7e08BTjZEv/AM11SvoLQPyjheOVOkd0I4xf3Lx68BOJ8+5E9Xfo3ze7chjh6YfuwK8APSptTW+YW89P+dz7L+RRJdttw5GswD0wysde2qoHmCzzQvaacp9BMc+7LqD+/YO5dONE74g+g24lS+/Md/Ffj5Rtg5bW7Si0b6noCKLuq4qV/+E776WV2DPMN5VJLWyqcoCTRsHfZGNqrapRolhhTAgMBAAECggEAU+y2+/6H4OSOQQZEPxvIny5T5O1jxLclQI3eFQ2treFxypsjAJPv0a8zVax/7oHUIARviE7yA41jl/VjzENjqphYk+zWbv67FLvEVrtk1FWO6pKpVtkehGWu5KI/yRvdqu6L8o1+9lnCTDwn1Hb3/EfdKJD4msYCvn32ol3tDMS00MrA/o0CwhK7kxNUB0aQGc2fk/sw7RZYTgp9ZgsygB/bRpRz3KYTuyoExJ8dLqK/zQ5LMAO0YaseSylhhYYY3VjvxwZlGe3kOJAkyRFwcJrDyxtJOGz+mthUYjZPANB+s1muP7oZKzFiZ7ebnQatBnQXWJtd8DWb2zn4CYe5sQKBgQDsBzAMpWslNOh4ZoUVCfIf7ph+X+xIJc6z/sI0EN9ZGOxSm9BM88ViADO7NJcVjthsmEIxZmXhMCoTbRgbzzK3AQcHrH5etbPL0zSi/lLsxPLnQUFB61tXo6uRxMhORYc4eA08wT5MrDfr+3kbBCC9jj+CjZt4AO6RNiLNDXltKQKBgQDHNFqNfBvOspaUyAoCmyiK4bjHEXOXwCJv+7eCN2GRTt4x0pfjcnGPKbLLwz3ZzANxV9zdfd5khSOr3zPjbImywvT5A0dyK5fpWUX2sCmh0Dr5GQZ1GNLYlSyYMASh5Aya3ERE3driK4KoiLCx0rx5EMuyOEvTC7DDBt7LC2iNGwKBgD0F3rZmTj1gYHpAh0fZOnBnD6YcxsogfECtLSio9BXlC7SP9Frw66daxgUwo58/P1VUSEjdYJnIChjbq9AE0eferCKfxkxWThX0lTdO6cGtad/AT07rusH93u3hIqiZ8Uv4PfPnQmQwYoTwj28cshpQHLt0QS3Vj9ibVoQNZ8eBAoGBAJJIUc7gOpNY9dcf5qnvQqBqDYSxvg7L2v4c/K80+AL6xZQUr9Eoc1fMKS4Um9DaThTnjptgGPX8ByvpZnM1exC4tag+zjJ67L1A/22eA5R+EzBlPiazKfI96VeAu4Yer1rxMXjY6il4BiS46/hJZnoI6Wxght3eEdnFq8mzAP3NAoGAFRwpItgKuZeGi++vAlrqLXj0P4mwjupfctQyGDJHWnj+AO1bwU6Dz/8QXLB4U1nGRdGb1TLF8U5BP3hKAi4wlSERTru0grzcdDF2tn3LInTA+dLcMzTsZ0Wi8WV4HVn6LpvUgw7XvdLKC1mmtQWpGNHA7A+jCyXYUiOYhQMakVE=";
    //正式环境
    public static final String APP_ID="2017020805562220";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh0aO0RAiDeM+EzGtnUVtgJ5xu9WPmoy7HA4GJdcGq3Y+vOBykhjGEMHNbi0HXN2lXKf4HsxMLVZ0mGFlfdaQJzrgHe5NejMSGXhcCv7ke1+nI2Jn1zhqWMY1bJ8Gx3Rj8x0hyjlww47iWpDOOpBYdf5nVqQOa0b+1NXU4wuLoDGKNrW/hYuqKONRE8xtKQIpOElR+RM96GDsHsilvr5SZdcphshaE/X6k8TkW3y3TT66DGH/mG9C+3Kpxz52nbD+2dQl3GFKcX5Q250owkbF3BTWKdukT+uYeemyCgN/H6IvK4juG7n4iQTd9OdnPKfsy1+GPU3P4JEswY3ubHexKQIDAQAB";
    public static final String APP_PRIVATE_KEY="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+i2NfCmkOYztaVyOkGJylGqIvawi1B5A9LTUt2tk2VhtRoRehaF9PcjyGsBjeiY6cJF9W3qdkSYGoO/VAdDNHX/FTfwKW4ASnib+CW2WHM9xh9oi8ya+aNAQGrI7baBAKdCqzmsZvfq1ohBAneNb/sGhDOhFFEJQI/WmLRuqeULCKVOT8yg5982RHSsz6UicSmk0JYedeKwjukprzWkHbCF1pgk/HuEvJvL+OV7JfELwkiIan9Z0fN6ZpxGqoa7PMs1KO5h0R/J7srKWuWPITqbBXXeULF2oRLDJNH/UDLT+74TWeaC/XWhUo649y46N7arFfiMoLmGix5q0ex2Z3AgMBAAECggEBALJB9qoQNC5YmUsv4FEBEXLIUthnmtK5C7Cf3XNTnQffUFlyiofe++N4LLWYgvuV1suun17Gl/UTa2/2/77Xtq1cTYledg6t7szJ6lbKqFXvJ4qiOFPdnyF07htMTa1nUjJlcRlrf58I9LOlcPNscj7sSu4xobxqAQPUB2xb4NxPZRCjXYp0Dwpw+VCtUnAjPQetbdiF6AeMkZDbQIWc3p+cZcXGN9GzKhlI/Gjvnd+1rc2khTw6/OiA8NGuZMRmZCWHLtrxUzH8nZsMa8g9+PbGFP5l4DLEJIbZcVHU/HGkfFPn8d7Nt1435H2qNkQVX1T2wEtE+qqttWJGbdbdplECgYEA5E9LRDk1585t4BEtBxClHXOd2e5D/5c2PBTJ1IeJxWyAB10ZLrHF5O7X4A+eZSCH3GKcnqXmPPipsngtwUeVNof3cf3ZGfsjXHOqgDzLM17VJFYqzXXIYk/qMDMxgtC9lf/GM9Qw6sF3dlyCvmiJsR6TRHe0Ft7fb+TJaBymnx8CgYEA1aeI0b0HdTPbkJa91FqML0AaBrY9SA1rGi6hPZFVTSsEBfztnhCtdCTB0T6dNuIq514z1CE9w5prUx6/xncXnY61a57btT29TGlgHXc5lpXvoCQLSyfPi3PJd4ewlOrT6lGaAPSz4XsViWjaBW2pT8c6Hnp0SQUYmxI38ih7RakCgYBm+j0AsK1XuQxmHFkq7p8LAbMZMRcKmbGkt9uxONEzcxTsPVm2bgjMkc5dCa5v/pRJG1F5XWrT5XTlQr+ghiEPT5SUneLmjRgOAz3MSZ99tDB/Cz6THaUcct82jmCumGhq18sWZYcK2+h+QovWRmCUuy3BnBxmlHBDj/YoN4QQEQKBgQC6WSKblNzRW8awyNmPQt6sQ6OvoPm4Q4Pb1TnrYjxdTxx2QJHb4L345CpsX8lt2jwDiwV3ivq9BY2AdPxdlPfzA0q3MFp/LPdTk0Ey0g9XiTT0nxt+lJ2QCxB4gdABQwRMhpnlJYlTLDCvpdUrEOew3pqRhMqx+ZDpiC1lt+s06QKBgQDfaaIyPfqc0X2zDPvsLOgfbJtQbKnJ1T4TGvrZbzn2P+tQd5Hh63DMAkNfotrQQyYzWEiD7yzpIkJbErMzk3+amqBe3AgmkYmkOWIugNjRtfr7fu/Mndsy4GAJjsymb9nZWDdAEJRQ0qi7TIQAqGJmvTpW+0aCTLkFPupPsQfWig==";
    
    
    public static final String CHARSET = "utf-8";
    
    private static Logger log = LoggerFactory.getLogger(PayController.class);
    
    @RequestMapping("pumpkinPay")
    @ResponseBody
    public String pumpkinPay(PumpkinPay pumpkinPay){
    	ReturnData returnData = new ReturnData();
    	boolean b = payService.addPumpkinPayInfo(pumpkinPay);
    	returnData.setStatus(b);
    	return JsonUtil.toJson(returnData);
    }
    
    /**
     * 支付宝获取加密订单
     * @param phoneNum
     * @param notify_url
     * @param amountInt
     * @param outTradeNo
     * @return
     */
     @SuppressWarnings("finally")
	 @RequestMapping(value="orderInfo",method=RequestMethod.POST)
     @ResponseBody
     public String getorderInfo(@RequestParam(value="phoneNum",required=false) String phoneNum,
                                @RequestParam(value="notify_url",required=false) String notify_url,
                                @RequestParam(value="amountInt",required=false) String amountInt,
                                @RequestParam(value="outTradeNo",required=false) String outTradeNo,
                                @RequestParam(value="body",required=false) String body,
                                @RequestParam(value="userId",required=false) String userId,
                                @RequestParam(value="comform",required=false) String comform,
                                @RequestParam(value="publicationId",required=false) String publicationId,
                                @RequestParam(value="ip",required=false) String ip){
         
/*         ZFBInfo zfbInfo = new ZFBInfo();
         boolean rsa2 = (AviationPartnerConfig.RSA2_PRIVATE.length() > 0);
            Map<String, String> params = AviationOrderInfoUtil2_0.buildOrderParamMap(AviationPartnerConfig.APPID, rsa2,amountInt,phoneNum,notify_url,body);
            String orderParam = AviationOrderInfoUtil2_0.buildOrderParam(params);

            String privateKey = rsa2 ? AviationPartnerConfig.RSA2_PRIVATE : AviationPartnerConfig.RSA_PRIVATE;
            String sign = AviationOrderInfoUtil2_0.getSign(params, privateKey, rsa2);
            final String orderInfo = orderParam + "&" + sign;
            zfbInfo.setDDH(outTradeNo);
            serviceRecordService.addUserPublication(userId, comform, amountInt, publicationId, 0, outTradeNo, ip, body, orderInfo, "支付宝");
              */  
        // return orderInfo;
         
         
         
       //实例化客户端
         AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
         //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
         AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
         //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
         AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
         model.setBody(body);
         model.setSubject(phoneNum);
         model.setOutTradeNo(outTradeNo);
         model.setTimeoutExpress("30m");
         model.setTotalAmount(amountInt);
         model.setProductCode("QUICK_MSECURITY_PAY");
         request.setBizModel(model);
         request.setNotifyUrl(notify_url);
         String orderInfo=null;
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
			orderInfo=response.getBody();						// 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			e.printStackTrace();
			orderInfo=null;
		}finally {
			
			return orderInfo;
		}
     }
    
    /**
     * 支付宝异步通知接口
     * @param request
     * @param response
     * @throws UnsupportedEncodingException
     * @throws InterruptedException
     */
    @RequestMapping(value = "NotifyReceiver", method = RequestMethod.POST)
    public void notify(HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, InterruptedException { 
        log.info("info支付宝异步通知接口");
        log.info("info支付宝异步通知接口");
        Map requestParams = request.getParameterMap(); 
        Map<String, String> params = new HashMap<String, String>(); 
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        
      PrintWriter writer = null;
      boolean containsKey=false;
      String trade_status=null;
      String  out_trade_no = null;
      String trade_no = null;
      String gmt_close = null;
      String sign =null;
      
		try {
			writer = response.getWriter();

			 containsKey = params.containsKey("trade_status");
			 trade_status = params.get("trade_status");
			 out_trade_no = params.get("out_trade_no");
			 trade_no = params.get("trade_no");
			 gmt_close = params.get("gmt_close");
			 sign = params.get("sign");
			
			if (gmt_close == null) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date();
				String format = simpleDateFormat.format(date);
				gmt_close = format;
			}
			log.info("info签名算法: " + params.get("sign_type"));
			log.info("info签名charset: " + params.get("charset"));
			log.info("info状态参数: " + containsKey);
			
			boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
			
			log.info("info商户订单号: " + params.get("out_trade_no"));
			log.info("info是否成功: " + signVerified);
			log.info("info状态: " + params.get("trade_status"));

			if (signVerified) {
				log.info("验证成功");
				
			/*	RechargeBILLDomain rechargeBILL = new RechargeBILLDomain();
				rechargeBILL.setAsynState(trade_status);
				rechargeBILL.setZFBDDH(out_trade_no);
				rechargeBILL.setZFBLSH(trade_no);
				rechargeBILL.setZFBTIME(gmt_close);
				Thread.sleep(5000);*/
				writer.print("success");
			} else {
				log.info("验证失败");
				writer.print("fail");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			log.info("错误信息：" + e.getMessage());
			writer.print("fail");// 验签发生异常,则直接返回失败
		} catch (IOException e) {
			e.printStackTrace();
		}
      
    }
    
    /**
     * 获取微信支付的信息
     * @param ip
     * @param body
     * @param createTime
     * @param userId
     * @param totalFee
     * @param notifyUrl
     * @param tradeType
     * @return
     */
    /**
     * @param ip
     * @param body
     * @param createTime
     * @param userId
     * @param totalFee
     * @param notifyUrl
     * @param tradeType
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("getWXOrder")
    @ResponseBody
    public String getWXOrder(@RequestParam("ip") String ip,
                             @RequestParam("body") String body,
                             @RequestParam("createTime") String createTime,
                             @RequestParam("userId") String userId,
                             @RequestParam("totalFee") String totalFee,
                             @RequestParam("notifyUrl") String notifyUrl,
                             @RequestParam("tradeType") String tradeType,
                             @RequestParam("comform") String comform,
                             @RequestParam("publicationId") String publicationId) throws UnsupportedEncodingException{
        String s = createTime +"ht" + userId;
        Long l = Long.valueOf(totalFee);
        XOrderEntity_In xOrderEntity_In = new XOrderEntity_In();
        xOrderEntity_In.setAppid(WX_APPID);
        xOrderEntity_In.setMch_id(WX_MC_ID);
        xOrderEntity_In.setNonce_str(UUID.randomUUID().toString().substring(0, 32));
        xOrderEntity_In.setBody(body);
        xOrderEntity_In.setOut_trade_no(s);
        xOrderEntity_In.setTotal_fee(l);
        xOrderEntity_In.setSpbill_create_ip(ip);
        xOrderEntity_In.setNotify_url(notifyUrl);
        xOrderEntity_In.setTrade_type(tradeType);
        try {
            String sign = WXSignUtil.generateSign(xOrderEntity_In, "sign", null, "&key=" + WX_API_KEY);
            xOrderEntity_In.setSign(sign);
            //xOrderEntity_In.setBody(Encodes.urlDecode(xOrderEntity_In.getBody()));
        } catch (Exception e) {
            log.error("错误信息："+ e.getMessage());
        }
        String sendStr = XstreamUtils.toXML(xOrderEntity_In, XOrderEntity_In.class);
        log.info("生成订单接口参数：" + sendStr);
        // 【调用接口】
        
        
        String PayResponseContent = HttpUtil.httpPost(generateOrderUrl,sendStr);
        log.info("生成订单响应参数：" + PayResponseContent);
        XOrderEntity_Out xOrderReturnEntity = XstreamUtils.toBean(PayResponseContent, XOrderEntity_Out.class);
        AppPayEntity_In appPayEntity_In = new AppPayEntity_In();
        
        String send=null;
        if (xOrderReturnEntity.getReturn_code().equalsIgnoreCase("SUCCESS")) {
            String wxRepSign = xOrderReturnEntity.getSign();
            xOrderReturnEntity.setSign("");
            String wxSign = WXSignUtil.generateSign(xOrderReturnEntity, "sign", null, "&key=" + WX_API_KEY);
            if (wxRepSign.equals(wxSign)) {
                if (xOrderReturnEntity.getResult_code().equalsIgnoreCase("SUCCESS")) {
                    
                    if (StringUtils.isNotBlank(xOrderReturnEntity.getPrepay_id())) {
                        String prepayId = xOrderReturnEntity.getPrepay_id();
                        // 【为app准备支付相关参数】
                        appPayEntity_In.setAppid(xOrderReturnEntity.getAppid());
                        appPayEntity_In.setPartnerid(xOrderReturnEntity.getMch_id());
                        appPayEntity_In.setPrepayid(prepayId);
                        appPayEntity_In.setPackages("Sign=WXPay");
                        String noncestr = UUID.randomUUID().toString().substring(0, 32);// 随机字符
                        appPayEntity_In.setNoncestr(noncestr);
                        String timeStamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
                        appPayEntity_In.setTimestamp(timeStamp);
                        // java关键字，参数名称替换
                        Map<String, String> replaceFieldMap = new HashMap();
                        replaceFieldMap.put("packages", "package");
                        // 生成签名
                        String appPay_sign = WXSignUtil.generateSign(appPayEntity_In, "sign", replaceFieldMap, "&key=" + WX_API_KEY);
                        appPayEntity_In.setSign(appPay_sign);
                        
                          appPayEntity_In.setReturn_code("SUCCESS");
                           send=XstreamUtils.toXML(appPayEntity_In, AppPayEntity_In.class);
                          return send;
                        }
                }
            }
            
        }
        
        appPayEntity_In.setReturn_code("FAIL");
        send=XstreamUtils.toXML(appPayEntity_In, AppPayEntity_In.class);
        return  send;
    }
    
    /**
     * 微信支付异步通知接口
     * @param request
     * @param response
     */
    @RequestMapping("WXOrderNotice")
    public void orderNotice(HttpServletRequest request, HttpServletResponse response){
        
        
        log.info("微信---通知回调接口参数 ---进入方法 *********************************");
        
        
        XOrderNoticeEntity_Out XOrderNoticeEntity_Out = new XOrderNoticeEntity_Out();

        String reqMsg = "";
        StringBuffer reqBuf = new StringBuffer();
        String reponseMsg = "";
        InputStream inputStream;
        try {
            // 【接口通知信息】
            inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((reqMsg = reader.readLine()) != null) {
                reqBuf.append(reqMsg);
            }
            reader.close();
            inputStream.close();

            
            log.info("微信---通知回调接口参数 ---微信返回的xml :"+reqBuf.toString());
            
            // 【处理通知信息】
            XOrderNoticeEntity_In XOrderNoticeEntity = null;
            if (reqBuf.toString().length() > 0) {
                log.info("微信---通知回调接口参数：" + reqBuf.toString());
                // 【XML格式校验(转换校验、非空校验、签名校验)】
                try {
                    XOrderNoticeEntity = XstreamUtils.toBean(reqBuf.toString(), XOrderNoticeEntity_In.class);
                } catch (Exception e) {
                    // xml格式错误
                    XOrderNoticeEntity_Out.setReturn_code("FAIL");
                    XOrderNoticeEntity_Out.setReturn_msg("XML_FORMAT_ERROR");
                    return;
                }

                // 【非空参数校验】
                Set notNullFields = this.initNullField();
                boolean paramFlag = ParamHandleUtil.checkParam(XOrderNoticeEntity, notNullFields);
                if (paramFlag) {
                        // 【签名校验】
                        String reqSign = XOrderNoticeEntity.getSign();
                        XOrderNoticeEntity.setSign("");
                        String generateSign = WXSignUtil.generateSign(XOrderNoticeEntity, "sign", null, "&key=" + WX_API_KEY);
                        if (reqSign.equals(generateSign)) {
                            if (XOrderNoticeEntity.getReturn_code().equalsIgnoreCase("SUCCESS")) {
                                
                                
                                
                                //if (XOrderNoticeEntity.getResult_code().equalsIgnoreCase("SUCCESS")) {
                                    XOrderNoticeEntity_Out.setReturn_code("SUCCESS");
                                    XOrderNoticeEntity_Out.setReturn_msg("OK");
                                    return;
                                //}
                            }
                        } else {
                            // 签名错误
                            XOrderNoticeEntity_Out.setReturn_code("FAIL");
                            XOrderNoticeEntity_Out.setReturn_msg("SIGNERROR");
                            return;
                        }
                    } else {
                        // 账号信息错误
                        XOrderNoticeEntity_Out.setReturn_code("FAIL");
                        XOrderNoticeEntity_Out.setReturn_msg("APPID_MCHID_NOT_MATCH");
                        return;
                    }
                } else {
                    // 缺少参数
                    XOrderNoticeEntity_Out.setReturn_code("FAIL");
                    XOrderNoticeEntity_Out.setReturn_msg("LACK_PARAMS");
                    return;
                }
             
        } catch (Exception e) {
            XOrderNoticeEntity_Out.setReturn_code("FAIL");
            XOrderNoticeEntity_Out.setReturn_msg(e.getMessage());
            log.error(e.getMessage());
        } finally {
            // 【输出反馈】
            OutputStream out;
            try {
                reponseMsg = XstreamUtils.toXML(XOrderNoticeEntity_Out, XOrderNoticeEntity_Out.class);
                log.info("微信---通知回调响应参数：" + reponseMsg);
                out = response.getOutputStream();
                out.write(reponseMsg.getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                log.error("Stream error!");
            }
        }
    }
    /**
     * 初始非空字段
     * 
     * @return
     */
    private Set initNullField() {
        Set notNullFields = new HashSet();
        notNullFields.add("return_code");
        notNullFields.add("appid");
        notNullFields.add("mch_id");
        notNullFields.add("nonce_str");
        notNullFields.add("sign");
        notNullFields.add("result_code");
        notNullFields.add("trade_type");
        notNullFields.add("prepay_id");

        return notNullFields;
    }
}
