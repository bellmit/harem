package com.yimayhd.harem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.model.Restaurant;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantService;

/**
 * 餐厅服务实例
 * 
 * @author yebin
 *
 */
public class RestaurantServiceImpl implements RestaurantService { 

	@Override
	public PageVO<Restaurant> getList(RestaurantListQuery query) throws Exception {
		int totalCount = count(query);
		PageVO<Restaurant> page = new PageVO<Restaurant>(query.getPageNumber(), query.getPageSize(), totalCount);
		List<Restaurant> itemList = find(query);
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

	protected List<Restaurant> find(RestaurantListQuery query) throws Exception {
		List<Restaurant> restaurants = new ArrayList<Restaurant>();
		for (Long i = 0L; i < query.getPageSize(); i++) {
			restaurants.add(getById(i));
		}
		return restaurants;
	}

}
