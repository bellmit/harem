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
		id = 7;
		// TODO YEBIN 通过ID获取跟团游对象
		return getById(id, GroupTravel.class);
	}
}
