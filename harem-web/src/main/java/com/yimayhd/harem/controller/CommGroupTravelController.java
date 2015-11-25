package com.yimayhd.harem.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.model.ProductInfoVO;

/**
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/groupTravel")
public class CommGroupTravelController extends BaseController {
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
}
