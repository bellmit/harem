package com.yimayhd.harem.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;

/**
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/goupTravel")
public class CommGroupTravelController extends BaseController {
	/**
	 * 基本信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public String info(Long id) throws Exception {
		put("query", new ArrayList<Object>());
		return "/system/comm/groupTravel/info";
	}
}
