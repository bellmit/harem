package com.yimayhd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * 用来处理一些遵循ISO8601标准的工具类
 *
 * Created by wqy on 15-6-11.
 */
public class TimeUtils {

    private static final String STANDARD_TIME_ZONE = "GMT0";
    private static final String LOCAL_TIME_ZONE = "GMT+8:00";

    public static final String FORMAT_RULE_0 = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_RULE_1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String FORMAT_RULE_2 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String FORMAT_RULE_3 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX";

    public static String formatLocalDateString(String dateStr) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_RULE_1);
        TimeZone zone0 = TimeZone.getTimeZone(STANDARD_TIME_ZONE);
        sdf.setTimeZone(zone0);
        Date date = sdf.parse(dateStr);
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_RULE_0);
        TimeZone zone1 = TimeZone.getTimeZone(LOCAL_TIME_ZONE);
        sdf1.setTimeZone(zone1);

        return sdf1.format(date);
    }

    public static String formatLocalDateString(String dateStr, String formatRule) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(formatRule);
        TimeZone zone0 = TimeZone.getTimeZone(STANDARD_TIME_ZONE);
        sdf.setTimeZone(zone0);
        Date date = sdf.parse(dateStr);
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_RULE_0);
        TimeZone zone1 = TimeZone.getTimeZone(LOCAL_TIME_ZONE);
        sdf1.setTimeZone(zone1);

        return sdf1.format(date);
    }

    public static Date formatLocalDate(String dateStr) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_RULE_1);
        TimeZone zone0 = TimeZone.getTimeZone(STANDARD_TIME_ZONE);
        sdf.setTimeZone(zone0);
        Date date = sdf.parse(dateStr);

        return date;
    }

    public static Date formatLocalDate(String dateStr, String formatRule) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat(formatRule);
        TimeZone zone0 = TimeZone.getTimeZone(STANDARD_TIME_ZONE);
        sdf.setTimeZone(zone0);
        Date date = sdf.parse(dateStr);

        return date;
    }

}
