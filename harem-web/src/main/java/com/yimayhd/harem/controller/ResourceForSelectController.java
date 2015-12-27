package com.yimayhd.harem.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.harem.model.query.CommodityListQuery;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.query.TravelkaPageQuery;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
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
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private TripService tripService;

	/**
	 * 选择景点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody ResponseVo queryRestaurantForSelect(RestaurantListQuery query) {
		PageVO<RestaurantDO> pageVo = restaurantService.pageQueryRestaurant(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
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
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, ScenicPageQuery scenicPageQuery,
			Integer pageNumber) throws Exception {
		if (pageNumber != null) {
			scenicPageQuery.setPageNo(pageNumber);
		}
		PageVO<ScenicDO> pageVo = scenicService.getList(scenicPageQuery);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", scenicPageQuery);
		return new ResponseVo(result);
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
	 * 选择活动商品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneActivityComm")
	public String selectOneActivityComm() throws Exception {
		return "/system/resource/forSelect/selectOneActivityComm";
	}

	/**
	 * 选择活动商品
	 * 
	 * @return
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityComm")
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, CommodityListQuery commodityListQuery)
			throws Exception {
		PageVO<ItemVO> pageVO = commodityService.getList(commodityListQuery);
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("itemTypeList", itemTypeList);
		result.put("pageVo", pageVO);
		result.put("query", commodityListQuery);
		return new ResponseVo(result);
	}

	/**
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody ResponseVo queryHotelForSelect(HotelListQuery query) throws Exception {
		PageVO<HotelDO> pageVo = hotelService.pageQueryHotel(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
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
	public @ResponseBody ResponseVo queryUserForSelect(UserDOPageQuery query, Integer pageNumber) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
		PageVO<UserDO> pageVo = userService.getUserListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
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
	public @ResponseBody ResponseVo queryTravelKaForSelect(TravelkaPageQuery query) throws Exception {
		Integer pageNumber = getInteger("pageNumber");
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
		PageVO<TravelKaVO> pageVo = userService.getTravelKaListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
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

	@RequestMapping(value = "/selectMustBuy")
	public String selectMustBuy() throws Exception {
		return "/system/resource/forSelect/selectMustBuy";
	}
	
	/**
	* @Title: listMustBuy 
	* @Description:(获取买必推荐列表) 
	* @author create by yushengwei @ 2015年12月27日 下午4:23:41 
	* @param @param regionIntroduceQuery
	* @param @return
	* @param @throws Exception 
	* @return ResponseVo 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/listMustBuy")
	public @ResponseBody ResponseVo listMustBuy(RegionIntroduceQuery query,Integer pageNumber,Integer pageSize) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		} else {
			query.setPageNo(BaseQuery.DEFAULT_PAGE);
		}
		if(pageSize!= null) {
			query.setPageSize(pageSize);
		} else{
			query.setPageSize(BaseQuery.DEFAULT_SIZE);
		}
		PageVO<RegionIntroduceDO> pageVo = tripService.getPageRegionIntroduceDO(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	@RequestMapping(value = "/selectLive")
	public String selectLive() throws Exception {
		return "/system/resource/forSelect/selectLive";
	}

}
