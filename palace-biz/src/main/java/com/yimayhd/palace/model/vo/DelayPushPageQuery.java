package com.yimayhd.palace.model.vo;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.resourcecenter.model.query.PageQuery;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuxp on 2016/8/19.
 */
public class DelayPushPageQuery extends BaseQuery implements Serializable {

    private static final long serialVersionUID = 1246741057676218638L;

    /**
     * 话题名称
     */
    private String topicName;

    public void setSended(int sended) {
        this.sended = sended;
    }

    /**
     * 发起时间开始

     */
    private Date starteTime;

    /**
     * 发起时间截止
     */
    private Date endTime;

    /**
     * 发送对象
     */
    private int sendType;

    /**
     * 状态
     */
    private int status;

    /**
     * 短信or推送
     */
    private int type;

    private int sended;

    private Date currentDate;

    public int getSended() {
        return sended;
    }

    public Date getCurrentDate() {
        return new Date();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Date getStarteTime() {
        return starteTime;
    }

    public void setStarteTime(Date starteTime) {
        this.starteTime = starteTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
