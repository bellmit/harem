package com.yimayhd.harem.service;

import com.yimayhd.harem.model.vo.CfgBaseVO;
import com.yimayhd.harem.model.vo.CfgResultVO;
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
	public RcResult<Boolean> addVipList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加线路信息
	 */
	public RcResult<Boolean> addLineList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加旅游咖信息
	 */
	public RcResult<Boolean> addTravelKaList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加目的地信息
	 */
	public RcResult<Boolean> addCityList(CfgBaseVO cfgBaseVO);

	/**
	 * 添加游记信息
	 */
	public RcResult<Boolean> addTravelSpecialList(CfgBaseVO cfgBaseVO);
	
	/**
	 * 获取会员专享信息
	 */
	public CfgResultVO getVipList();
	
	/**
	 * 获取线路信息
	 */
	public CfgResultVO getLineList();
	
	/**
	 * 获取旅游咖信息
	 */
	public CfgResultVO getTravelKaList();
	
	/**
	 * 获取目的地信息
	 */
	public CfgResultVO getCityList();
	
	/**
	 * 获取游记信息
	 */
	public CfgResultVO getTravelSpecialList();
	
}
