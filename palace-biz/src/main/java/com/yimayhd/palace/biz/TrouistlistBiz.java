package com.yimayhd.palace.biz;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.guide.*;
import com.yimayhd.palace.repo.GuideRepo;
import com.yimayhd.palace.service.GuideManageService;
import com.yimayhd.palace.service.TouristManageService;
import com.yimayhd.palace.tair.TcCacheManager;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;

import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.palace.repo.PictureTextRepo;
import org.springframework.ui.Model;

import javax.annotation.Resource;


/**
 * 景点列表
 * <p>
 * Created by haozhu on 16/8/18.
 */
public class TrouistlistBiz {

    private static final Logger log = LoggerFactory.getLogger("TrouistlistBiz");
    @Resource
    private TouristManageService touristManageService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private TcCacheManager tcCacheManager;

    // delete删除单个景点
    public ICResult<Boolean> deleteAttraction(long attractionId) {
        return touristManageService.deleteAttraction(attractionId);
    }

    // 2
    // add增加保存景点
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO) {
        return touristManageService.addAttractionAndFocus(attractionFocusAddDTO);
    }

    // update更新保存景点
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO) {
        return touristManageService.updateAttractionAndFocus(attractionFocusUpdateDTO);
    }

    // 查询线路信息
    public GuideLineVO queryGuideLine(long guideId, long id) throws Exception {
        if (guideId <= 0) {
            return null;
        }
        GuideLineVO guideLineVO = new GuideLineVO();
        ICResult<GuideLineDTO> result = touristManageService.queryGuideLine(guideId);
        if (result == null) {
            guideLineVO.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);

        } else if (!result.isSuccess()) {
            guideLineVO.setCode(result.getResultCode());
            guideLineVO.setMsg(result.getResultMsg());
            guideLineVO.setSuccess(false);
        } else {
            guideLineVO.setCode(result.getResultCode());
            guideLineVO.setMsg(result.getResultMsg());
            int temp = -1;
            if (result.getModule() != null) {
                for (int i = 0; i < result.getModule().getGuideLine().size(); i++) {
                    GuideLineEntry entry = result.getModule().getGuideLine().get(i);
                    if (entry.getAttractionId() == id) {
                        temp = i;
                        break;
                    }
                }
            }
            if (temp == -1) {
                // 0 不在线路中
                guideLineVO.setPosition(GuideLineVO.Position.out);
            } else if (temp == result.getModule().getGuideLine().size() - 1) {
                //2 线路的最后一个节点
                guideLineVO.setPosition(GuideLineVO.Position.end);
            } else {
                //1 线路的中间位置
                guideLineVO.setPosition(GuideLineVO.Position.between);
            }
        }
        return guideLineVO;
    }

    // 删除景点
    public BizResult<String> deleteAttraction(long guideId, long id, GuideLineVO.Position position) {
        // 源代码
        BizResult<String> bizResult = new BizResult<String>();
        if (id < 0) {
            log.error("params erruor :attractionId={} ", id);
            bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
            return bizResult;
        }
        ICResult<Boolean> result = touristManageService.deleteAttraction(id);
        if (result == null) {
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
        } else {
            bizResult.setCode(result.getResultCode());
            bizResult.setMsg(result.getResultMsg());
            bizResult.setSuccess(result.isSuccess());
            if (position == GuideLineVO.Position.between) { // 重置线路
                updateGuideLine(guideId, "");
            } else if (position == GuideLineVO.Position.end) {
                // 线路删除线路最后一个景点 更新线路
                ICResult<GuideLineDTO> guideLineDTOResult = touristManageService.queryGuideLine(guideId);
                if (guideLineDTOResult == null) {
                    bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                }
                if (guideLineDTOResult.isSuccess()
                        && guideLineDTOResult.getModule() != null
                        && guideLineDTOResult.getModule().getGuideLine() != null
                        && guideLineDTOResult.getModule().getGuideLine().size() > 0) {
                    List<GuideLineEntry> guideLine = new ArrayList<GuideLineEntry>();
                    guideLine.addAll(guideLineDTOResult.getModule().getGuideLine());
                    guideLine.remove(guideLine.size() - 1);
                    updateGuideLine(guideId, JSON.toJSONString(guideLine));
                }
            }
        }
        return bizResult;
    }

    // 查询景点和线路信息
    public BizResult<AttractionListGuideLineVO> queryGuideAttractionFocusInfo(long scenicId) {
        BizResult<AttractionListGuideLineVO> bizResult = new BizResult<AttractionListGuideLineVO>();
        if (scenicId <= 0) {
            bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
            return bizResult;
        }
        GuideCascadeAttractionVO guideCascadeAttractionVO = touristManageService.queryGuideAttractionFocusInfo(scenicId);
        if (guideCascadeAttractionVO == null)
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
        // 简化数据
        AttractionListGuideLineVO attractionListGuideLineVO = GuideConverter.guideCascadeAttractionDTO2AttractionListGuideLineVO(guideCascadeAttractionVO);
        if (attractionListGuideLineVO == null)
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
        bizResult.setValue(attractionListGuideLineVO);
        return bizResult;
    }

    // 更新线路
    public BizResult<String> updateGuideLine(long guideId, String lineListJson) {
        BizResult<String> result = new BizResult<String>();
        ArrayList<GuideLineEntry> guideLine = new ArrayList<GuideLineEntry>();
        GuideLineDTO guideLineDTO = new GuideLineDTO();

        if (!lineListJson.equals("")) {
            lineListJson = lineListJson.replaceAll("\\s*\\\"\\s*", "\\\"");
            guideLine.addAll(JSONArray.parseArray(lineListJson, GuideLineEntry.class));
            guideLineDTO.setGuideLine(guideLine);
        } else {
            guideLineDTO.setGuideLine(guideLine);
        }
        if (guideId != 0 && lineListJson != null) {
            ICResult<Boolean> saveResult = touristManageService.updateGuideLine(guideId, guideLineDTO);
            if (saveResult == null) {
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }
            result.setCode(saveResult.getResultCode());
            result.setMsg(saveResult.getResultMsg());
            result.setSuccess(saveResult.isSuccess());
            if (saveResult.isSuccess()) {
                result.setValue(guideId + "");
            }
            log.error("saveResult:{}", JSON.toJSONString(saveResult));
        }
        return result;
    }

    // 新增保存
    public BizResult<String> addTourist(GuideAttractionVO guideAttractionVO) {
        BizResult<String> bizResult = new BizResult<String>();
        try {
            if (guideAttractionVO.getId() != 0) {  // 新增后重新编辑 走更新景点详情接口
                BizResult<Boolean> updateTouristResult = updateTourist(guideAttractionVO);
                bizResult.setCode(updateTouristResult.getCode());
                bizResult.setMsg(updateTouristResult.getMsg());
                bizResult.setSuccess(updateTouristResult.isSuccess());
                if (updateTouristResult.isSuccess())
                    bizResult.setValue(guideAttractionVO.getId() + "");
                return bizResult;
            }

            // 新增判断景点编号
            GuideAttractionQueryDTO queryDTO = new GuideAttractionQueryDTO();
            queryDTO.setGuideId(guideAttractionVO.getGuideId());
            queryDTO.setNo(guideAttractionVO.getAttrNo());
            ICResult<List<GuideAttractionDO>> queryDTOResult = touristManageService.queryAttractionList(queryDTO);
            if (queryDTOResult.getModule() != null && queryDTOResult.getModule().size() > 0) {
                bizResult.setMsg("景点编号已重复");
                bizResult.setSuccess(false);
                return bizResult;
            }

            // 保存详情
            AttractionFocusAddDTO attractionFocusAddDTO = GuideConverter.attractionVO2AttractionFocusAddDTO(guideAttractionVO);
            ICResult<GuideAttractionDO> guideAttractionDOICResult = addAttractionAndFocus(attractionFocusAddDTO);
            if (guideAttractionDOICResult == null) {
                bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return bizResult;
            }
            bizResult.setCode(guideAttractionDOICResult.getResultCode());
            bizResult.setMsg(guideAttractionDOICResult.getResultMsg());
            bizResult.setSuccess(guideAttractionDOICResult.isSuccess());
            if (guideAttractionDOICResult.isSuccess() && guideAttractionDOICResult.getModule() != null)
                bizResult.setValue(guideAttractionDOICResult.getModule().getId() + "");
            log.error("saveResult:{}", JSON.toJSONString(bizResult));
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return bizResult;
    }

    // 更新景点详情
    public BizResult<Boolean> updateTourist(GuideAttractionVO guideAttractionVO) {
        BizResult<Boolean> bizResult = new BizResult<Boolean>();
        try {
            // 查询景点
            AttractionFocusDTO attractionFocusDTO = touristManageService.queryAttractionDetail(guideAttractionVO.getId());
            AttractionFocusVO attractionFocusVO = GuideConverter.attractionFocusDTO2AttractionFocusVO(attractionFocusDTO);
            // 修改景点编号后判断是否重复
            if (guideAttractionVO.getAttrNo() != attractionFocusVO.getGuideAttractionVO().getAttrNo()) {
                // 查询景点编号是否重复
                GuideAttractionQueryDTO queryDTO = new GuideAttractionQueryDTO();
                queryDTO.setGuideId(guideAttractionVO.getGuideId());
                queryDTO.setNo(guideAttractionVO.getAttrNo());
                ICResult<List<GuideAttractionDO>> queryDTOResult = touristManageService.queryAttractionList(queryDTO);
                if (queryDTOResult.getModule() != null && queryDTOResult.getModule().size() > 0) {
                    bizResult.setMsg("景点编号已重复");
                    bizResult.setSuccess(false);
                    return bizResult;
                }
            }

            AttractionFocusUpdateDTO attractionFocusUpdateDTO = GuideConverter.guideAttractionVO2AttractionFocusUpdateDTO(guideAttractionVO, attractionFocusDTO);
            ICResult<Boolean> saveResult = updateAttractionAndFocus(attractionFocusUpdateDTO);
            if (saveResult == null) {
                bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return bizResult;
            }
            bizResult.setCode(saveResult.getResultCode());
            bizResult.setMsg(saveResult.getResultMsg());
            bizResult.setSuccess(saveResult.isSuccess());
            log.error("saveResult:{}", JSON.toJSONString(saveResult));
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return bizResult;
    }

    // 保存图文
    public BizResult<Boolean> savePictureText(AttractionIntroducePicTextTitleVO attractionIntroducePicTextTitleVO) throws Exception {
        String key = Constant.APP + "_repeat_" + sessionManager.getUserId() + attractionIntroducePicTextTitleVO.getUuidPicText();
        boolean rs = tcCacheManager.addToTair(key, true, 2, 24 * 60 * 60);
        BizResult<Boolean> bizResult = new BizResult<Boolean>();
        if (rs) {
            try {
                if (StringUtils.isBlank(attractionIntroducePicTextTitleVO.getPicTextString())) {
                    log.warn("json is null");
                    bizResult.setSuccess(false);
                    return bizResult;
                }
                String json = attractionIntroducePicTextTitleVO.getPicTextString().replaceAll("\\s*\\\"\\s*", "\\\"");
                PictureTextVO pictureTextVO = (PictureTextVO) JSONObject.parseObject(json, PictureTextVO.class);

                touristManageService.savePictureText(attractionIntroducePicTextTitleVO.getAttractionId(), pictureTextVO);

                //// TODO: 16/9/2 更新标题
                GuideAttractionVO guideAttractionVO = new GuideAttractionVO();
                guideAttractionVO.setId(attractionIntroducePicTextTitleVO.getAttractionId());
                guideAttractionVO.setTitle(attractionIntroducePicTextTitleVO.getTitle().trim());  // 去掉前后空格
                guideAttractionVO.setSubTitle(attractionIntroducePicTextTitleVO.getSubTitle().trim());
                // updateTourist(guideAttractionVO,model);
                // 查询景点
                AttractionFocusDTO attractionFocusDTO = touristManageService.queryAttractionDetail(guideAttractionVO.getId());

                if (attractionFocusDTO == null) {
                    bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                    return bizResult;
                }
                AttractionFocusUpdateDTO attractionFocusUpdateDTO = GuideConverter.convertAttrattionVO2UpdateDTO(attractionIntroducePicTextTitleVO, attractionFocusDTO);
                ICResult<Boolean> updateResult = updateAttractionAndFocus(attractionFocusUpdateDTO);
                if (updateResult == null || !updateResult.isSuccess()) {
                    bizResult.setSuccess(false);
                    return bizResult;
                }
                return bizResult;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return bizResult;
    }
}

