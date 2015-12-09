package com.yimayhd.harem.util;

/**
 * Created by Administrator on 2015/12/9.
 */
public class PhoneUtil {
    /**
     * 电话去掉+86
     * @param phone
     * @return
     */
    public static String phoneFormat(String phone){
        if(null == phone){
            return phone;
        }
        if(-1 != phone.indexOf("+86")){
            return phone.substring(3);
        }
        return phone;
    }
}
