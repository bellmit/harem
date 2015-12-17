package com.yimayhd.harem.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
    	
    	// 酒店名称
    	if (StringUtils.isEmpty(restaurantListQuery.getName())) {   		
    		restaurantPageQuery.setName(restaurantListQuery.getName());
    	}
    	// 状态
    	if (restaurantListQuery.getStatus() != 0) {
    		restaurantPageQuery.setStatus(restaurantListQuery.getStatus());
    	}
    	// 联系人
    	if (StringUtils.isEmpty(restaurantListQuery.getPersonOrPhone())) {
    		restaurantPageQuery.setPersonOrPhone(restaurantListQuery.getPersonOrPhone());
    	}
    	// 创建时间
    	if (StringUtils.isEmpty(restaurantListQuery.getBeginTime())) {
    		restaurantPageQuery.setStartTime(restaurantListQuery.getBeginTime());
    	}
    	// 结束时间
    	if (StringUtils.isEmpty(restaurantListQuery.getEndTime())) {
    		restaurantPageQuery.setEndTime(restaurantListQuery.getEndTime());
    	}
    	
    	ICPageResult<RestaurantDO> icPageResult = itemQueryService.pageQueryRestaurant(restaurantPageQuery);
		
    	List<RestaurantDO> restaurantDOList = icPageResult.getList();
    	PageVO<RestaurantDO> pageVo = new PageVO<RestaurantDO>(restaurantPageQuery.getPageNo(), restaurantPageQuery.getPageSize(), icPageResult.getTotalCount(), restaurantDOList);
    	
    	return pageVo;
	}

}
