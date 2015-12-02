package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.model.query.ScenicSpotListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.harem.service.ScenicSpotService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private ScenicSpotService scenicSpotService;

	/**
	 * 选择景点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody Map<String, Object> queryRestaurantForSelect(RestaurantListQuery query) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择餐厅
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRestaurant")
	public String selectRstaurant() throws Exception {
		return "/system/resource/forSelect/selectRestaurant";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScenicSpotForSelect")
	public @ResponseBody Map<String, Object> queryScenicSpotForSelect(ScenicSpotListQuery query) throws Exception {
		PageVO<ScenicDO> pageVo = scenicSpotService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择景区
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenicSpot")
	public String selectScnicSpot() throws Exception {
		return "/system/resource/forSelect/selectScenicSpot";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody Map<String, Object> queryHotelForSelect(HotelListQuery query) throws Exception {
		PageVO<HotelDO> pageVo = hotelService.getListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/resource/forSelect/selectHotel";
	}
}
