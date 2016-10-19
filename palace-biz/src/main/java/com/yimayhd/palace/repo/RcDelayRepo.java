package com.yimayhd.palace.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.resourcecenter.domain.RcDelayPush;
import com.yimayhd.resourcecenter.model.enums.RcDelayType;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.model.result.ResultSupport;
import com.yimayhd.resourcecenter.service.RcDelayPushClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuxp on 2016/10/13.
 */
public class RcDelayRepo {

    @Autowired
    private RcDelayPushClientService rcDelayPushClientServiceRef;

    private Logger logger = LoggerFactory.getLogger(RcDelayRepo.class);

    public RcDelayPush insertMsg(RcDelayPush rcDelayPush) {
        if(null==rcDelayPush) {
            logger.error("RcDelayRepo insertMsg param is null");
            return null;
        }
        try {
            RcResult<RcDelayPush> result = rcDelayPushClientServiceRef.insertMsg(rcDelayPush);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo insertMsg rcDelayPush={}, result={}", JSON.toJSONString(rcDelayPush), JSON.toJSONString(result));
                return null;
            }
            return result.getT();
        } catch (Exception e) {
            logger.error("RcDelayRepo insertMsg rcDelayPush={}, exception={}", JSON.toJSONString(rcDelayPush), e);
            return null;
        }
    }

    public RcDelayPush insertPush(RcDelayPush rcDelayPush) {
        if(null==rcDelayPush) {
            logger.error("RcDelayRepo insertPush param is null");
            return null;
        }
        try {
            RcResult<RcDelayPush> result = rcDelayPushClientServiceRef.insertPush(rcDelayPush);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo insertPush rcDelayPush={}, result={}", JSON.toJSONString(rcDelayPush), JSON.toJSONString(result));
                return null;
            }
            return result.getT();
        } catch (Exception e) {
            logger.error("RcDelayRepo insertPush rcDelayPush={}, exception={}", JSON.toJSONString(rcDelayPush), e);
            return null;
        }
    }

    public boolean cancelMsg(long id) {
        if(0==id) {
            logger.error("RcDelayRepo cancelMsg param is null");
            return false;
        }
        try {
            ResultSupport result = rcDelayPushClientServiceRef.cancel(id, RcDelayType.MSG.getCode());
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo cancelMsg id={}, result={}", id, JSON.toJSONString(result));
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("RcDelayRepo cancelMsg id={}, exception={}", id, e);
            return false;
        }
    }

    public boolean cancelPush(long id) {
        if(0==id) {
            logger.error("RcDelayRepo cancelPush param is null");
            return false;
        }
        try {
            ResultSupport result = rcDelayPushClientServiceRef.cancel(id, RcDelayType.PUSH.getCode());
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo cancelPush id={}, result={}", id, JSON.toJSONString(result));
                return false;
            }
            return true;
        } catch (Exception e) {
            logger.error("RcDelayRepo cancelPush id={}, exception={}", id, e);
            return false;
        }
    }

    public RcDelayPush updateMsg(RcDelayPush rcDelayPush) throws Exception{
        if(null==rcDelayPush) {
            logger.error("RcDelayRepo updateMsg param is null");
            return null;
        }
        try {
            RcResult<RcDelayPush> result = rcDelayPushClientServiceRef.updateMsg(rcDelayPush);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo updateMsg rcDelayPush={}, result={}", JSON.toJSONString(rcDelayPush), JSON.toJSONString(result));
                return null;
            }
            return result.getT();
        } catch (Exception e) {
            logger.error("RcDelayRepo updateMsg rcDelayPush={}, exception={}", JSON.toJSONString(rcDelayPush), e);
            return null;
        }
    }

    public RcDelayPush updatePush(RcDelayPush rcDelayPush) {
        if(null==rcDelayPush) {
            logger.error("RcDelayRepo updatePush param is null");
            return null;
        }
        try {
            RcResult<RcDelayPush> result = rcDelayPushClientServiceRef.updatePush(rcDelayPush);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo updatePush rcDelayPush={}, result={}", JSON.toJSONString(rcDelayPush), JSON.toJSONString(result));
                return null;
            }
            return result.getT();
        } catch (Exception e) {
            logger.error("RcDelayRepo updatePush rcDelayPush={}, exception={}", JSON.toJSONString(rcDelayPush), e);
            return null;
        }
    }

    public RCPageResult<RcDelayPush> listMsg(RcDelayPushPageQuery rcDelayPushPageQuery) {
        if(null==rcDelayPushPageQuery) {
            logger.error("RcDelayRepo listMsg param is null");
            return null;
        }
        try {
            RCPageResult<RcDelayPush> result = rcDelayPushClientServiceRef.listMsg(rcDelayPushPageQuery);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo listMsg rcDelayPushPageQuery={}, result={}", JSON.toJSONString(rcDelayPushPageQuery), JSON.toJSONString(result));
                return null;
            }
            return result;
        } catch (Exception e) {
            logger.error("RcDelayRepo listMsg rcDelayPushPageQuery={}, exception={}", JSON.toJSONString(rcDelayPushPageQuery), e);
            return null;
        }
    }

    public RCPageResult<RcDelayPush> listPush(RcDelayPushPageQuery rcDelayPushPageQuery) {
        if(null==rcDelayPushPageQuery) {
            logger.error("RcDelayRepo listPush param is null");
            return null;
        }
        try {
            RCPageResult<RcDelayPush> result = rcDelayPushClientServiceRef.listPush(rcDelayPushPageQuery);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo listPush rcDelayPushPageQuery={}, result={}", JSON.toJSONString(rcDelayPushPageQuery), JSON.toJSONString(result));
                return null;
            }
            return result;
        } catch (Exception e) {
            logger.error("RcDelayRepo listPush rcDelayPushPageQuery={}, exception={}", JSON.toJSONString(rcDelayPushPageQuery), e);
            return null;
        }
    }

    public RcDelayPush getById(long id) {
        if(0==id) {
            logger.error("RcDelayRepo getById param is null");
            return null;
        }
        try {
            RcResult<RcDelayPush> result = rcDelayPushClientServiceRef.getById(id);
            if(null==result||!result.isSuccess()) {
                logger.error("RcDelayRepo getById id={}, result={}", id, JSON.toJSONString(result));
                return null;
            }
            return result.getT();
        } catch (Exception e) {
            logger.error("RcDelayRepo getById id={}, exception={}", id, e);
            return null;
        }
    }
}
