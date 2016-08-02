package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class SettlementVO extends SettlementDTO{
	
	private static final long serialVersionUID = 1L;
	
	private double channelFeeDouble;
	private double settlementAmountDouble;
	
	private String batchNoStr;
	private String accountTypeStr;

	public static SettlementVO getSettlementVO(SettlementDTO settlementDTO){
    	if(settlementDTO == null){
    		return null;
    	}
    	
    	SettlementVO settlementVO = new SettlementVO();
        BeanUtils.copyProperties(settlementDTO, settlementVO);
        
        //将分转成元
        settlementVO.setChannelFeeDouble(NumUtil.moneyTransformDouble(settlementDTO.getChannelFee()));
        settlementVO.setSettlementAmountDouble(NumUtil.moneyTransformDouble(settlementDTO.getSettlementAmount()));
        
        return settlementVO;
	}
	
	public static SettlementDTO getSettlementDTO(SettlementVO settlementVO){
    	if(settlementVO == null){
    		return null;
    	}
    	
    	SettlementDTO settlementDTO = new SettlementDTO();
        BeanUtils.copyProperties(settlementVO, settlementDTO);
        
        settlementDTO.setBatchNo(Integer.parseInt(settlementVO.getBatchNoStr()));
        settlementDTO.setAccountType(Integer.parseInt(settlementVO.getAccountTypeStr()));
        
        return settlementDTO;
	}
	
	public double getChannelFeeDouble() {
		return channelFeeDouble;
	}

	public void setChannelFeeDouble(double channelFeeDouble) {
		this.channelFeeDouble = channelFeeDouble;
	}
	
	public double getSettlementAmountDouble() {
		return settlementAmountDouble;
	}

	public void setSettlementAmountDouble(double settlementAmountDouble) {
		this.settlementAmountDouble = settlementAmountDouble;
	}
	
	public String getBatchNoStr() {
		return batchNoStr;
	}

	public void setBatchNoStr(String batchNoStr) {
		this.batchNoStr = batchNoStr;
	}

	public String getAccountTypeStr() {
		return accountTypeStr;
	}

	public void setAccountTypeStr(String accountTypeStr) {
		this.accountTypeStr = accountTypeStr;
	}
}
