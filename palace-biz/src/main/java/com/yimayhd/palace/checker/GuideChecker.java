package com.yimayhd.palace.checker;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xushubing on 2016/8/30.
 */
public class GuideChecker {
    private static final Logger log = LoggerFactory.getLogger(GuideChecker.class);

    public static CheckResult getCheckResult(PalaceReturnCode palaceReturnCode) {
        CheckResult checkResult = CheckResult.error();
        checkResult.setPalaceReturnCode(palaceReturnCode);
        return checkResult;
    }

    public static CheckResult checkGuideScenicVO(GuideScenicVO guideScenicVO) {
        if (guideScenicVO == null) {
            log.error(" guideScenicVO={} ");
            return getCheckResult(PalaceReturnCode.ADD_GUIDE_ERROR);

        }
        if (guideScenicVO.getScenicId() <= 0) {
            log.error(" guideScenicVO={}", JSON.toJSONString(guideScenicVO));
            return getCheckResult(PalaceReturnCode.ADD_GUIDE_ERROR_SCENICID);
        }
        return CheckResult.success();
    }
}
