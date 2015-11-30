package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.model.HomeVipVO;

/**
 * 首页配置信息
 * @author xusq
 *
 */
public interface HomeCfgService {

	public String addVipList(HomeVipVO homeVipVO);
	
	public List<HomeVipVO> getHomeVipList();
}
