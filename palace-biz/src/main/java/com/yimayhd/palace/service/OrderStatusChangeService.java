package com.yimayhd.palace.service;

import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.model.vo.TcMainOrderVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResult;

import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public interface OrderStatusChangeService {
    /**
     * 更改订单状态
     * @param orderStatusChangeParam
     * @return
     */
    public BizResult<String> updateStatus(OrderStatusChangeParam orderStatusChangeParam);

    /**
     * 查询更改状态订单列表
     * @param orderStatusChangeParam
     * @return
     */
    public BizResult<OrderStatusChangeVO>  queryList(OrderStatusChangeParam orderStatusChangeParam);
}
