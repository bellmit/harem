package com.yimayhd.palace.model.attachment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xushubing on 2016/8/18.
 */
public class AttachmentVO {
    private long id;
    private String inputFileTitle;//名称
    private String fileType;//类型
    private Date gmtCreated;
    private BigDecimal duration;//时长
    private String durationStr;//时长
    private int scope;//用途
    private String scopeStr;//用途
    private String remark;//备注
    private int status;//状态
    private String statusStr;//状态
    private String remoteUrl;//播放地址


    public long getId() {
        return id;
    }

    public AttachmentVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getInputFileTitle() {
        return inputFileTitle;
    }

    public AttachmentVO setInputFileTitle(String inputFileTitle) {
        this.inputFileTitle = inputFileTitle;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public AttachmentVO setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public AttachmentVO setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
        return this;
    }



    public String getRemark() {
        return remark;
    }

    public AttachmentVO setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public AttachmentVO setDuration(BigDecimal duration) {
        this.duration = duration;
        return this;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public AttachmentVO setDurationStr(String durationStr) {
        this.durationStr = durationStr;
        return this;
    }

    public int getScope() {
        return scope;
    }

    public AttachmentVO setScope(int scope) {
        this.scope = scope;
        return this;
    }

    public String getScopeStr() {
        return scopeStr;
    }

    public AttachmentVO setScopeStr(String scopeStr) {
        this.scopeStr = scopeStr;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public AttachmentVO setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public AttachmentVO setStatusStr(String statusStr) {
        this.statusStr = statusStr;
        return this;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public AttachmentVO setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
        return this;
    }
}
