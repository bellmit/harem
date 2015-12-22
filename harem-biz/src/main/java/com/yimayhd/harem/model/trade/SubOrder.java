package com.yimayhd.harem.model.trade;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class SubOrder {
    private BizOrderDO bizOrderDO;

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public SubOrder(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }
}
