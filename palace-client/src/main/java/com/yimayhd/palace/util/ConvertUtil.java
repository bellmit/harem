package com.yimayhd.palace.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangdi on 16/9/9.
 */
public class ConvertUtil {

    public static List<Long> stringTolong(String [] strArr){


        if(strArr.length==0){
            return null;
        }
        List<Long> longList = new ArrayList<Long>(strArr.length);
        for(String str : strArr){
            longList.add(Long.valueOf(str));
        }

        return longList;
    }
}
