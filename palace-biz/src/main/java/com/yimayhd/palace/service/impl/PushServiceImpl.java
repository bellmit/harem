package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description
 */
public class PushServiceImpl implements PushService {
    private static final Logger log = LoggerFactory.getLogger(PushServiceImpl.class);
    @Override
    public PageVO<BoothVO> getList(BaseQuery baseQuery) throws Exception {
        return null;
    }

    @Override
    public BizResult<Boolean> saveOrUpdate(PushVO pushVO) throws Exception {
        return null;
    }
}
