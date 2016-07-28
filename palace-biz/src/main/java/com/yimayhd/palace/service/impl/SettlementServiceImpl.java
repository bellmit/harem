package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.SettlementVO;
import com.yimayhd.palace.model.query.SettlementQuery;
import com.yimayhd.palace.repo.SettlementRepo;
import com.yimayhd.palace.service.SettlementService;
import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.ResultSupport;

/**
 * Created by hongfei.guo on 2016/07/20.
 */
public class SettlementServiceImpl implements SettlementService {
	private static final Logger log = LoggerFactory.getLogger(SettlementServiceImpl.class);
	
	@Autowired
    private SettlementRepo settlementRepo ;
	
	@Override
	public PageVO<SettlementVO> querySettlements(SettlementQuery query) throws Exception {
		
		com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = SettlementQuery.getSettlementDTO(query);
		PayPageResultDTO<SettlementDTO> result = settlementRepo.querySettlements(queryDO);
		if(result == null){
			log.error("settlementRepo.queryAuditProgress return value is null !returnValue : {}", JSON.toJSONString(result));
			return new PageVO<SettlementVO>();
		}
		
		List<SettlementVO> listVO = new ArrayList<SettlementVO>();
		List<SettlementDTO> listDO = result.getList();
		if(!CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(SettlementVO.getSettlementVO(listDO.get(i)));
			}
		}
		return new PageVO<SettlementVO>(query.getPageNumber(), query.getPageSize(), result.getTotalCount(), listVO);
	}

	@Override
	public PageVO<SettlementVO> querySettlementDetails(SettlementQuery query) throws Exception {
		
		com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = SettlementQuery.getSettlementDTO(query);
		PayPageResultDTO<SettlementDTO> result = settlementRepo.querySettlementDetails(queryDO);
		if(result == null){
			log.error("settlementRepo.queryAuditProgress return value is null !returnValue : {}", JSON.toJSONString(result));
			return new PageVO<SettlementVO>();
		}
		
		List<SettlementVO> listVO = new ArrayList<SettlementVO>();
		List<SettlementDTO> listDO = result.getList();
		if(!CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(SettlementVO.getSettlementVO(listDO.get(i)));
			}
		}
		return new PageVO<SettlementVO>(query.getPageNumber(), query.getPageSize(), result.getTotalCount(), listVO);
	}

	@Override
	public boolean settlementFailRetry(SettlementVO settlementVO) throws Exception {
		if(settlementVO == null){
			return false;
		}
		
		ResultSupport result = settlementRepo.settlementFailRetry(SettlementVO.getSettlementDTO(settlementVO));
		if(null == result){
			log.error("SettlementServiceImpl.settlementFailRetry-settlementRepo.settlementFailRetry result is null and parame: {}", JSON.toJSONString(settlementVO));
			throw new BaseException("修改返回结果为空,修改失败");
		} else if(!result.isSuccess()){
			log.error("SettlementServiceImpl.settlementFailRetry-settlementRepo.settlementFailRetry error: {} and parame: {}", JSON.toJSONString(result), JSON.toJSONString(settlementVO));
			throw new BaseException(result.getResultMsg());
		}
		return result.isSuccess();
	}
}
