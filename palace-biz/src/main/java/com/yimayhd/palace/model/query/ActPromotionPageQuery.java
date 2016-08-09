package com.yimayhd.palace.model.query;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageQuery;

/**
 * Created by Administrator on 2016/3/23.
 */
public class ActPromotionPageQuery extends BaseQuery {
    private String title;
    private int status;
    private int type;
    private String beginTime;
    private String endTime;
    private int lotteryType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(int lotteryType) {
        this.lotteryType = lotteryType;
    }
}
