package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.model.query.DestinationQuery;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.model.result.RcResult;

public interface DestinationRPCService {

	/**
	 * 查询目的地列表
	 * @param destinationQuery
	 * @return
	 */
	public RcResult<List<DestinationDO>> queryDestinationList(DestinationQuery destinationQuery);
}
