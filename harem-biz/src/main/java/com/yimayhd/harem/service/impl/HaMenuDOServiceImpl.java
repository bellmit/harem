package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaMenuDOMapper;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.service.HaMenuDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 菜单表
 * @author czf
 */
@Service
public class HaMenuDOServiceImpl extends BaseServiceImpl<HaMenuDO> implements HaMenuDOService{

    @Autowired
    private HaMenuDOMapper haMenuMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haMenuMapper);
    }
}
