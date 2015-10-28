package com.yimayhd.service;

import com.yimayhd.model.Refund;

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
}
