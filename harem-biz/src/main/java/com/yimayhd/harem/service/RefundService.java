package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Refund;
import com.yimayhd.harem.model.query.RefundListQuery;
import com.yimayhd.tradecenter.client.model.domain.imall.TcRefundRecordDO;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface RefundService {
    /**
     * 获取退款列表(可带查询条件)
     * @return 退款列表
     */
    PageVO<TcRefundRecordDO> getList(RefundListQuery refundListQuery)throws Exception;

    /**
     * 根据id退款信息
     * @param refundId
     * @return 退款信息
     * @throws Exception
     */
    Refund getById(long refundId)throws Exception;

}
