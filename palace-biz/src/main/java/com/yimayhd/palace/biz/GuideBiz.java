package com.yimayhd.palace.biz;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.dto.guide.AttractionCascadeFocusDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideCascadeAttractionDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideLineDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.palace.checker.GuideChecker;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.service.GuideManageService;
import com.yimayhd.palace.service.TouristManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushubing on 2016/8/30.
 */
public class GuideBiz {
    protected Logger log = LoggerFactory.getLogger(GuideBiz.class);
    @Resource
    private GuideManageService guideManageService;
    @Resource
    private TouristManageService touristManageService;

    /**
     * 校验数据
     *
     * @param guideScenicVO
     * @return
     */
    private CheckResult checkGuideScenicVO(GuideScenicVO guideScenicVO) {
        CheckResult checkResult = GuideChecker.checkGuideScenicVO(guideScenicVO);

        checkResult.setSuccess(true);
        return checkResult;
    }

    private CheckResult checkGuideScenicVO(long scenicId) {
        GuideScenicDTO guideScenicDTO = guideManageService.queryGuideDetailByScenicId(scenicId);
        if (guideScenicDTO != null) {
            log.error(" scenicId={}", JSON.toJSONString(scenicId));
            return GuideChecker.getCheckResult(PalaceReturnCode.ADD_GUIDE_ERROR_SCENICID_REPEATED);
        }
        return CheckResult.success();
    }

    /**
     * 添加导览
     *
     * @param guideScenicVO
     * @return
     */
    public CheckResult addGuide(GuideScenicVO guideScenicVO) {
        CheckResult checkResult = checkGuideScenicVO(guideScenicVO);
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        checkResult = checkGuideScenicVO(guideScenicVO.getScenicId());
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        boolean result = guideManageService.addGuide(guideScenicVO);
        checkResult.setSuccess(result);
        return checkResult;
    }

    /**
     * 修改导览
     *
     * @param guideScenicVO
     * @return
     */

    public CheckResult updateGuide(GuideScenicVO guideScenicVO) {

        CheckResult checkResult = checkGuideScenicVO(guideScenicVO);
        if (checkResult.isSuccess()) {
            boolean result = guideManageService.updateGuide(guideScenicVO);
            checkResult.setSuccess(result);
        }

        return checkResult;
    }

    /**
     * 上架
     *
     * @param guideId
     * @return
     */
    public CheckResult upStatus(final long guideId) {
        GuideScenicVO guideScenicVO = guideManageService.getGuideById(guideId);
        CheckResult checkResult = GuideChecker.checkUpStatusGuideScenicVO(guideScenicVO);
        if (checkResult.isSuccess()) {
            //查询有没有景点信息
            List<GuideAttractionDO> guideAttractionDOList = guideManageService.queryAttraction(guideId);
            if (guideAttractionDOList == null || guideAttractionDOList.size() <= 0) {
                return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_ATTRACTION_ERROR);
            }

            GuideCascadeAttractionDTO guideCascadeAttractionDTO = guideManageService.queryGuideAttractionFocusInfo(guideScenicVO.getScenicId());
            if (guideCascadeAttractionDTO == null) {
                return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_ATTRACTION_ERROR);
            }
            /**
             * 导览景区信息
             */
            GuideScenicDTO guideScenicDTO = guideCascadeAttractionDTO.getGuideScenicDTO();
            if (guideScenicDTO == null) {
                return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_SCENIC_ERROR);
            }

            /**
             * 景点列表
             */
            List<AttractionCascadeFocusDTO> attractionDTOList = guideCascadeAttractionDTO.getAttractionDTOList();
            if (attractionDTOList == null || attractionDTOList.size() <= 0) {
                return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_ATTRACTION_ERROR);
            }

            for (AttractionCascadeFocusDTO attractionCascadeFocusDTO : attractionDTOList) {
                GuideAttractionDO guideAttractionDO = attractionCascadeFocusDTO.getGuideAttractionDO();
                if (guideAttractionDO == null) {
                    return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_ATTRACTION_ERROR);
                }
                PictureTextVO picTextVO = touristManageService.getPictureText(guideAttractionDO.getId());
                if (picTextVO == null || picTextVO.getPictureTextItems().size() <= 0) {
                    return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_ATTRACTION_PICTEXT_ERROR);
                }
            }

            /**
             * 推荐线路
             */
            GuideLineDTO guideLineDTO = guideCascadeAttractionDTO.getGuideLineDTO();
            if (guideLineDTO == null) {
                return GuideChecker.getCheckResult(PalaceReturnCode.UP_GUIDE_STATUS_LINE_ERROR);

            }

            boolean result = guideManageService.upStatus(guideId);
            CheckResult checkResult1 = new CheckResult();
            checkResult1.setSuccess(result);
            return checkResult1;
        } else {
            return checkResult;
        }
    }

}
