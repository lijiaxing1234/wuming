package com.thinkgem.jeesite.modules.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

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

import sun.swing.StringUIClientPropertyKey;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.sun.tracing.dtrace.Attributes;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.utils.EmailUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.web.entity.PumpkinPay;
import com.thinkgem.jeesite.modules.web.entity.pay.UserOrderWithBLOBs;
import com.thinkgem.jeesite.modules.web.service.PayService;
import com.thinkgem.jeesite.modules.web.service.UserInfoService;
import com.thinkgem.jeesite.modules.web.service.UserOrderService;
import com.thinkgem.jeesite.modules.web.util.ApiDemo4Java;
import com.thinkgem.jeesite.modules.web.util.GlobalConfigUtils;
import com.thinkgem.jeesite.modules.web.util.HttpUtil;
import com.thinkgem.jeesite.modules.web.util.JsonUtil;
import com.thinkgem.jeesite.modules.web.util.ReturnData;
import com.thinkgem.jeesite.modules.web.util.XstreamUtils;


/**
 *  支付宝、微信、发送短信 等相关接口控制
 *
 */
@Controller
@RequestMapping("/web/aviation")
public class AviationController extends BaseController {
	
	/*
    //微信相关
    public static final String generateOrderUrl=GlobalConfigUtils.getKeyValue("weixin.generateOrderUrl");
    
    public static final String WX_APPID =GlobalConfigUtils.getKeyValue("weixin.app.id");
    
    public static final String WX_MCH_ID = GlobalConfigUtils.getKeyValue("weixin.mch.id");
    
    public static final String WX_API_KEY = GlobalConfigUtils.getKeyValue("weixin.api.key");
    */
    
    //支付宝
    
    // 沙箱正常的
   // public static final String APP_ID="2016073100135181";
   // public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA13pkjEJ4ucJWXGJnjPUJ8FYVO8pk+5o0o6rhyjWruxYtCrKxDtUXkgnO5MdHHvsElsgALxalk9Of/qvF92dU6+BjkINq94izFriOxAf0+c3sN+WZ4geuaVMJdNF3UEF0mwKnIILkATJub9C2Hj6Sy2pyYTRGEAspX6UKNJEHOGN5FO77mWWigjZmOJGUbXtdbvFxjvdaD1/kWKzecf6bnBOB2mhcuc9LbDHYPBTZgL7yyAxGR8S5fnTR5FUmiQp80k2/wwti+oniJWWPU8rr9FZ5h3/ll/9yhAGwZX2WTdng62FpJkpVQJquipBsx/SEsYShCmHUUyHGg26bYUvAXwIDAQAB";
   // public static final String APP_PRIVATE_KEY="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3qdtMkG7raRiBOPx20tG37X5HmHegsVh3Ftr8Dp/EOMsCONOfZMOInws257pFPYLrAFRcwS4oQg4VDf3ghIjKvBwcDQxIXSlYHbB4ORDLnCXahSHxD65S+QtHMU1q7e08BTjZEv/AM11SvoLQPyjheOVOkd0I4xf3Lx68BOJ8+5E9Xfo3ze7chjh6YfuwK8APSptTW+YW89P+dz7L+RRJdttw5GswD0wysde2qoHmCzzQvaacp9BMc+7LqD+/YO5dONE74g+g24lS+/Md/Ffj5Rtg5bW7Si0b6noCKLuq4qV/+E776WV2DPMN5VJLWyqcoCTRsHfZGNqrapRolhhTAgMBAAECggEAU+y2+/6H4OSOQQZEPxvIny5T5O1jxLclQI3eFQ2treFxypsjAJPv0a8zVax/7oHUIARviE7yA41jl/VjzENjqphYk+zWbv67FLvEVrtk1FWO6pKpVtkehGWu5KI/yRvdqu6L8o1+9lnCTDwn1Hb3/EfdKJD4msYCvn32ol3tDMS00MrA/o0CwhK7kxNUB0aQGc2fk/sw7RZYTgp9ZgsygB/bRpRz3KYTuyoExJ8dLqK/zQ5LMAO0YaseSylhhYYY3VjvxwZlGe3kOJAkyRFwcJrDyxtJOGz+mthUYjZPANB+s1muP7oZKzFiZ7ebnQatBnQXWJtd8DWb2zn4CYe5sQKBgQDsBzAMpWslNOh4ZoUVCfIf7ph+X+xIJc6z/sI0EN9ZGOxSm9BM88ViADO7NJcVjthsmEIxZmXhMCoTbRgbzzK3AQcHrH5etbPL0zSi/lLsxPLnQUFB61tXo6uRxMhORYc4eA08wT5MrDfr+3kbBCC9jj+CjZt4AO6RNiLNDXltKQKBgQDHNFqNfBvOspaUyAoCmyiK4bjHEXOXwCJv+7eCN2GRTt4x0pfjcnGPKbLLwz3ZzANxV9zdfd5khSOr3zPjbImywvT5A0dyK5fpWUX2sCmh0Dr5GQZ1GNLYlSyYMASh5Aya3ERE3driK4KoiLCx0rx5EMuyOEvTC7DDBt7LC2iNGwKBgD0F3rZmTj1gYHpAh0fZOnBnD6YcxsogfECtLSio9BXlC7SP9Frw66daxgUwo58/P1VUSEjdYJnIChjbq9AE0eferCKfxkxWThX0lTdO6cGtad/AT07rusH93u3hIqiZ8Uv4PfPnQmQwYoTwj28cshpQHLt0QS3Vj9ibVoQNZ8eBAoGBAJJIUc7gOpNY9dcf5qnvQqBqDYSxvg7L2v4c/K80+AL6xZQUr9Eoc1fMKS4Um9DaThTnjptgGPX8ByvpZnM1exC4tag+zjJ67L1A/22eA5R+EzBlPiazKfI96VeAu4Yer1rxMXjY6il4BiS46/hJZnoI6Wxght3eEdnFq8mzAP3NAoGAFRwpItgKuZeGi++vAlrqLXj0P4mwjupfctQyGDJHWnj+AO1bwU6Dz/8QXLB4U1nGRdGb1TLF8U5BP3hKAi4wlSERTru0grzcdDF2tn3LInTA+dLcMzTsZ0Wi8WV4HVn6LpvUgw7XvdLKC1mmtQWpGNHA7A+jCyXYUiOYhQMakVE=";
    
    public static final String CHARSET = "utf-8";
    
    public static final String alipay_generateOrderUrl=GlobalConfigUtils.getKeyValue("alipay.generateOrderUrl");
    public static final String alipay_notify_url=GlobalConfigUtils.getKeyValue("alipay.notify_url");
    public static final String APP_ID=GlobalConfigUtils.getKeyValue("alipay.app.id");
    public static final String ALIPAY_PUBLIC_KEY =GlobalConfigUtils.getKeyValue("alipay.public.key");
    public static final String APP_PRIVATE_KEY=GlobalConfigUtils.getKeyValue("alipay.private.key");
    
   
    //短信接口
 //   public static final String API_SMS=GlobalConfigUtils.getKeyValue("sms.api.url");
    
    private static Logger log = LoggerFactory.getLogger(AviationController.class);
    
    @Autowired
    UserOrderService userOrderService;
    
    @Autowired
	PayService payService;
    
    @Autowired
    UserInfoService userInfoService;
    
    @RequestMapping("pumpkinPay")
    @ResponseBody
    public String pumpkinPay(PumpkinPay pumpkinPay){
    	ReturnData returnData = new ReturnData();
    	int totalIntegral = userInfoService.getTotalIntegral(pumpkinPay.getUserId());
    	if (totalIntegral < pumpkinPay.getPumpkinCount()) {
    		returnData.setStatus(false);
    		returnData.setMessage("南瓜数量不足!");
    		return JsonUtil.toJson(returnData);
		}
    	if (pumpkinPay.getCourseId() == null || pumpkinPay.getCourseId() == "") {
    		returnData.setStatus(false);
    		returnData.setMessage("课程不能为空!");
    		return JsonUtil.toJson(returnData);
		}
    	if (pumpkinPay.getUserId() == null || pumpkinPay.getUserId() == "") {
    		returnData.setStatus(false);
    		returnData.setMessage("用户不能为空!");
    		return JsonUtil.toJson(returnData);
		}
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
	 @RequestMapping(value="orderInfo",method=RequestMethod.POST)
     @ResponseBody
     public String getorderInfo(@RequestParam(value="userId",required=true) String userId,
    		                    @RequestParam(value="phone",required=true) String phone,
                                @RequestParam(value="amountInt",required=false) String amountInt,
                                @RequestParam(value="outTradeNo",required=false) String outTradeNo,
                                @RequestParam(value="body",required=false) String body,
                                @RequestParam(value="comform",required=false) String comform,
                                @RequestParam(value="targetId",required=false) String targetId){
       
    	 
       String orderInfo=null;
    	
       if(StringUtils.isNotBlank(alipay_generateOrderUrl)
    		    &&StringUtils.isNotBlank(APP_ID)
    		    &&StringUtils.isNotBlank(ALIPAY_PUBLIC_KEY)
    		    &&StringUtils.isNotBlank(APP_PRIVATE_KEY)
    		    &&StringUtils.isNotBlank(alipay_notify_url))  {
       
	       //实例化客户端
	         AlipayClient alipayClient = new DefaultAlipayClient(alipay_generateOrderUrl, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
	         //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
	         AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
	         //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
	         AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
	         model.setBody(body);
	         model.setSubject(phone);
	         model.setOutTradeNo(outTradeNo);
	         model.setTimeoutExpress("30m");
	         model.setTotalAmount(amountInt);
	         model.setProductCode("QUICK_MSECURITY_PAY");
	         request.setBizModel(model);
	         request.setNotifyUrl(alipay_notify_url);
			try {
				// 这里和普通的接口调用不同，使用的是sdkExecute
				
				AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
				System.out.println(response.getBody());// 就是orderString
				orderInfo=response.getBody();						// 可以直接给客户端请求，无需再做处理。
				
				UserOrderWithBLOBs  record=new UserOrderWithBLOBs();
				record.setPayType("支付宝");
				
				record.setOrderNo(outTradeNo);
				record.setOrderInfo(orderInfo);
				record.setCreateTime(new Date());
				record.setStatus(0);
				
				record.setUserId(userId);
				record.setPhone(phone);
				record.setComform(comform);
				record.setTargetId(targetId);
				record.setPrice(BigDecimal.valueOf(Double.valueOf(amountInt)));
				
				userOrderService.insertUserOrderSelective(record);
				
			} catch (AlipayApiException e) {
				e.printStackTrace();
				orderInfo=null;
			}
       }
		
		return orderInfo;
		
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
			log.info(params.toString());
			log.info("info签名算法: " + params.get("sign_type"));
			log.info("info签名charset: " + params.get("charset"));
			log.info("info状态参数: " + containsKey);
			
			boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
			
			log.info("info商户订单号: " + params.get("out_trade_no"));
			log.info("info是否成功: " + signVerified);
			log.info("info状态: " + params.get("trade_status"));
			log.info("sign状态："+sign);

			if (signVerified) {
				log.info("验证成功");
				
				UserOrderWithBLOBs  record=new UserOrderWithBLOBs();
				record.setOrderNo(out_trade_no);
				record.setStatus(1);
				record.setUpdateTime(new Date());
				record.setPayReuslt(JSON.toJSONString(params));
				userOrderService.updateUserOrderSelective(record);
				
				writer.print("success");
			} else {
				log.info("验证失败");
				
				UserOrderWithBLOBs  record=new UserOrderWithBLOBs();
				record.setOrderNo(out_trade_no);
				record.setStatus(2);
				record.setUpdateTime(new Date());
				record.setPayReuslt(JSON.toJSONString(params));
				userOrderService.updateUserOrderSelective(record);
				
				writer.print("fail");
			}
		} catch (AlipayApiException e) {
			UserOrderWithBLOBs  record=new UserOrderWithBLOBs();
			record.setOrderNo(out_trade_no);
			record.setStatus(2);
			record.setUpdateTime(new Date());
			params.put("异常", e.getMessage());
			record.setPayReuslt(JSON.toJSONString(params));
			userOrderService.updateUserOrderSelective(record);
			e.printStackTrace();
			log.info("错误信息：" + e.getMessage());
			writer.print("fail");// 验签发生异常,则直接返回失败
		} catch (IOException e) {
			e.printStackTrace();
		}
      
    }
    
    @ResponseBody
    @RequestMapping(value = "sendMessagePSW")
    public String sendMessagePSW(String phone) throws UnsupportedEncodingException {
    	ReturnData returnData = new ReturnData();
    	  if (StringUtils.isNotEmpty(phone)) {
    		 
    		  try {
    			  
    			  Date day=new Date();
    			 // String taday= ""+day.getYear()+day.getMonth()+ day.getDay();  //日期
    			  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH");
    			   String taday = formatter.format(day);
    			   
    			  String smsMaxSendCountString =GlobalConfigUtils.getKeyValue("smsMaxSendCount");
    			  int smsMaxSendCount =Integer.parseInt(smsMaxSendCountString);
    			  int count =0;
    			  String tadayCount =EmailUtils.get(phone);  //获取改手机号发送短信条数
    			  if (StringUtils.isNotBlank(tadayCount)) {
    				  String []arr =tadayCount.split(",");
        			  if(arr!=null&&arr.length==2)
        			  {
        				  if(arr[0].equals(taday))  //当天的发送的短信
        				  {
        					  count = Integer.parseInt(arr[1]);
        					  if (count>=smsMaxSendCount) {
        						  returnData.setData("当前发送信息达到上限！请过一个小时再试！");
    				              returnData.setMessage("1");
    				              return JsonUtil.toJson(returnData);
        						 
    						} 
        				  }
        			  }
				}
    			  count++; //
    			  EmailUtils.put(phone, taday+","+count) ; //记录该手机当天发送的短信条数
    			  
    			  
			} catch (Exception e) {
				// TODO: handle exception
			}
    		  
    		  
    		  
    		  //int a = (int)(Math.random()*9+1)*100000;
    		  int a = new Random().nextInt(999999);
              String content = "【职考通】您的验证码为["+a+"],验证码有效期5分钟.";
              String encode = URLEncoder.encode(content, "GBK");
              String encode2 = URLEncoder.encode(encode, "GBK");
              
              String pass =GlobalConfigUtils.getKeyValue("smsPassword");
              
            
              String url = "http://api.itrigo.net/mt.jsp?cpName=znzlaw1&cpPwd="+pass+"&phones="+phone+"&msg="+encode2;
              //String sendSms = ApiDemo4Java.sendTplSms(phone, "JSM41287-0001", content);
              String httpPost = HttpUtil.httpPost(url, null);
              if (httpPost.equalsIgnoreCase("0")) {
				returnData.setStatus(true);
              }
             
              returnData.setData(a);
              returnData.setMessage(httpPost);
              return JsonUtil.toJson(returnData);
    	  }else {
    		  return null;
		} 
    }
}