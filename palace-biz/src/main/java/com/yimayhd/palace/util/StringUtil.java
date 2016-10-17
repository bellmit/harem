package com.yimayhd.palace.util;

/**
 * Created by xushubing on 2016/10/17.
 */
public class StringUtil {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0; // string.isEmpty() in Java 6
    }

    public static boolean isNotNullOrNotEmpty(String string) {
        return !isNullOrEmpty(string);
    }
}
