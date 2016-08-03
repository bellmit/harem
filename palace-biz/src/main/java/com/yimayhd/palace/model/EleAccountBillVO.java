package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class EleAccountBillVO extends EleAccountBillDTO {

	private static final long serialVersionUID = 1L;
	
	/**收支金额(元)*/
    private double transAmountYuan;
	/**账户余额（元）*/
    private double accountBalanceYuan;
	
	public static EleAccountBillVO getEleAccountBillVO(EleAccountBillDTO eleAccountBillDTO){
    	if(eleAccountBillDTO == null){
    		return null;
    	}
    	
    	EleAccountBillVO eleAccountBillVO = new EleAccountBillVO();
        BeanUtils.copyProperties(eleAccountBillDTO, eleAccountBillVO);
        eleAccountBillVO.setTransAmountYuan(NumUtil.moneyTransformDouble(eleAccountBillVO.getTransAmount()));
        eleAccountBillVO.setAccountBalanceYuan(NumUtil.moneyTransformDouble(eleAccountBillVO.getAccountBalance()));
        return eleAccountBillVO;
    }
	
	public double getTransAmountYuan() {
		return transAmountYuan;
	}

	public void setTransAmountYuan(double transAmountYuan) {
		this.transAmountYuan = transAmountYuan;
	}

	public double getAccountBalanceYuan() {
		return accountBalanceYuan;
	}

	public void setAccountBalanceYuan(double accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
}
