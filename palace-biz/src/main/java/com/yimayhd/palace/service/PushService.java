package com.yimayhd.palace.service;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ActivityVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.result.BizResult;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description
 */
public interface PushService {

    PageVO<BoothVO> getList(BaseQuery baseQuery)throws Exception;

    BizResult<Boolean> saveOrUpdate(PushVO pushVO) throws Exception;

}
