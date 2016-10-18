package com.yimayhd.palace.service.impl;

import com.google.common.base.Strings;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.RcDelayPushConverter;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.repo.RcDelayRepo;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.service.RcDelayPushService;
import com.yimayhd.palace.util.HandleFilesUtils;
import com.yimayhd.resourcecenter.domain.RcDelayPush;
import com.yimayhd.resourcecenter.model.enums.RcDelayType;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.stone.enums.DomainType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by liuxp on 2016/10/13.
 */
public class RcDelayPushServiceImpl implements RcDelayPushService {
    private Logger logger = LoggerFactory.getLogger(RcDelayPushServiceImpl.class);

    @Autowired
    private RcDelayRepo rcDelayRepo;
    @Value("${palace.tfsRootPath}")
    private String tfsRootPath ;

    @Resource
    private PushService pushService;
    @Override
    public PageVO<PushVO> getPushList(RcDelayPushPageQuery rcDelayPushPageQuery) {

        rcDelayPushPageQuery.setNeedCount(true);
        rcDelayPushPageQuery.setType(RcDelayType.PUSH.getCode());
        RCPageResult<RcDelayPush> result = rcDelayRepo.listPush(rcDelayPushPageQuery);
        if(null==result) {
            return null;
        }
        return new PageVO<PushVO>(rcDelayPushPageQuery.getPageNo(), rcDelayPushPageQuery.getPageSize(), result.getTotalCount(), RcDelayPushConverter.convertToList(result.getList()));
    }

    @Override
    public PushVO insertPush(PushVO pushVO) {

        if(null==pushVO|| StringUtils.isEmpty(pushVO.getMsgTitle())||StringUtils.isEmpty(pushVO.getPushContent())||0==pushVO.getSendDomainId()||0==pushVO.getPushModelType()||StringUtils.isEmpty(pushVO.getPushDate())) {
            logger.error("RcDelayPushServiceImpl insertPush param is null!");
            return null;
        }
        pushVO.setDomain(DomainType.DOMAIN_JX.getType());
        //file
        if(!Strings.isNullOrEmpty(pushVO .getPushModelFilePath())){
            HandleFilesUtils handleFilesUtils = new HandleFilesUtils();
            Set<String> setString =   HandleFilesUtils.getDistinctStringFromTFS(tfsRootPath + pushVO.getPushModelFilePath());
        }
        RcDelayPush rcDelayPush = rcDelayRepo.insertPush(RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO));

        if(null==rcDelayPush) {
            return null;
        }
        pushVO.setId(rcDelayPush.getId());
        return pushVO;
    }

    @Override
    public boolean cancelPush(long id) {

        if(0==id) {
            logger.error("RcDelayPushServiceImpl cancelPush param is null!");
            return false;
        }

        return rcDelayRepo.cancelPush(id);
    }

    @Override
    public PushVO updatePush(PushVO pushVO) {
        if(null==pushVO|| StringUtils.isEmpty(pushVO.getMsgTitle())||StringUtils.isEmpty(pushVO.getPushContent())||0==pushVO.getSendDomainId()||0==pushVO.getPushModelType()||StringUtils.isEmpty(pushVO.getPushDate())) {
            logger.error("RcDelayPushServiceImpl updatePush param is null!");
            return null;
        }
        pushVO.setDomain(DomainType.DOMAIN_JX.getType());
        RcDelayPush rcDelayPush = rcDelayRepo.updatePush(RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO));
        if(null==rcDelayPush) {
            return null;
        }
        pushVO.setId(rcDelayPush.getId());
        return pushVO;
    }

    @Override
    public PushVO getById(final long id) {
        RcDelayPush rcDelayPush =  rcDelayRepo.getById(id);

        return RcDelayPushConverter.convertRcDelayPushToPushVo(rcDelayPush);
    }
}
