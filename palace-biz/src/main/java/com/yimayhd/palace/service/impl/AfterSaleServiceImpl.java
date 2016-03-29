package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.service.AfterSaleService;
import com.yimayhd.refund.client.domain.RefundOrderDO;
import com.yimayhd.refund.client.query.RefundOrderQuery;
import com.yimayhd.refund.client.result.BaseResult;
import com.yimayhd.refund.client.result.refundorder.RefundOrderPageQueryResult;
import com.yimayhd.refund.client.service.RefundQueryService;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
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

    @Override
    public RefundOrderDO querySingRefundOrder(long refundOrderId) {
        BaseResult<RefundOrderDO> result = refundQueryService.querySingleRefundOrder(refundOrderId);
        if(null == result || !result.isSuccess()){
            log.error(">>>AfterSaleServiceImpl.querySingRefundOrder result is null or notSuccess,Parameters="
                    + refundOrderId+"|||result="+JSON.toJSONString(result));
        }
        return result.getValue();
    }

   public PageVO<RefundOrderDO> queryRefundOrder(RefundOrderQuery var1){
    RefundOrderPageQueryResult result = refundQueryService.queryRefundOrder(var1);
       if(null ==result || !result.isSuccess()){
           log.error(">>>AfterSaleServiceImpl.queryRefundOrder result is null or notSuccess,Parameters="
                   + JSON.toJSONString(var1)+"|||result="+JSON.toJSONString(result));
       }
       return new PageVO<RefundOrderDO >(var1.getPageNo(), var1.getPageSize(), result.getTotalCount(), result.getRefundOrderDOList());

     /* List<RefundOrderDO> list  = new ArrayList<RefundOrderDO>();
       RefundOrderDO r1 = new RefundOrderDO();
       r1.setId(1111L);
       r1.setBizOrderId(12222L);
       r1.setBuyerAccount("1333333333322");
       r1.setRefundStatus(1);
       r1.setGmtCreated(new Date());
       RefundOrderDO r2 = new RefundOrderDO();
       r2.setId(2222L);
       r2.setBizOrderId(22222L);
       r2.setBuyerAccount("12222222222");
       r2.setRefundStatus(3);
       r2.setGmtCreated(new Date());
       list.add(r1);list.add(r2);
       return new PageVO<RefundOrderDO >(1, 10,100, list);*/
   }

}
