package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.Refund;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.tradecenter.client.model.enums.RefundStatus;
import org.apache.commons.lang3.StringUtils;
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

    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public String list(Model model, OrderListQuery orderListQuery){
        try {
            PageVO pageVo = getPageVo(orderListQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("orderList", pageVo.getItemList());
            model.addAttribute("orderListQuery", orderListQuery);
            model.addAttribute("orderStat", orderListQuery.getOrderStat());
            return "/system/aftersale/gfAfterSaleList";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("gfOrderList|parameter="+ JSON.toJSONString(orderListQuery)+"|||exception="+e);
        }
        return "/error";

    }

    public PageVO getPageVo(OrderListQuery orderListQuery) throws Exception{
        orderListQuery.setDomain(1100);//TODO:enum类
        if(null == orderListQuery || StringUtils.isEmpty(orderListQuery.getOrderStat())){
            orderListQuery.setOrderStat(RefundStatus.APPLY_REFUND.toString());//默认退款
        }
        PageVO<MainOrder> pageVo = orderService.getOrderList(orderListQuery);
        return pageVo;
    }

}
