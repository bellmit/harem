package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.Order;

/**
 * Created by Administrator on 2015/10/27.
 */
public interface OrderService {
    Order getById(long id)throws Exception;
    /**
     * 获取活动订单列表(可带查询条件)
     * @return 活动订单
     */
	List<Order> getActivityOrderList(Order order)throws Exception;

   
}
