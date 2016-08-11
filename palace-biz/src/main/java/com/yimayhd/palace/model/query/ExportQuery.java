package com.yimayhd.palace.model.query;

import java.io.Serializable;
import java.util.Set;

/**
 * @author create by yushengwei on 2016/7/18
 * @Description:导出excel的查询条件
 */
public class ExportQuery extends OrderListQuery implements Serializable{
    private Set<Integer> ids;
    public boolean resolve = false;//强制的下载

    public boolean isResolve() {
        return resolve;
    }

    public void setResolve(boolean resolve) {
        this.resolve = resolve;
    }

    public Set<Integer> getIds() {
        return ids;
    }
    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }
}
