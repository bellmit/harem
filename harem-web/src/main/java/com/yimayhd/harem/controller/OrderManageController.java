package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.yimayhd.service.MessageCodeService;

/**
 * 订单管理
 * @author czf
 */
@Controller
@RequestMapping("/orderManage")
public class OrderManageController extends BaseController {
    @Autowired
    private OrderService orderService;

   /* *//**
     * 订单列表
     * @return 交易列表
     * @throws Exception
     *//*
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    String list(Model model) throws Exception {
        return "/system/order/list";
    }*/
    /**
     * 根据ID获取订单详情
     * @return 交易详情
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    String getById(Model model,@PathVariable(value = "id") long id) throws Exception {
        Order order = orderService.getById(id);
        model.addAttribute("order",order);
        return "/system/order/information";
    }

}
