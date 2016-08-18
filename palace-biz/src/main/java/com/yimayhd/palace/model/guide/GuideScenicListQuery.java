package com.yimayhd.palace.model.guide;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicListQuery extends BaseQuery {
    private String scenicName;//景区名称
    private String scenicResourceNum;//景区资源编号
    private int status;// 状态

    public String getScenicName() {
        return scenicName;
    }

    public GuideScenicListQuery setScenicName(String scenicName) {
        this.scenicName = scenicName;
        return this;
    }

    public String getScenicResourceNum() {
        return scenicResourceNum;
    }

    public GuideScenicListQuery setScenicResourceNum(String scenicResourceNum) {
        this.scenicResourceNum = scenicResourceNum;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public GuideScenicListQuery setStatus(int status) {
        this.status = status;
        return this;
    }
}
