package com.thinkgem.jeesite.modules.web.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Http连接
 * @author lwy
 *
 */
public class HttpUtil {
	
	private static final Logger logger = Logger.getLogger(HttpUtil.class);
	
	
	public static String httpPost(String sendUrl,String sendContent) {
		StringBuffer resContent = new StringBuffer();
		try{
			// 建立连接
			URL url = new URL(sendUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			// 设置参数
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setUseCaches(false);
			httpConn.setRequestMethod("POST");
			// 设置请求属性
			httpConn.setRequestProperty("Content-Type", "text/xml");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			//httpConn.setRequestProperty("Charset", "UTF-8");
			// 连接
			httpConn.connect();
			
			// 建立输入流，向指向的URL传入参数
/*			DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
			dos.writeBytes(new String(sendContent.getBytes(),"UTF-8"));*/
			if (StringUtils.isNotBlank(sendContent)) {
				OutputStream outputStream = httpConn.getOutputStream();  
				outputStream.write(sendContent.getBytes("UTF-8"));  
				outputStream.close();
			}
			
			/*dos.flush();
			dos.close();*/
			// 获得响应状态
			int resultCode = httpConn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode) {
				String readLine = new String();
				BufferedReader resReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				while ((readLine = resReader.readLine()) != null) {
					resContent.append(readLine);
				}
				resReader.close();
			}
			httpConn.disconnect();
		}catch(Exception e){
			e.printStackTrace();
		}
		return resContent.toString();
	}
	
	
	
	public static String httpsRequest(String requestUrl, String output) {  
        try{  
            URL url = new URL(requestUrl);  
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setRequestMethod("POST");  
            if (null != output) {  
                OutputStream outputStream = connection.getOutputStream(); 
                outputStream.write(output.getBytes("UTF-8"));  
                outputStream.close();  
            }  
            // 从输入流读取返回内容  
            InputStream inputStream = connection.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            StringBuffer buffer = new StringBuffer();  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            inputStream.close();  
            inputStream = null;  
            connection.disconnect();  
            return buffer.toString();  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
  
        return "";  
    }  
}
