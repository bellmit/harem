package com.yimayhd.palace.util;

import java.util.ArrayList;
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
	
	public static List<String> array2List(String[] array){
		if( null == array || array.length == 0 ){
			return null;
		}
		List<String> list = new ArrayList<String>() ;
		for(String val : array ){
			list.add(val);
		}
		return list ;
	}
}
