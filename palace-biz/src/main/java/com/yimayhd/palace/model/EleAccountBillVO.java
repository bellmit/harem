package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class EleAccountBillVO extends EleAccountBillDTO {

	private static final long serialVersionUID = 1L;
	
	public static EleAccountBillVO getEleAccountBillVO(EleAccountBillDTO eleAccountBillDTO){
    	if(eleAccountBillDTO == null){
    		return null;
    	}
    	
    	EleAccountBillVO eleAccountBillVO = new EleAccountBillVO();
        BeanUtils.copyProperties(eleAccountBillDTO, eleAccountBillVO);
        return eleAccountBillVO;
    }
}
