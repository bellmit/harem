package com.yimayhd.palace.result;

import java.util.List;

import com.yimayhd.palace.model.jiuxiu.JiuxiuTcMainOrder;
import com.yimayhd.tradecenter.client.model.result.ResultSupport;

public class BatchJiuxiuOrderResult extends ResultSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<JiuxiuTcMainOrder> jiuxiuTcMainOrders;
    private long totalCount ;
    
	public List<JiuxiuTcMainOrder> getJiuxiuTcMainOrders() {
		return jiuxiuTcMainOrders;
	}
	public void setJiuxiuTcMainOrders(List<JiuxiuTcMainOrder> jiuxiuTcMainOrders) {
		this.jiuxiuTcMainOrders = jiuxiuTcMainOrders;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
    

}
