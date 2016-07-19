package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.domain.audit.PayAuditResultDO;

/**
 * Created by hongfei.guo on 2016/07/19.
 */
public class PayAuditResultVO extends PayAuditResultDO {

	private static final long serialVersionUID = 1L;
    
    public static PayAuditResultVO getPayAuditResultVO(PayAuditResultDO payAuditResultDO){
    	if(payAuditResultDO == null){
    		return null;
    	}
    	
        PayAuditResultVO payAuditResultVO = new PayAuditResultVO();
        BeanUtils.copyProperties(payAuditResultDO, payAuditResultVO);
        return payAuditResultVO;
    }
}
