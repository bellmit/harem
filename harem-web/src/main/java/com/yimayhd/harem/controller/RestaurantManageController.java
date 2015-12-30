package com.yimayhd.harem.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.RestaurantForm;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.service.RestaurantRPCService;
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
@RequestMapping("/B2C/restaurantManage")
public class RestaurantManageController extends BaseController {
	@Autowired
	private RestaurantRPCService restaurantRPCService;

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String restaurant(@PathVariable("id") Long id) throws Exception {
		RestaurantDO restaurant = restaurantRPCService.getRestaurantById(id);
		put("restaurant", restaurant);
		return "/system/restaurant/Info";
	}

	/**
	 * 新增餐厅（资源）
	 * 
	 * @return 餐厅（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/restaurant/edit";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) {
		RestaurantDO restaurantDO = restaurantRPCService.getRestaurantById(id);
		MasterRecommend recommend = restaurantDO.getRecommend();
		model.addAttribute("restaurant", restaurantDO);
		model.addAttribute("recommend", recommend);
		List<String> pictures = restaurantDO.getPictures();
		if (CollectionUtils.isNotEmpty(pictures)) {
			String coverPics = StringUtils.join(restaurantDO.getPictures(), "|");
			model.addAttribute("coverPics", coverPics);
		}
		return "/system/restaurant/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(RestaurantForm form) throws Exception {
		RestaurantDO restaurant = form.getRestaurant();
		restaurant.setRecommend(form.getRecommend());
		String coverPics = form.getCoverPics();
		if (StringUtils.isNotBlank(coverPics)) {
			restaurant.setPictures(Arrays.asList(StringUtils.split(coverPics)));
		}
		ICResult<Boolean> icResult = restaurantRPCService.addRestaurant(restaurant);
		ResponseVo responseVo = new ResponseVo();
		boolean result = icResult.isSuccess();
		if (!result) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		return responseVo;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String restaurantList(RestaurantListQuery restaurantListQuery) throws Exception {
		PageVO<RestaurantDO> pageVo = restaurantRPCService.pageQueryRestaurant(restaurantListQuery);
		// System.out.println(JSON.toJSONString(pageVo));
		put("pageVo", pageVo);
		put("query", restaurantListQuery);
		return "/system/restaurant/list";
	}

	@RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateRoleStatus(@PathVariable("id") int id,
			@RequestParam(value = "restaurantStatus", required = true) Integer restaurantStatus) throws Exception {

		RestaurantDO restaurantDO = new RestaurantDO();
		restaurantDO.setId(id);
		restaurantDO.setStatus(restaurantStatus);
		ICResult<Boolean> icResult = restaurantRPCService.updateRestaurant(restaurantDO);

		ResponseVo responseVo = new ResponseVo();
		if (!icResult.isSuccess()) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	/**
	 * 动态状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchUpdateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchUpdateStatus(@RequestParam("restaurantIdList[]") ArrayList<Integer> restaurantIdList,
			int restaurantStatus) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		for (Integer id : restaurantIdList) {
			RestaurantDO restaurantDO = new RestaurantDO();
			restaurantDO.setId(id);
			restaurantDO.setStatus(restaurantStatus);
			ICResult<Boolean> icResult = restaurantRPCService.updateRestaurant(restaurantDO);
			if (!icResult.isSuccess()) {
				responseVo.setStatus(ResponseStatus.ERROR.VALUE);
				return responseVo;
			}
		}
		return responseVo;
	}
}
