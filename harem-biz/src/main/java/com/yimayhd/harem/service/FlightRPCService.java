package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.ic.client.model.domain.FlightCompanyDO;
import com.yimayhd.ic.client.model.query.FlightCompanyPageQuery;

/**
 * 航班查询接口
 * 
 * @author yebin
 *
 */
public interface FlightRPCService {
	PageVO<FlightCompanyDO> pageQueryFlightCompany(FlightCompanyPageQuery query);

	List<FlightCompanyDO> findAllFlightCompany();
}
