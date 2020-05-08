package com.thinkgem.jeesite.modules.web.util.pay;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.thinkgem.jeesite.modules.web.util.MD5Util;

/**
 * 微信签名
 * 
 * @author lwy
 * 
 */
public class WXSignUtil {

	/**
	 * 生成签名
	 * 
	 * @param object
	 * @param exptFieldName
	 * @param appendStr
	 * @return
	 */
	public static String generateSign(Object object, String exptFieldName, Map<String, String> replaceField, String appendStr) {
		String sign = "";
		try {
			StringBuffer paramBuf = new StringBuffer();
			// 获取字段名
			Class<?> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();
			String[] sortFields = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				sortFields[i] = fields[i].getName();
			}
			// 排序
			Arrays.sort(sortFields);
			// 拼接参数
			for (int st = 0; st < sortFields.length; st++) {
				if (!sortFields[st].equalsIgnoreCase(exptFieldName)) {
					String fieldName = sortFields[st];
					if (null != replaceField && replaceField.keySet().contains(fieldName)) {
						fieldName = replaceField.get(fieldName);
					}
					Method method = (Method) object.getClass().getMethod("get" + getMethodName(sortFields[st]));
					if (null != method.invoke(object)) {
						String fieldVal = method.invoke(object).toString();
						if (StringUtils.isNotBlank(fieldVal)) {
							paramBuf.append(fieldName);
							paramBuf.append("=");
							paramBuf.append(fieldVal);
							paramBuf.append("&");
						}
					}
				}
			}
			if (paramBuf.length() > 0) {
				String paramStr = paramBuf.toString().substring(0, paramBuf.toString().length() - 1) + appendStr;
				// 对参数串进行加密
				sign = MD5Util.md5(paramStr).toUpperCase();
			}
		} catch (Exception e) {
			sign = "";
		}
		return sign;
	}

	/**
	 * 获取get方法名
	 * 
	 * @param fildeName
	 * @return
	 */
	public static String getMethodName(String fildeName) {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
