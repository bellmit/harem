package com.yimayhd.palace.model.orderLog;


import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/10.
 */
public class OrderStatusChangeLogResult implements Serializable{

    private static final long serialVersionUID = -6468215396236714902L;
    private List<OrderStatusChangeLogDTO> orderStatusChangeLogDTOList;
    private int totalCount;

    public List<OrderStatusChangeLogDTO> getOrderStatusChangeLogDTOList() {
        return orderStatusChangeLogDTOList;
    }

    public void setOrderStatusChangeLogDTOList(List<OrderStatusChangeLogDTO> orderStatusChangeLogDTOList) {
        this.orderStatusChangeLogDTOList = orderStatusChangeLogDTOList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
