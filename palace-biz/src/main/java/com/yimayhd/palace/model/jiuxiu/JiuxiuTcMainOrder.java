package com.yimayhd.palace.model.jiuxiu;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.tradecenter.client.model.result.order.create.TcMainOrder;

public class JiuxiuTcMainOrder extends TcMainOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /** 订单基本信息 */
	private JiuxiuTcBizOrder jiuxiuTcBizOrder;
	 /** 子订单列表 */
    private List<JiuxiuTcDetailOrder> jiuxiuTcDetailOrders;
    
	public JiuxiuTcBizOrder getJiuxiuTcBizOrder() {
		return jiuxiuTcBizOrder;
	}
	public void setJiuxiuTcBizOrder(JiuxiuTcBizOrder jiuxiuTcBizOrder) {
		this.jiuxiuTcBizOrder = jiuxiuTcBizOrder;
	}
	public List<JiuxiuTcDetailOrder> getJiuxiuTcDetailOrders() {
		return jiuxiuTcDetailOrders;
	}
	public void setJiuxiuTcDetailOrders(
			List<JiuxiuTcDetailOrder> jiuxiuTcDetailOrders) {
		this.jiuxiuTcDetailOrders = jiuxiuTcDetailOrders;
	}
    
    
}
