package com.yimayhd.palace.service;

import com.yimayhd.lgcenter.client.domain.ExpressCodeRelationDO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.tradecenter.client.model.param.order.SellerSendGoodsDTO;

import java.util.List;

/**
 * 订单服务接口
 * 
 * @author zhaozhaonan
 *
 */
public interface OrderService {
	/**
	 * 通过ID获取订单
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	OrderDetails getOrderById(long id) throws Exception;

	/**
	 * 获取订单列表
	 * @return
	 * @throws Exception
	 */
	PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception;


	boolean buyerConfirmGoods(long id);

	boolean sellerSendGoods(long id);

	boolean sellerSendGoods(SellerSendGoodsDTO sellerSendGoodsDTO);

	List<ExpressCodeRelationDO> selectAllExpressCode();

	boolean refundOrder(long id);

	boolean closeOrder(long id);



}
