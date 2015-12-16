package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class RestaurantRPCServiceImpl implements RestaurantRPCService {

    @Autowired
    private ItemQueryService itemQueryService;

	@Override
	public PageVO<RestaurantDO> pageQueryHotel(
			RestaurantListQuery restaurantListQuery) {
		
		RestaurantPageQuery restaurantPageQuery = new RestaurantPageQuery();
		restaurantPageQuery.setNeedCount(true);
    	restaurantPageQuery.setPageNo(restaurantListQuery.getPageNumber());
    	restaurantPageQuery.setPageSize(restaurantListQuery.getPageSize());
    	
    	ICPageResult<RestaurantDO> icPageResult = itemQueryService.pageQueryRestaurant(restaurantPageQuery);
		
    	List<RestaurantDO> restaurantDOList = icPageResult.getList();
    	PageVO<RestaurantDO> pageVo = new PageVO<RestaurantDO>(restaurantPageQuery.getPageNo(), restaurantPageQuery.getPageSize(), icPageResult.getTotalCount(), restaurantDOList);
    	
    	return pageVo;
	}

}
