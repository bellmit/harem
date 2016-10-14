package com.yimayhd.palace.repo.selleradmin;

import com.alibaba.fastjson.JSON;
import com.taobao.tair.json.Json;
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
        try{
            SellerResult<OrderOperationLogResult> result =  orderOperationLogService.queryOrderOperationLogDOList(query);
            logger.info("result={}",JSON.toJSONString(result));
        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        return null;
    }

    /**
     * 查询 总记录数
     * @param query
     * @return
     */
    public SellerResult<Integer> queryOrderOperationLogDOListCount(OrderOperationLogQuery query){
        logger.info(JSON.toJSONString(query));
        try{
            SellerResult<Integer> result = orderOperationLogService.queryOrderOperationLogDOListCount(query);
            return result;
        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        return null;
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
        try{
            SellerResult<Boolean>  result = orderOperationLogService.insertOrderOperationLogDO(orderOperationLogDTO);
            logger.info("result="+JSON.toJSONString(result));
        }catch(Exception e){
            logger.error("远程调用异常",e);
        }
        return  null ;
    }
}
