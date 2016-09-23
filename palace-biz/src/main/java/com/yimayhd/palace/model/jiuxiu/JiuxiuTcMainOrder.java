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
    
    private long requirement;

    private long value; 
    
    private long iteamPrice_;
    
    private long userPointNum;

	private long mainOrderTotalChangeFee;//异常流订单实付金额
    
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
	public long getRequirement() {
		return requirement;
	}
	public void setRequirement(long requirement) {
		this.requirement = requirement;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	public long getIteamPrice_() {
		return iteamPrice_;
	}
	public void setIteamPrice_(long iteamPrice_) {
		this.iteamPrice_ = iteamPrice_;
	}
	public long getUserPointNum() {
		return userPointNum;
	}
	public void setUserPointNum(long userPointNum) {
		this.userPointNum = userPointNum;
	}

	public long getMainOrderTotalChangeFee() {
		return mainOrderTotalChangeFee;
	}

	public void setMainOrderTotalChangeFee(long mainOrderTotalChangeFee) {
		this.mainOrderTotalChangeFee = mainOrderTotalChangeFee;
	}
}
