package com.yimayhd.palace.model.vo;

import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeVO implements Serializable {

    private static final long serialVersionUID = -2531177081102535848L;

    private List<TcMainOrderVO> tcMainOrderVOList;
    private long totalCount ;

    public List<TcMainOrderVO> getTcMainOrderVOList() {
        return tcMainOrderVOList;
    }

    public void setTcMainOrderVOList(List<TcMainOrderVO> tcMainOrderVOList) {
        this.tcMainOrderVOList = tcMainOrderVOList;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
