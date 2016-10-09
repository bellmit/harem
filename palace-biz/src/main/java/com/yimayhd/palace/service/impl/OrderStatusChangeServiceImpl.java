package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.convert.OrderStatusChangeConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.model.vo.TcMainOrderVO;
import com.yimayhd.palace.repo.order.TcTradeRepo;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.OrderStatusChangeService;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;
import com.yimayhd.tradecenter.client.model.result.order.BatchBizQueryResult;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import com.yimayhd.tradecenter.client.service.trade.TcTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

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

    /**
     * 订单状态修改
     * @param orderStatusChangeParam
     * @return
     */
    @Override
    public BizResult<String> updateStatus(OrderStatusChangeParam orderStatusChangeParam) {
         OrderStatusChangeConverter converter = new OrderStatusChangeConverter(orderStatusChangeParam);
         BizResult checkResult =  converter.checkUpdateStatus();
         if(!checkResult.isSuccess()){
             logger.error("参数错误,msg={}",checkResult.getMsg());
             return checkResult;
         }
        ResultSupport result= null;
        try{
            switch (orderStatusChangeParam.getOrderChangeStatus()) {
                case FINISH:
                    result = tcTradeRepo.updateOrderStatusToFinish(orderStatusChangeParam.getBizOrderIds());
                    break;

                case CANCEL:
                    result = tcTradeRepo.updateOrderStatusToCancel(orderStatusChangeParam.getBizOrderIds());
                    break;

                default:
                    return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
            }
            if (result==null||!result.isSuccess()){
                logger.error("修改订单状态失败,result={}",result);
                return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
            }

        }catch (Exception e){
            logger.error("远程调用repo异常",e);
            return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
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
        OrderStatusChangeConverter converter = new OrderStatusChangeConverter(orderStatusChangeParam);
        OrderStatusChangeVO result = new OrderStatusChangeVO();
        BizResult checkResult =  converter.checkQueryList();
        if(!checkResult.isSuccess()){
            logger.error("参数错误,msg={}",checkResult.getMsg());
            return checkResult;
        }
        try{
            BatchBizQueryResult repoResult = tcTradeRepo.queryOrderForUpdateStatus(converter.getOrderQueryDTO());
            if(repoResult==null||!repoResult.isSuccess()){
                logger.error("查询订单列表失败,result={}", JSON.toJSONString(repoResult));
                return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),repoResult.getResultMsg(),null);
            }
            List<TcMainOrder> tcMainOrderList=   repoResult.getBizOrderDOList();
            result.setTcMainOrderVOList(converter.getTcMainOrderVOList(tcMainOrderList));
            result.setTotalCount(repoResult.getTotalCount());

        }catch(Exception e){
            logger.error("调用rep异常",e);
            return BizResult.buildFailResult(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorCode(),PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg(),null);
        }
        return BizResult.buildSuccessResult(result);
    }
}
