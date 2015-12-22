package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.convert.OrderConverter;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author yebin, zhaozhaonan
 *
 */
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TcQueryService tcQueryServiceRef;
	@Autowired
	private UserService userServiceRef;

	@Override
	public PageVO<BizOrderDO> getOrderList(OrderListQuery orderListQuery) throws Exception {
		long userId = 0;
		List<BizOrderDO> list = null;
		if (StringUtils.isNotEmpty(orderListQuery.getBuyerName()) || StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())){
			UserDOPageQuery userDOPageQuery = new UserDOPageQuery();
			userDOPageQuery.setPageNo(1);
			userDOPageQuery.setPageSize(1);
			if (StringUtils.isNotEmpty(orderListQuery.getBuyerName())){
				userDOPageQuery.setNickname(orderListQuery.getBuyerName());
			}
			if (StringUtils.isNotEmpty(orderListQuery.getBuyerPhone())){
				userDOPageQuery.setMobile(orderListQuery.getBuyerPhone());
			}
			BasePageResult<UserDO> basePageResult = userServiceRef.findPageResultByCondition(userDOPageQuery);
			if (basePageResult.isSuccess()){
				if (!CollectionUtils.isEmpty(basePageResult.getList())){
					UserDO userDO = basePageResult.getList().get(0);
					userId = userDO.getId();
				}
			}

		}

		OrderQueryDTO orderQueryDTO = OrderConverter.orderListQueryToOrderQueryDTO(orderListQuery,userId);
		BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrders(orderQueryDTO);
		if (batchQueryResult.isSuccess()){
			//订单信息
			List<BizOrderDO> bizOrderDOList = batchQueryResult.getBizOrderDOList();
			if (!CollectionUtils.isEmpty(bizOrderDOList) && StringUtils.isNotEmpty(orderQueryDTO.getItemName())){
				OrderQueryDTO orderQueryDTOMain = new OrderQueryDTO();
				List<Long> bizOrderIds = new ArrayList<Long>();
				List<BizOrderDO> bizOrderDOListMain = new ArrayList<BizOrderDO>();
				for (BizOrderDO bizOrderDO : bizOrderDOList) {
					if(bizOrderDO.getIsMain() == MainDetailStatus.NO.getType()){
						bizOrderIds.add(bizOrderDO.getParentId());
						orderQueryDTOMain.setBizOrderIds(bizOrderIds);
						BatchQueryResult batchQueryResultMain = tcQueryServiceRef.queryOrders(orderQueryDTO);
						if (batchQueryResultMain.isSuccess() && !CollectionUtils.isEmpty(batchQueryResultMain.getBizOrderDOList())){
							bizOrderDOListMain.addAll(batchQueryResultMain.getBizOrderDOList());
						}
					}
				}

				if (bizOrderDOListMain.size()>0){
					for (BizOrderDO bizOrderDOMain : bizOrderDOListMain){
						List<BizOrderDO> bizOrderDOTempList = new ArrayList<BizOrderDO>();
						for (BizOrderDO bizOrderDO :bizOrderDOList){
							if (bizOrderDO.getParentId() == bizOrderDOMain.getBizOrderId()){
								bizOrderDOTempList.add(bizOrderDO);
							}
						}
						bizOrderDOMain.setDetailOrderList(bizOrderDOTempList);
					}
				}
				list = bizOrderDOListMain;
			}else{
				list = bizOrderDOList;
			}
		}


		PageVO<BizOrderDO> orderPageVO = new PageVO<BizOrderDO>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(),
				(int)batchQueryResult.getTotalCount(),list);
		return orderPageVO;
	}

	@Override
	public Order getOrderById(long id) throws Exception {
//		SingleQueryResult singleQueryResult = orderRepo.queryOrderSingle(orderListQuery.getOrderNO());
//		if (singleQueryResult.isSuccess()){
//			singleQueryResult.getBizOrderDO();
//		}
		return null;
	}

}
