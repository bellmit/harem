package com.yimayhd.palace.model;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.domain.audit.PayAuditOrderDO;

/**
 * Created by Administrator on 2015/12/8.
 */
public class PayAuditOrderVO extends PayAuditOrderDO {
	
	private static final long serialVersionUID = 1L;

	/**交易金额*/
	private double tradeAmountYuan;

    /**对方交易金额*/
    private double oppositeTradeAmountYuan;

	
    public static PayAuditOrderVO getPayAuditOrderVO(PayAuditOrderDO payAuditOrderDO){
    	if(payAuditOrderDO == null){
    		return null;
    	}
    	
    	PayAuditOrderVO payAuditOrderVO = new PayAuditOrderVO();
        BeanUtils.copyProperties(payAuditOrderDO, payAuditOrderVO);
        
        payAuditOrderVO.setTradeAmountYuan(NumUtil.moneyTransformDouble(payAuditOrderVO.getTradeAmount()));
        payAuditOrderVO.setOppositeTradeAmountYuan(NumUtil.moneyTransformDouble(payAuditOrderVO.getOppositeTradeAmount()));
        
        return payAuditOrderVO;
    }
    
    public double getTradeAmountYuan() {
		return tradeAmountYuan;
	}
	public void setTradeAmountYuan(double tradeAmountYuan) {
		this.tradeAmountYuan = tradeAmountYuan;
	}
	public double getOppositeTradeAmountYuan() {
		return oppositeTradeAmountYuan;
	}
	public void setOppositeTradeAmountYuan(double oppositeTradeAmountYuan) {
		this.oppositeTradeAmountYuan = oppositeTradeAmountYuan;
	}
}
