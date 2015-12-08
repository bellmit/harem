package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.query.RefundListQuery;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.harem.service.RefundService;
import com.yimayhd.harem.util.excel.JxlFor2003;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;
import com.yimayhd.user.session.manager.SessionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 退款管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/refundManage")
public class RefundManageController extends BaseController {

	@Autowired
	private RefundService refundService;

	/**
	 * 退款列表
	 * 
	 * @return 退款列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String refundList(Model model, RefundListQuery refundListQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId = 1;
		PageVO<IMallRefundRecordDO> pageVO = refundService.getList(sellerId,refundListQuery);
		model.addAttribute("pageVo", pageVO);
		model.addAttribute("refundListQuery", refundListQuery);
		model.addAttribute("refundList", null == pageVO ? null : pageVO.getItemList());
		return "/system/refund/list";
	}
	/**
	 * 导出退款列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/refund/export", method = RequestMethod.GET)

	public String refundListExport(HttpServletRequest request,HttpServletResponse response,RefundListQuery refundListQuery) throws Exception {
		//TODO
		long sellerId = Long.parseLong(SessionUtils.getUserId());
		//long sellerId = 1;
		List<IMallRefundRecordDO> iMallRefundRecordDOList = refundService.exportRefundList(sellerId,refundListQuery);
		if(null != iMallRefundRecordDOList && iMallRefundRecordDOList.size() > 0) {

			List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
			headList.add(new BasicNameValuePair("tradeId", "交易编号"));
			headList.add(new BasicNameValuePair("department", "部门"));
			headList.add(new BasicNameValuePair("jobNumber", "工号"));
			headList.add(new BasicNameValuePair("terminalNumber", "终端编号"));
			headList.add(new BasicNameValuePair("refundPayment", "实际退款金额(单位：分)"));
			headList.add(new BasicNameValuePair("payment", "付款金额(单位：分)"));
			headList.add(new BasicNameValuePair("refundTime", "退款时间"));
			headList.add(new BasicNameValuePair("receiptTime", "小票时间"));
			JxlFor2003.exportExcel(response, "refundList.xls", iMallRefundRecordDOList, headList);
			return null;
		}else{
			return "/error";
		}
	}
}
