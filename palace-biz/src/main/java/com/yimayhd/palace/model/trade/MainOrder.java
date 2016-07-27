package com.yimayhd.palace.model.trade;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.palace.util.OrderUtil;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.PromotionInfo;
import com.yimayhd.tradecenter.client.model.enums.BizOrderExtFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.user.client.domain.UserDO;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class MainOrder {
    private BizOrderDO bizOrderDO;

    private LogisticsOrderDO logisticsOrderDO;

    private List<SubOrder> subOrderList;

    private long orderTotalFee;

    private int orderActionStates;

    private int orderShowState;

    private UserDO user;
    
    private PayOrderDO payOrderDO;

    private ExpressVO express;

    public ExpressVO getExpress() {
        return express;
    }

    public void setExpress(ExpressVO express) {
        this.express = express;
    }

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public List<SubOrder> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<SubOrder> subOrderList) {
        this.subOrderList = subOrderList;
    }

    public int getOrderActionStates() {
        return orderActionStates;
    }

    public void setOrderActionStates(int orderActionStates) {
        this.orderActionStates = orderActionStates;
    }

    public int getOrderShowState() {
        return orderShowState;
    }

    public void setOrderShowState(int orderShowState) {
        this.orderShowState = orderShowState;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }
    /**
     * 订单优惠金额
     * @return
     */
    public long getPromotionFee(){
    	long fee = OrderUtil.getOrderFee(bizOrderDO, BizOrderFeatureKey.ORDER_PROMOTION_FEE) ;
    	return  fee;
    }
    public long getVoucherFee(){
    	long fee = OrderUtil.getOrderFee(bizOrderDO, BizOrderFeatureKey.ORDER_VOUCHER_FEE) ;
    	return  fee;
    }
    public long getOriginalFee(){
    	long fee = OrderUtil.getOrderFee(bizOrderDO, BizOrderFeatureKey.MAIN_ORDER_TOTAL_FEE) ;
    	return  fee;
    }
    
    

    public long getOrderTotalFee() {
        return orderTotalFee;
    }

    public void setOrderTotalFee(long orderTotalFee) {
        this.orderTotalFee = orderTotalFee;
    }

   public LogisticsOrderDO getLogisticsOrderDO() {
        return logisticsOrderDO;
    }

    public void setLogisticsOrderDO(LogisticsOrderDO logisticsOrderDO) {
        this.logisticsOrderDO = logisticsOrderDO;
    }

     public MainOrder(BizOrderDO bizOrderDO, List<SubOrder> subOrderList) {
        this.bizOrderDO = bizOrderDO;
        this.subOrderList = subOrderList;
        if( bizOrderDO != null ){
        	this.orderTotalFee = bizOrderDO.getActualTotalFee() ;
        }

     }
     
     
     public String getPromotionInfoTitle(){
     	if( bizOrderDO == null ){
     		return null;
     	}
     	PromotionInfo info = (PromotionInfo) bizOrderDO.getExtFeature(BizOrderExtFeatureKey.SUB_ORDER_USE_PROMOTION) ;
     	if( info == null ){
     		return null;
     	}
     	String title = info.getTitle();
     	return title ;
     }
     public String getBueryMemo(){
    	 if( bizOrderDO == null ){
    		 return null;
    	 }
    	 String info = (String) bizOrderDO.getExtFeature(BizOrderExtFeatureKey.BUYER_MEMO) ;
    	 return  info ;
     }

	public PayOrderDO getPayOrderDO() {
		return payOrderDO;
	}

	public void setPayOrderDO(PayOrderDO payOrderDO) {
		this.payOrderDO = payOrderDO;
	}
     
}
