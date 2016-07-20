package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.EleAccBalanceResultVO;
import com.yimayhd.palace.model.EleAccountBillVO;
import com.yimayhd.palace.model.query.AccountQuery;


public interface AccountService {
	
	/**
	 * 账户余额查询
	 * @param query
	 * @return
	 */
	public EleAccBalanceResultVO queryEleAccBalance(AccountQuery query) throws Exception;
	
	/**
	 * 账户余额明细
	 * @param query
	 * @return
	 */
	public PageVO<EleAccountBillVO> queryEleAccBillDetail(AccountQuery query) throws Exception;
}
