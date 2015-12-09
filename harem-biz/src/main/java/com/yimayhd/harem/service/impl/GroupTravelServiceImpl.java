package com.yimayhd.harem.service.impl;

import java.util.List;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.service.GroupTravelService;
import com.yimayhd.ic.client.model.result.item.LineResult;

public class GroupTravelServiceImpl extends TravelServiceImpl<GroupTravel> implements GroupTravelService {

	@Override
	public void saveOrUpdate(GroupTravel groupTravel) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public GroupTravel getById(long id) throws Exception {
		return getById(id, GroupTravel.class);
	}

	@Override
	protected GroupTravel createTravelInstance(LineResult lineResult, List<ComTagDO> comTagDOs) throws Exception {
		GroupTravel travel = new GroupTravel();
		travel.init(lineResult, comTagDOs);
		return travel;
	}

}
