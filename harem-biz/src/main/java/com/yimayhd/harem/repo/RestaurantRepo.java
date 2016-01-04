package com.yimayhd.harem.repo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ResourcePublishService;

/**
 * 资源-餐厅Repo
 * 
 * @author yebin
 *
 */
public class RestaurantRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private ResourcePublishService resourcePublishService;

	public PageVO<RestaurantDO> pageQueryRestaurant(RestaurantPageQuery query) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.pageQueryRestaurant", query);
		ICPageResult<RestaurantDO> icPageResult = itemQueryServiceRef.pageQueryRestaurant(query);
		RepoUtils.resultLog(log, "itemQueryServiceRef.pageQueryRestaurant", icPageResult);
		int totalCount = icPageResult.getTotalCount();
		List<RestaurantDO> restaurantDOList = new ArrayList<RestaurantDO>();
		if (CollectionUtils.isNotEmpty(icPageResult.getList())) {
			restaurantDOList.addAll(icPageResult.getList());
		}
		return new PageVO<RestaurantDO>(query.getPageNo(), query.getPageSize(), totalCount, restaurantDOList);

	}

	public void updateRestaurant(RestaurantDO restaurantDO) {
		RepoUtils.requestLog(log, "resourcePublishService.updateRestaurant");
		ICResult<Boolean> result = resourcePublishService.updateRestaurant(restaurantDO);
		RepoUtils.resultLog(log, "resourcePublishService.updateRestaurant", result);
	}

	public RestaurantDO getRestaurantById(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getRestaurant", id);
		ICResult<RestaurantDO> restaurant = itemQueryServiceRef.getRestaurant(id);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getRestaurant", restaurant);
		return restaurant.getModule();
	}

	public void addRestaurant(RestaurantDO restaurantDO) {
		RepoUtils.requestLog(log, "resourcePublishService.addRestaurant");
		ICResult<Boolean> restaurant = resourcePublishService.addRestaurant(restaurantDO);
		RepoUtils.resultLog(log, "resourcePublishService.addRestaurant", restaurant);
	}
}
