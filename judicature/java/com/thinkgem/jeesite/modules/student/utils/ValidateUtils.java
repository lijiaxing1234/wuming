package com.thinkgem.jeesite.modules.student.utils;

/**
 * 手机端调试信息，验证传入参数空值问题
 * @author .36
 *
 */
public class ValidateUtils {
	public static String vaParam(String... strings) {
		String message = "";
		for (String string : strings) {
			if (null == string || "".equals(string)) {
				message = "传入参数值不存在或值为空";
			} else {
				message = "参数正常";
			}
		}
		if ("参数正常".equals(message)) {
			return null;
		} else {
			return message;
		}
	}
}