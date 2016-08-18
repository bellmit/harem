package com.yimayhd.palace.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicPageQueryDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicUpdateDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.guide.GuideService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by liuxp on 2016/8/17.
 */
public class GuideRepo {

    protected Logger log = LoggerFactory.getLogger(GuideRepo.class);

    @Autowired
    private GuideService guideServiceRef;

    public ICPageResult<GuideScenicDTO> getGuidePageList(GuideScenicPageQueryDTO guideScenicQueryDTO) {

        try {
            ICPageResult<GuideScenicDTO> result = guideServiceRef.getGuidePageList(guideScenicQueryDTO);
            if(result.isSuccess()&&!result.getList().isEmpty()) {
                log.info("getGuidePageList guideScenicQueryDTO={}, result={}", JSON.toJSONString(guideScenicQueryDTO), JSON.toJSONString(result));
                return result;
            } else {
                log.error("getGuidePageList guideScenicQueryDTO={}, result={}", JSON.toJSONString(guideScenicQueryDTO), JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("getGuidePageList guideScenicQueryDTO={}, exception={}", JSON.toJSONString(guideScenicQueryDTO), e);
            return null;
        }
    }

    public ICResult<GuideScenicDO> addGuide(GuideScenicDO guideScenicDO) {
        try {
            ICResult<GuideScenicDO> result = guideServiceRef.addGuide(guideScenicDO);
            if(result.isSuccess()&&result.getModule()!=null) {
                log.info("addGuide guideScenicDO={}, result={}", JSON.toJSONString(guideScenicDO), JSON.toJSONString(result));
                return result;
            } else {
                log.error("addGuide guideScenicDO={}, result={}", JSON.toJSONString(guideScenicDO), JSON.toJSONString(result));
                return null;
            }
        } catch (Exception e) {
            log.error("addGuide guideScenicDO={}, exception={}", JSON.toJSONString(guideScenicDO), e);
            return null;
        }
    }

    public boolean updateGuide(GuideScenicUpdateDTO updateDTO) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuide(updateDTO);
            if(result.isSuccess()&&result.getModule()) {
                log.info("updateGuide updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                return true;
            } else {
                log.error("updateGuide updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                return false;
            }
        } catch (Exception e) {
            log.error("updateGuide updateDTO={}, exception={}", JSON.toJSONString(updateDTO), e);
            return false;
        }
    }

    public boolean updateGuideStatus(int status, long guideId) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideStatus(status, guideId);
            if(result.isSuccess()&&result.getModule()) {
                log.info("updateGuideStatus status={}, guideId={}, result={}", status, guideId, JSON.toJSONString(result));
                return true;
            } else {
                log.error("updateGuideStatus status={},guideId={}, result={}", status, guideId, JSON.toJSONString(result));
                return false;
            }
        } catch (Exception e) {
            log.error("updateGuideStatus status={},guideId={}, exception={}", status, guideId, e);
            return false;
        }
    }

    public boolean updateGuideWeight(int weight, long guideId) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideWeight(weight, guideId);
            if(result.isSuccess()&&result.getModule()) {
                log.info("updateGuideWeight status={}, guideId={}, result={}", weight, guideId, JSON.toJSONString(result));
                return true;
            } else {
                log.error("updateGuideWeight status={},guideId={}, result={}", weight, guideId, JSON.toJSONString(result));
                return false;
            }
        } catch (Exception e) {
            log.error("updateGuideWeight status={},guideId={}, exception={}", weight, guideId, e);
            return false;
        }
    }
}
