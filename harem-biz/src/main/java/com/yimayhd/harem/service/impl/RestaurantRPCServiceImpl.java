package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
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
		// 联系人
		restaurantPageQuery.setPersonOrPhone(restaurantListQuery.getPersonOrPhone());
		// 创建时间
		restaurantPageQuery.setStartTime(restaurantListQuery.getBeginTime());
		// 结束时间
		restaurantPageQuery.setEndTime(restaurantListQuery.getEndTime());
		LogUtil.requestLog(log, "itemQueryServiceRef.pageQueryRestaurant", restaurantPageQuery);
		ICPageResult<RestaurantDO> icPageResult = itemQueryServiceRef.pageQueryRestaurant(restaurantPageQuery);
		LogUtil.icResultLog(log, "itemQueryServiceRef.pageQueryRestaurant", icPageResult);
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
	public RestaurantDO getRestaurantBy(long id) {
		LogUtil.requestLog(log, "itemQueryServiceRef.getRestaurant", id);
		ICResult<RestaurantDO> restaurant = itemQueryServiceRef.getRestaurant(id);
		LogUtil.icResultLog(log, "itemQueryServiceRef.getRestaurant", restaurant);
		return restaurant.getModule();
	}

	@Override
	public ICResult<Boolean> addRestaurant(RestaurantDO restaurantDO) {
		
		ICResult<Boolean> restaurant = resourcePublishService.addRestaurant(restaurantDO);
		
		if (restaurant != null && restaurant.isSuccess()) {
			return restaurant;
		} else {
			log.error("添加餐厅失败：restaurant=" + JSON.toJSONString(restaurantDO));
			throw new BaseException(restaurant.getResultMsg());
		}
		
	}

}
