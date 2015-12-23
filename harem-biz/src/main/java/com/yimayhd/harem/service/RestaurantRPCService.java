package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.result.ICResult;

public interface RestaurantRPCService {

	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantListQuery restaurantListQuery);

	/**
	 * 更新餐厅资源
	 */
	public ICResult<Boolean> updateRestaurant(RestaurantDO restaurantDO);

	public RestaurantDO getRestaurantBy(long id);
	
	public  ICResult<Boolean> addRestaurant(RestaurantDO restaurantDO);
}
