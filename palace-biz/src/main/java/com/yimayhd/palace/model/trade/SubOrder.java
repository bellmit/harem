package com.yimayhd.palace.model.trade;

import org.apache.commons.lang3.math.NumberUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.palace.util.OrderUtil;
import com.yimayhd.promotion.client.enums.PromotionType;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.PromotionInfo;
import com.yimayhd.tradecenter.client.model.enums.BizOrderExtFeatureKey;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import org.apache.velocity.tools.Toolbox;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class SubOrder {
    private BizOrderDO bizOrderDO;
    private long startTime;
    private long endTime;
    private long executeTime;
    private String vTxt;

    public String gyCode;//订单对应商品在管易中的编码

    public SubOrder(){}

    public SubOrder(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public SubOrder(BizOrderDO bizOrderDO, long startTime, long endTime) {
        this.bizOrderDO = bizOrderDO;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public SubOrder(BizOrderDO bizOrderDO, long executeTime) {
        this.bizOrderDO = bizOrderDO;
        this.executeTime = executeTime;
    }

    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getvTxt() {
        return vTxt;
    }

    public void setvTxt(String vTxt) {
        this.vTxt = vTxt;
    }
    
    public String getPromotionInfoTitle(){
    	if( bizOrderDO == null ){
    		return null;
    	}
    	PromotionInfo info = (PromotionInfo) bizOrderDO.getExtFeature(BizOrderExtFeatureKey.SUB_ORDER_USE_PROMOTION) ;
    	if( info == null ){
    		return null;
    	}
    	int type = info.getType() ;
    	if( PromotionType.DIRECT_REDUCE.getType() == type ){
    		long reduceFee = info.getDiscountFee() ;
    		if( reduceFee > 0 ){
    			//FIXME 伍正飞 修改
    			/*return "直降"+ NumUtil.moneyTrans(reduceFee)+"元";*/
               return "直降"+ NumUtil.totalFee(1,reduceFee)+"元";

    		}
    	}
    	String title = info.getTitle();
    	return title ;
    }
    
    
    public long getSumFee(){
    	long fee = OrderUtil.getOrderFee(bizOrderDO, BizOrderFeatureKey.SUB_ORDER_TOTAL_FEE) ;
    	return fee ;
    }

    public String getGyCode() {
        return gyCode;
    }

    public void setGyCode(String gyCode) {
        this.gyCode = gyCode;
    }
}
