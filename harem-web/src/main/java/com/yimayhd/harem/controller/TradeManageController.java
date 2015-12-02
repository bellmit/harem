package com.yimayhd.harem.controller;

import java.util.List;

import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.model.vo.TradeVO;
import com.yimayhd.harem.service.TradeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public String list(Model model, TradeListQuery tradeListQuery) throws Exception {
		PageVO<BizOrderDO> pageVo = tradeService.getOrderList(tradeListQuery);
		List<BizOrderDO> bizOrderDOList = pageVo.getItemList();
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("tradeListQuery", tradeListQuery);
		model.addAttribute("orderList", bizOrderDOList);
		return "/system/trade/order/list";
	}

	/**
	 * 交易详情
	 * 
	 * @return 交易详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(@PathVariable(value = "id") long id) throws Exception {
		return "/system/trade/information";
	}

	/**
	 * 支付记录列表
	 *
	 * @return 支付记录列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay/list", method = RequestMethod.GET)
	public String payList(Model model, PayListQuery payListQuery) throws Exception {
		PageVO<PayOrderDO> pageVo  = tradeService.getPayOrderList(payListQuery);
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
		tradeService.exportPayOrderList(response,payListQuery);

	}

}
