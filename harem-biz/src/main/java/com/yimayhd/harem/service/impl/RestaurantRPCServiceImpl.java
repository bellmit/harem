package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.harem.util.LogUtil;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

public class RestaurantRPCServiceImpl implements RestaurantRPCService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	@Autowired
	private ResourcePublishService resourcePublishService;

	@Override
	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantListQuery restaurantListQuery) {

		RestaurantPageQuery restaurantPageQuery = new RestaurantPageQuery();
		restaurantPageQuery.setNeedCount(true);
		restaurantPageQuery.setPageNo(restaurantListQuery.getPageNumber());
		restaurantPageQuery.setPageSize(restaurantListQuery.getPageSize());
		// 酒店名称
		restaurantPageQuery.setName(restaurantListQuery.getName());
		// 状态
		restaurantPageQuery.setStatus(restaurantListQuery.getStatus());
		// // 创建时间
		if (!StringUtils.isBlank(restaurantListQuery.getBeginTime())) {
			Date startTime = DateUtil.parseDate(restaurantListQuery.getBeginTime());
			restaurantPageQuery.setStartTime(startTime);
		}
		// // 结束时间
		if (!StringUtils.isBlank(restaurantListQuery.getEndTime())) {
			Date endTime = DateUtil.parseDate(restaurantListQuery.getEndTime());
			restaurantPageQuery.setEndTime(DateUtil.add23Hours(endTime));
		}
		LogUtil.requestLog(log, "itemQueryServiceRef.pageQueryRestaurant", restaurantPageQuery);
		ICPageResult<RestaurantDO> icPageResult = itemQueryServiceRef.pageQueryRestaurant(restaurantPageQuery);
		LogUtil.resultLog(log, "itemQueryServiceRef.pageQueryRestaurant", icPageResult);
		int totalCount = icPageResult.getTotalCount();
		List<RestaurantDO> restaurantDOList = new ArrayList<RestaurantDO>();
		if (CollectionUtils.isNotEmpty(icPageResult.getList())) {
			restaurantDOList.addAll(icPageResult.getList());
		}
		return new PageVO<RestaurantDO>(restaurantPageQuery.getPageNo(), restaurantPageQuery.getPageSize(), totalCount,
				restaurantDOList);

	}

	@Override
	public ICResult<Boolean> updateRestaurant(RestaurantDO restaurantDO) {

		return resourcePublishService.updateRestaurant(restaurantDO);
	}

	@Override
	public RestaurantDO getRestaurantById(long id) {
		LogUtil.requestLog(log, "itemQueryServiceRef.getRestaurant", id);
		ICResult<RestaurantDO> restaurant = itemQueryServiceRef.getRestaurant(id);
		LogUtil.resultLog(log, "itemQueryServiceRef.getRestaurant", restaurant);
		return restaurant.getModule();
	}

	@Override
	public ICResult<Boolean> addRestaurant(RestaurantDO restaurantDO) {
		LogUtil.requestLog(log, "resourcePublishService.addRestaurant");
		ICResult<Boolean> restaurant = resourcePublishService.addRestaurant(restaurantDO);
		LogUtil.resultLog(log, "resourcePublishService.addRestaurant", restaurant);
		return restaurant;
	}

}
