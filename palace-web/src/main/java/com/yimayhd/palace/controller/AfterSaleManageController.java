package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.Refund;
import com.yimayhd.palace.model.query.OrderListQuery;
import com.yimayhd.palace.model.trade.MainOrder;
import com.yimayhd.palace.service.AfterSaleService;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.refund.client.query.RefundOrderQuery;
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

    @Autowired private AfterSaleService afterSaleService;

    @RequestMapping(value = "/refund/list", method = RequestMethod.GET)
    public String list(Model model, RefundOrderQuery refundOrderQuery){
        try {
            PageVO pageVo = getPageVo(refundOrderQuery);
            model.addAttribute("pageVo", pageVo);
            model.addAttribute("orderList", pageVo.getItemList());
            model.addAttribute("orderListQuery", refundOrderQuery);
            model.addAttribute("orderStat", refundOrderQuery.getRefundStatus());
            return "/system/aftersale/gfAfterSaleList";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("gfOrderList|parameter="+ JSON.toJSONString(refundOrderQuery)+"|||exception="+e);
        }
        return "/error";

    }

    public PageVO getPageVo(RefundOrderQuery refundOrderQuery) throws Exception{
        refundOrderQuery.setDomain(1100);//TODO:enum类
        refundOrderQuery.setNeedCount(true);
        if(null == refundOrderQuery || refundOrderQuery.getRefundStatus() == 0 ){
            refundOrderQuery.setRefundStatus(RefundStatus.APPLY_REFUND.getStatus());//默认退款
        }
        PageVO<RefundOrderDO> pageVo = afterSaleService.queryRefundOrder(refundOrderQuery);
        return pageVo;
    }

    @RequestMapping(value = "/refund/detail", method = RequestMethod.GET)
    public String detail(Model model, OrderListQuery orderListQuery,int type){
        String orderNO = orderListQuery.getOrderNO();
        if(StringUtils.isEmpty(orderNO)){
            return "error";
        }
        RefundOrderDO refundOrderDO = afterSaleService.querySingRefundOrder(Long.parseLong(orderNO));
        if(null != refundOrderDO){
            model.addAttribute("orderShowState", refundOrderDO.getRefundStatus());
        }
        model.addAttribute("refundOrderDO", refundOrderDO);
        if(type==2){
            return "/system/aftersale/shenhe";
        }
        return "/system/aftersale/chakan";
    }

    //审核
    @RequestMapping(value = "/refund/audit", method = RequestMethod.GET)
    @ResponseBody
    public String audit(Model model,long id,int type){
        return null ;
    }

}
