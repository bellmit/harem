package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.domain.audit.PayAuditOrderDO;

/**
 * Created by Administrator on 2015/12/8.
 */
public class PayAuditOrderVO extends PayAuditOrderDO {
	
	private static final long serialVersionUID = 1L;
    
    public static PayAuditOrderVO getPayAuditOrderVO(PayAuditOrderDO payAuditOrderDO){
    	if(payAuditOrderDO == null){
    		return null;
    	}
    	
    	PayAuditOrderVO payAuditOrderVO = new PayAuditOrderVO();
        BeanUtils.copyProperties(payAuditOrderDO, payAuditOrderVO);
        return payAuditOrderVO;
    }
}
