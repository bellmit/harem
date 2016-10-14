package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;

/**
 * Created by liuxp on 2016/10/13.
 */
public interface RcDelayPushService {

    public PageVO<PushVO> getPushList(RcDelayPushPageQuery rcDelayPushPageQuery);

    public PushVO insertPush(PushVO pushVO);

    public boolean cancelPush(long id);

    public PushVO updatePush(PushVO pushVO);
}
