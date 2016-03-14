package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author create by yushengwei on 2016/3/11
 * @Description：售后管理
 * @return $returns
 */

@Controller
@RequestMapping("/GF/aftersale")

public class AfterSaleManageController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired private OrderService orderService;

    //仅退款列表页
    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public String list(Model model, OrderListQuery orderListQuery){
        try {
            PageVO pageVo = getPageVo(orderListQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("orderList", pageVo.getItemList());
            model.addAttribute("orderListQuery", orderListQuery);
            return "/system/aftersale/gfAfterSaleList";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("gfOrderList|parameter="+ JSON.toJSONString(orderListQuery)+"|||exception="+e);
        }
        return "/error";

    }


    //退款退货列表页
    @RequestMapping(value = "/refund/goods/list")
    public String refund(Model model, OrderListQuery orderListQuery){
        try {
            PageVO pageVo = getPageVo(orderListQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("orderList", pageVo.getItemList());
            model.addAttribute("orderListQuery", orderListQuery);
            return "/system/aftersale/gfAfterSaleList_goods";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("gfOrderList|parameter="+ JSON.toJSONString(orderListQuery)+"|||exception="+e);
        }
        return "error";
    }

    public PageVO getPageVo(OrderListQuery orderListQuery) throws Exception{
        orderListQuery.setDomain(1100);//TODO:enum类
        PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
        return pageVo;
    }

}
