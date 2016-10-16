package com.yimayhd.palace.convert;

import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogDTO;
import com.yimayhd.palace.model.orderLog.OrderStatusChangeLogQuery;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogDTO;
import com.yimayhd.sellerAdmin.client.model.orderLog.OrderOperationLogQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class OrderStatusChangeLogConverter {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusChangeLogConverter.class);
    private BeanCopier doCopier = BeanCopier.create( OrderOperationLogDTO.class,OrderStatusChangeLogDTO.class ,false);

    private OrderStatusChangeLogQuery orderStatusChangeLogQuery;

    public OrderStatusChangeLogConverter(){

    }

    public OrderStatusChangeLogConverter(OrderStatusChangeLogQuery query){
        this.orderStatusChangeLogQuery = query;
    }

    public OrderOperationLogQuery getLogQuery(){
        if(orderStatusChangeLogQuery==null){
            return null;
        }
        OrderOperationLogQuery query = new OrderOperationLogQuery();
        try{
            if(StringUtils.isNotBlank(orderStatusChangeLogQuery.getBizNo())){
                query.setBizNo(orderStatusChangeLogQuery.getBizNo());
            }
            if(StringUtils.isNotBlank(orderStatusChangeLogQuery.getOperationId())){
                query.setOperationId(Long.valueOf(orderStatusChangeLogQuery.getOperationId()).longValue());
            }
            if(StringUtils.isNotBlank(orderStatusChangeLogQuery.getGmtCreatedStartStr())){
                query.setGmtCreatedStart(DateUtil.formatStrTimeForDate(orderStatusChangeLogQuery.getGmtCreatedStartStr()));
            }
            if(StringUtils.isNotBlank(orderStatusChangeLogQuery.getGmtCreatedEndStr())){
                query.setGmtCreatedEnd(DateUtil.formatStrTimeForDate(orderStatusChangeLogQuery.getGmtCreatedEndStr()));
            }
        }catch(Exception e){
            logger.error("bean 转换异常",e);
        }
        query.setNeedCount(true);
        return query;
    }

    public OrderStatusChangeLogDTO converBeanDto(OrderOperationLogDTO dto){
        if(dto==null){
            return null;
        }
        OrderStatusChangeLogDTO logDTO = new  OrderStatusChangeLogDTO();
        try{
            doCopier.copy(dto,logDTO,null);
        }catch (Exception e){
            logger.error("bean 转换异常",e);
            return null;

        }

        return logDTO;
    }

    public List<OrderStatusChangeLogDTO> getLogDTOList (List<OrderOperationLogDTO> list) throws Exception{
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<OrderStatusChangeLogDTO> logDTOs = new ArrayList<OrderStatusChangeLogDTO>(list.size());
        for(OrderOperationLogDTO operLogDto :list){
            logDTOs.add(converBeanDto(operLogDto));
        }
        return logDTOs;
    }

}
