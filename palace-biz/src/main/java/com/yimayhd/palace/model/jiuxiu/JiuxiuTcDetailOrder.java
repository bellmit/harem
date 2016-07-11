package com.yimayhd.palace.model.jiuxiu;

import java.io.Serializable;

import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;

public class JiuxiuTcDetailOrder extends TcDetailOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 /** 子订单基本信息 */
    private JiuxiuTcBizOrder jiuxiuTcBizOrder;
    /** 子订单实付价 */
    private long itemPrice_;
    
	public JiuxiuTcBizOrder getJiuxiuTcBizOrder() {
		return jiuxiuTcBizOrder;
	}
	public void setJiuxiuTcBizOrder(JiuxiuTcBizOrder jiuxiuTcBizOrder) {
		this.jiuxiuTcBizOrder = jiuxiuTcBizOrder;
	}
	public long getItemPrice_() {
		return itemPrice_;
	}
	public void setItemPrice_(long itemPrice_) {
		this.itemPrice_ = itemPrice_;
	}
    

}
