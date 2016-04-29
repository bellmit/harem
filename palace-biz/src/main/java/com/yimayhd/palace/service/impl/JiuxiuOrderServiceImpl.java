package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcBizOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcDetailOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcMainOrder;
import com.yimayhd.palace.model.jiuxiu.helper.JiuxiuHelper;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.result.BatchJiuxiuOrderResult;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.palace.util.Common;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.tradecenter.client.model.param.order.*;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单管理实现
 * 
 * @author 
 *
 */
public class JiuxiuOrderServiceImpl implements JiuxiuOrderService {
	private static final Logger log = LoggerFactory.getLogger(JiuxiuOrderServiceImpl.class);
	@Autowired
    private SessionManager sessionManager;
	@Autowired
	private TcBizQueryService tcBizQueryServiceRef;
	@Autowired
	private UserService userServiceRef;

	@Override
	public BatchJiuxiuOrderResult getOrderList(JiuxiuOrderListQuery jiuxiuOrderListQuery) throws Exception {
		OrderQueryDTO dto = new OrderQueryDTO();
		dto.setDomain(Constant.DOMAIN_JIUXIU);
		dto.setPageNo(jiuxiuOrderListQuery.getPageNumber());
		dto.setPageSize(jiuxiuOrderListQuery.getPageSize());
//		dto.setSellerId(sessionManager.getUserId());
		//商品类型
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getItemType())){
			dto.setOrderBizTypes(new int[]{Integer.parseInt(jiuxiuOrderListQuery.getItemType())});
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getItemName())){
			dto.setItemName(jiuxiuOrderListQuery.getItemName());
		}
		
		//订单编号
		List<Long> bizOrderIds = new ArrayList<Long>();
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getOrderNO()) && Common.regularMatches("[0-9]{1,}", jiuxiuOrderListQuery.getOrderNO().trim())){
			bizOrderIds.add(Long.parseLong(jiuxiuOrderListQuery.getOrderNO().trim()));
			dto.setBizOrderIds(bizOrderIds);
		}
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBeginDate())){
            try {
            	dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate()));
            } catch (ParseException e) {
            	log.error("dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getEndDate())){
            try {
            	dto.setEndDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getEndDate()));
            } catch (ParseException e) {
            	log.error("dto.setStartDate(DateUtil.convertStringToDate(jiuxiuOrderListQuery.getBeginDate())); Exception:" + e);
                e.printStackTrace();
            }
        }
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBuyerPhone())){
			dto.setPhone(jiuxiuOrderListQuery.getBuyerPhone());
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getBuyerName())){
			dto.setBuyerNick(jiuxiuOrderListQuery.getBuyerName());
		}
		//订单状态
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getOrderStat())){
			dto.setBizOrderStatus(Integer.parseInt(jiuxiuOrderListQuery.getOrderStat()));
		}
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getMerchantName())){
			dto.setMerchantName(jiuxiuOrderListQuery.getMerchantName());
		}
		//商户编号
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getMerchantNo())){
			dto.setSellerId(Long.parseLong(jiuxiuOrderListQuery.getMerchantNo()));
		}
		BatchBizQueryResult result = tcBizQueryServiceRef.queryOrderForAdmin(dto);
		BatchJiuxiuOrderResult jiuxiuResult = new BatchJiuxiuOrderResult();
		List<JiuxiuTcMainOrder> jiuxiuTcMainOrders = new ArrayList<JiuxiuTcMainOrder>();
		jiuxiuResult.setTotalCount(result.getTotalCount());
		if(result.isSuccess() && null!=result.getBizOrderDOList()){
			for(int i=0;i<result.getBizOrderDOList().size();i++){
				TcMainOrder tcMainOrder = result.getBizOrderDOList().get(i);
				JiuxiuTcMainOrder jiuxiuTcMainOrder = new JiuxiuTcMainOrder();
				List<TcDetailOrder> tcDetailOrders = tcMainOrder.getDetailOrders();
				List<JiuxiuTcDetailOrder> jiuxiuTcDetailOrders = new ArrayList<JiuxiuTcDetailOrder>();
				JiuxiuTcBizOrder jiuxiuTcBizOrder =new JiuxiuTcBizOrder();
				if(null!=tcDetailOrders && tcDetailOrders.size()>0){
					for(int j=0;j<tcDetailOrders.size();j++){
						TcDetailOrder tcDetailOrder = tcDetailOrders.get(j);
						JiuxiuTcDetailOrder jiuxiuTcDetailOrder = new JiuxiuTcDetailOrder();
						if(null!=tcDetailOrder.getBizOrder()){
							//封装订单基本信息
							JiuxiuHelper.fillBizOrder(jiuxiuTcBizOrder, tcDetailOrder.getBizOrder(), userServiceRef.getUserDOById(tcDetailOrder.getBizOrder().getBuyerId()).getMobileNo());
						}
						//子订单详情下添加子订单基本信息
						JiuxiuHelper.fillDetailOrder(jiuxiuTcDetailOrder, jiuxiuTcBizOrder, tcDetailOrder);
						
						jiuxiuTcDetailOrders.add(jiuxiuTcDetailOrder);
					}
				}
				jiuxiuTcMainOrder.setTotalFee(tcMainOrder.getTotalFee());
				jiuxiuTcMainOrder.setJiuxiuTcBizOrder(jiuxiuTcBizOrder);
				jiuxiuTcMainOrder.setJiuxiuTcDetailOrders(jiuxiuTcDetailOrders);
				jiuxiuTcMainOrder.setMerchantInfo(tcMainOrder.getMerchantInfo());
				jiuxiuTcMainOrders.add(jiuxiuTcMainOrder);
			}
		}
		jiuxiuResult.setJiuxiuTcMainOrders(jiuxiuTcMainOrders);
		return jiuxiuResult;
	}
	
}
