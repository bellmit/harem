package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.jiuxiu.JiuxiuTcMainOrder;
import com.yimayhd.palace.model.query.JiuxiuOrderListQuery;
import com.yimayhd.palace.result.BatchJiuxiuOrderResult;
import com.yimayhd.palace.service.JiuxiuOrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
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
				model.addAttribute("order", tcMainOrder);
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
				
			}
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
