package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.pay.client.model.domain.audit.PayAuditOrderDO;
import com.yimayhd.pay.client.model.domain.audit.PayAuditResultDO;
import com.yimayhd.pay.client.model.query.audit.AuditOrderQuery;
import com.yimayhd.pay.client.model.query.audit.AuditProgressQuery;
import com.yimayhd.pay.client.model.query.audit.AuditResultQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.audit.AuditResult;
import com.yimayhd.pay.client.service.audit.AuditService;

/**
 * 对账管理Repo
 * 
 * @author hongfei.guo
 *
 */
public class AuditRepo{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AuditService auditServiceRef;
	
	/**
	 * 渠道对账进度查询
	 * @param auditProgressQuery
	 * @return
	 */
	public PayPageResultDTO<PayAuditResultDO> queryAuditProgress(AuditProgressQuery query) {
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditProgress", query);
		PayPageResultDTO<PayAuditResultDO> result = auditServiceRef.queryAuditProgress(query);
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditProgress", result);
		return result;
	}
	
	/**
	 * 渠道对账汇总查询
	 * @param auditResultQuery
	 * @return
	 */
	public AuditResult queryAuditResult(AuditResultQuery query) {
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditResult", query);
		AuditResult result = auditServiceRef.queryAuditResult(query);
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditResult", result);
		return result;
	}
	
	/**
	 * 渠道对账明细查询
	 * @param auditOrderQuery
	 * @return
	 */
	public PayPageResultDTO<PayAuditOrderDO> queryAuditOrder(AuditOrderQuery query) {
		
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditOrder", query);
		PayPageResultDTO<PayAuditOrderDO> result = auditServiceRef.queryAuditOrder(query);
		RepoUtils.requestLog(log, "auditServiceRef.queryAuditOrder", result);
		return result;
	}
}
