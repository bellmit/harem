package com.yimayhd.harem.model;

import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;

/**
 * 餐厅表单对象
 * 
 * @author yebin
 *
 */
public class RestaurantForm {
	private RestaurantDO restaurant;
	private MasterRecommend recommend;

	public RestaurantDO getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantDO restaurant) {
		this.restaurant = restaurant;
	}

	public MasterRecommend getRecommend() {
		return recommend;
	}

	public void setRecommend(MasterRecommend recommend) {
		this.recommend = recommend;
	}
}
