package com.yimayhd.palace.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2015/10/27.
 */
public class DateFormat {
    /**
     * 开始日期格式化为"yyyy-MM-dd 00-00-00"
     * @param date
     * @return
     * @throws ParseException
     */
    public Date begindDateFormat(String date) throws ParseException {
        //java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.text.DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dfNew.parse(date + " 00-00-00");
    }
    /**
     * 结束格式化为"yyyy-MM-dd 23-59-59"
     * @param date
     * @return
     * @throws ParseException
     */
    public Date endDateFormat(Date date) throws ParseException {
        //java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.text.DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dfNew.parse(date + " 23-59-59");
    }

    public static String dateFormat(Date date,String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateFormat = simpleDateFormat.format(date);
        return dateFormat;
    }
    
    public static Date getDateEnd(String time){

    	if(StringUtils.isBlank(time)){
    		return null;
    	}
    	
		time  = time + " 23:59:59";
		
		Date end = parseDate(time, "yyyy-MM-dd HH:mm:ss");
		return end;
		
	}
	public static Date getDateStart(String time){
    	if(StringUtils.isBlank(time)){
    		return null;
    	}
		time  = time + " 00:00:00";
		
		Date end = parseDate(time, "yyyy-MM-dd HH:mm:ss");
		return end ;
		
	}
	
	public static Date parseDate(String dayFormat, String format){
		if( dayFormat == null || format == null || "".equals(format)){
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(dayFormat);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
