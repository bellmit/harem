package com.yimayhd.palace.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.PayAuditOrderVO;
import com.yimayhd.palace.model.PayAuditResultVO;
import com.yimayhd.palace.model.query.AuditQuery;


public interface AuditService {
	
	/**
	 * 渠道对账进度查询
	 * @param query
	 * @return
	 */
	PageVO<PayAuditResultVO> queryAuditProgress(AuditQuery query) throws Exception;
	
	/**
	 * 渠道对账汇总查询
	 * @param query
	 * @return
	 */
	List<PayAuditResultVO> queryAuditResult(AuditQuery query) throws Exception;
	
	/**
	 * 渠道对账明细查询
	 * @param query
	 * @return
	 */
	PageVO<PayAuditOrderVO> queryAuditOrder(AuditQuery query) throws Exception;
	
	/**
	 * 下载对账单
	 * @param query
	 * @return
	 */
	void downloadAuditOrder(HttpServletResponse response, AuditQuery query) throws Exception;
}
