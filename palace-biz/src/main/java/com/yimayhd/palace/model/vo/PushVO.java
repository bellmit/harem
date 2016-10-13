package com.yimayhd.palace.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description 推送对象
 */
public class PushVO implements Serializable{
    private int domain;
    private String subject;//主题
    private int pushType;//推送类型
    private String msgTitle;//消息栏标题
    private String msgContent;//消息栏内容
    public String pushContent;//推送内容
    public String operation;//跳转类型
    public String operationContent;//跳转内容
    public int pushModelType;//推广对象类型
    public String pushModelFilePath;//特定的推广对象文件地址
    public String pushDate;//推送时间
    public Date createDate;
    public Date updateDate;


    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperationContent() {
        return operationContent;
    }

    public void setOperationContent(String operationContent) {
        this.operationContent = operationContent;
    }

    public int getPushModelType() {
        return pushModelType;
    }

    public void setPushModelType(int pushModelType) {
        this.pushModelType = pushModelType;
    }

    public String getPushModelFilePath() {
        return pushModelFilePath;
    }

    public void setPushModelFilePath(String pushModelFilePath) {
        this.pushModelFilePath = pushModelFilePath;
    }

    public String getPushDate() {
        return pushDate;
    }

    public void setPushDate(String pushDate) {
        this.pushDate = pushDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
