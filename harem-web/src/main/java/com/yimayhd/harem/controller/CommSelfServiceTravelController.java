package com.yimayhd.harem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
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
public class CommSelfServiceTravelController extends BaseController {
	@Autowired
	private SelfServiceTravelService selfServiceTravelService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail() throws Exception {
		SelfServiceTravel product = selfServiceTravelService.getById(1);
		put("product", product);
		return "/system/comm/travel/selfServiceTravel/detail";
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
	@RequestMapping(value = "/tripPackageInfo", method = RequestMethod.GET)
	public String tripPackageInfo() throws Exception {
		return "/system/comm/travel/selfServiceTravel/tripPackageInfo";
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
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody SelfServiceTravel save(String json) throws Exception {
		SelfServiceTravel selfServiceTravel = JSON.parseObject(json, SelfServiceTravel.class);
		System.out.println(JSON.toJSONString(selfServiceTravel));
		selfServiceTravelService.save(selfServiceTravel);
		return selfServiceTravel;
	}
}
