package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.ic.client.model.enums.PropertyType;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.TcSingleQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.tradecenter.client.util.SkuUtils;
import com.yimayhd.user.session.manager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 订单管理
 * 
 * @author 
 */
@Controller
@RequestMapping("/jiuxiu/orderManage")
public class JiuxiuOrderManageController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(JiuxiuOrderManageController.class);
	@Autowired
	private TcBizQueryService tcBizQueryServiceRef;
	@Autowired
	private JiuxiuOrderService jiuxiuOrderService;
	@Autowired
    private SessionManager sessionManager;

	
	/**
	 * 根据ID获取订单详情
	 * 
	 * @return 订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrder/{id}", method = RequestMethod.GET)
	public String getGoodOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderQueryOption opt = new OrderQueryOption();
		opt.setNeedDetailOrder(true);
		opt.setNeedExtFeature(true);
		TcSingleQueryResult result = tcBizQueryServiceRef.querySingle(id, opt);
		if(result.isSuccess() && null!=result.getTcMainOrder()){
			if(null!= result.getTcMainOrder().getBizOrder() && (sessionManager.getUserId() == result.getTcMainOrder().getBizOrder().getSellerId())){
				model.addAttribute("order", result.getTcMainOrder());
			}
		}
		return "/system/order/orderInfo";
	}

	/**
	 * 订单列表
	 * 
	 * @return 订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrderList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, JiuxiuOrderListQuery jiuxiuOrderListQuery) throws Exception {
		try {
			BatchBizQueryResult result = jiuxiuOrderService.getOrderList(jiuxiuOrderListQuery);
			int totalCount = 0;
			List<TcMainOrder> tcMainOrderList = new ArrayList<TcMainOrder>();
			if(null != result && null != result.getBizOrderDOList()){
				totalCount = (int)result.getTotalCount();
				tcMainOrderList = result.getBizOrderDOList();
//				for(int i=0;i<result.getBizOrderDOList().size();i++){
//					TcMainOrder tcMainOrder = result.getBizOrderDOList().get(i);
//					for(int j=0;j<tcMainOrder.getDetailOrders().size();j++){
//						TcDetailOrder tcDetailOrder = tcMainOrder.getDetailOrders().get(j);
//						tcDetailOrder.setStartDate(DateUtil.parseDate(SkuUtils.getPropertyValue(tcDetailOrder.getBizOrder().getSkuInfo(), PropertyType.START_DATE.getType())));
//						tcDetailOrder.setLinePackage(SkuUtils.getPropertyValue(tcDetailOrder.getBizOrder().getSkuInfo(), PropertyType.LINE_PACKAGE.getType()));
//						tcDetailOrder.setPersonType(SkuUtils.getPropertyValue(tcDetailOrder.getBizOrder().getSkuInfo(), PropertyType.PERSON_TYPE.getType()));
//					}
//				}
			}
			PageVO<TcMainOrder> orderPageVO = new PageVO<TcMainOrder>(jiuxiuOrderListQuery.getPageNumber(),jiuxiuOrderListQuery.getPageSize(),
					totalCount,tcMainOrderList);
			model.addAttribute("pageVo", orderPageVO);
			model.addAttribute("result", result.getBizOrderDOList());
			model.addAttribute("orderListQuery", jiuxiuOrderListQuery);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "/system/order/orderList";
	}

}
