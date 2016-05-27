package com.yimayhd.palace.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.query.DestinationQuery;
import com.yimayhd.palace.service.DestinationRPCService;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;

public class DestinationRPCServiceImpl implements DestinationRPCService {

	private static final Logger log = LoggerFactory.getLogger(DestinationRPCServiceImpl.class);

    @Autowired
    private DestinationService destinationServiceRef;

	/**
	 * 查询目的地列表
	 * @param destinationQuery
	 * @return
	 */
	public RcResult<List<DestinationDO>> queryDestinationList(DestinationQuery query){
		
		DestinationQueryDTO queryDTO = new DestinationQueryDTO();
		if (query.getLevel() != 0) {			
			queryDTO.setLevel(query.getLevel());
		}
		
		if (query.getOutType() != 0) {			
			queryDTO.setOutType(query.getOutType());
		}
		
		if (query.getCode() != 0) {			
			queryDTO.setCode(query.getCode());
		}
		
		if (query.getParentCode() != 0) {			
			queryDTO.setParentCode(query.getParentCode());
		}
		
		query.setPageSize(Constant.DEFAULT_PAGE_MAX_SIZE);		
		RcResult<List<DestinationDO>> result = destinationServiceRef.queryDestinationList(queryDTO);
		return result;
	};
}
