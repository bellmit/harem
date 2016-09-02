package com.yimayhd.palace.model.guide;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by xushubing on 2016/8/18.
 */
public class ScenicListQuery extends BaseQuery {
    private static final long serialVersionUID = -1052349335019169325L;
    private String scenicName;//景区名称

    public String getScenicName() {
        return scenicName;
    }

    public ScenicListQuery setScenicName(String scenicName) {
        this.scenicName = scenicName;
        return this;
    }

    public Long getScenicNumber() {
        return scenicNumber;
    }

    public ScenicListQuery setScenicNumber(Long scenicNumber) {
        this.scenicNumber = scenicNumber;
        return this;
    }

    private Long scenicNumber;//景区资源编号

}
