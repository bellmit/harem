package com.yimayhd.palace.model.jiuxiu;

import java.io.Serializable;

import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;

public class JiuxiuTcBizOrder extends TcBizOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 买家手机号 */
	private String buyerPhone;

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	
	
	
}
