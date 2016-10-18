package com.yimayhd.palace.model.vo;

import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.resourcecenter.model.enums.RcDelaySendTargetType;
import com.yimayhd.resourcecenter.model.enums.RcDelayStatus;
import com.yimayhd.resourcecenter.model.enums.ShowcaseFeatureKey;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description 推送对象
 */
public class PushVO implements Serializable{
    private static final long serialVersionUID = -4381015314038648525L;
    private int domain;
    private String subject;//主题
    private int pushType;//推送类型
    private String msgTitle;//消息栏标题
    private String msgContent;//消息栏内容
    private String pushContent;//推送内容
    private String operation;//跳转类型
    private String operationContent;//跳转内容
    private int pushModelType;//推广对象类型
    private String pushModelFilePath;//特定的推广对象文件地址
    private Date pushDate;//推送时间
    private int status;//推送状态
    private long operationUserId;//操作人ID
    private long sendPeople;//发送人数
    private long seePeople;//查看人数
    private Date createDate;
    private Date updateDate;
    private long id; //主键
    private String outId; // 关联id
    private String transformFileUrl;//转换后的文件地址
    private long sendDomainId;
    private Map<String, String> feature;

    private String statusStr;
    private String pushModelTypeStr;
    private String selectOpContent;//选中回显
    private String operationDetailId;//

    public String getPushModelTypeStr() {
        RcDelaySendTargetType rcDelaySendTargetType = getSendTargetTypeByValue(pushModelType);
        if(rcDelaySendTargetType==null){
            return null;
        }
        return rcDelaySendTargetType.getDesc();
    }

    public void setPushModelTypeStr(String pushModelTypeStr) {
        this.pushModelTypeStr = pushModelTypeStr;
    }

    public String getStatusStr() {
        RcDelayStatus rcDelayStatus = getStatusByValue(status);
        if (rcDelayStatus == null) {
            return null;
        }
        if(RcDelayStatus.CANCEL.getCode()!=status) {
            if (getPushDate() != null&&getPushDate().before(new Date())) {
                setStatus(RcDelayStatus.PUSHED.getCode());
                return RcDelayStatus.PUSHED.getDesc();
            }
        }
        return rcDelayStatus.getDesc();
    }
    private static RcDelaySendTargetType getSendTargetTypeByValue(int status) {
        for (RcDelaySendTargetType rcDelaySendTargetType : RcDelaySendTargetType.values()) {
            if (rcDelaySendTargetType.getCode() == status) {
                return rcDelaySendTargetType;
            }
        }
        return null;
    }
    private static RcDelayStatus getStatusByValue(int status) {
        for (RcDelayStatus rcDelayStatus : RcDelayStatus.values()) {
            if (rcDelayStatus.getCode() == status) {
                return rcDelayStatus;
            }
        }
        return null;
    }
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getFeature() {
        return com.yimayhd.resourcecenter.util.FeatureUtil.toString(feature);
    }

    public void setFeature(String featureString) {
        this.feature = com.yimayhd.resourcecenter.util.FeatureUtil.fromString(featureString);
    }

    public void addFeature(ShowcaseFeatureKey showcaseFeatureKey, String value) {
        if (showcaseFeatureKey == null || StringUtils.isBlank(value)) {
            return;
        }
        if (feature == null) {
            feature = new HashMap<String, String>();
        }
        feature.put(showcaseFeatureKey.getCode(), value);
    }

    public String getRcFeature(ShowcaseFeatureKey showcaseFeatureKey) {
        if (feature == null || showcaseFeatureKey == null) {
            return null;
        }
        return feature.get(showcaseFeatureKey.getCode());
    }




    public String getOperationDetailId() {
        return operationDetailId;
    }

    public void setOperationDetailId(String operationDetailId) {
        this.operationDetailId = operationDetailId;
    }

    public String getSelectOpContent() {
        return selectOpContent;
    }

    public void setSelectOpContent(String selectOpContent) {
        this.selectOpContent = selectOpContent;
    }

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

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(long operationUserId) {
        this.operationUserId = operationUserId;
    }

    public long getSendPeople() {
        return sendPeople;
    }

    public void setSendPeople(long sendPeople) {
        this.sendPeople = sendPeople;
    }

    public long getSeePeople() {
        return seePeople;
    }

    public void setSeePeople(long seePeople) {
        this.seePeople = seePeople;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getTransformFileUrl() {
        return transformFileUrl;
    }

    public void setTransformFileUrl(String transformFileUrl) {
        this.transformFileUrl = transformFileUrl;
    }

    public long getSendDomainId() {
        return sendDomainId;
    }

    public void setSendDomainId(long sendDomainId) {
        this.sendDomainId = sendDomainId;
    }
}
