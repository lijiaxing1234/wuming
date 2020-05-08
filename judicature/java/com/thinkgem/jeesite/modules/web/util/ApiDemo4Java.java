package com.thinkgem.jeesite.modules.web.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
  * @ClassName: ApiDemo
  * @Description: TODO
  *
  */
public class ApiDemo4Java {


	static String account = "JSM41287";
	
	static String password = "vkgb6ndx";
	
	static String veryCode = "9wzn1xwv0xao";
	
	static String http_url  = "http://112.74.76.186:8030";
	
	public static final String CHARSET_UTF8 = "UTF-8";
	
	public static final String CHARSET_GBK = "GBK";
	
	
	public static String getBalance(){
		String balanceUrl = http_url + "/service/httpService/httpInterface.do?method=getAmount";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username",account);
		params.put("password",password);
		params.put("veryCode",veryCode);
		String result = sendHttpPost(balanceUrl, params);
		return result;
	}
	
	public static String sendSms(String mobile,String content){
		String sendSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);
		params.put("msgtype", "1");
		params.put("code", "utf-8");
		String result = sendHttpPost(sendSmsUrl, params);
		return result;
	}
	
	
	public static String sendTplSms(String mobile,String tplId,String content){
		String sendTplSmsUrl = http_url + "/service/httpService/httpInterface.do?method=sendMsg";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		params.put("mobile", mobile);
		params.put("content", content);	//����ֵ����Ӣ�Ķ��Ÿ���
		params.put("msgtype", "2");		//2-ģ�����
		params.put("tempid", tplId);	//ģ����
		params.put("code", "utf-8");
		String result = sendHttpPost(sendTplSmsUrl, params);
		return result;
	}
	
	
	public static String queryReport(){
		String reportUrl = http_url + "/service/httpService/httpInterface.do?method=queryReport";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(reportUrl, params);
		return result;
	}
	/**
	 * ��ѯ���лظ�����
	 * @return
	 * String  xml�ַ�������ʽ��ο��ĵ�˵��
	 */
	public static String queryMo(){
		String moUrl = http_url + "/service/httpService/httpInterface.do?method=queryMo";
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", account);
		params.put("password", password);
		params.put("veryCode", veryCode);
		String result = sendHttpPost(moUrl, params);
		return result;
	}
	
	
	/***
	 * 
	 * @param apiUrl �ӿ������ַ
	 * @param paramsMap �����������
	 * @return xml�ַ�������ʽ��ο��ĵ�˵��
	 * String
	 */
	private static String sendHttpPost(String apiUrl, Map<String, String> paramsMap) {
		String responseText = "";
		StringBuilder params = new StringBuilder();
		Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();
			params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		try {
			URL url = new URL(apiUrl);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CHARSET_UTF8);
			out.write(params.toString()); //post�Ĺؼ����ڣ�
			out.flush();
			out.close();
			//��ȡ��Ӧ����ֵ
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,CHARSET_UTF8));
			String temp = "";
			while (( temp = br.readLine()) != null) {
				responseText += temp;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseText;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//��ѯ�˺����
		//System.out.println(getBalance());
		
		//������ͨ���ţ��޸Ľ��ն��ŵ��ֻ����뼰��������,���������Ӣ�Ķ��Ÿ��������֧��100������
		//System.out.println(sendSms("1**********,1**********", "������֤����8888,��ע�Ᵽ�ܣ�����֤���֪���ˡ�"));
		
		/**
		 * ����ģ����ţ��޸Ľ��ն��ŵ��ֻ����뼰���õ�ģ����
		 * ע�⣺
		 * 	1.����ģ����ű���ֵ���ܰ���Ӣ�Ķ��ź͵Ⱥţ�, =��
		 *  2.�����ַ�����ת��:
		 *  	+   %2b  
		 *  	�ո�    %20  
		 * 		&   %26
		 * 		%	%25
		 */
//		System.out.println(sendTplSms("1**********","JSM40001-0006","@1@=������ʥ,@2@=51233678"));
		
		//��ѯ�·����ŵ�״̬����
//		System.out.println(queryReport());
		
		//��ѯ���лظ�����
		//System.out.println(queryMo());
	}

}
