package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.model.vo.TradeVO;
import com.yimayhd.harem.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 交易管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/tradeManage")
public class TradeManageController extends BaseController {
	@Autowired
	private TradeService tradeService;

	/**
	 * 交易列表
	 * 
	 * @return 交易列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, TradeListQuery tradeListQuery) throws Exception {
		TradeVO tradeVO = new TradeVO();
		tradeVO.setTradeListQuery(tradeListQuery);
		List<Trade> tradeList = tradeService.getList(tradeVO);
		PageVO<Trade> pageVo = new PageVO<Trade>(tradeListQuery.getPageNumber(), tradeListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("tradeListQuery", tradeListQuery);
		model.addAttribute("tradeList", tradeList);
		return "/system/trade/list";
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

}
