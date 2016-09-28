package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccBalanceDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class EleAccBalanceVO extends EleAccBalanceDTO {

	private static final long serialVersionUID = 1L;
	
	/**账户余额*/
    private double accountBalanceYuan;

	public static EleAccBalanceVO getEleAccBalanceVO(EleAccBalanceDTO eleAccBalanceDTO){
    	if(eleAccBalanceDTO == null){
    		return null;
    	}
    	
    	EleAccBalanceVO eleAccBalanceVO = new EleAccBalanceVO();
        BeanUtils.copyProperties(eleAccBalanceDTO, eleAccBalanceVO);
        
        eleAccBalanceVO.setAccountBalanceYuan(NumUtil.moneyTransformDouble(eleAccBalanceVO.getAccountBalance()));
        
        return eleAccBalanceVO;
    }
	
	public double getAccountBalanceYuan() {
		return accountBalanceYuan;
	}

	public void setAccountBalanceYuan(double accountBalanceYuan) {
		this.accountBalanceYuan = accountBalanceYuan;
	}
}
