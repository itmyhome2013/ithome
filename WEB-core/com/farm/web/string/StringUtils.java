package com.farm.web.string;

public class StringUtils {
	/**
	 * 如果字符串为null 返回"" 防止报空指针异常
	 * @param str
	 * @return
	 */
	public static Object isNull(Object str){
		if(str == null){
			return "";
		}
		return str;
	}
	
	/**
	 * 字符串滤空
	 *
	 * @param str
	 *            需要过滤的字符串
	 * @return 过滤后的字符串
	 */
	public static String filterNull(final String str) {
		String s = "";
		if (str == null || "null".equals(str.trim())) {
			s = "";
		} else {
			s = new String(str.trim());
		}

		return s;
	}
}
