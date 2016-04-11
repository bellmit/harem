package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.RefundOrderVO;
import com.yimayhd.palace.model.trade.OrderDetails;
import com.yimayhd.palace.service.AfterSaleService;
import com.yimayhd.palace.service.OrderService;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.refund.client.param.ExamineRefundOrderDTO;
import com.yimayhd.refund.client.query.RefundOrderQuery;
import com.yimayhd.refund.client.result.BaseResult;
import com.yimayhd.refund.client.result.ResultSupport;
import com.yimayhd.refund.client.result.refundorder.ExamineRefundOrderResult;
import com.yimayhd.refund.client.result.refundorder.RefundOrderPageQueryResult;
import com.yimayhd.refund.client.service.RefundQueryService;
import com.yimayhd.refund.client.service.RefundService;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author create by yushengwei on 2016/3/25
 * @Description
 */
public class AfterSaleServiceImpl implements AfterSaleService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired RefundQueryService refundQueryService;

    @Autowired RefundService refundService;

    @Autowired private OrderService orderService;

    @Override
    public ExamineRefundOrderResult examineRefundOrder(ExamineRefundOrderDTO examineRefundOrderDTO) {
        ExamineRefundOrderResult result = refundService.examineRefundOrder(examineRefundOrderDTO);
        if(null == result || !result.isSuccess()){
            recordLog("querySingRefundOrder",examineRefundOrderDTO,result);
        }
        return result;
    }

    @Override
    public RefundOrderVO querySingRefundOrder(long refundOrderId) {
        //查退款单信息
        RefundOrderVO rv = new RefundOrderVO();
        try {
            BaseResult<RefundOrderDO> result = refundQueryService.querySingleRefundOrder(refundOrderId);
            if(null == result || !result.isSuccess()){
                recordLog("querySingRefundOrder.refundQueryService.querySingleRefundOrder",refundOrderId,result);
                return null;
            }
            RefundOrderDO rdo = result.getValue();
            rv.setRefundOrderDO(rdo);
            //查订单信息
            OrderDetails orderDetails = orderService.getOrderById(rdo.getBizOrderId());
            if(null == orderDetails ){
                recordLog("querySingRefundOrder.orderService.getOrderById",rdo.getBizOrderId(),orderDetails);
                return rv;
            }
            rv.setSignId(rdo.getBizOrderId()+"_"+rdo.getId());
            rv.setOrderDetails(orderDetails);

        } catch (Exception e) {
            e.printStackTrace();
            recordLog("querySingRefundOrder",refundOrderId,e);
        }
        return rv;
    }

   public PageVO<RefundOrderDO> queryRefundOrder(RefundOrderQuery var1){
    RefundOrderPageQueryResult result = refundQueryService.queryRefundOrder(var1);
       if(null ==result || !result.isSuccess()){
           recordLog("queryRefundOrder",var1,result);
       }
       return new PageVO<RefundOrderDO >(var1.getPageNo(), var1.getPageSize(), result.getTotalCount(), result.getRefundOrderDOList());

   }

    public void recordLog(String funcName,Object para,Object result){
        //XXX:为什么不统一一下ResultSupport呢
        //if(null ==result || (result instanceof ResultSupport && !((ResultSupport) result).isSuccess())){}
        log.error(">>>AfterSaleServiceImpl."+funcName+" result is null or notSuccess,Parameters="
                + JSON.toJSONString(para)+"|||result="+JSON.toJSONString(result));
    }

}
