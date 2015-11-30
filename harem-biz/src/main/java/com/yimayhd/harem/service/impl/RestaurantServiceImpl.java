package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

/**
 * 餐厅服务实例
 * 
 * @author yebin
 *
 */
public class RestaurantServiceImpl implements RestaurantService {
	private List<RestaurantDO> table = new ArrayList<RestaurantDO>();

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
	public RestaurantDO getById(Long id) throws Exception {
		RestaurantDO restaurant = new RestaurantDO();
		restaurant.setId(id);
		restaurant.setName("怡心园" + id);
		restaurant.setPictures("餐厅图片");
		restaurant.setPictures("餐厅图片");
		restaurant.setLogoUrl("餐厅图片");
		restaurant.setContactPerson("Tracy");
		restaurant.setContactPhone("18611111111");
		restaurant.setLocationProvinceId(1);
		restaurant.setLocationProvinceName("云南");
		restaurant.setLocationCityId(2);
		restaurant.setLocationCityName("昆明");
		restaurant.setStatus(1);
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
		return query(table, query).size();
	}

	protected List<RestaurantDO> find(RestaurantListQuery query) throws Exception {
		int fromIndex = query.getPageSize() * (query.getPageNumber() - 1);
		int toIndex = query.getPageSize() * query.getPageNumber();
		List<RestaurantDO> result = query(table, query);
		if (result.size() > 0) {
			if (toIndex > result.size()) {
				toIndex = result.size();
			}
			result = result.subList(fromIndex, toIndex);
		}
		return result;
	}

	private List<RestaurantDO> query(List<RestaurantDO> restaurants, RestaurantListQuery query) {
		List<RestaurantDO> result = new ArrayList<RestaurantDO>();
		for (RestaurantDO restaurant : restaurants) {
			if (StringUtils.isNotBlank(query.getName())) {
				if (restaurant.getName().indexOf(query.getName()) != -1) {
					result.add(restaurant);
				}
			} else {
				result.add(restaurant);
			}
		}
		return result;
	}

	@Override
	public PageVO<RestaurantDO> getListByPage(RestaurantListQuery query) throws Exception {
		int totalCount = count(query);
		PageVO<RestaurantDO> page = new PageVO<RestaurantDO>(query.getPageNumber(), query.getPageSize(), totalCount);
		List<RestaurantDO> itemList = find(query);
		page.setItemList(itemList);
		return page;
	}

}
