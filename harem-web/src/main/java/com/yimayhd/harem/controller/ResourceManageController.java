package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.AreaService;
import com.yimayhd.harem.base.AreaType;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.service.RestaurantService;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.result.ICResult;

/**
 * 资源管理
 * 
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceManage")
public class ResourceManageController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private RestaurantRPCService restaurantRPCService;

	@RequestMapping(value = "/restaurant/list", method = RequestMethod.GET)
	public String restaurantList(RestaurantListQuery restaurantListQuery) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantService.getListByPage(restaurantListQuery);
		put("pageVo", pageVo);
		put("query", restaurantListQuery);
		return "/system/resource/restaurant/list";
	}

	@RequestMapping(value = "/restaurant/{id}", method = RequestMethod.GET)
	public String restaurant(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantService.getById(id);
		put("restaurant", restaurant);
		return "/system/resource/restaurant/Info";
	}
	
	/**
	 * 新增餐厅（资源）
	 * 
	 * @return 餐厅（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/restaurant/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/restaurant/edit";
	}
	
	
	@RequestMapping(value = "/restaurant/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(RestaurantDO restaurantDO, MasterRecommend recommend, String name2) throws Exception {
		
		ResponseVo responseVo = new ResponseVo();
		recommend.setName(name2);
		
		restaurantDO.setRecommend(JSON.toJSONString(recommend));
		ICResult<Boolean> icResult = restaurantRPCService.addRestaurant(restaurantDO);
		boolean result = icResult.isSuccess();
		if (!result) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}
	
	@RequestMapping(value = "/restaurant/list2", method = RequestMethod.GET)
	public String restaurantList2(RestaurantListQuery restaurantListQuery) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantRPCService.pageQueryRestaurant(restaurantListQuery);
		//System.out.println(JSON.toJSONString(pageVo));
		put("pageVo", pageVo);
		put("query", restaurantListQuery);
		return "/system/restaurant/list";
	}
	
	@RequestMapping(value = "/restaurant/updateStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateRoleStatus(
			@PathVariable("id") int id,
			@RequestParam(value = "restaurantStatus", required = true) Integer restaurantStatus)
			throws Exception {

		RestaurantDO restaurantDO = new RestaurantDO();
		restaurantDO.setId(id);
		restaurantDO.setStatus(restaurantStatus);
		ICResult<Boolean> icResult = restaurantRPCService.updateRestaurant(restaurantDO);

		ResponseVo responseVo = new ResponseVo();
		if (!icResult.getModule()) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	@RequestMapping(value = "/restaurant2/{id}", method = RequestMethod.GET)
	public String restaurant2(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantService.getById(id);
		put("restaurant", restaurant);
		return "/system/restaurant/view";
	}
}
