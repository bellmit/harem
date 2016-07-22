package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.EleAccBalanceResultVO;
import com.yimayhd.palace.model.EleAccBalanceVO;
import com.yimayhd.palace.model.EleAccountBillVO;
import com.yimayhd.palace.model.query.AccountQuery;
import com.yimayhd.palace.repo.AccountRepo;
import com.yimayhd.palace.service.AccountService;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBalanceQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.eleaccount.EleAccBalanceResult;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccBalanceDTO;
import com.yimayhd.pay.client.model.result.eleaccount.dto.EleAccountBillDTO;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class AccountServiceImpl implements AccountService {
	private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
    private AccountRepo accountRepo ;

	@Override
	public EleAccBalanceResultVO queryEleAccBalance(AccountQuery query) throws Exception {
		
		EleAccBalanceResultVO resultVO = new EleAccBalanceResultVO();
		
		try {
			Long.parseLong(query.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("AccountServiceImpl.queryEleAccBalance param is illegal : {}, and exception is {}", JSON.toJSONString(query), e);
			return resultVO;
		}
		
		resultVO.setEleAccBalanceVOPage(new PageVO<EleAccBalanceVO>());
		
		EleAccBalanceQuery queryDO = AccountQuery.getEleAccBalanceQuery(query);
		EleAccBalanceResult result = accountRepo.queryEleAccBalance(queryDO);
		if(result == null){
			log.error("accountRepo.queryEleAccBalance return value is null !returnValue : {}", JSON.toJSONString(result));
			return resultVO;
		}
		
		List<EleAccBalanceVO> listVO = new ArrayList<EleAccBalanceVO>();
		List<EleAccBalanceDTO> listDO = result.getEleAccBalanceDTOList();
		if(CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(EleAccBalanceVO.getEleAccBalanceVO(listDO.get(i)));
			}
		}
		
		PageVO<EleAccBalanceVO> pageVO = new PageVO<EleAccBalanceVO>(query.getPageNumber(), query.getPageSize(), result.getTotalCount(), listVO);
		resultVO.setEleAccBalanceVOPage(pageVO);
		resultVO.setTotalAmount(result.getTotalAmount());
		return resultVO;
	}

	@Override
	public PageVO<EleAccountBillVO> queryEleAccBillDetail(AccountQuery query) throws Exception {
		
		
		EleAccBillDetailQuery queryDO = AccountQuery.getEleAccBillDetailQuery(query);
		PayPageResultDTO<EleAccountBillDTO> result = accountRepo.queryEleAccBillDetail(queryDO);
		if(result == null){
			log.error("accountRepo.queryEleAccBillDetail return value is null !returnValue : {}", JSON.toJSONString(result));
			return new PageVO<EleAccountBillVO>();
		}
		
		List<EleAccountBillVO> listVO = new ArrayList<EleAccountBillVO>();
		List<EleAccountBillDTO> listDO = result.getList();
		if(CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(EleAccountBillVO.getEleAccountBillVO(listDO.get(i)));
			}
		}
		return new PageVO<EleAccountBillVO>(query.getPageNumber(), query.getPageSize(), result.getTotalCount(), listVO);
	}
	
}
