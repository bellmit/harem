package com.yimayhd.harem.mapper;

import com.yimayhd.harem.base.BaseMapper;
import com.yimayhd.harem.model.HaMenuDO;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
public interface HaMenuMapper extends BaseMapper<HaMenuDO>{

    List<HaMenuDO> getMenuListByUserId(long id)throws Exception;

}
