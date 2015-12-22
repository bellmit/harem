package com.yimayhd.harem.repo;

import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.SingleQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: zhaozhaonan
 * Date: 2015/12/18
 * 订单server
 */
public class OrderRepo {

    public static final Logger logger = LoggerFactory.getLogger(OrderRepo.class);

    @Autowired
    private TcQueryService tcQueryServiceRef;

    /**
     * 根据各种条件订单查询
     */
    public BatchQueryResult queryOrders(OrderQueryDTO orderQueryDTO){
        if (orderQueryDTO!=null){
            BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrders(orderQueryDTO);
            return batchQueryResult;
        }
        return null;
    }

    /**
     * 如果有订单编号，忽略其他查询条件
     */
    public SingleQueryResult queryOrderSingle(String orderNo){
        SingleQueryResult singleQueryResult = tcQueryServiceRef.querySingle(Long.parseLong(orderNo),null);
        return singleQueryResult;
    }



}
