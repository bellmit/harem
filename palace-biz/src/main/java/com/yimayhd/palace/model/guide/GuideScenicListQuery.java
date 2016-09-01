package com.yimayhd.palace.model.guide;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicListQuery extends BaseQuery {
    private static final long serialVersionUID = 2350825712224763525L;
    private String scenicName;//景区名称
    private Long scenicResourceNum;//景区资源编号
    private int status = -1;// 状态
    private long guideId;//导览id
    private String guideName;//导览名称

    public long getGuideId() {
        return guideId;
    }

    public void setGuideId(long guideId) {
        this.guideId = guideId;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getScenicName() {
        return scenicName;
    }

    public GuideScenicListQuery setScenicName(String scenicName) {
        this.scenicName = scenicName;
        return this;
    }

    public Long getScenicResourceNum() {
        return scenicResourceNum;
    }

    public GuideScenicListQuery setScenicResourceNum(Long scenicResourceNum) {
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
