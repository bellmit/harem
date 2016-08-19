package com.yimayhd.palace.service;

import com.yimayhd.palace.model.item.IcMerchantVO;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.result.BatchJiuxiuOrderResult;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.user.client.result.BaseResult;

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
	BatchJiuxiuOrderResult getOrderList(JiuxiuOrderListQuery jiuxiuOrderListQuery)
			throws Exception;

	/**
	 * 获取商户信息
	 * @param sellerId
	 * @return
     */
	public BaseResult<TcMerchantInfo> getTcMerchantInfo(long sellerId);
}
