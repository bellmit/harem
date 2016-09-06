package com.yimayhd.palace.model.attachment;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.util.DateUtil;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * Created by xushubing on 2016/8/18.
 */
public class AttachmentListQuery extends BaseQuery {
    /**
     * 文件名称
     */
    private String inputFileTitle;

    /**
     * 30:mp3 40:mp4,所属表字段为rc_media.file_type
     */
    private int fileType;

    /**
     * 用途,10:全部;20:导览;30:H5,所属表字段为rc_media.scope
     */
    private int scope;

    /**
     * 状态,10:上架;20:下架,所属表字段为rc_media.status
     */
    private int status;

    /**
     * 发起时间开始
     */
    private Date startTime;
    private String startTimeStr;

    /**
     * 发起时间截止
     */
    private Date endTime;
    private String endTimeStr;

    private String remark;//备注

    private int multi;//1 单选 2 多选

    public int getMulti() {
        return multi;
    }

    public AttachmentListQuery setMulti(int multi) {
        this.multi = multi;
        return this;
    }

    public String getStartTimeStr() {
        if (startTime != null) {
            startTimeStr = DateUtil.formatDate(startTime);
        }
        return startTimeStr;
    }

    public AttachmentListQuery setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
        return this;
    }

    public String getEndTimeStr() {
        if (endTime != null) {
            endTimeStr = DateUtil.formatDate(endTime);
        }
        return endTimeStr;
    }

    public AttachmentListQuery setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
        return this;
    }

    public String getInputFileTitle() {
        return inputFileTitle;
    }

    public void setInputFileTitle(String inputFileTitle) {
        this.inputFileTitle = inputFileTitle;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getStartTime() {
        if (startTimeStr != null) {
            try {
                startTime = DateUtils.parseDate(startTimeStr, Locale.CHINA, DateUtil.DATE_TIME_FORMAT);
            } catch (ParseException e) {
                return null;
            }
        }
        return startTime;
    }

    public AttachmentListQuery setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        if (endTimeStr != null) {
            try {
                endTime = DateUtils.parseDate(endTimeStr, Locale.CHINA, DateUtil.DATE_TIME_FORMAT);
            } catch (ParseException e) {
                return null;
            }
        }
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
