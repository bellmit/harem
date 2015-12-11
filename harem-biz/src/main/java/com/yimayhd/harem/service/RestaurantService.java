package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

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
	PageVO<RestaurantDO> getListByPage(RestaurantListQuery query) throws Exception;

	/**
	 * 获取指定ID餐厅信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	RestaurantDO getById(Long id) throws Exception;
}
