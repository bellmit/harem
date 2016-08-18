package com.yimayhd.palace.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

public class TextUtil {
	
	private static final Pattern TAG_PATTERN = Pattern.compile("([#])([^#]+)([#])");
	/**
	 * 转为JSON字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static final String toJSONString(Object obj) {
		return JSON.toJSONString(obj);
	}


	public static String getPicFirst(String picUrls){
		if(StringUtils.isNotBlank(picUrls)){
			String[] arr = picUrls.split("\\|");
			return arr[0];
		}
		return "";
	}
	
	/*
	 * 获取微博话题内容
	 */
	public static List<String> getTopicContent(String str) {
		Set<String> set = new HashSet<String>();
		if( StringUtils.isBlank(str) ){
			return null;
		}
		Matcher m = TAG_PATTERN.matcher(str.trim());
		while (m.find()) {
			set.add(m.group());
		}
		return new ArrayList<String>(set);
	}

	public static String html(String str) {
		str = str.replaceAll("\"", "&quot;");
//		str = str.replace("'", "&#39;");
//		str = StringEscapeUtils.escapeHtml(str);
		return str;
	}
	
}
