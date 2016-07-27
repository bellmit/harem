package com.yimayhd.palace.repo;

import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Created by p on 7/20/16.
 */
public class OrderRepo {
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private TcBizQueryService tcBizQueryServiceRef;


    public BatchBizQueryResult queryOrderByOrderIds(List<Long> ids) {
        BatchBizQueryResult result = new BatchBizQueryResult();
        try {
            OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
            orderQueryDTO.setBizOrderIds(ids);
            orderQueryDTO.setDomain(B2CConstant.GF_DOMAIN);
            result = tcBizQueryServiceRef.queryOrderForAdmin(orderQueryDTO);
        } catch (Exception e) {
            log.error("Exception", e);
        }
        return result;
    }
}
