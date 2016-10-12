package com.yimayhd.palace.convert;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.param.OrderStatusChangeParam;
import com.yimayhd.palace.model.vo.OrderStatusChangeVO;
import com.yimayhd.palace.model.vo.TcMainOrderVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeConverter {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeConverter.class);
    private OrderStatusChangeParam orderStatusChangeParam;
    private BeanCopier beanCopier = BeanCopier.create(TcMainOrder.class, TcMainOrderVO.class, false);


    public OrderStatusChangeConverter(OrderStatusChangeParam orderStatusChangeParam){
        this.orderStatusChangeParam= orderStatusChangeParam;
    }

    public OrderOperationLogDTO getLogDto(){
        OrderOperationLogDTO dto = new OrderOperationLogDTO();
        dto.setOperationId(orderStatusChangeParam.getUserId());
        dto.setBizNo(orderStatusChangeParam.getBizOrderIdStr());
        dto.setContent(orderStatusChangeParam.getDesc());
        dto.setStatus(orderStatusChangeParam.getOrderChangeStatus());
        return dto;
    }

    public OrderQueryDTO getOrderQueryDTO(){
        OrderQueryDTO dto = new OrderQueryDTO();
        dto.setDomain(Constant.DOMAIN_JIUXIU);
        dto.setBizOrderIds(orderStatusChangeParam.getBizOrderIds());
        return dto;
    }

    public List<TcMainOrderVO> getTcMainOrderVOList (List<TcMainOrder> bizOrderDOList){
        if(CollectionUtils.isEmpty(bizOrderDOList)){
            return null;
        }
        List<TcMainOrderVO> voList = new ArrayList<TcMainOrderVO>(bizOrderDOList.size());
        try{

            for(TcMainOrder tcMainOrder :bizOrderDOList){
                TcMainOrderVO vo = new TcMainOrderVO();
                beanCopier.copy(tcMainOrder,vo,null);
                voList.add(vo);
            }
        }catch (Exception e){
            logger.error("数据转换异常",e);
            return null;
        }

        return voList;
    }



    /**
     * 订单状态修改验证`
     * @return
     */
    public BizResult checkUpdateStatus(){
        if(orderStatusChangeParam==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }
        if(orderStatusChangeParam.getOrderChangeStatus()==0){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单修改状态为空",null);
        }
        if(CollectionUtils.isEmpty(orderStatusChangeParam.getBizOrderIds())){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单编号不能为空",null);
        }
        return BizResult.buildSuccessResult(null);
    }

    /**
     * 查询修改订单状态列表
     * @return
     */
    public  BizResult checkQueryList(){
        if(orderStatusChangeParam==null){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),PalaceReturnCode.PARAM_ERROR.getErrorMsg(),null);
        }

        if(CollectionUtils.isEmpty(orderStatusChangeParam.getBizOrderIds())){
            return BizResult.buildFailResult(PalaceReturnCode.PARAM_ERROR.getErrorCode(),"订单编号不能为空",null);
        }
        return BizResult.buildSuccessResult(null);
    }



    public OrderStatusChangeParam getOrderStatusChangeParam() {
        return orderStatusChangeParam;
    }

    public void setOrderStatusChangeParam(OrderStatusChangeParam orderStatusChangeParam) {
        this.orderStatusChangeParam = orderStatusChangeParam;
    }
}
