package com.yimayhd.controller;

import com.yimayhd.base.BaseController;
import com.yimayhd.base.ResponseVo;
import com.yimayhd.model.Order;
import com.yimayhd.model.Refund;
import com.yimayhd.model.query.RefundListQuery;
import com.yimayhd.service.OrderService;
import com.yimayhd.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 退款管理
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
     * @return 退款列表
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model,RefundListQuery refundListQuery) throws Exception {
        Refund refund = new Refund();
        refund.setRefundListQuery(refundListQuery);
        List<Refund> refundList = refundService.getList(refund);
        model.addAttribute("refundListQuery",refundListQuery);
        model.addAttribute("refundList",refundList);
        return "/system/refund/list";
    }
    /**
     * 根据退款ID退款详情
     * @return 退款详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Refund refund = refundService.getById(id);
        Order order = orderService.getById(id);
        model.addAttribute("order",order);
        model.addAttribute("refund",refund);
        return "/system/refund/information";
    }

    /**
     * 退款详情
     * @tradId 交易订单
     * @return 退款详情
     * @throws Exception
     */
    @RequestMapping(value = "/add/{tradId}", method = RequestMethod.GET)
    public
    String toAddRefund(Model model,@PathVariable(value = "tradId") long tradId) throws Exception {
        Order order = orderService.getById(tradId);
        model.addAttribute("order",order);
        return "/system/refund/add";
    }

    /**
     * 根据退款金额计算退款信息
     * @return 退款信息
     * @throws Exception
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo getRefundDataByRefundMoney(Model model,double refundMoney) throws Exception {
        Refund refund = refundService.getRefundDataByRefundMoney(refundMoney);
        return new ResponseVo(refund);
    }

    /**
     * 提交退款
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo refund(Refund refund) throws Exception {
        refundService.addRefund(refund);
        return new ResponseVo();
    }




}
