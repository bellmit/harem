package com.yimayhd.palace.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PhoneUtil {

    public static final String MOBILE_PREFIX = "+86";

    /**
     * 电话去掉+86
     * @param phone
     * @return
     */
    public static String phoneFormat(String phone){
        if(null == phone){
            return phone;
        }
        if(-1 != phone.indexOf(MOBILE_PREFIX)){
            return phone.substring(3);
        }
        return phone;
    }
    //手机号5-8位加星号
    public static String mask(String mobile){
        if(mobile==null ||  ! (mobile.trim().length()>0 )){
            return mobile;
        }
        String prefix=mobile.substring(0,3);
        String suffix =mobile.substring(mobile.length()-4,mobile.length());
        return prefix+"****"+ suffix;
    }
    
    public static String getMobileWithoutPrefix(String mobile){
    	if( StringUtils.isBlank(mobile) ){
    		return null;
    	}
    	if( mobile.startsWith(MOBILE_PREFIX) ){
    		return mobile.substring(MOBILE_PREFIX.length() ) ;
    	}
    	return mobile;
    }
}
