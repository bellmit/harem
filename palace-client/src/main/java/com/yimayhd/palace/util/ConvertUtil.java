package com.yimayhd.palace.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangdi on 16/9/9.
 */
public class ConvertUtil {
    private static final Logger log = LoggerFactory.getLogger(ConvertUtil.class);
    public static List<Long> stringTolong(String [] strArr){


        if(strArr.length==0){
            return null;
        }
        List<Long> longList = new ArrayList<Long>(strArr.length);
        try{
            for(String str : strArr){
                longList.add(Long.valueOf(str));
            }
        }catch (Exception e){
            log.error("stringTolong is exception",e);
        }


        return longList;
    }
}
