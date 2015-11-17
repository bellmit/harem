package com.yimayhd.harem.service.impl;

import java.util.List;
import java.util.Map;

import com.yimayhd.harem.common.Page;
import com.yimayhd.harem.common.PageRequest;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.service.RestaurantService;

/**
 * 餐厅服务实例
 * 
 * @author yebin
 *
 */
public class RestaurantServiceImpl implements RestaurantService {

	@Override
	public Page<Restaurant> getList(PageRequest pageRequest) throws Exception {
		int totalCount = count(pageRequest.getFilters());
		Page<Restaurant> page = new Page<Restaurant>(pageRequest, totalCount);
		List<Restaurant> itemList = find(pageRequest);
		page.setItemList(itemList);
		return page;
	}

	@Override
	public Restaurant getById(Long id) throws Exception {
		
		return null;
	}

	/**
	 * 查找总数
	 * 
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	protected Integer count(Map<String, Object> filters) throws Exception {
		return 1000;
	}

	protected List<Restaurant> find(PageRequest pageRequest) throws Exception {
		return null;
	}

}
