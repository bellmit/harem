package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

public interface RestaurantRPCService {

	public PageVO<RestaurantDO> pageQueryHotel(RestaurantListQuery restaurantListQuery);
	
}
