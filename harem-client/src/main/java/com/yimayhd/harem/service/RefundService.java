package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Refund;

import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface RefundService {
    /**
     * 获取退款列表(可带查询条件)
     * @return 退款列表
     */
    List<Refund> getList(Refund refund)throws Exception;

    /**
     * 根据id退款信息
     * @param refundId
     * @return 退款信息
     * @throws Exception
     */
    Refund getById(long refundId)throws Exception;

    /**
     * 根据退款金额计算退款信息
     * @param refundMoney
     * @return 退款信息
     * @throws Exception
     */
    Refund getRefundDataByRefundMoney(double refundMoney)throws Exception;

    /**
     * 提交退款
     * @param refund 退款信息
     * @throws Exception
     */
    void addRefund(Refund refund)throws Exception;
}
