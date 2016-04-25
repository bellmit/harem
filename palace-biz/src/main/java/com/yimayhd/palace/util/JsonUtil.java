package com.yimayhd.palace.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	
	public static String toJsonString(Object obj){
		if( obj == null ){
			return null;
		}
		return JSON.toJSONString(obj) ;
	}
}
