package com.yimayhd.palace.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.palace.constant.Constant;

public class CommonUtil {
	public static <T> String list2String(List<T> list){
		if( CollectionUtils.isEmpty(list) ){
			return null;
		}
		StringBuilder sb = new StringBuilder() ;
		for( T t : list ){
			sb.append(t).append(Constant.COMMA) ;
		}
		sb.setLength(sb.length() - 1 );
		return sb.toString();
	}
}
