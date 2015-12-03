package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaUrlMapper;
import com.yimayhd.harem.model.HaUrlDO;
import com.yimayhd.harem.service.HaUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * controller路径表
 * @author czf
 */
//@Service
public class HaUrlServiceImpl extends BaseServiceImpl<HaUrlDO> implements HaUrlService{

    @Autowired
    private HaUrlMapper haUrlMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haUrlMapper);
    }
}
