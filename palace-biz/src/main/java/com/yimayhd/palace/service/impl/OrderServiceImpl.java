package com.yimayhd.palace.service.impl;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.lgcenter.client.dto.TaskInfoRequestDTO;
import com.yimayhd.lgcenter.client.result.BaseResult;
import com.yimayhd.lgcenter.client.service.LgService;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.OrderConverter;
import com.yimayhd.palace.enums.PromotionTypes;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.repo.PayRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.promotion.client.enums.PromotionType;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.PromotionInfo;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.CloseOrderReason;
import com.yimayhd.tradecenter.client.model.enums.MainDetailStatus;
import com.yimayhd.tradecenter.client.model.enums.OrderSourceType;
import com.yimayhd.tradecenter.client.model.param.order.*;
import com.yimayhd.tradecenter.client.model.param.refund.RefundTradeDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.BuyerConfirmGoodsResult;
import com.yimayhd.tradecenter.client.model.result.order.SellerSendGoodsResult;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author zhaozhaonan
 *
 */
public class OrderServiceImpl implements OrderService {
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private TcQueryService tcQueryServiceRef;
	@Autowired
	private TcTradeService tcTradeServiceRef;
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private LgService lgService;
	@Autowired
	private PayRepo payRepo ;

	@Override
	public PageVO<MainOrder> getOrderList(OrderListQuery orderListQuery) throws Exception {
		List<MainOrder> mainOrderList = new ArrayList<MainOrder>();
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
				}else{
					PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(), 0,mainOrderList);
					return orderPageVO;
				}
			}
		}

		OrderQueryDTO orderQueryDTO = OrderConverter.orderListQueryToOrderQueryDTO(orderListQuery,userId);
		if (orderQueryDTO!=null){
			BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrders(orderQueryDTO);
//			System.err.println(JSON.toJSONString(batchQueryResult));
			if (batchQueryResult.isSuccess()){
				//订单信息
				List<BizOrderDO> bizOrderDOList = batchQueryResult.getBizOrderDOList();
				//如果使用名称查询，查询出的全部是子订单，需要把子订单放入父订单中。
				if (!CollectionUtils.isEmpty(bizOrderDOList) && StringUtils.isNotEmpty(orderQueryDTO.getItemName())){
					OrderQueryDTO orderQueryDTOMain = new OrderQueryDTO();
					List<Long> bizOrderIds = new ArrayList<Long>();
					List<BizOrderDO> bizOrderDOListMain = new ArrayList<BizOrderDO>();
					for (BizOrderDO bizOrderDO : bizOrderDOList) {
						//判断是否是主订单
						if(bizOrderDO.getIsMain() == MainDetailStatus.NO.getType()){
							bizOrderIds.add(bizOrderDO.getParentId());
							orderQueryDTOMain.setBizOrderIds(bizOrderIds);
						}else{
							bizOrderDOListMain.add(bizOrderDO);
						}
					}
					if (!CollectionUtils.isEmpty(bizOrderIds)) {
						BatchQueryResult batchQueryResultMain = tcQueryServiceRef.queryOrders(orderQueryDTOMain);
						if (batchQueryResultMain.isSuccess() && !CollectionUtils.isEmpty(batchQueryResultMain.getBizOrderDOList())){
							bizOrderDOListMain.addAll(batchQueryResultMain.getBizOrderDOList());
						}
					}

					if (bizOrderDOListMain.size()>0){
						for (BizOrderDO bizOrderDOMain : bizOrderDOListMain){
							List<BizOrderDO> bizOrderDOTempList = new ArrayList<BizOrderDO>();
							for (BizOrderDO bizOrderDO : bizOrderDOList){
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
			if (!CollectionUtils.isEmpty(list)){
				for (BizOrderDO bizOrderDO : list) {
					//TODO:这里转换的话，需要判断查询条件传过来的订单状态是什么，然后过滤掉相应的数据
					MainOrder mo = OrderConverter.orderVOConverter(bizOrderDO);
					mo = OrderConverter.mainOrderStatusConverter(mo,bizOrderDO);
					//FIXME
					UserDO user = userServiceRef.getUserDOById(bizOrderDO.getBuyerId(), false);
					mo.setUser(user);
					mainOrderList.add(mo);
				}
			}
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(),
					(int)batchQueryResult.getTotalCount(),mainOrderList);
			return orderPageVO;
		}else{
			PageVO<MainOrder> orderPageVO = new PageVO<MainOrder>(orderListQuery.getPageNumber(),orderListQuery.getPageSize(),
					0,mainOrderList);
			return orderPageVO;
		}

	}

	@Override
	public OrderDetails getOrderById(long id) throws Exception {
		OrderQueryOption orderQueryOption = new OrderQueryOption();
		orderQueryOption.setAll();
		orderQueryOption.setNeedLogisticsOrder(true);
		orderQueryOption.setNeedExtFeature(true);
		try {
			SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(id,orderQueryOption);
			if (singleQueryResult.isSuccess()){
				OrderDetails orderDetails = new OrderDetails();
				MainOrder mainOrder = OrderConverter.orderVOConverter(singleQueryResult.getBizOrderDO());
				mainOrder = OrderConverter.mainOrderStatusConverter(mainOrder,singleQueryResult.getBizOrderDO());
				if (mainOrder!=null){
					if (singleQueryResult.getLogisticsOrderDO()!=null){
						mainOrder.setLogisticsOrderDO(singleQueryResult.getLogisticsOrderDO());
					}
					orderDetails.setMainOrder(mainOrder);
				}
				if (mainOrder.getBizOrderDO()!=null){
					long buyerId = mainOrder.getBizOrderDO().getBuyerId();
					UserDO buyer = userServiceRef.getUserDOById(buyerId, false);
					orderDetails.setBuyerName(null ==buyer ? "":buyer.getName());
					orderDetails.setBuyerNiceName(null ==buyer ? "":buyer.getNickname());
					orderDetails.setBuyerPhoneNum(null ==buyer ? "":buyer.getMobileNo());
					//订单来源
					int payChannel = BizOrderUtil.getInt(mainOrder.getBizOrderDO(), BizOrderFeatureKey.ORDER_SOURCE);
					if (payChannel == OrderSourceType.PC.getBizType()){
						orderDetails.setOrderFrom(OrderSourceType.PC.getDes());
					}else if (payChannel == OrderSourceType.MOBILE.getBizType()){
						orderDetails.setOrderFrom(OrderSourceType.MOBILE.getDes());
					}else if(payChannel == OrderSourceType.OFFLINE.getBizType()){
						orderDetails.setOrderFrom(OrderSourceType.OFFLINE.getDes());
					}
					//付款方式
					orderDetails.setPayChannel(BizOrderUtil.getString(mainOrder.getBizOrderDO(), BizOrderFeatureKey.PAY_CHANNEL));
				}

				if (singleQueryResult.getPayOrderDO()!=null){
					orderDetails.setTotalFee(singleQueryResult.getPayOrderDO().getTotalFee());
					orderDetails.setActualTotalFee(singleQueryResult.getPayOrderDO().getActualTotalFee());
				}

				//参加人
				List<ContactUser> contactUserList = BizOrderUtil.getCheckInUserList(mainOrder.getBizOrderDO());
				if (!CollectionUtils.isEmpty(contactUserList)){
					orderDetails.setTourists(contactUserList);
				}
				//联系人
				ContactUser contactUser = BizOrderUtil.getContactUser(singleQueryResult.getBizOrderDO());
				String email = BizOrderUtil.getLineContactEmail(singleQueryResult.getBizOrderDO());
				if (contactUser!=null){
					contactUser.setContactEmail(email);
					orderDetails.setContacts(contactUser);
				}
				//买家备忘录
				String buyerMemo = BizOrderUtil.getBuyerMemo(mainOrder.getBizOrderDO());
				orderDetails.setBuyerMemo(buyerMemo);

				//关闭原因
				int closeReasonId = BizOrderUtil.getCloseReasonId(mainOrder.getBizOrderDO());
				if (closeReasonId!=0){
					String closeReason = CloseOrderReason.getReasonByType(closeReasonId).getDesc();
					orderDetails.setCloseReason(closeReason);
				}

				//确认、发货时间
				LogisticsOrderDO logisticsOrderDO = singleQueryResult.getLogisticsOrderDO();
				if (logisticsOrderDO!=null){
					Date consignTime = logisticsOrderDO.getConsignTime();
					if (consignTime!=null){
						orderDetails.setConsignTime(consignTime);
					}
				}
				
				BizOrderDO bizOrderDO = mainOrder.getBizOrderDO() ;
				long bizOrderId = bizOrderDO.getBizOrderId() ;
				int domainId = bizOrderDO.getDomain() ;
				BizResult<PayOrderDO> queryPayOrderResult = payRepo.getPayOrderList(bizOrderId, domainId);
				if( queryPayOrderResult != null && queryPayOrderResult.isSuccess() ){
					PayOrderDO payOrderDO = queryPayOrderResult.getValue();
					mainOrder.setPayOrderDO(payOrderDO);
				}

				//物流信息
				LogisticsOrderDO log = mainOrder.getLogisticsOrderDO();
				if(null != log && StringUtils.isNotEmpty(log.getExpressNo()) ){
					TaskInfoRequestDTO taskInfoRequestDTO = new TaskInfoRequestDTO();
					taskInfoRequestDTO.setNumber(log.getExpressNo());
					taskInfoRequestDTO.setCompany(StringUtils.isEmpty(log.getExpressCompany()) ? "" : log.getExpressCompany());
					BaseResult<ExpressVO> lgResult =  lgService.getLogisticsInfo(taskInfoRequestDTO);
					if (lgResult.isSuccess() && lgResult.getValue()!=null){
						orderDetails.setExpress(lgResult.getValue());
					}
				}
				//优惠
				long orderPromotionFee = BizOrderUtil.getOrderPromotionFee(mainOrder.getBizOrderDO());
				long orderVoucherFee = BizOrderUtil.getOrderVoucherFee(mainOrder.getBizOrderDO());
				orderDetails.setOrderPromotionFee(orderPromotionFee);
				orderDetails.setOrderVoucherFee(orderVoucherFee);
				PromotionInfo promotionInfo = BizOrderUtil.getPromotionInfo(mainOrder.getBizOrderDO());
				if(null != promotionInfo){
					orderDetails.setOrderPromotionInfo(promotionInfo);
					PromotionTypes orderPromotion = PromotionTypes.getByType(promotionInfo.getType());
					if(null != orderPromotion){//只针对全场满减的
						String desc = orderPromotion.getDesc() + NumUtil.moneyTransform( promotionInfo.getRequirement() )
								+ orderPromotion.getDesc_suffix() + NumUtil.moneyTransform( promotionInfo.getValue() );
						orderDetails.setPromotionInfoDesc(desc);
					}
				}

				return orderDetails;
			}
		}catch (Exception e){
			e.printStackTrace();
			log.error("public OrderDetails getOrderById(long id);" + e);
			return null;
		}

		return null;
	}


	//完成
	@Override
	public boolean buyerConfirmGoods(long id) {
		BuyerConfirmGoodsDTO buyerConfirmGoodsDTO = new BuyerConfirmGoodsDTO();
		buyerConfirmGoodsDTO.setBizOrderId(id);
		try {
			BuyerConfirmGoodsResult buyerConfirmGoodsResult = tcTradeServiceRef.buyerConfirmGoods(buyerConfirmGoodsDTO);
			return buyerConfirmGoodsResult.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.buyerConfirmGoods(buyerConfirmGoodsDTO);" + e);
			return false;
		}
	}


	//发货--确认/
	@Override
	public boolean sellerSendGoods(long id) {
		SellerSendGoodsDTO sellerSendGoodsDTO = new SellerSendGoodsDTO();
		sellerSendGoodsDTO.setBizOrderId(id);
		try {
			SellerSendGoodsResult sellerSendGoodsResult = tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);
			return sellerSendGoodsResult.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.sellerSendGoods(sellerSendGoodsDTO);" + e);
			return false;
		}

	}

	//退款
	@Override
	public boolean refundOrder(long id) {
		RefundTradeDTO refundTradeDTO = new RefundTradeDTO();
		refundTradeDTO.setBizOrderId(id);
		try {
			ResultSupport resultSupport = tcTradeServiceRef.refundOrder(refundTradeDTO);
			return resultSupport.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.refundOrder(refundTradeDTO);" + e);
			return false;
		}
	}


	@Override
	public boolean closeOrder(long id) {
		try {
			CloseOrderDTO closeOrderDTO = new CloseOrderDTO();
			closeOrderDTO.setBizOrderId(id);
			closeOrderDTO.setCloseOrderReasonId(CloseOrderReason.CLOSE_BY_ADMIN.getType());
			ResultSupport resultSupport = tcTradeServiceRef.closeOrder(closeOrderDTO);
			return resultSupport.isSuccess();
		}catch (Exception e){
			log.error("tcTradeServiceRef.closeOrder(id);" + e);
			return false;
		}
	}
}
