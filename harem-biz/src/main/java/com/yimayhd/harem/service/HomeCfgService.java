package com.yimayhd.harem.service;

import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.harem.model.vo.HomeResultVO;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
 * 首页配置信息
 * @author xusq
 *
 */
public interface HomeCfgService {

	/**
	 * 添加会员专享信息
	 */
	public RcResult<Boolean> addVipList(HomeBaseVO homeVipVO);

	/**
	 * 添加线路信息
	 */
	public RcResult<Boolean> addLineList(HomeBaseVO homeBaseVO);

	/**
	 * 添加旅游咖信息
	 */
	public RcResult<Boolean> addTravelKaList(HomeBaseVO homeBaseVO);

	/**
	 * 添加目的地信息
	 */
	public RcResult<Boolean> addCityList(HomeBaseVO homeBaseVO);

	/**
	 * 添加游记信息
	 */
	public RcResult<Boolean> addTravelSpecialList(HomeBaseVO homeBaseVO);
	
	/**
	 * 获取会员专享信息
	 */
	public HomeResultVO getVipList();
	
	/**
	 * 获取线路信息
	 */
	public HomeResultVO getLineList();
	
	/**
	 * 获取旅游咖信息
	 */
	public HomeResultVO getTravelKaList();
	
	/**
	 * 获取目的地信息
	 */
	public HomeResultVO getCityList();
	
	/**
	 * 获取游记信息
	 */
	public HomeResultVO getTravelSpecialList();
	
}
