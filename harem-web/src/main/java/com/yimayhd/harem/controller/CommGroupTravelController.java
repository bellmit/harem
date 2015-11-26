package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/groupTravel")
public class CommGroupTravelController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private ScenicSpotService scenicSpotService;

	/**
	 * 基本信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
	public String baseInfo() throws Exception {
		return "/system/comm/groupTravel/baseInfo";
	}

	/**
	 * 行程信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tripInfo", method = RequestMethod.GET)
	public String triInfo() throws Exception {
		return "/system/comm/groupTravel/tripInfo";
	}

	/**
	 * 价格信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/priceInfo", method = RequestMethod.GET)
	public String priceInfo() throws Exception {
		return "/system/comm/groupTravel/priceInfo";
	}

	/**
	 * 选择交通
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTraffic")
	public String selectTraffic() throws Exception {
		return "/system/comm/groupTravel/selectTraffic";
	}

	/**
	 * 选择餐厅
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRestaurant")
	public String selectRstaurant() throws Exception {
		return "/system/comm/groupTravel/selectRestaurant";
	}

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
	 * 选择景区
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenicSpot")
	public String selectScnicSpot() throws Exception {
		return "/system/comm/groupTravel/selectScenicSpot";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/comm/groupTravel/selectHotel";
	}
}
