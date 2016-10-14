package com.yimayhd.palace.repo.selleradmin;

import com.alibaba.fastjson.JSON;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import com.yimayhd.sellerAdmin.client.service.OrderOperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by wangdi on 16/10/11.
 */
public class OrderOperationLogRepo {
    private static final Logger logger = LoggerFactory.getLogger(OrderOperationLogRepo.class);

    @Autowired
    private OrderOperationLogService orderOperationLogService;

    /**
     * 查询列表
     * @param query
     * @return
     */
    public SellerResult<OrderOperationLogResult> queryOrderOperationLogDOList(OrderOperationLogQuery query){
        logger.info(JSON.toJSONString(query));
        SellerResult result=null;
        try{
            result = orderOperationLogService.queryOrderOperationLogDOList(query);

        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        logger.info("result={}",JSON.toJSONString(result));
        return result;
    }

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public SellerResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query){
        logger.info(JSON.toJSONString(query));
        SellerResult result=null;
        try{
            result = orderOperationLogService.queryOrderOperationLogDOListCount(query);
        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        return result;
    }

    /**
     * 插入
     * @param orderOperationLogDTO
     * @return
     */
    public  SellerResult<Boolean> insertOrderOperationLogDO(OrderOperationLogDTO orderOperationLogDTO){
        logger.info(JSON.toJSONString(orderOperationLogDTO));
        if(orderOperationLogDTO==null){
            logger.error("参数错误,orderOperationLogDTO is null");
           return null;
        }
        SellerResult result= null;
        try{
            result = orderOperationLogService.insertOrderOperationLogDO(orderOperationLogDTO);
        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        return  result ;
    }
}
