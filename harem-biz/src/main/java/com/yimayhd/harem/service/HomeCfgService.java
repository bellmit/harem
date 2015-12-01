package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.HomeBaseVO;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
 * 首页配置信息
 * @author xusq
 *
 */
public interface HomeCfgService {

	public RcResult<Boolean> addVipList(HomeBaseVO homeVipVO);
	
	public List<HomeBaseVO> getHomeVipList();

	public RcResult<Boolean> addLineList(HomeBaseVO homeBaseVO);

	public RcResult<Boolean> addTravelKaList(HomeBaseVO homeBaseVO);

	public RcResult<Boolean> addCityList(HomeBaseVO homeBaseVO);

	public RcResult<Boolean> addTravelSpecialList(HomeBaseVO homeBaseVO);
}
