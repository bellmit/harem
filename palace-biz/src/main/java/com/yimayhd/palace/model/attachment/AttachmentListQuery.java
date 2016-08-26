package com.yimayhd.palace.model.attachment;

import com.yimayhd.palace.base.BaseQuery;

import java.util.Date;

/**
 * Created by xushubing on 2016/8/18.
 */
public class AttachmentListQuery extends BaseQuery {
    /**
     * 文件名称
     */
    private String inputFileName;

    /**
     *  30:mp3 40:mp4,所属表字段为rc_media.file_type
     */
    private int fileType;

    /**
     *  用途,10:全部;20:导览;30:H5,所属表字段为rc_media.scope
     */
    private int scope;

    /**
     *  状态,10:上架;20:下架,所属表字段为rc_media.status
     */
    private int status;

    /**
     *  发起时间开始
     */
    private Date starteTime;

    /**
     *  发起时间截止
     */
    private Date endTime;

    private String remark;//备注

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
