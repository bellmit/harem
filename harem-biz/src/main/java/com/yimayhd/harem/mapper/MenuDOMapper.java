package com.yimayhd.harem.mapper;

import com.yimayhd.harem.model.MenuDO;

public interface MenuDOMapper {

    int deleteByPrimaryKey(long id);

    int insertSelective(MenuDO record);

    MenuDO selectByPrimaryKey(long id);

    int updateByPrimaryKey(MenuDO record);
}