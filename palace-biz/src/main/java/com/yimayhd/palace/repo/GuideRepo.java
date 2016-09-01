package com.yimayhd.palace.repo;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.guide.GuideService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.error.PalaceReturnCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * Created by liuxp on 2016/8/17.
 */
public class GuideRepo {

    protected Logger log = LoggerFactory.getLogger(GuideRepo.class);

    @Autowired
    private GuideService guideServiceRef;

    @Autowired
    private PictureTextRepo pictureTextRepo;

    public ICPageResult<GuideScenicDTO> getGuidePageList(GuideScenicPageQueryDTO guideScenicQueryDTO) {

        try {
            ICPageResult<GuideScenicDTO> result = guideServiceRef.getGuidePageList(guideScenicQueryDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("getGuidePageList guideScenicQueryDTO={}, result={}", JSON.toJSONString(guideScenicQueryDTO), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("getGuidePageList guideScenicQueryDTO={}, result={}", JSON.toJSONString(guideScenicQueryDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("getGuidePageList guideScenicQueryDTO={}, exception={}", JSON.toJSONString(guideScenicQueryDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public GuideScenicDO addGuide(GuideScenicDO guideScenicDO) {
        try {
            ICResult<GuideScenicDO> result = guideServiceRef.addGuide(guideScenicDO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("addGuide guideScenicDO={}, result={}", JSON.toJSONString(guideScenicDO), JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("addGuide guideScenicDO={}, result={}", JSON.toJSONString(guideScenicDO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("addGuide guideScenicDO={}, exception={}", JSON.toJSONString(guideScenicDO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 删除导览
     *
     * @param guideId
     * @return
     */
    public boolean deleteGuide(final long guideId) {
        try {
            ICResult<Boolean> result = guideServiceRef.deleteGuide(guideId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("addGuide guideId={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    return result.getModule() == null ? false : result.getModule();
                } else {
                    log.error("addGuide guideId={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("addGuide guideId={}, exception={}", JSON.toJSONString(guideId), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 根据导览id查询导览景区信息
     *
     * @param guideId
     * @return
     */
    public GuideScenicDTO queryGuideDetail(long guideId) {
        try {
            ICResult<GuideScenicDTO> result = guideServiceRef.queryGuideDetail(guideId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("queryGuideDetail guideId={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("queryGuideDetail guideId ={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryGuideDetail guideId={}, exception={}", JSON.toJSONString(guideId), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public GuideScenicTipsDO queryGuideScenicTips(long guideId) {
        try {
            ICResult<GuideScenicTipsDO> result = guideServiceRef.queryGuideScenicTips(guideId);
            if (result != null) {
                if (result.isSuccess() && result.getModule() != null) {
                    log.info("queryGuideScenicTips guideId={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("queryGuideScenicTips guideId ={}, result={}", JSON.toJSONString(guideId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryGuideScenicTips guideId={}, exception={}", JSON.toJSONString(guideId), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public GuideScenicDTO queryGuideDetailByScenicId(final long scenicId) {
        try {
            ICResult<GuideScenicDTO> result = guideServiceRef.queryGuideDetailByScenicId(scenicId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("queryGuideDetailByScenicId scenicId={}, result={}", JSON.toJSONString(scenicId), JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("queryGuideDetailByScenicId scenicId guideScenicDO={}, result={}", JSON.toJSONString(scenicId), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryGuideDetailByScenicId scenicId={}, exception={}", JSON.toJSONString(scenicId), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public GuideScenicTipsDO saveGuideScenicTips(GuideScenicTipsDO guideScenicTipsDO) {
        try {
            ICResult<GuideScenicTipsDO> result = guideServiceRef.saveGuideScenicTips(guideScenicTipsDO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("saveGuideScenicTips guideScenicTipsDO={}, result={}", JSON.toJSONString(guideScenicTipsDO), JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("saveGuideScenicTips guideScenicTipsDO={}, result={}", JSON.toJSONString(guideScenicTipsDO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("saveGuideScenicTips guideScenicTipsDO={}, exception={}", JSON.toJSONString(guideScenicTipsDO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public boolean updateGuideScenicTips(GuideTipsUpdateDTO guideScenicTipsDTO) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideScenicTips(guideScenicTipsDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateGuideScenicTips guideScenicTipsDTO={}, result={}", JSON.toJSONString(guideScenicTipsDTO), JSON.toJSONString(result));
                    return result.getModule() == null ? false : result.getModule();
                } else {
                    log.error("updateGuideScenicTips guideScenicTipsDTO={}, result={}", JSON.toJSONString(guideScenicTipsDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateGuideScenicTips guideScenicTipsDTO={}, exception={}", JSON.toJSONString(guideScenicTipsDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public boolean updateGuide(GuideScenicUpdateDTO updateDTO) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuide(updateDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateGuide updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                    return result.getModule() == null ? false : result.getModule();
                } else {
                    log.error("updateGuide updateDTO={}, result={}", JSON.toJSONString(updateDTO), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateGuide updateDTO={}, exception={}", JSON.toJSONString(updateDTO), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public boolean updateGuideStatus(int status, long guideId) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideStatus(status, guideId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateGuideStatus status={}, guideId={}, result={}", status, guideId, JSON.toJSONString(result));
                    return result.getModule() == null ? false : result.getModule();
                } else {
                    log.error("updateGuideStatus status={},guideId={}, result={}", status, guideId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateGuideStatus status={},guideId={}, exception={}", status, guideId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public boolean updateGuideWeight(int weight, long guideId) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideWeight(weight, guideId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("updateGuideWeight status={}, guideId={}, result={}", weight, guideId, JSON.toJSONString(result));
                    return result.getModule() == null ? false : result.getModule();
                } else {
                    log.error("updateGuideWeight status={},guideId={}, result={}", weight, guideId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateGuideWeight status={},guideId={}, exception={}", weight, guideId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 根据导览id查询景点列表
     *
     * @return
     * @parameter guideId
     */
    public List<GuideAttractionDO> queryAttraction(long guideId) {
        try {
            ICResult<List<GuideAttractionDO>> result = guideServiceRef.queryAttraction(guideId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("queryAttraction  guideId={}, result={}", guideId, JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("queryAttraction guideId={}, result={}", guideId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryAttraction guideId={}, exception={}", guideId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    /**
     * 根据景区id查询景点列表+看点列表
     *
     * @param scenicId
     * @return
     */
    public GuideCascadeAttractionDTO queryGuideAttractionFocusInfo(long scenicId) {
        try {
            ICResult<GuideCascadeAttractionDTO> result = guideServiceRef.queryGuideAttractionFocusInfo(scenicId);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("queryGuideAttractionFocusInfo  scenicId={}, result={}", scenicId, JSON.toJSONString(result));
                    return result.getModule();
                } else {
                    log.error("queryGuideAttractionFocusInfo scenicId={}, result={}", scenicId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryGuideAttractionFocusInfo scenicId={}, exception={}", scenicId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    public ICPageResult<ScenicDO> queryCanGuideScenic(ScenicPageQuery scenicPageQuery) {
        try {
            ICPageResult<ScenicDO> result = guideServiceRef.queryCanGuideScenic(scenicPageQuery);
            if (result != null) {
                if (result.isSuccess() && !result.getList().isEmpty()) {
                    log.info("queryCanGuideScenic scenicPageQuery={}, result={}", JSON.toJSONString(scenicPageQuery), JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("queryCanGuideScenic scenicPageQuery={}, result={}", JSON.toJSONString(scenicPageQuery), JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryCanGuideScenic scenicPageQuery={}, exception={}", JSON.toJSONString(scenicPageQuery), e);
            throw new BaseException(e, e.getMessage());
        }
    }

    // 删除景点
    public ICResult<Boolean> deleteAttraction(long attractionId) {
        try {
            ICResult<Boolean> result = guideServiceRef.deleteAttraction(attractionId);
            if (result != null) {
                if (result.isSuccess() && result.getModule()) {
                    log.info("deleteAttraction attractionId={},result={}", attractionId, JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("deleteAttraction attractionId={},result={}", attractionId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("deleteAttraction attractionId={} exception={}", attractionId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    // 景点详情
    public ICResult<AttractionFocusDTO> queryAttractionDetail(long attractionId) {
        try {
            ICResult<AttractionFocusDTO> result = guideServiceRef.queryAttractionDetail(attractionId);
            if (result != null) {
                if (result.getModule().getAttractionDO() == null || result.getModule().getGuideFocusDOList() == null) {
                    log.info("queryAttractionDetail attractionId={},result={}", attractionId, JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("queryAttractionDetail attractionId={},result={}", attractionId, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("queryAttractionDetail attractionId={} exception={}", attractionId, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    // 新增景点
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO) {
        try {
            ICResult<GuideAttractionDO> result = guideServiceRef.addAttractionAndFocus(attractionFocusAddDTO);
            if (result != null) {
                if (result.isSuccess()) {
                    log.info("attractionFocusAddDTO attractionFocusAddDTO={},  result={}",attractionFocusAddDTO, JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("attractionFocusAddDTO attractionFocusAddDTO={}, result={}", attractionFocusAddDTO, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("addAttractionAndFocus attractionFocusAddDTO={} exception={}", attractionFocusAddDTO, e);
            throw new BaseException(e, e.getMessage());
        }

    }

    // 更新景点
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateAttractionAndFocus(attractionFocusUpdateDTO);
            if (result != null) {
                if (result.isSuccess() && result.getModule()) {
                    log.info("updateAttractionAndFocus attractionFocusUpdateDTO={},result={}", attractionFocusUpdateDTO, JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("updateAttractionAndFocus attractionFocusUpdateDTO={},result={}", attractionFocusUpdateDTO, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateAttractionAndFocus attractionFocusUpdateDTO={} exception={}", attractionFocusUpdateDTO, e);
            throw new BaseException(e, e.getMessage());
        }
    }

    // 更新线路
    public ICResult<Boolean> updateGuideLine(long guideId, GuideLineDTO guideLineDTO) {
        try {
            ICResult<Boolean> result = guideServiceRef.updateGuideLine(guideId, guideLineDTO);
            if (result != null) {
                if (result.isSuccess() && result.getModule()) {
                    log.info("updateGuideLine guideId={},guideLineDTO={},result={}", guideId, guideLineDTO, JSON.toJSONString(result));
                    return result;
                } else {
                    log.error("updateGuideLine guideId={},guideLineDTO={},result={}", guideId, guideLineDTO, JSON.toJSONString(result));
                    throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
                }
            } else {
                throw new BaseException(PalaceReturnCode.REMOTE_CALL_FAILED.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("updateGuideLine guideId={} ,guideLineDTO={},exception={}", guideId, guideLineDTO, e);
            throw new BaseException(e, e.getMessage());
        }
    }




}
