package com.yimayhd.harem.service;

import com.yimayhd.harem.model.Region;

import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
public interface RegionService {
    List<Region> getProvince()throws Exception;
    List<Region> getRegionByParentId(long id) throws Exception;
}
