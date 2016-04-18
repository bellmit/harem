package com.yimayhd.palace.service;

import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;

/**
 * 订单服务接口
 * 
 * @author 
 *
 */
public interface JiuxiuOrderService {

	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	BatchBizQueryResult getOrderList(JiuxiuOrderListQuery jiuxiuOrderListQuery)
			throws Exception;
}
