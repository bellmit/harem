package com.yimayhd.date;


import org.springframework.util.StringUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wqy on 15-6-11.
 */
public class TimeTest {
    protected static Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void printSysProperties(){
        Properties props=System.getProperties();
        Iterator iter=props.keySet().iterator();
        while(iter.hasNext()){
            String key=(String)iter.next();
            System.out.println(key+" = "+ props.get(key));
        }
    }

    /**
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * @param timeZoneOffset
     * @return
     */
    public String getFormatedDateString(int timeZoneOffset){
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        if (ids.length == 0) {
            // if no ids were returned, something is wrong. use default TimeZone
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        return sdf.format(new Date());
    }

    public static String getFormatedDateString(String _timeZone){
        TimeZone timeZone = null;
        if(StringUtils.isEmpty(_timeZone)){
            timeZone = TimeZone.getDefault();
        }else{
            timeZone = TimeZone.getTimeZone(_timeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
        sdf.setTimeZone(timeZone);
        //TimeZone.setDefault(timeZone);
        System.out.print(_timeZone + ":");
        return sdf.format(new Date());
    }

    public static void setCNTimeZone(){
        final TimeZone zone = TimeZone.getTimeZone("GMT+8");
        TimeZone.setDefault(zone);
    }

    public static void main(String args[]){
        System.out.println(getFormatedDateString(""));
        System.out.println(getFormatedDateString("Asia/Shanghai"));
        System.out.println(getFormatedDateString("Japan"));
        System.out.println(getFormatedDateString("Europe/Madrid"));
        System.out.println(getFormatedDateString("GMT+8:00"));

        printSysProperties();
    }
}
