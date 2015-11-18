package com.yimayhd.harem.service;

import com.yimayhd.harem.base.BaseService;
import com.yimayhd.harem.model.HaMenuDO;

import java.util.List;

/**
 * 菜单表
 * @author czf
 */
public interface HaMenuService extends BaseService<HaMenuDO>{

    /**
     * 根据用户ID获取菜单权限列表
     * @param id
     * @return
     * @throws Exception
     */
    List<HaMenuDO> getMenuListByUserId(long id)throws Exception;
    List<HaMenuDO> getMenuList()throws Exception;
}
