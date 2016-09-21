package com.yimayhd.palace.model.guide;

/**
 * Created by xushubing on 2016/9/2.
 */
public class GuideFocusVO {
    private long id;
    private String inputFileTitle;//名称
    private long duration;//时长
    private String durationStr;//时长
    private String fileKey;//
    private String focusOrder;

    public String getFocusOrder() {
        return focusOrder;
    }

    public GuideFocusVO setFocusOrder(String focusOrder) {
        this.focusOrder = focusOrder;
        return this;
    }

    public long getId() {
        return id;
    }

    public GuideFocusVO setId(long id) {
        this.id = id;
        return this;
    }

    public String getInputFileTitle() {
        return inputFileTitle;
    }

    public GuideFocusVO setInputFileTitle(String inputFileTitle) {
        this.inputFileTitle = inputFileTitle;
        return this;
    }

    public long getDuration() {
        return duration;
    }

    public GuideFocusVO setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public GuideFocusVO setDurationStr(String durationStr) {
        this.durationStr = durationStr;
        return this;
    }

    public String getFileKey() {
        return fileKey;
    }

    public GuideFocusVO setFileKey(String fileKey) {
        this.fileKey = fileKey;
        return this;
    }
}
