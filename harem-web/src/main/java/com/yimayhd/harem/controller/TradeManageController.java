package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.BizOrderVO;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.service.TradeService;
import com.yimayhd.harem.util.excel.JxlFor2003;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.user.session.manager.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.mina.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 交易管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/tradeManage")
public class TradeManageController extends BaseController {
	@Autowired
	private TradeService tradeService;

	/**
	 * 交易列表
	 * 
	 * @return 交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/list", method = RequestMethod.GET)
	public String orderList(Model model, TradeListQuery tradeListQuery) throws Exception {
		//TODO
		//long sellerId = StringUtils.isBlank(SessionUtils.getUserId()) ? 10000000 :Long.parseLong(SessionUtils.getUserId());
		long sellerId = 1;
		PageVO<BizOrderVO> pageVo = tradeService.getOrderList(sellerId,tradeListQuery);
		List<BizOrderVO> bizOrderVOList = pageVo.getItemList();
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("tradeListQuery", tradeListQuery);
		model.addAttribute("orderList", bizOrderVOList);
		return "/system/trade/order/list";
	}
	/**
	 * 导出交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/export", method = RequestMethod.GET)
	public void orderListExport(HttpServletRequest request,HttpServletResponse response,TradeListQuery tradeListQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId = 1;
		List<BizOrderDO> bizOrderDOList = tradeService.exportOrderList(sellerId, tradeListQuery);
		if(null != bizOrderDOList && bizOrderDOList.size() > 0) {
			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("bizOrderId", "交易号"));
			headList.add(new BasicNameValuePair("payOrderId", "流水号"));
			headList.add(new BasicNameValuePair("payStatus", "支付方式"));
			headList.add(new BasicNameValuePair("actualTotalFee", "付款金额"));
			headList.add(new BasicNameValuePair("totalAmount", "交易金额"));
			headList.add(new BasicNameValuePair("gmtCreated", "交易时间"));
			JxlFor2003.exportExcel(response, "orderList.xls", bizOrderDOList, headList);
		}
	}

	/**
	 * 交易详情
	 * 
	 * @return 交易详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public String getById(Model model,@PathVariable(value = "orderId") long orderId) throws Exception {
		List<BizOrderDO> bizOrderDOList = tradeService.getOrderByOrderId(orderId);
		model.addAttribute("orderDetailList",bizOrderDOList);
		return "/system/trade/order/detail";
	}

	/**
	 * 支付记录列表
	 *
	 * @return 支付记录列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/list", method = RequestMethod.GET)
	public String payList(Model model, PayListQuery payListQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId =10000000;
		PageVO<PayOrderDO> pageVo  = tradeService.getPayOrderList(sellerId,payListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("payListQuery", payListQuery);
		model.addAttribute("payList", pageVo.getItemList());
		return "/system/trade/pay/list";
	}
	/**
	 * 导出支付记录列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/export", method = RequestMethod.GET)
	public void payListExport(HttpServletRequest request,HttpServletResponse response,PayListQuery payListQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId =10000000;
		List<PayOrderDO> payOrderDOList = tradeService.exportPayOrderList(sellerId,payListQuery);
		if(payOrderDOList.size() > 0) {
			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("tradeNo", "交易号"));
			headList.add(new BasicNameValuePair("id", "商户订单号"));
			headList.add(new BasicNameValuePair("subject", "商品信息"));
			headList.add(new BasicNameValuePair("buyerAccount", "对方账号"));
			headList.add(new BasicNameValuePair("totalAmount", "交易金额"));
			headList.add(new BasicNameValuePair("payStatus", "状态"));
			JxlFor2003.exportExcel(response, "payList.xls", payOrderDOList, headList);
		}
	}

}
