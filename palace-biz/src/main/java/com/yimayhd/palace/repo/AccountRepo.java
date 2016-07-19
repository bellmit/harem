package com.yimayhd.palace.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBalanceQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccBalanceResult;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccBalanceDTO;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;
import com.yimayhd.pay.client.service.eleaccount.EleAccBillService;

/**
 * 账户管理Repo
 * 
 * @author hongfei.guo
 *
 */
public class AccountRepo{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected EleAccBillService eleAccBillServiceRef;
	
	/**
	 * 账户余额查询
	 * @param query
	 * @return
	 */
	public PageVO<EleAccBalanceDTO> queryEleAccBalance(EleAccBalanceQuery query) {
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBalance", query);
		EleAccBalanceResult result = eleAccBillServiceRef.queryEleAccBalance(query);
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBalance", result);
		int count = result.getTotalCount();
		List<EleAccBalanceDTO> list = result.getEleAccBalanceDTOList();
		if (list == null) {
			list = new ArrayList<EleAccBalanceDTO>();
		}
		return new PageVO<EleAccBalanceDTO>(query.getPageNo(), query.getPageSize(), count, list);
	}
	
	/**
	 * 账户余额明细
	 * @param query
	 * @return
	 */
	public PageVO<EleAccountBillDTO> queryEleAccBillDetail(EleAccBillDetailQuery query) {
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", query);
		PayPageResultDTO<EleAccountBillDTO> result = eleAccBillServiceRef.queryEleAccBillDetail(query);
		RepoUtils.requestLog(log, "eleAccBillServiceRef.queryEleAccBillDetail", result);
		int count = result.getTotalCount();
		List<EleAccountBillDTO> list = result.getList();
		if (list == null) {
			list = new ArrayList<EleAccountBillDTO>();
		}
		return new PageVO<EleAccountBillDTO>(query.getPageNo(), query.getPageSize(), count, list);
	}
	
}
