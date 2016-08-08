package com.yimayhd.palace.mapper;

import com.yimayhd.palace.base.BaseMapper;
import com.yimayhd.palace.model.HaMenuDO;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
public interface HaMenuMapper extends BaseMapper<HaMenuDO>{

    List<HaMenuDO> getMenuListByUserId(long id)throws Exception;
    List<HaMenuDO> getUrlListByUserId(long id)throws Exception;
    List<HaMenuDO> getMenuList() throws Exception;
}
