package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.SettlementVO;
import com.yimayhd.palace.model.query.SettlementQuery;


public interface SettlementService {
	
	/**
	 * 账户余额查询
	 * @param query
	 * @return
	 */
	PageVO<SettlementVO> querySettlements(SettlementQuery query) throws Exception;
	
	/**
	 * 账户余额明细
	 * @param query
	 * @return
	 */
	PageVO<SettlementVO> querySettlementDetails(SettlementQuery query) throws Exception;
	
	/**
	 * 账户余额明细
	 * @param query
	 * @return
	 */
	boolean settlementFailRetry(SettlementVO settlementVO) throws Exception;
}
