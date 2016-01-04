package com.yimayhd.harem.repo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.util.RepoUtils;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.query.RestaurantPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;

/**
 * 资源-餐厅Repo
 * 
 * @author yebin
 *
 */
public class RestaurantRepo extends ResourceRepo {

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

	public boolean updateRestaurant(RestaurantDO restaurantDO) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.updateRestaurant");
		ICResult<Boolean> result = resourcePublishServiceRef.updateRestaurant(restaurantDO);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.updateRestaurant", result);
		return result.getModule();
	}

	public RestaurantDO getRestaurantById(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getRestaurant", id);
		ICResult<RestaurantDO> restaurant = itemQueryServiceRef.getRestaurant(id);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getRestaurant", restaurant);
		return restaurant.getModule();
	}

	public boolean addRestaurant(RestaurantDO restaurantDO) {
		RepoUtils.requestLog(log, "resourcePublishServiceRef.addRestaurant");
		ICResult<Boolean> restaurant = resourcePublishServiceRef.addRestaurant(restaurantDO);
		RepoUtils.resultLog(log, "resourcePublishServiceRef.addRestaurant", restaurant);
		return restaurant.getModule();
	}
}
