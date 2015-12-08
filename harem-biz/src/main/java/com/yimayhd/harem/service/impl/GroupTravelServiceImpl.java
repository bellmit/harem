package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.service.GroupTravelService;

public class GroupTravelServiceImpl extends TravelServiceImpl implements GroupTravelService {

	@Override
	public void saveOrUpdate(GroupTravel groupTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupTravel getById(long id) throws Exception {
		return getById(id, GroupTravel.class);
	}
}
