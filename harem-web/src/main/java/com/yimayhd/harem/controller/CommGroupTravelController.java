package com.yimayhd.harem.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.service.ComCenterService;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.model.travel.groupTravel.TripTraffic;
import com.yimayhd.harem.service.GroupTravelService;
import com.yimayhd.ic.client.model.enums.LineOwnerType;

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
	@Resource
	private ComCenterService comCenterServiceRef;

	/*
	 * @Autowired private RegionClientService regionClientService;
	 */

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
			initBaseInfo();
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
		initBaseInfo();
		return "/system/comm/travel/groupTravel/detail";
	}

	private void initBaseInfo() {
		put("PT_DEFAULT", LineOwnerType.DEFAULT.getType());
		put("PT_MASTER", LineOwnerType.MASTER.getType());
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
		GroupTravel gt = (GroupTravel) JSONObject.parseObject(json, GroupTravel.class);
		System.out.println(JSON.toJSONString(gt));
		return gt;
	}

	/**
	 * 选择交通
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTraffic")
	public String selectTraffic() throws Exception {
		put("ways", TripTraffic.ways());
		return "/system/comm/travel/groupTravel/selectTraffic";
	}
}
