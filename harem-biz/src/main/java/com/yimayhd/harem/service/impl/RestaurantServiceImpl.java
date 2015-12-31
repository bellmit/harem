package com.yimayhd.harem.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.RestaurantVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.repo.RestaurantRepo;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;

public class RestaurantServiceImpl implements RestaurantService {
	@Autowired
	private RestaurantRepo restaurantRepo;

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
		return restaurantRepo.pageQueryRestaurant(restaurantPageQuery);

	}

	@Override
	public RestaurantDO getRestaurantById(long id) {
		return restaurantRepo.getRestaurantById(id);
	}

	@Override
	public void publish(RestaurantVO restaurantVO) {
		long id = restaurantVO.getId();
		if (id > 0) {
			RestaurantDO restaurantDO = getRestaurantById(id);
			RestaurantDO restaurantDTO = restaurantVO.toRestaurantDO(restaurantDO);
			restaurantRepo.updateRestaurant(restaurantDTO);
		} else {
			restaurantRepo.addRestaurant(restaurantVO.toRestaurantDO());
		}
	}

	@Override
	public void changeStatus(long id, int status) {
		// TODO 暂不实现
	}
}
