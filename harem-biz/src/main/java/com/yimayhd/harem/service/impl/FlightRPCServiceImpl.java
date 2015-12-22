package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.ic.client.model.domain.FlightCompanyDO;
import com.yimayhd.ic.client.model.query.FlightCompanyPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class FlightRPCServiceImpl implements com.yimayhd.harem.service.FlightRPCService {

	private static final Logger log = LoggerFactory.getLogger(FlightRPCServiceImpl.class);
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	private static final int TOTAL_SIZE = 10000;

	@Override
	public PageVO<FlightCompanyDO> pageQueryFlightCompany(FlightCompanyPageQuery query) {
		ICPageResult<FlightCompanyDO> result = itemQueryServiceRef.queryFlightCompany(query);
		List<FlightCompanyDO> itemList = new ArrayList<FlightCompanyDO>();
		int totalCount = 0;
		if (null != result && result.isSuccess()) {
			totalCount = result.getTotalCount();
			if (CollectionUtils.isNotEmpty(result.getList())) {
				itemList.addAll(result.getList());
			}
			return new PageVO<FlightCompanyDO>(query.getPageNo(), query.getPageSize(), totalCount, itemList);
		} else {
			log.error("检索航空公司失败：query={}", JSON.toJSONString(query));
			log.error("检索航空公司失败：result={}", JSON.toJSONString(result));
			throw new BaseException("检索航空公司失败");
		}
	}

	@Override
	public List<FlightCompanyDO> findAllFlightCompany() {
		FlightCompanyPageQuery query = new FlightCompanyPageQuery();
		query.setPageNo(1);
		query.setPageSize(TOTAL_SIZE);
		return pageQueryFlightCompany(query).getItemList();
	}

}
