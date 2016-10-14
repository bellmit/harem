package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.thoughtworks.xstream.mapper.Mapper;
import com.yimayhd.palace.convert.OrderStatusChangeLogConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogQuery;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogResult;
import com.yimayhd.palace.repo.selleradmin.OrderOperationLogRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.OrderStatusChangeLogService;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogResult;
import com.yimayhd.sellerAdmin.client.result.SellerResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeLogServiceImpl implements OrderStatusChangeLogService {

    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeLogServiceImpl.class);

    @Autowired
    private OrderOperationLogRepo orderOperationLogRepo;

    /**
     * 查询列表
     * @param query
     * @return
     */
    @Override
    public BizResult<OrderStatusChangeLogResult> queryOrderStatusChangeLogList(OrderStatusChangeLogQuery query) {
        OrderStatusChangeLogConverter conver = new OrderStatusChangeLogConverter(query);
        if(query==null){
            logger.error("参数错误,query is null");
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        OrderStatusChangeLogResult result =new OrderStatusChangeLogResult();
        try{

            SellerResult<OrderOperationLogResult>  sellerResult  = orderOperationLogRepo.queryOrderOperationLogDOList(conver.getLogQuery());
            if(sellerResult==null||!sellerResult.isSuccess()){
                logger.error("查询信息失败,errormsg={}", JSON.toJSONString(sellerResult));
                return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
            }
            OrderOperationLogResult operResult = sellerResult.getResultObject();
            if(operResult==null){
                logger.error("没有可查询数据,operResult is null");
                return BizResult.buildSuccessResult(result);
            }
            result.setOrderStatusChangeLogDTOList(conver.getLogDTOList(operResult.getOrderOperationLogDTOList()));
            result.setTotalCount(operResult.getTotalCount());
        }catch (Exception e){
            logger.error("查询数据异常",e);
            return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
        }


        return BizResult.buildSuccessResult(result);
    }
}
