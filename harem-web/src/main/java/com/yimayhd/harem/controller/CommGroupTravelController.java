package com.yimayhd.harem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.model.query.RestaurantListQuery;
import com.yimayhd.harem.model.query.ScenicSpotListQuery;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.service.GroupTravelService;
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
	private GroupTravelService groupTravelService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "id") long id) throws Exception {
		if (id != 0) {
			GroupTravel gt = groupTravelService.getById(id);
			put("product", gt);
		}
		return "/system/comm/travel/groupTravel/detail";
	}

	/**
	 * 创建跟团游
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create() throws Exception {
		return "/system/comm/travel/groupTravel/detail";
	}

	/**
	 * 基本信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/baseInfo", method = RequestMethod.GET)
	public String baseInfo() throws Exception {
		return "/system/comm/travel/baseInfo";
	}

	/**
	 * 行程信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/tripInfo", method = RequestMethod.GET)
	public String tripInfo() throws Exception {
		return "/system/comm/travel/groupTravel/tripInfo";
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
		return "/system/comm/travel/priceInfo";
	}

	/**
	 * 批量录入
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert() throws Exception {
		return "/system/comm/travel/batchInsert";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody GroupTravel save(String json) throws Exception {
		GroupTravel groupTravel = JSON.parse(json, GroupTravel.class);
		groupTravelService.save(groupTravel);
		return groupTravel;
	}

	/**
	 * 选择交通
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTraffic")
	public String selectTraffic() throws Exception {
		return "/system/comm/travel/groupTravel/selectTraffic";
	}
}
