package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.HomeBaseVO;

/**
 * 首页配置信息
 * @author xusq
 *
 */
public interface HomeCfgService {

	public boolean addVipList(HomeBaseVO homeVipVO);
	
	public List<HomeBaseVO> getHomeVipList();

	public boolean addLineList(HomeBaseVO homeBaseVO);

	public boolean addTravelKaList(HomeBaseVO homeBaseVO);

	public boolean addCityList(HomeBaseVO homeBaseVO);

	public boolean addTravelSpecialList(HomeBaseVO homeBaseVO);
}
