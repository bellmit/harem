package com.yimayhd.harem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
	private List<Restaurant> table = new ArrayList<Restaurant>();

	public RestaurantServiceImpl() {
		for (long i = 0; i < 100; i++) {
			try {
				table.add(getById(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
		restaurant.setName("怡心园" + id);
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
		return find(table, query).size();
	}

	protected List<Restaurant> find(RestaurantListQuery query) throws Exception {
		int fromIndex = query.getPageSize() * (query.getPageNumber() - 1);
		int toIndex = query.getPageSize() * query.getPageNumber();
		return find(table, query).subList(fromIndex, toIndex);
	}

	private List<Restaurant> find(List<Restaurant> restaurants, RestaurantListQuery query) {
		List<Restaurant> result = new ArrayList<Restaurant>();
		for (Restaurant restaurant : restaurants) {
			if (StringUtils.isNotBlank(query.getName()) && restaurant.getName().indexOf(query.getName()) != -1) {
				result.add(restaurant);
			}
		}
		return restaurants;
	}

}
