package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.query.TravelOfficialListQuery;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;

import java.util.List;

/**
 * Created by Administrator on 2015/11/10.
 */
public interface TravelOfficialService {
        /**
         * 获取官方游记列表(可带查询条件)
         * @return 官方游记列表
         */
        PageVO<SnsTravelSpecialtyDO> getList(TravelOfficialListQuery travelOfficialListQuery)throws Exception;
        /**
         * 获取官方游记详情
         * @return 官方游记详情
         */
        TravelOfficial getById(long id)throws Exception;

        /**
         * 添加官方游记
         * @param travelOfficial
         * @return
         * @throws Exception
         */
        TravelOfficial add(TravelOfficial travelOfficial)throws Exception;

        /**
         * 修改官方游记
         * @param travelOfficial
         * @throws Exception
         */
        void modify(TravelOfficial travelOfficial)throws Exception;
}
