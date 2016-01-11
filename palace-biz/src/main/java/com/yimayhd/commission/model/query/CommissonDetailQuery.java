package com.yimayhd.commission.model.query;

import com.yimayhd.palace.base.BaseQuery;

/**
 * Created with IntelliJ IDEA.
 * User: zhaoyue
 * Date: 2016/1/11
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
public class CommissonDetailQuery extends BaseQuery {


    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
