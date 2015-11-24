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
	public String baseInfo(ProductInfoVO productInfoVO) throws Exception {
		return "/system/comm/groupTravel/baseInfo";
	}

	/**
	 * 价格信息2
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/priceInfo", method = RequestMethod.GET)
	public String priceInfo() throws Exception {
		return "/system/comm/groupTravel/priceInfo";
	}
}
