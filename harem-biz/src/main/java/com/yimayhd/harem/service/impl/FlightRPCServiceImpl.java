package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.domain.FlightCompanyDO;
import com.yimayhd.ic.client.model.query.FlightCompanyPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class FlightRPCServiceImpl implements com.yimayhd.harem.service.FlightRPCService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	private static final int TOTAL_SIZE = 10000;

	@Override
	public PageVO<FlightCompanyDO> pageQueryFlightCompany(FlightCompanyPageQuery query) {
		LogUtil.requestLog(log, "itemQueryServiceRef.queryFlightCompany", query);
		ICPageResult<FlightCompanyDO> result = itemQueryServiceRef.queryFlightCompany(query);
		LogUtil.icResultLog(log, "itemQueryServiceRef.queryFlightCompany", result);
		List<FlightCompanyDO> itemList = new ArrayList<FlightCompanyDO>();
		int totalCount = result.getTotalCount();
		if (CollectionUtils.isNotEmpty(result.getList())) {
			itemList.addAll(result.getList());
		}
		return new PageVO<FlightCompanyDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
	}

	@Override
	public List<FlightCompanyDO> findAllFlightCompany() {
		FlightCompanyPageQuery query = new FlightCompanyPageQuery();
		query.setPageNo(1);
		query.setPageSize(TOTAL_SIZE);
		return pageQueryFlightCompany(query).getItemList();
	}

}