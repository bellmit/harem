package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcDetailOrder;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcMainOrder;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.result.BatchJiuxiuOrderResult;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.param.pay.QuerySingleDTO;
import com.yimayhd.pay.client.model.result.PayResultDTO;
import com.yimayhd.pay.client.service.QueryPayOrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.VoucherInfo;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryOption;
import com.yimayhd.tradecenter.client.model.result.order.TcSingleQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.UserOptions;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private MerchantService userMerchantServiceRef;
	@Autowired
	private QueryPayOrderService queryPayOrderServiceRef;

	
	/**
	 * 根据ID获取订单详情
	 * 
	 * @return 订单详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/goodsOrder/{id}", method = RequestMethod.GET)
	public String getGoodOrderById(Model model, @PathVariable(value = "id") long id) throws Exception {
		try {
			OrderQueryOption opt = new OrderQueryOption();
			opt.setNeedDetailOrder(true);
			opt.setNeedExtFeature(true);
			opt.setNeedLogisticsOrder(true);
			TcSingleQueryResult result = tcBizQueryServiceRef.querySingle(id, opt);
			
			if(result.isSuccess() && null!=result.getTcMainOrder()){
				TcMainOrder tcMainOrder = result.getTcMainOrder();

				long sellerId = tcMainOrder.getBizOrder()==null?0:tcMainOrder.getBizOrder().getSellerId();
				BaseResult<MerchantUserDTO> merchantUserDTO = userMerchantServiceRef.getMerchantAndUserBySellerId(sellerId, Constant.DOMAIN_JIUXIU);
				TcMerchantInfo tcMerchantInfo = new TcMerchantInfo();
				if(null!= merchantUserDTO.getValue() && null!= merchantUserDTO.getValue().getMerchantDO()){
					tcMerchantInfo.setMerchantName(merchantUserDTO.getValue().getMerchantDO().getName());
					tcMerchantInfo.setMerchantId(merchantUserDTO.getValue().getMerchantDO().getId());

					tcMainOrder.setMerchantInfo(tcMerchantInfo);
				}
				JiuxiuTcMainOrder jiuxiuTcMainOrder = new JiuxiuTcMainOrder();
				BeanUtils.copyProperties(tcMainOrder, jiuxiuTcMainOrder);
				//添加主订单原价
				jiuxiuTcMainOrder.setIteamPrice_(BizOrderUtil.getMainOrderTotalFee(tcMainOrder.getBizOrder().getBizOrderDO()));
				//获取优惠劵优惠金额
				VoucherInfo voucherInfo = BizOrderUtil.getVoucherInfo(tcMainOrder.getBizOrder().getBizOrderDO());
				if(null!=voucherInfo){
					jiuxiuTcMainOrder.setRequirement(voucherInfo.getRequirement());
					jiuxiuTcMainOrder.setValue(voucherInfo.getValue());
				}
				//获取使用的积分
				jiuxiuTcMainOrder.setUserPointNum(BizOrderUtil.getUsePointNum(tcMainOrder.getBizOrder().getBizOrderDO()));
				
				//根据userId获取用户信息
				UserDO buyer = userServiceRef.getUserDOById(tcMainOrder.getBizOrder()==null?0:tcMainOrder.getBizOrder().getBuyerId());
				model.addAttribute("phone", buyer.getMobileNo());
				if(null!=tcMainOrder.getDetailOrders()){
					List<TcDetailOrder> tcDetailOrders = tcMainOrder.getDetailOrders();
					
					model.addAttribute("startDate", tcDetailOrders.get(0).getStartDate());
					UserDO seller = userServiceRef.getUserDOById(tcDetailOrders.get(0).getBizOrder()==null?0:tcDetailOrders.get(0).getBizOrder().getSellerId());
					if(null!=seller){
						boolean isUserTalent = UserOptions.USER_TALENT.has(seller.getOptions());
						boolean isCommercialTenant = UserOptions.COMMERCIAL_TENANT.has(seller.getOptions());
						if(isUserTalent){
							//model.addAttribute("talent", tcMainOrder.getBizOrder()==null?"": tcMainOrder.getBizOrder().getSellerNick());
							model.addAttribute("talent", seller.getNickname());
						}
						if(isCommercialTenant){
							model.addAttribute("merchant", tcMainOrder.getMerchantInfo() == null? "" : tcMainOrder.getMerchantInfo().getMerchantName());
						}
					}
					//获取卖家备注
					BizOrderDO bizOrderDO = new BizOrderDO();
					bizOrderDO.setDomain(Constant.DOMAIN_JIUXIU);
					bizOrderDO.setBizOrderId(tcMainOrder.getBizOrder()==null?0:tcMainOrder.getBizOrder().getBizOrderId());
					model.addAttribute("sellerMsg", BizOrderUtil.getSellerMemo(bizOrderDO));
				}
				//复制详情到自定义详情中
				List<JiuxiuTcDetailOrder> jiuxiuTcDetailOrderList= new ArrayList<JiuxiuTcDetailOrder>();
				for (TcDetailOrder tcDetailOrder : jiuxiuTcMainOrder.getDetailOrders()) {
					JiuxiuTcDetailOrder jiuxiuTcDetailOrder = new JiuxiuTcDetailOrder();
					BeanUtils.copyProperties(tcDetailOrder, jiuxiuTcDetailOrder);
					jiuxiuTcDetailOrderList.add(jiuxiuTcDetailOrder);
				}
				jiuxiuTcMainOrder.setJiuxiuTcDetailOrders(jiuxiuTcDetailOrderList);
				
				
				for (JiuxiuTcDetailOrder jiuxiuTcDetailOrder : jiuxiuTcMainOrder.getJiuxiuTcDetailOrders()) {
					//订单实付总额
			        long total = BizOrderUtil.getSubOrderActualFee(jiuxiuTcDetailOrder.getBizOrder().getBizOrderDO());
					 //获取子订单实付金额
			        if(jiuxiuTcDetailOrder.getBizOrder().getBuyAmount() > 0){
			        	long act = total/jiuxiuTcDetailOrder.getBizOrder().getBuyAmount();
			        	jiuxiuTcDetailOrder.setItemPrice_(act);
			        }
				}
				
				model.addAttribute("order", jiuxiuTcMainOrder);
			}
			
			//查询支付信息
			QuerySingleDTO query = new QuerySingleDTO();
			query.setDomain(Constant.DOMAIN_JIUXIU);
			query.setBizOrderId(id);
			PayResultDTO<PayOrderDO> payResult = queryPayOrderServiceRef.querySinglePayOrder(query);
			if(payResult.isSuccess() && null!=payResult.getModule()){
				model.addAttribute("tradeNo", payResult.getModule().getTradeNo());
			}
			model.addAttribute("OrderBizType", OrderBizType.values());
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			e.printStackTrace();
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
			BatchJiuxiuOrderResult result = jiuxiuOrderService.getOrderList(jiuxiuOrderListQuery);
			int totalCount = 0;
			List<JiuxiuTcMainOrder> tcMainOrderList = new ArrayList<JiuxiuTcMainOrder>();
			if(null != result && null != result.getJiuxiuTcMainOrders()){
				totalCount = (int)result.getTotalCount();
				tcMainOrderList = result.getJiuxiuTcMainOrders();
			}
			PageVO<JiuxiuTcMainOrder> orderPageVO = new PageVO<JiuxiuTcMainOrder>(jiuxiuOrderListQuery.getPageNumber(),jiuxiuOrderListQuery.getPageSize(),
					totalCount,tcMainOrderList);
			model.addAttribute("pageVo", orderPageVO);
			model.addAttribute("result", result.getJiuxiuTcMainOrders());
			model.addAttribute("orderListQuery", jiuxiuOrderListQuery);
		} catch (Exception e) {
			LOG.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "/system/order/orderList";
	}

}
