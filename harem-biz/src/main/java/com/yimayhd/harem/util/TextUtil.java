package com.yimayhd.harem.util;

import com.alibaba.fastjson.JSON;

public class TextUtil {
	/**
	 * 转为JSON字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toJSONStringForHTML(Object obj) {
		return JSON.toJSONString(obj);
	}
}
