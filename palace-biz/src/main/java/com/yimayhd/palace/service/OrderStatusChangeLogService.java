package com.yimayhd.palace.service;

import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogQuery;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogResult;
import com.yimayhd.palace.result.BizResult;

/**
 * Created by wangdi on 16/10/12.
 */
public interface OrderStatusChangeLogService {

    /**
     * 查询 列表
     * @param query
     * @return
     */
    public BizResult<OrderStatusChangeLogResult> queryOrderStatusChangeLogList(OrderStatusChangeLogQuery query);


}
