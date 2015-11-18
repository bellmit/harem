package com.yimayhd.harem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.common.Page;
import com.yimayhd.harem.common.PageRequest;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantService;

/**
 * 餐厅服务实例
 * 
 * @author yebin
 *
 */
public class RestaurantServiceImpl implements RestaurantService {

	@Override
	public Page<Restaurant> getList(PageRequest<RestaurantListQuery> pageRequest) throws Exception {
		int totalCount = count(pageRequest.getQuery());
		Page<Restaurant> page = new Page<Restaurant>(pageRequest, totalCount);
		List<Restaurant> itemList = find(pageRequest);
		page.setItemList(itemList);
		return page;
	}

	@Override
	public Restaurant getById(Long id) throws Exception {
		Region province = new Region();
		province.setName("云南");
		province.setId(1L);
		Region city = new Region();
		city.setName("昆明");
		city.setId(2L);
		city.setParentId(province.getId());
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurant.setCode("YM00000000" + id);
		restaurant.setName("怡心园");
		restaurant.setImageUrl("餐厅图片");
		restaurant.setBasePrice(BigDecimal.valueOf(68));
		restaurant.setCity(city);
		restaurant.setContactName("Tracy");
		restaurant.setContactPhone("18611111111");
		restaurant.setProvince(province);
		restaurant.setState(1);
		return restaurant;
	}

	/**
	 * 查找总数
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	protected Integer count(RestaurantListQuery query) throws Exception {
		return 1000;
	}

	protected List<Restaurant> find(PageRequest<RestaurantListQuery> pageRequest) throws Exception {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		for (Long i = 0L; i < 10; i++) {
			restaurants.add(getById(i));
		}
		return restaurants;
	}

}
