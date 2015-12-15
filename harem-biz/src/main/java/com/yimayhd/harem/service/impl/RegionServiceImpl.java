package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionClientService;

/**
 * Created by Administrator on 2015/11/13.
 */
public class RegionServiceImpl implements RegionService {

	@Resource
	private RegionClientService regionClientServiceRef;

	@Override
	public List<Region> getProvince() throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		for (int i = 0; i < 5; i++) {
			Region regionData = new Region();
			regionData.setId((long) (i * 10 + 10));
			regionData.setName("省份" + i);
			regionList.add(regionData);
		}
		return regionList;
	}

	@Override
	public List<Region> getRegionByParentId(long id) throws Exception {
		List<Region> regionList = new ArrayList<Region>();
		for (int i = 0; i < 5; i++) {
			Region regionData = new Region();
			regionData.setId((long) (i * 10 + 100));
			regionData.setName("城市" + i);
			regionData.setParentId(id);
			regionList.add(regionData);
		}
		return regionList;
	}

	@Override
	public List<Region> getRegions(RegionType regionType) throws BaseException {
		List<Region> regions = new ArrayList<Region>();
		RCPageResult<RegionDO> pageResult = regionClientServiceRef.getRegionDOListByType(regionType.getType());
		if (pageResult != null && pageResult.isSuccess()) {
			List<RegionDO> regionDOs = pageResult.getList();
			if (CollectionUtils.isNotEmpty(regionDOs)) {
				for (RegionDO regionDO : regionDOs) {
					regions.add(new Region(regionDO));
				}
			}
		} else {
			throw new BaseException("获取地区信息失败：RegionType={0}", regionType);
		}
		return regions;
	}

}
