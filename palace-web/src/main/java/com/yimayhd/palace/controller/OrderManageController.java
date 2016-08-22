package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.lgcenter.client.domain.ExpressCodeRelationDO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.service.LogisticsService;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.AdjustFeeDTO;
import com.yimayhd.tradecenter.client.model.param.order.SellerSendGoodsDTO;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 订单管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/orderManage")
public class OrderManageController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderManageController.class);
	@Autowired
	private OrderService orderService;
	@Autowired
	LogisticsService logisticsService;
	@Autowired private SessionManager sessionManager;

	/**
	 * 退款
	 */
	@RequestMapping(value = "/refundOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo refundOrderById(long orderId) throws Exception {
		boolean flag = orderService.refundOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 完成
	 */
	@RequestMapping(value = "/buyerConfirmGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo buyerConfirmGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.buyerConfirmGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 发货+确认
	 */
	@RequestMapping(value = "/sellerSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo sellerSendGoods(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.sellerSendGoods(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}


	/**
	 * 关闭、取消订单
	 */
	@RequestMapping(value = "/closeOrder", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo closeOrder(long orderId, HttpServletRequest request) throws Exception {
		boolean flag = orderService.closeOrder(orderId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}







	/**
	 * 根据ID获取路线订单详情
	 * @return 路线订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrder/{id}", method = RequestMethod.GET)
	public String getRouteOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/routeOrderInfo";
	}

	/**
	 * 路线订单列表
	 * @return 路线订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/routeOrderList", method = RequestMethod.GET)
	public String routeOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.LINE.getBizType(),OrderBizType.FLIGHT_HOTEL.getBizType(),OrderBizType.SPOTS_HOTEL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/routeOrderList";
	}

	/**
	 * 根据ID获取景区订单详情
	 * 
	 * @return 景区订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/scenicSpotOrder/{id}", method = RequestMethod.GET)
	public String getScenicSpotOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/scenicSpotOrderInfo";
	}

	/**
	 * 景区订单列表
	 * 
	 * @return 景区订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/scenicSpotOrderList", method = RequestMethod.GET)
	public String scenicSpotOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.SPOTS.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/scenicSpotOrderList";
	}

	/**
	 * 根据ID获取实物商品订单详情
	 * 
	 * @return 实物商品订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrder/{id}", method = RequestMethod.GET)
	public String getGoodOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/goodsOrderInfo";
	}

	/**
	 * 实物商品订单列表
	 * 
	 * @return 实物商品订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrderList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.NORMAL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/goodsOrderList";
	}

	/**
	 * 根据ID获取活动订单详情
	 * 
	 * @return 活动订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityOrder/{id}", method = RequestMethod.GET)
	public String getActivityOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/activityOrderInfo";
	}

	/**
	 * 活动订单
	 * 
	 * @return 活动订单
	 * @throws Exception
	 */
	@RequestMapping(value = "/activityOrderList", method = RequestMethod.GET)
	public String activityOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.ACTIVITY.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/activityOrderList";
	}

	/**
	 * 根据ID获取会员卡订单详情
	 * 
	 * @return 活动会员卡详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/membershipCardOrder/{id}", method = RequestMethod.GET)
	public String getMembershipCardOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/membershipCardOrderInfo";
	}

	/**
	 * 会员卡订单列表
	 * 
	 * @return 会员卡订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/membershipCardOrderList", method = RequestMethod.GET)
	public String membershipCardOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.MEMBER_RECHARGE.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/membershipCardOrderList";
	}



	/**
	 * 根据ID获取路线订单详情
	 * @return 路线订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/hotelOrder/{id}", method = RequestMethod.GET)
	public String getHotelOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		if (orderDetails!=null){
			model.addAttribute("order", orderDetails);
		}
		return "/system/order/hotelOrderInfo";
	}

	/**
	 * 路线订单列表
	 * @return 路线订单列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/hotelOrderList", method = RequestMethod.GET)
	public String hotelOrderList(Model model, OrderListQuery orderListQuery) throws Exception {
		int [] orderBizTypes = {OrderBizType.HOTEL.getBizType()};
		orderListQuery.setOrderTypes(orderBizTypes);
		PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("orderList", pageVo.getItemList());
		model.addAttribute("orderListQuery", orderListQuery);
		return "/system/order/hotelOrderList";
	}

	/**
	 * @Title: GFOrderList
	 * @Description:(GF订单列表)
	 * @author create by yushengwei @ 2016年2月26日
	 * @throws
	 */
	@RequestMapping(value = "/gfOrderList",method = RequestMethod.GET)//, method = RequestMethod.GET
	public String gfOrderList(Model model, OrderListQuery orderListQuery){
		try {
			orderListQuery.setDomain(1100);//TODO:enum类
			//这里莫名其妙的多个，待后期检查vm页面代码
			if(StringUtils.isNotEmpty(orderListQuery.getItemName()) && orderListQuery.getItemName().equals(",")){
				orderListQuery.setItemName(null);
			}
			PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("orderList", pageVo.getItemList());
			model.addAttribute("orderListQuery", orderListQuery);
			return "/system/order/gf/gfOrderList";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("gfOrderList|parameter="+ JSON.toJSONString(orderListQuery)+"|||exception="+e);
		}
		return "/error";
	}

	/**
	 * @Title: gfOrderDetailById
	 * @Description:(根据ID获取GF订单详情)
	 * @author create by yushengwei @ 2016年2月26日
	 * @throws
	 */
	@RequestMapping(value = "/gfOrder/{id}", method = RequestMethod.GET)
	public String gfOrderDetailById(Model model, @PathVariable(value = "id") long id) throws Exception {
		OrderDetails orderDetails = orderService.getOrderById(id);
		model.addAttribute("logistics",orderDetails.getExpress());
		model.addAttribute("order", orderDetails);
		MainOrder mainOrder = orderDetails.getMainOrder();
		if( mainOrder != null  ){
			model.addAttribute("lgOrder", mainOrder.getLogisticsOrderDO());
		}
		return "/system/order/gf/gfOrderInfo";
	}

	@RequestMapping(value = "/gfSendGoods", method = RequestMethod.GET)
	public String toGfSendGoods(Model model,long bizOrderId){
		List<ExpressCodeRelationDO> list = orderService.selectAllExpressCode();//查询物流公司接口
		model.addAttribute("listExpress",list);
		model.addAttribute("bizOrderId",bizOrderId);
		return "/system/order/gf/gfSendGoods";
	}

	@RequestMapping(value = "/gfSendGoods", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo gfSendGoods(long bizOrderId,String expressCompany,String expressNo){
		if(0==bizOrderId || StringUtils.isEmpty(expressCompany) || StringUtils.isEmpty(expressNo)){
			return new ResponseVo(ResponseStatus.INVALID_DATA);
		}
		long userId = sessionManager.getUserId();
		StringBuilder sb = new StringBuilder();
		sb.append(" time=").append(DateUtil.dateToString(new Date(),DateUtil.DATE_TIME_FORMAT))
		  .append(" userid=").append(userId)
		  .append(" bizOrderId=").append(bizOrderId)
		  .append(" expressCompany=").append(expressCompany)
		  .append(" expressNo=").append(expressNo);
		LOG.info(sb.toString());
		SellerSendGoodsDTO sg = new SellerSendGoodsDTO();
		sg.setBizOrderId(bizOrderId);
		sg.setExpressCompany(expressCompany);
		sg.setExpressNo(expressNo);
		boolean flag = orderService.sellerSendGoods(sg);
		return new ResponseVo(flag);
	}


	@RequestMapping(value = "/gfAdjustFee", method = RequestMethod.GET)
	public String toGfAdjustFee(Model model,long bizOrderId,String oldPrice,String newPrice,String remark){
		model.addAttribute("bizOrderId",bizOrderId);
		model.addAttribute("oldPrice",StringUtils.isEmpty(oldPrice)?"":oldPrice);
		if(StringUtils.isEmpty(newPrice) || "0.00".equals(newPrice)){newPrice = "";}
		model.addAttribute(newPrice);
		model.addAttribute("remark",remark);
		return "/system/order/gf/gfAdjustFee";
	}

	@RequestMapping(value = "/gfAdjustFee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo gfAdjustFee(long bizOrderId,String oldPrice,String newPrice,String remark){
		try {
			long userId = sessionManager.getUserId();
			if(0 == userId ){
                return new ResponseVo(ResponseStatus.UNAUTHORIZED);
            }
			if(0==bizOrderId || (StringUtils.isEmpty(oldPrice)) || (StringUtils.isEmpty(newPrice))){//|| (!NumberUtils.isNumber(newPrice))
                return new ResponseVo(ResponseStatus.INVALID_DATA);
            }
			if(StringUtils.isEmpty(remark) || lessPoints(newPrice)){
				return new ResponseVo(ResponseStatus.UNSUCCESSFUL.VALUE,"小数点后最多精确2位，请重新修改");
			}
			//TODO:有空改成正则
			if(newPrice.equals("0")||newPrice.equals("0.0")||newPrice.equals("0.00")){
				return new ResponseVo(ResponseStatus.UNSUCCESSFUL.VALUE,"订单金额必须大于 0 元");
			}
			StringBuilder sb = new StringBuilder();
			sb.append("userId=").append(userId).append(",bizOrderId=").append(bizOrderId).append(",oldPrice=").append(oldPrice).append(",newPrice=").append(newPrice).append(",remark=").append(remark);
			LOG.info(sb.toString());
			AdjustFeeDTO sg = new AdjustFeeDTO();
			sg.setBizOrderId(bizOrderId);
			sg.setUpdateFee(NumUtil.moneyTransformString(newPrice));
			sg.setRemark(remark);
			sg.setUserId(userId);
			boolean flag = orderService.adjustFee(sg);
			return new ResponseVo(flag);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new ResponseVo(false);
		}
	}

	public boolean  lessPoints(String str){
		int index = str.lastIndexOf(".");
		if(index > -1) {
			int len = str.substring(index + 1).length();
			return (len>2)?true:false;
		}
		return false;
	}

}
