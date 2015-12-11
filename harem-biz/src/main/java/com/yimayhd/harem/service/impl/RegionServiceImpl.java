package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.Region;
import com.yimayhd.harem.service.RegionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public class RegionServiceImpl implements RegionService {
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
}
