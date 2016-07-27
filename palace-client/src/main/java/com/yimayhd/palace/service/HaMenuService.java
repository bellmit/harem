package com.yimayhd.palace.service;

import com.yimayhd.palace.base.BaseService;
import com.yimayhd.palace.model.HaMenuDO;

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

    /**
     * 根据用户ID获取权限url
     * @param id
     * @return
     * @throws Exception
     */
    List<HaMenuDO> getUrlListByUserId(long id)throws Exception;
    
    List<HaMenuDO> getMenuList()throws Exception;
    /**
     * 同一权限调用，调用membercenter的权限接口
     * 根据用户id获取菜单权限列表
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    List<com.yimayhd.membercenter.client.domain.HaMenuDO> getMenuListByUserIdFromCatch(long userId)throws Exception;

    /**
     * 更新用户菜单缓存
     * @param token
     * @return
     * @throws Exception
     */
    boolean cacheMenuListByUserId(String token)throws Exception;

}
