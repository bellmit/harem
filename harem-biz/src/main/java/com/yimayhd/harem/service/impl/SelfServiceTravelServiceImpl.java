package com.yimayhd.harem.service.impl;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.harem.model.travel.selfServiceTravel.SelfServiceTravel;
import com.yimayhd.harem.service.SelfServiceTravelService;
import com.yimayhd.ic.client.model.result.item.LineResult;

public class SelfServiceTravelServiceImpl extends TravelServiceImpl<SelfServiceTravel>
		implements SelfServiceTravelService {

	@Override
	public void saveOrUpdate(SelfServiceTravel selfServiceTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SelfServiceTravel getById(long id) throws Exception {
		return getById(id, SelfServiceTravel.class);
	}

	@Override
	protected SelfServiceTravel createTravelInstance(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		SelfServiceTravel travel = new SelfServiceTravel();
		travel.init(lineResult, comTagDOs);
		return travel;
	}

}
