package com.thinkgem.jeesite.modules.web.util.pay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 非空参数校验并生成对象
 * 
 * 
 */
public class ParamHandleUtil {

	private static final Logger logger = Logger.getLogger(ParamHandleUtil.class);

	/**
	 * 初始化参数并校验非空参数
	 * 
	 * @param request
	 * @param notNullFields
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkParam(Object object, HttpServletRequest request,Set notNullFields) {
		boolean flag = false;
		try {
			// 获取对象所有属性
			Class<?> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();

			// 获取请求参数
			request.setCharacterEncoding("utf-8");
			InputStream inreq = request.getInputStream();
			StringBuffer paramsBuf = new StringBuffer();
			if (inreq != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inreq, "utf-8");
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String str;
				while (((str = reader.readLine()) != null)) {
					paramsBuf.append(str);
				}
				reader.close();

				JSONObject jsonObject = null;
				if (StringUtils.isNotBlank(paramsBuf.toString())) {
					jsonObject = JSONObject.parseObject(paramsBuf.toString());
					// 加载参数对象
					for (Field field : fields) {
						field.setAccessible(true);
						if (notNullFields.contains(field.getName())) {
							if (jsonObject.containsKey(field.getName())) {
								field.set(object, jsonObject.get(field.getName()));
							} else {
								flag = false;
								break;
							}
						} else {
							if (jsonObject.containsKey(field.getName())) {
								field.set(object, null != jsonObject.get(field.getName()) ? jsonObject.get(field.getName()) : "");
							} else {
								// 自动取默认值
							}
						}
						field.setAccessible(false);
						flag = true;
					}
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 校验非空参数
	 * 
	 * @param object
	 * @param notNullFields
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkParam(Object object, Set notNullFields) {
		boolean flag = false;
		try {
			// 获取对象所有属性
			Class<?> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();

			if (null != object) {
				// 加载参数对象
				for (Field field : fields) {
					if (notNullFields.contains(field.getName())) {
						Method method = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						if (null != method.invoke(object)) {
							String fieldVal = method.invoke(object).toString();
							if (StringUtils.isBlank(fieldVal)) {
								flag = false;
								break;
							} else {
								flag = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
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
