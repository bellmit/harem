package com.yimayhd.controller;

import com.yimayhd.base.BaseController;
import com.yimayhd.base.ResponseVo;
import com.yimayhd.model.Refund;
import com.yimayhd.model.query.RefundListQuery;
import com.yimayhd.service.RefundService;
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

    private RefundService refundService;


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
     * 退款详情
     * @return 退款详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(@PathVariable(value = "id") long id) throws Exception {
        return "/system/refund/information";
    }
    /**
     * 退款
     * @return 退款
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public
    ResponseVo refund(Refund refund) throws Exception {
        return new ResponseVo();
    }


}
