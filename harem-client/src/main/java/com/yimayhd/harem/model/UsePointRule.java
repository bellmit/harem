package com.yimayhd.harem.model;

import com.yimayhd.harem.base.BaseModel;

/**
 * Created by Administrator on 2015/11/9.
 */
public class UsePointRule extends BaseModel {
    private Long ratio;//比率（人民币/积分）；

    public Long getRatio() {
        return ratio;
    }

    public void setRatio(Long ratio) {
        this.ratio = ratio;
    }
}
