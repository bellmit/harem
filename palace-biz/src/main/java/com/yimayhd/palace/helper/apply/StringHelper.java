package com.yimayhd.palace.helper.apply;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
//        	str = str.trim();
//            Pattern p = Pattern.compile("[\t|\r|\n]w+[\t|\r|\n]");
//            Matcher m = p.matcher(str);
            dest = str.replaceAll("^[　*| *| *|//s*]*", "").replaceAll("[　*| *| *|//s*]*$", "");
        }
        return dest;
    }
}
