package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Refund;
import com.yimayhd.harem.model.query.RefundListQuery;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface RefundService {

    /**
     * 获取退款列表(可带查询条件)
     * @param sellerId 商家ID
     * @param refundListQuery 查询条件
     * @return
     * @throws Exception
     */
    PageVO<IMallRefundRecordDO> getList(long sellerId,RefundListQuery refundListQuery)throws Exception;

    /**
     * 导出退款列表
     * @param sellerId 商家ID
     * @param refundListQuery
     * @return
     */
    List<IMallRefundRecordDO> exportRefundList(long sellerId,RefundListQuery refundListQuery)throws Exception;

}
