package com.yimayhd.harem.model.query;

import com.yimayhd.harem.base.BaseQuery;

/**
 * Created by Administrator on 2015/10/27.
 */
public class OrderListQuery extends BaseQuery {
    private String orderNO;//订单编号
    private String orderStat;//订单状态
    private String buyerPhone;//买家手机号
    private String buyerName;//买家昵称
    private String activityName;//活动名称
    private String affiliatedAsk;//附属要求
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public String getOrderStat() {
		return orderStat;
	}
	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getAffiliatedAsk() {
		return affiliatedAsk;
	}
	public void setAffiliatedAsk(String affiliatedAsk) {
		this.affiliatedAsk = affiliatedAsk;
	}
    
    

  
}
