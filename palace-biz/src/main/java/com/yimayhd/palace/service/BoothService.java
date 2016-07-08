package com.yimayhd.palace.service;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.resourcecenter.domain.AppVersionDO;
import com.yimayhd.resourcecenter.model.query.AppVersionQuery;

import java.util.List;

/**
 * Created by czf on 2016/4/13.
 */
public interface BoothService {
    /**
     * 获取booth列表
     * @return
     * @throws Exception
     */
    PageVO<BoothVO> getList(BaseQuery baseQuery)throws Exception;

    /**
     * 新增booth
     * @param entity
     * @return
     * @throws Exception
     */
    BoothVO add(BoothVO entity)throws Exception;

    BoothVO saveOrUpdate(BoothVO entity)throws Exception;

    List<AppVersionDO> queryAppVersionList(AppVersionQuery appVersionQuery);

    BoothVO get(long id)throws Exception;

    PageVO<AppVersionDO> getAppVersionList(AppVersionQuery query)throws Exception;

}
