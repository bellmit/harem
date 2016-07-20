package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class SettlementVO extends SettlementDTO{
	
	private static final long serialVersionUID = 1L;

	public static SettlementVO getSettlementVO(SettlementDTO settlementDTO){
    	if(settlementDTO == null){
    		return null;
    	}
    	
    	SettlementVO settlementVO = new SettlementVO();
        BeanUtils.copyProperties(settlementDTO, settlementVO);
        return settlementVO;
	}
	
	public static SettlementDTO getSettlementDTO(SettlementVO settlementVO){
    	if(settlementVO == null){
    		return null;
    	}
    	
    	SettlementDTO settlementDTO = new SettlementDTO();
        BeanUtils.copyProperties(settlementVO, settlementDTO);
        return settlementVO;
	}
}
