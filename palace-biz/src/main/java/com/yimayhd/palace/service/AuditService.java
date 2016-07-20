package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.AuditResultVO;
import com.yimayhd.palace.model.PayAuditOrderVO;
import com.yimayhd.palace.model.PayAuditResultVO;
import com.yimayhd.palace.model.query.AuditQuery;


public interface AuditService {
	
	/**
	 * 渠道对账进度查询
	 * @param auditProgressQuery
	 * @return
	 */
	PageVO<PayAuditResultVO> queryAuditProgress(AuditQuery query) throws Exception;
	
	/**
	 * 渠道对账汇总查询
	 * @param query
	 * @return
	 */
	List<AuditResultVO> queryAuditResult(AuditQuery query) throws Exception;
	
	/**
	 * 渠道对账明细查询
	 * @param auditOrderQuery
	 * @return
	 */
	PageVO<PayAuditOrderVO> queryAuditOrder(AuditQuery query) throws Exception;
}
