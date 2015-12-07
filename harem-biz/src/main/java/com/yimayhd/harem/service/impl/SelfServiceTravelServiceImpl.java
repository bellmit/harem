package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.travel.selfServiceTravel.SelfServiceTravel;
import com.yimayhd.harem.service.SelfServiceTravelService;

public class SelfServiceTravelServiceImpl extends TravelServiceImpl implements SelfServiceTravelService {

	@Override
	public void saveOrUpdate(SelfServiceTravel selfServiceTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public SelfServiceTravel getById(long id) throws Exception {
		id = 3;
		return getById(id, SelfServiceTravel.class);
	}

}
