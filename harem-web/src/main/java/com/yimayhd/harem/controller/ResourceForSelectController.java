package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.query.TravelkaPageQuery;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;

/**
 * 资源选择理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private RestaurantRPCService restaurantService;
	@Autowired
	private ScenicService scenicService;
	@Autowired
	private HotelRPCService hotelService;
	@Autowired
	private UserRPCService userService;

	/**
	 * 选择景点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody Map<String, Object> queryRestaurantForSelect(RestaurantListQuery query) {
		PageVO<RestaurantDO> pageVo = restaurantService.pageQueryRestaurant(query);
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
	 * 选择景区
	 * 
	 * @return
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScenicForSelect")
	public @ResponseBody Map<String, Object> queryScenicForSelect(Model model, ScenicPageQuery scenicPageQuery,
			Integer pageNumber) throws Exception {
		if (pageNumber != null) {
			scenicPageQuery.setPageNo(pageNumber);
		}
		PageVO<ScenicDO> pageVo = scenicService.getList(scenicPageQuery);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", scenicPageQuery);
		return result;
	}

	/**
	 * 选择景区
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenic")
	public String selectScenic() throws Exception {
		return "/system/resource/forSelect/selectScenic";
	}

	/**
	 * 选择景区
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneScenic")
	public String selectOneScenic() throws Exception {
		return "/system/resource/forSelect/selectOneScenic";
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody Map<String, Object> queryHotelForSelect(HotelListQuery query) throws Exception {
		PageVO<HotelDO> pageVo = hotelService.pageQueryHotel(query);
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

	/**
	 * 选择用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserForSelect")
	public @ResponseBody Map<String, Object> queryUserForSelect(UserDOPageQuery query) throws Exception {
		PageVO<UserDO> pageVo = userService.getUserListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择一个用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneUser")
	public String selectOneUser() throws Exception {
		return "/system/resource/forSelect/selectOneUser";
	}

	/**
	 * 选择用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser() throws Exception {
		return "/system/resource/forSelect/selectUser";
	}

	/**
	 * 选择旅游咖
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTravelKaForSelect")
	public @ResponseBody Map<String, Object> queryTravelKaForSelect(TravelkaPageQuery query) throws Exception {
		Integer pageNumber = getInteger("pageNumber");
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
		PageVO<TravelKaVO> pageVo = userService.getTravelKaListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return result;
	}

	/**
	 * 选择旅游咖
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneTravelKa")
	public String selectOneTravelKa() throws Exception {
		return "/system/resource/forSelect/selectOneTravelKa";
	}

	/**
	 * 选择一个旅游咖
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTravelKa")
	public String selectTravelKa() throws Exception {
		return "/system/resource/forSelect/selectTravelKa";
	}

}
