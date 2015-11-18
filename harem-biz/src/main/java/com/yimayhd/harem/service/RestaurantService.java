package com.yimayhd.harem.service;

import com.yimayhd.harem.model.query.base.Page;
import com.yimayhd.harem.model.query.base.PageRequest;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.model.query.RestaurantListQuery;

/**
 * 餐厅服务
 * 
 * @author yebin
 *
 */
public interface RestaurantService {
	/**
	 * 分页获取餐厅列表
	 * 
	 * @param pageRequest
	 * @return
	 * @throws Exception
	 */
	Page<Restaurant> getList(PageRequest<RestaurantListQuery> pageRequest) throws Exception;

	/**
	 * 获取指定ID餐厅信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Restaurant getById(Long id) throws Exception;
}
