package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.palace.util.Common;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.tradecenter.client.model.param.order.*;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
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

	@Override
	public BatchBizQueryResult getOrderList(JiuxiuOrderListQuery jiuxiuOrderListQuery) throws Exception {
		OrderQueryDTO dto = new OrderQueryDTO();
		dto.setDomain(Constant.DOMAIN_JIUXIU);
//		dto.setSellerId(sessionManager.getUserId());
		//商品类型
		if(StringUtils.isNotEmpty(jiuxiuOrderListQuery.getItemType())){
			dto.setOrderBizTypes(new int[]{Integer.parseInt(jiuxiuOrderListQuery.getItemType())});
		}
		dto.setItemName(jiuxiuOrderListQuery.getItemName());
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
		dto.setPhone(jiuxiuOrderListQuery.getBuyerPhone());
		dto.setBuyerNick(jiuxiuOrderListQuery.getBuyerName());
		//订单状态
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getOrderStat())){
			dto.setBizOrderStatus(Integer.parseInt(jiuxiuOrderListQuery.getOrderStat()));
		}
		dto.setMerchantName(jiuxiuOrderListQuery.getMerchantName());
		//商户编号
		if (StringUtils.isNotEmpty(jiuxiuOrderListQuery.getMerchantNo())){
			dto.setSellerId(Long.parseLong(jiuxiuOrderListQuery.getMerchantNo()));
		}
		return tcBizQueryServiceRef.queryOrderForAdmin(dto);
	}

	
}
