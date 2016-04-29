package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.model.jiuxiu.JiuxiuTcBizOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcDetailOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcMainOrder;
import com.yimayhd.palace.model.jiuxiu.helper.JiuxiuHelper;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.result.BatchJiuxiuOrderResult;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.tradecenter.client.model.param.order.*;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
		
		JiuxiuHelper.fillOrderQueryDTO(dto, jiuxiuOrderListQuery);
		
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
				
				if(null!=tcMainOrder){
					//封装订单基本信息
					JiuxiuHelper.fillBizOrder(jiuxiuTcBizOrder, tcMainOrder.getBizOrder(), userServiceRef.getUserDOById(tcMainOrder.getBizOrder().getBuyerId()).getMobileNo());
				}
				if(null!=tcDetailOrders && tcDetailOrders.size()>0){
					for(int j=0;j<tcDetailOrders.size();j++){
						TcDetailOrder tcDetailOrder = tcDetailOrders.get(j);
						JiuxiuTcDetailOrder jiuxiuTcDetailOrder = new JiuxiuTcDetailOrder();
						
						//子订单详情下添加子订单基本信息
						JiuxiuHelper.fillDetailOrder(jiuxiuTcDetailOrder, tcDetailOrder.getBizOrder(), tcDetailOrder);
						
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
