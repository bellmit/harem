package com.yimayhd.palace.model;

import com.yimayhd.palace.base.PageVO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class EleAccBalanceResultVO {

	/**总金额*/
    private double totalAmount;

	/**
     * 电子账户的余额DTO
     */
    private PageVO<EleAccBalanceVO> eleAccBalanceVOPage;

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public PageVO<EleAccBalanceVO> getEleAccBalanceVOPage() {
		return eleAccBalanceVOPage;
	}

	public void setEleAccBalanceVOPage(PageVO<EleAccBalanceVO> eleAccBalanceVOPage) {
		this.eleAccBalanceVOPage = eleAccBalanceVOPage;
	}
}
