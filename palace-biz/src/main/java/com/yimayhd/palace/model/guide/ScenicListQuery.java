package com.yimayhd.palace.model.guide;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created by liuxp on 2016/8/22.
 */
public class ScenicListQuery extends BaseQuery{

    private String scenicName;

    private long scenicNum;

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public long getScenicNum() {
        return scenicNum;
    }

    public void setScenicNum(long scenicNum) {
        this.scenicNum = scenicNum;
    }
}
