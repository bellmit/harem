package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.DelayPushPageQuery;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.resourcecenter.domain.RcDelayPush;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;

/**
 * Created by liuxp on 2016/10/13.
 */
public interface RcDelayPushService {

    public PageVO<PushVO> getPushList(DelayPushPageQuery delayPushPageQuery);

    public PushVO insertPush(PushVO pushVO) throws Exception;

    public boolean cancelPush(long id);

    public PushVO updatePush(PushVO pushVO) throws Exception;

    public PushVO  getById(long id) ;
}
