package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.AuditResultVO;
import com.yimayhd.palace.model.PayAuditOrderVO;
import com.yimayhd.palace.model.PayAuditResultVO;
import com.yimayhd.palace.model.query.AuditQuery;
import com.yimayhd.palace.repo.AuditRepo;
import com.yimayhd.palace.service.AuditService;
import com.yimayhd.pay.client.model.domain.audit.PayAuditOrderDO;
import com.yimayhd.pay.client.model.domain.audit.PayAuditResultDO;
import com.yimayhd.pay.client.model.query.audit.AuditOrderQuery;
import com.yimayhd.pay.client.model.query.audit.AuditProgressQuery;
import com.yimayhd.pay.client.model.query.audit.AuditResultQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.model.result.audit.AuditResult;
import com.yimayhd.pay.client.model.result.audit.dto.AuditResultDTO;

/**
 * Created by hongfei.guo on 2015/11/18.
 */
public class AuditServiceImpl implements AuditService {
	private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);
	
	@Autowired
    private AuditRepo auditRepo ;

	@Override
	public PageVO<PayAuditResultVO> queryAuditProgress(AuditQuery query) throws Exception {
		
		AuditProgressQuery queryDO = AuditQuery.getAuditProgressQuery(query);
		PayPageResultDTO<PayAuditResultDO> result = auditRepo.queryAuditProgress(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditProgress return value is null !returnValue :" + JSON.toJSONString(result));
			return new PageVO<PayAuditResultVO>();
		}
		
		List<PayAuditResultVO> listVO = new ArrayList<PayAuditResultVO>();
		List<PayAuditResultDO> listDO = result.getList();
		if(CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(PayAuditResultVO.getPayAuditResultVO(listDO.get(i)));
			}
		}
		return new PageVO<PayAuditResultVO>(1, 10, 10, listVO);
	}

	@Override
	public List<AuditResultVO> queryAuditResult(AuditQuery query) throws Exception {
		
		AuditResultQuery queryDO = AuditQuery.getAuditResultQuery(query);
		AuditResult result = auditRepo.queryAuditResult(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditResult return value is null !returnValue :" + JSON.toJSONString(result));
			return new ArrayList<AuditResultVO>();
		}
		
		List<AuditResultVO> listVO = new ArrayList<AuditResultVO>();
		List<AuditResultDTO> listDO = result.getAuditResultDTOList();
		if(CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(AuditResultVO.getAuditResultVO(listDO.get(i)));
			}
		}
		return listVO;
	}

	@Override
	public PageVO<PayAuditOrderVO> queryAuditOrder(AuditQuery query) throws Exception {
		
		AuditOrderQuery queryDO = AuditQuery.getAuditOrderQuery(query);
		PayPageResultDTO<PayAuditOrderDO> result = auditRepo.queryAuditOrder(queryDO);
		if(result == null){
			log.error("auditRepo.queryAuditProgress return value is null !returnValue :" + JSON.toJSONString(result));
			return new PageVO<PayAuditOrderVO>();
		}
		
		List<PayAuditOrderVO> listVO = new ArrayList<PayAuditOrderVO>();
		List<PayAuditOrderDO> listDO = result.getList();
		if(CollectionUtils.isEmpty(listDO)){
			for(int i = 0; i < listDO.size(); i++){
				listVO.add(PayAuditOrderVO.getPayAuditOrderVO(listDO.get(i)));
			}
		}
		return new PageVO<PayAuditOrderVO>(queryDO.getPageNo(), queryDO.getPageSize(), result.getTotalCount(), listVO);
	}
}
