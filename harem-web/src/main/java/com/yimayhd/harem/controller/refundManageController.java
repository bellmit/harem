package com.yimayhd.harem.controller;

import java.util.List;

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
@RequestMapping("/refundManage")
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
		List<Refund> refundList = refundService.getList(refundVO);
		PageVO<Refund> pageVo = new PageVO<Refund>(refundListQuery.getPageNumber(), refundListQuery.getPageSize(), 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("refundListQuery", refundListQuery);
		model.addAttribute("refundList", refundList);
		return "/system/refund/list";
	}

	/**
	 * 根据退款ID退款详情
	 * 
	 * @return 退款详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Refund refund = refundService.getById(id);
		Order order = orderService.getOrderById(id);
		PageVO<Order> pageVo = new PageVO<Order>(1, 20, 14800);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("order", order);
		model.addAttribute("refund", refund);
		return "/system/refund/detail";
	}

	/**
	 * 退款详情
	 * 
	 * @tradId 交易订单
	 * @return 退款详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/add/{tradId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVo toAddRefund(@PathVariable(value = "tradId") long tradId) throws Exception {
		Order order = orderService.getOrderById(tradId);
		// model.addAttribute("order",order);
		// return "/system/refund/add";
		return new ResponseVo(order);
	}

	/**
	 * 根据退款金额计算退款信息
	 * 
	 * @return 退款信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/refund", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo getRefundDataByRefundMoney(Model model, double refundMoney) throws Exception {
		Refund refund = refundService.getRefundDataByRefundMoney(refundMoney);
		return new ResponseVo(refund);
	}

	/**
	 * 提交退款
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo refund(Refund refund) throws Exception {
		refundService.addRefund(refund);
		return new ResponseVo();
	}

}
