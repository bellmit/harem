package com.yimayhd.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseQuery implements Serializable {


    private static final long serialVersionUID = 7184354135734117464L;


    /**
     * 开始时间
     */
    private Date beginDate;

    /**
     * 结束时间
     */
    private Date endDate;

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) throws ParseException {
        if(beginDate != null){
            beginDate = this.dateFormat(beginDate,true);
        }
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) throws ParseException {
        if(endDate != null){
            endDate = this.dateFormat(endDate,false);
        }
        this.endDate = endDate;
    }

    /**
     * 开始日期格式化为"yyyy-MM-dd 00-00-00" 结束格式化为"yyyy-MM-dd 23-59-59"
     * @param date
     * @param isBegin 是否是开始时间
     * @return
     * @throws ParseException
     */
    public Date dateFormat(Date date,boolean isBegin) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dfNew = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        if(isBegin){
            date = dfNew.parse(df.format(date) + " 00-00-00");
        } else{
            date = dfNew.parse(df.format(date) + " 23-59-59");
        }
        return date;
    }

}
