package com.yimayhd.harem.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseQuery implements Serializable {


    private static final long serialVersionUID = 7184354135734117464L;


    /**
     * 开始时间
     */
    private String beginDate;

    /**
     * 结束时间
     */
    private String endDate;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



}
