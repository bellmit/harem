package com.yimayhd.harem.controller;

import java.util.List;

import com.yimayhd.tradecenter.client.model.domain.imall.TcRefundRecordDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.Refund;
import com.yimayhd.harem.model.query.RefundListQuery;
import com.yimayhd.harem.model.vo.RefundVO;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.harem.service.RefundService;

//import com.yimayhd.service.MessageCodeService;

/**
 * 退款管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/refundManage")
public class refundManageController extends BaseController {

	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderService orderService;

	/**
	 * 退款列表
	 * 
	 * @return 退款列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, RefundListQuery refundListQuery) throws Exception {
		RefundVO refundVO = new RefundVO();
		refundVO.setRefundListQuery(refundListQuery);
		PageVO<TcRefundRecordDO> pageVO = refundService.getList(refundListQuery);
		PageVO<Refund> pageVo = new PageVO<Refund>(refundListQuery.getPageNumber(), refundListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("refundListQuery", refundListQuery);
		model.addAttribute("refundList", pageVO.getItemList());
		return "/system/refund/list";
	}
}
