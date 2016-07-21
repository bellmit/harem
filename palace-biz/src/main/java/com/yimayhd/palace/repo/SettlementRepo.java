package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.pay.client.model.query.settlement.SettlementQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;
import com.yimayhd.pay.client.service.settlement.SettlementService;
/**
 * 结算管理Repo
 * 
 * @author hongfei.guo
 *
 */
public class SettlementRepo{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected SettlementService settlementServiceRef;
	
	/**
	 * 查询所有结算信息
	 * @param query
	 * @return
	 */
	public PayPageResultDTO<SettlementDTO> querySettlements(SettlementQuery query) {
		RepoUtils.requestLog(log, "settlementServiceRef.querySettlements", query);
		PayPageResultDTO<SettlementDTO> result = null;//settlementServiceRef.querySettlements(query);
		RepoUtils.requestLog(log, "settlementServiceRef.querySettlements", result);
		return result;
	}
	
	/**
	 * 查询达人结算详细信息
	 * @param query
	 * @return
	 */
	public PayPageResultDTO<SettlementDTO> querySettlementDetails(SettlementQuery query) {
		RepoUtils.requestLog(log, "settlementServiceRef.querySettlementDetails", query);
		PayPageResultDTO<SettlementDTO> result = null;//settlementServiceRef.querySettlementDetails(query);
		RepoUtils.requestLog(log, "settlementServiceRef.querySettlementDetails", result);
		return result;
	}
	
	/**
	 * 结算失败重新提交
	 * @param query
	 * @return
	 */
	public ResultSupport settlementFailRetry(SettlementDTO settlement) {
		RepoUtils.requestLog(log, "settlementServiceRef.settlementFailRetry", settlement);
		ResultSupport result = null;//settlementServiceRef.settlementFailRetry(settlement);
		RepoUtils.requestLog(log, "settlementServiceRef.settlementFailRetry", result);
		return result;
	}
}
