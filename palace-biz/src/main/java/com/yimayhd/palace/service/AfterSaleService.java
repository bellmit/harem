package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.refund.client.query.RefundOrderQuery;
import com.yimayhd.refund.client.result.BaseResult;
import com.yimayhd.refund.client.result.refundorder.RefundOrderPageQueryResult;

/**
 * @author create by yushengwei on 2016/3/25
 * @Description
 * @return $returns
 */
public interface AfterSaleService {

    PageVO<RefundOrderDO> queryRefundOrder(RefundOrderQuery var1);

    RefundOrderDO querySingRefundOrder(long refundOrderId);


}
