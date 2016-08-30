package com.yimayhd.palace.biz;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.palace.checker.GuideChecker;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.service.GuideManageService;
import com.yimayhd.palace.service.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by xushubing on 2016/8/30.
 */
public class GuideBiz {
    protected Logger log = LoggerFactory.getLogger(GuideBiz.class);
    @Resource
    private GuideManageService guideManageService;

    private CheckResult checkGuideScenicVO(GuideScenicVO guideScenicVO) {
        CheckResult checkResult = GuideChecker.checkGuideScenicVO(guideScenicVO);
        GuideScenicDTO guideScenicDTO = guideManageService.queryGuideDetailByScenicId(guideScenicVO.getScenicId());
        if (guideScenicDTO != null) {
            log.error(" guideScenicVO={}", JSON.toJSONString(guideScenicVO));
            return GuideChecker.getCheckResult(PalaceReturnCode.ADD_GUIDE_ERROR_SCENICID_REPEATED);
        }
        checkResult.setSuccess(true);
        return checkResult;
    }

    public CheckResult addGuide(GuideScenicVO guideScenicVO) {

        CheckResult checkResult = checkGuideScenicVO(guideScenicVO);
        if (checkResult.isSuccess()) {
            boolean result = guideManageService.addGuide(guideScenicVO);
            checkResult.setSuccess(result);
        }

        return checkResult;
    }

    public CheckResult updateGuide(GuideScenicVO guideScenicVO) {

        CheckResult checkResult = checkGuideScenicVO(guideScenicVO);
        if (checkResult.isSuccess()) {
            boolean result = guideManageService.updateGuide(guideScenicVO);
            checkResult.setSuccess(result);
        }

        return checkResult;
    }

    public CheckResult upStatus(final long guideId) {
        boolean result = guideManageService.upStatus(guideId);
        return CheckResult.success();
    }

}
