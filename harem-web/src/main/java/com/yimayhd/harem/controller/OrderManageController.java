package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVo;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.OrderListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.model.vo.OrderVO;
import com.yimayhd.harem.model.vo.TradeVO;
import com.yimayhd.harem.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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


    /**
     * 活动订单
     * @return 活动订单
     * @throws Exception
     */
    @RequestMapping(value = "/activityOrderList", method = RequestMethod.GET)
    public
    String activityOrderList(Model model,OrderListQuery orderListQuery,PageVo pageVo) throws Exception {
    	OrderVO orderVO = new OrderVO();
    	orderVO.setOrderListQuery(orderListQuery);
        List<Order> activityOrderList = orderService.getActivityOrderList(orderVO);
        pageVo.setTotalSum((long) 14800);
        model.addAttribute("pageVo", pageVo);
        model.addAttribute("orderListQuery", orderListQuery);
        model.addAttribute("orderList", activityOrderList);
        return "/system/order/list";
    }

}
