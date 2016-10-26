package com.yimayhd.palace.service.impl;

import com.google.common.base.Strings;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.RcDelayPushConverter;
import com.yimayhd.palace.model.vo.DelayPushPageQuery;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.repo.RcDelayRepo;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.service.RcDelayPushService;
import com.yimayhd.palace.service.TfsService;
import com.yimayhd.palace.tair.CacheLockManager;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by liuxp on 2016/10/13.
 */
public class RcDelayPushServiceImpl implements RcDelayPushService {
    private Logger logger = LoggerFactory.getLogger(RcDelayPushServiceImpl.class);

    @Autowired
    private RcDelayRepo rcDelayRepo;
    @Value("${palace.tfsRootPath}")
    private String tfsRootPath;

    private static final int LIMIT_PUSH = 1000;

    @Autowired
    private CacheLockManager cacheLockManager;

    @Resource
    private TfsService tfsService;

    @Override
    public PageVO<PushVO> getPushList(DelayPushPageQuery delayPushPageQuery) {
        RcDelayPushPageQuery rcDelayPushPageQuery = new RcDelayPushPageQuery();
        rcDelayPushPageQuery.setPageNo(delayPushPageQuery.getPageNumber());
        rcDelayPushPageQuery.setPageSize(delayPushPageQuery.getPageSize());
        rcDelayPushPageQuery.setNeedCount(true);
        rcDelayPushPageQuery.setStatus(delayPushPageQuery.getStatus());
        //query
        rcDelayPushPageQuery.setTopicName(org.apache.commons.lang3.StringUtils.isEmpty(delayPushPageQuery.getTopicName()) ? "" : delayPushPageQuery.getTopicName());
        rcDelayPushPageQuery.setStarteTime(delayPushPageQuery.getStarteTime());
        Date endDate = delayPushPageQuery.getEndTime();
        if (null != endDate) {
            endDate.setHours(23);
            endDate.setMinutes(59);
            endDate.setSeconds(59);
        }
        rcDelayPushPageQuery.setEndTime(endDate);
        rcDelayPushPageQuery.setSendType(delayPushPageQuery.getSendType());
        rcDelayPushPageQuery.setType(delayPushPageQuery.getType());
        rcDelayPushPageQuery.setType(RcDelayType.PUSH.getCode());
        RCPageResult<RcDelayPush> result = rcDelayRepo.listPush(rcDelayPushPageQuery);
        if (null == result) {
            return null;
        }
        return new PageVO<PushVO>(rcDelayPushPageQuery.getPageNo(), rcDelayPushPageQuery.getPageSize(), result.getTotalCount(), RcDelayPushConverter.convertToList(result.getList()));
    }

    @Override
    public PushVO insertPush(PushVO pushVO) throws Exception {

        if (null == pushVO || StringUtils.isEmpty(pushVO.getSubject()) || StringUtils.isEmpty(pushVO.getPushContent()) || 0 == pushVO.getSendDomainId() || 0 == pushVO.getPushModelType() || StringUtils.isEmpty(pushVO.getPushDateStr())) {
            logger.error("RcDelayPushServiceImpl insertPush param is null!");
            throw new BaseException("参数不正确！");
        }
        Date d;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            d = sdf.parse(pushVO.getPushDateStr());
        } catch (ParseException e) {
            d = new Date();
        }
        if (d.before(new Date())) {
            logger.error("RcDelayPushServiceImpl insertPush sendDate is before currentDate");
            throw new BaseException("发送时间不得早于当前时间！");

        }
        pushVO.setDomain(DomainType.DOMAIN_JX.getType());
        //file
        transfFile(pushVO);
        RcDelayPush rcDelayPush = rcDelayRepo.insertPush(RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO));
        if (null == rcDelayPush) {
            return null;
        }
        pushVO.setId(rcDelayPush.getId());
        return pushVO;
    }

    @Override
    public boolean cancelPush(long id) {

        if (0 == id) {
            logger.error("RcDelayPushServiceImpl cancelPush param is null!");
            return false;
        }

        return rcDelayRepo.cancelPush(id);
    }

    private void transfFile(PushVO pushVO) throws BaseException {
        //file
        if (!Strings.isNullOrEmpty(pushVO.getPushModelFilePath())) {
            String fileName = pushVO.getPushModelFilePath();
            String newFileName = tfsService.tfsFileConvert(fileName, ".txt", true, LIMIT_PUSH);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(newFileName)) {
                pushVO.setTransformFileUrl(newFileName);
            }
        }
    }

    @Override
    public PushVO updatePush(PushVO pushVO) throws Exception {
        if (null == pushVO || StringUtils.isEmpty(pushVO.getSubject()) || StringUtils.isEmpty(pushVO.getPushContent()) || 0 == pushVO.getSendDomainId() || 0 == pushVO.getPushModelType() || StringUtils.isEmpty(pushVO.getPushDateStr())) {
            logger.error("RcDelayPushServiceImpl updatePush param is null!");
            throw new BaseException("参数不正确！");
        }
        Date d;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            d = sdf.parse(pushVO.getPushDateStr());
        } catch (ParseException e) {
            d = new Date();
        }
        if (d.before(new Date())) {
            logger.error("RcDelayPushServiceImpl updatePush sendDate is before currentDate");
            throw new BaseException("发送时间不得早于当前时间！");
        }

        pushVO.setDomain(DomainType.DOMAIN_JX.getType());
        //file
        transfFile(pushVO);
        RcDelayPush rcDelayPush = rcDelayRepo.updatePush(RcDelayPushConverter.convertPushVOToRcDelayPush(pushVO));
        if (null == rcDelayPush) {
            return null;
        }
        pushVO.setId(rcDelayPush.getId());
        return pushVO;
    }

    @Override
    public PushVO getById(final long id) {
        RcDelayPush rcDelayPush = rcDelayRepo.getById(id);

        return RcDelayPushConverter.convertRcDelayPushToPushVo(rcDelayPush);
    }
}
