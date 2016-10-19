package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.convert.OrderStatusChangeConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.model.vo.TcMainOrderVO;
import com.yimayhd.palace.repo.order.TcTradeRepo;
import com.yimayhd.palace.repo.selleradmin.OrderOperationLogRepo;
import com.yimayhd.palace.repo.user.UserRepo;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.OrderStatusChangeService;
import com.yimayhd.sellerAdmin.client.enums.OrderOperationLogStatus;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeServiceImpl implements OrderStatusChangeService {

    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeServiceImpl.class);
    private static final int FINISH=1;
    private static final int CANCEL=2;
    @Autowired
    private TcTradeRepo tcTradeRepo;

    @Autowired
    private OrderOperationLogRepo orderOperationLogRepo;

    @Autowired
    private UserRepo userRepo;



    /**
     * 订单状态修改
     * @param orderStatusChangeParam
     * @return
     */
    @Override
    public BizResult<String> updateStatus(OrderStatusChangeParam orderStatusChangeParam) {
        logger.info("updateStatus param:"+JSON.toJSONString(orderStatusChangeParam));
         OrderStatusChangeConverter converter = new OrderStatusChangeConverter(orderStatusChangeParam);
         BizResult checkResult =  converter.checkUpdateStatus();
         if(!checkResult.isSuccess()){
             logger.error("参数错误,msg={}",checkResult.getMsg());
             return checkResult;
         }
        ResultSupport result= null;
        OrderOperationLogDTO logDTO = converter.getLogDto();
        logDTO.setContent(OrderOperationLogStatus.getByType(Integer.valueOf(orderStatusChangeParam.getOrderChangeStatus()).intValue()).getName());//最终修改状态
        try{
            switch (Integer.valueOf(orderStatusChangeParam.getOrderChangeStatus()).intValue()) {
                case FINISH:
                    result = tcTradeRepo.updateOrderStatusToFinishForOperator(orderStatusChangeParam.getBizOrderIds());
                    break;

                case CANCEL:
                    result = tcTradeRepo.updateOrderStatusToCancelForOperator(orderStatusChangeParam.getBizOrderIds());
                    break;

                default:
                    return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
            }
            if (result==null||!result.isSuccess()){
                logger.error("修改订单状态失败,result={}",result);
                logDTO.setStatus(OrderOperationLogStatus.FAIL.getType());
                return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
            }

        }catch (Exception e){
            logger.error("远程调用repo异常",e);
            logDTO.setStatus(OrderOperationLogStatus.SUCCESS.getType());
            return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
        }
        try{
            /**更新状态成功,插入日志*/
            logDTO.setStatus(OrderOperationLogStatus.SUCCESS.getType());
            orderOperationLogRepo.insertOrderOperationLogDO(logDTO);
        }catch (Exception e){
            logger.error("记录操作日志异常",e);
            return BizResult.buildFailResult(PalaceReturnCode.SYSTEM_ERROR.getErrorCode(),PalaceReturnCode.SYSTEM_ERROR.getErrorMsg(),null);

        }

        return BizResult.buildSuccessResult(null);
    }

    /**
     * 查询修改状态列表
     * @param orderStatusChangeParam
     * @return
     */
    @Override
    public BizResult<OrderStatusChangeVO> queryList(OrderStatusChangeParam orderStatusChangeParam) {
        logger.info("调用queryList 接口 ");
        OrderStatusChangeConverter converter = new OrderStatusChangeConverter(orderStatusChangeParam);
        OrderStatusChangeVO result = new OrderStatusChangeVO();
        BizResult checkResult =  converter.checkQueryList();
        if(!checkResult.isSuccess()){
            logger.error("参数错误,msg={}",checkResult.getMsg());
            return checkResult;
        }
        try{
            BatchBizQueryResult repoResult = tcTradeRepo.queryOrderForAdmin(converter.getOrderQueryDTO());
            if(repoResult==null||!repoResult.isSuccess()){
                logger.error("查询订单列表失败,result={}", JSON.toJSONString(repoResult));
                return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
            }
            List<TcMainOrder> tcMainOrderList=   repoResult.getBizOrderDOList();
            List<TcMainOrderVO> tcMainOrderVOList = converter.getTcMainOrderVOList(tcMainOrderList);// 中台返回订单信息
              /**订单信息出里*/
            result.setTcMainOrderVOList(converter.secondaryTcMainOrder(tcMainOrderVOList));
            result.setTotalCount(repoResult.getTotalCount());

            logger.info("query list info :"+JSON.toJSONString(result));


        }catch(Exception e){
            logger.error("调用rep异常",e);
            return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
        }
        return BizResult.buildSuccessResult(result);
    }
}
