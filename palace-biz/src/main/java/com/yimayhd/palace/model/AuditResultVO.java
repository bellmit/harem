package com.yimayhd.palace.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.pay.client.model.result.audit.dto.AuditResultDTO;

/**
 * Created by hongfei.guo on 2016/07/19.
 */
public class AuditResultVO extends AuditResultDTO {

	private static final long serialVersionUID = 1L;

    public static AuditResultVO getAuditResultVO(AuditResultDTO auditResultDTO){
    	if(auditResultDTO == null){
    		return null;
    	}
    	
        AuditResultVO auditResultVO = new AuditResultVO();
        BeanUtils.copyProperties(auditResultDTO, auditResultVO);
        return auditResultVO;
    }
}
