package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.model.Region;
import com.yimayhd.resourcecenter.model.enums.RegionType;

/**
 * Created by Administrator on 2015/11/13.
 */
public interface RegionService {
	List<Region> getProvince() throws Exception;

	List<Region> getRegionByParentId(long id) throws Exception;

	/**
	 * 获取地区
	 * 
	 * @return
	 * @throws BaseException
	 * @throws Exception
	 */
	List<Region> getRegions(RegionType regionType) throws BaseException;

}
