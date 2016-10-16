package com.yimayhd.palace.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_TIME = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	public static final String DAY_HORU_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 日期格式化成字符串 输入：日期对象 输出：如：2012-10-11 12:11:13
	 *
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		StringBuffer result = new StringBuffer();
		if (date != null) {
			SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
			result.append(format.format(date));
		}
		return result.toString();
	}
}
