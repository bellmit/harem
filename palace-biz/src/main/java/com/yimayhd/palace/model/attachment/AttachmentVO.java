package com.yimayhd.palace.model.attachment;

import com.yimayhd.resourcecenter.model.enums.MediaFileScope;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.enums.MediaFileType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xushubing on 2016/8/18.
 */
public class AttachmentVO {
    private long id;
    private String inputFileTitle;//名称
    private int fileType;//类型
    private String fileTypeStr;//类型
    private Date gmtCreated;
    private long duration;//时长
    private String durationStr;//时长
    private int scope;//用途
    private String scopeStr;//用途
    private String remark;//备注
    private int status;//状态
    private String statusStr;//状态
    private String remoteUrl;//播放地址

    private String fileKey;//

    private int minute;
    private int second;
    private int minuteVo;
    private int secondVo;
    private long fsize;

    private double fsizeM;//文件大小 M单位


    public double getFsizeM() {
        BigDecimal bigDecimal = new BigDecimal((double)fsize);
        BigDecimal bigDecima2 = BigDecimal.valueOf(1024);
        return bigDecimal.divide(bigDecima2,2,BigDecimal.ROUND_HALF_UP).divide(bigDecima2,2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public AttachmentVO setFsizeM(double fsizeM) {
        this.fsizeM = fsizeM;
        return this;
    }

    public long getFsize() {
        return fsize;
    }

    public AttachmentVO setFsize(long fsize) {
        this.fsize = fsize;
        return this;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public int getMinuteVo() {
        return (int)duration/60;
    }

    public void setMinuteVo(int minuteVo) {
        this.minuteVo = minuteVo;
    }

    public int getSecondVo() {
        return (int)duration%60;
    }

    public void setSecondVo(int secondVo) {
        this.secondVo = secondVo;
    }

    public int getMinute() {

        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

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

    public int getFileType() {
        return fileType;
    }

    public AttachmentVO setFileType(int fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFileTypeStr() {
        if (fileType <= 0) {
            return null;
        }
        MediaFileType mediaFileType = MediaFileType.getByValue(fileType);
        if (mediaFileType == null) {
            return null;
        }
        return mediaFileType.getDesc();
    }

    public AttachmentVO setFileTypeStr(String fileTypeStr) {
        this.fileTypeStr = fileTypeStr;
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

    public long getDuration() {
        return duration;
    }

    public AttachmentVO setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public String getDurationStr() {
        if (duration <= 0) {
            return null;
        }
        return duration / 60 + "分" + duration % 60 + "秒";
        // return durationStr;
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
        MediaFileScope mediaFileScope = MediaFileScope.getByValue(scope);
        if (mediaFileScope == null) {
            return null;
        }
        return mediaFileScope.getDesc();
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
        MediaFileStatus mediaFileStatus = MediaFileStatus.getByValue(status);
        if (mediaFileStatus == null) {
            return null;
        }
        return mediaFileStatus.getDesc();
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
