package com.yimayhd.palace.repo.order;

import com.alibaba.fastjson.JSON;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcBizQueryService;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class TcTradeRepo {
    private static final Logger logger = LoggerFactory.getLogger(TcTradeRepo.class);

    @Autowired
    private TcTradeService tcTradeServiceRef;

    @Autowired
    private TcBizQueryService tcBizQueryServiceRef;

    /**
     * 查询可更改状态订单
     * @param orderQueryDTO
     * @return
     */
   public BatchBizQueryResult queryOrderForAdmin(OrderQueryDTO orderQueryDTO){
        logger.info("queryList--param repo:"+JSON.toJSONString(orderQueryDTO));
        if(orderQueryDTO==null){
            logger.error("参数 orderQueryDTO is null");
            return null;
        }
        try{
            BatchBizQueryResult result =  tcBizQueryServiceRef.queryOrderForAdmin(orderQueryDTO);
            logger.info("queryList-- repo result={}",JSON.toJSONString(result));
            return result;
        }catch (Exception e){
            logger.error("远程调用异常,",e);
        }
        return null;

    }

    /**
     * 更改订单状态未已完成
     * @param bizOrderIds
     * @return
     */
   public ResultSupport updateOrderStatusToFinishForOperator(List<Long> bizOrderIds){

        logger.info("repo---:"+JSON.toJSONString(bizOrderIds));
        if(CollectionUtils.isEmpty(bizOrderIds)){
            logger.error("参数 bizOrderIds is null");
            return null;
        }
        try{
            ResultSupport result =  tcTradeServiceRef.updateOrderStatusToFinishForOperator(bizOrderIds);
            logger.info("repo--- result={}",JSON.toJSONString(result));
            return result;
        }catch (Exception e){
            logger.error("远程调用异常,",e);
        }
        return null;
    }

    /**
     * 更新订单状态已取消
     * @param bizOrderIds
     * @return
     */
    public ResultSupport updateOrderStatusToCancelForOperator(List<Long> bizOrderIds){

        logger.info("repo---"+JSON.toJSONString(bizOrderIds));
        if(CollectionUtils.isEmpty(bizOrderIds)){
            logger.error("参数 bizOrderIds is null");
            return null;
        }
        try{
            ResultSupport result =  tcTradeServiceRef.updateOrderStatusToCancelForOperator(bizOrderIds);
            logger.info("repo--- result={}",JSON.toJSONString(result));
            return result;
        }catch (Exception e){
            logger.error("远程调用异常,",e);
        }
        return null;
    }

}
