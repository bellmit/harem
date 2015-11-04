package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseServiceImpl;
import com.yimayhd.harem.mapper.HaMenuMapper;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.service.HaMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
//@Service
public class HaMenuServiceImpl extends BaseServiceImpl<HaMenuDO> implements HaMenuService{

    @Autowired
    private HaMenuMapper haMenuMapper;

    @Override
    protected void initBaseMapper() {
        setBaseMapper(haMenuMapper);
    }

    @Override
    public List<HaMenuDO> getMenuListByUserId(long id) throws Exception {
        return haMenuMapper.getMenuListByUserId(id);
    }
}
