package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseTravelController;
import com.yimayhd.harem.model.travel.selfServiceTravel.SelfServiceTravel;
import com.yimayhd.harem.service.SelfServiceTravelService;

/**
 * 商品-自由行
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/selfServiceTravel")
public class CommSelfServiceTravelController extends BaseTravelController {
	@Autowired
	private SelfServiceTravelService selfServiceTravelService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable(value = "id") long id) throws Exception {
		if (id > 0) {
			initBaseInfo();
			SelfServiceTravel product = selfServiceTravelService.getById(id);
			put("product", product);
		}
		return "/system/comm/travel/selfServiceTravel/detail";
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
		return "/system/comm/travel/selfServiceTravel/detail";
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
	 * 选择酒店
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/comm/travel/selectHotel";
	}

	/**
	 * 添加航班
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addFlight")
	public String addFlight() throws Exception {
		return "/system/comm/travel/selfServiceTravel/addFlight";
	}

	/**
	 * 填写航班信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setFlightInfo")
	public String setFlightInfo() throws Exception {
		return "/system/comm/travel/selfServiceTravel/setFlightInfo";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody SelfServiceTravel save(String json) throws Exception {
		SelfServiceTravel selfServiceTravel = JSON.parseObject(json, SelfServiceTravel.class);
		System.out.println(JSON.toJSONString(selfServiceTravel));
		selfServiceTravelService.saveOrUpdate(selfServiceTravel);
		return selfServiceTravel;
	}
}
