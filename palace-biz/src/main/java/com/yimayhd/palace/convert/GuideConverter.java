package com.yimayhd.palace.convert;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideFocusDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.model.guide.*;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 导览转换
 * Created by xushubing on 2016/8/18.
 */
public class GuideConverter {
    public static GuideScenicDO guideSceniceVO2GuideScenicDO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideScenicDO guideScenicDO = new GuideScenicDO();
        if (guideVo.getGuideId() != null && guideVo.getGuideId() > 0) {
            guideScenicDO.setId(guideVo.getGuideId());
        }
        guideScenicDO.setAudioTime(guideVo.getAudioTime() == null ? 0 : guideVo.getAudioTime());
        guideScenicDO.setStatus(guideVo.getStatus());
        guideScenicDO.setGuideAudio(guideVo.getGuideAudio());
        guideScenicDO.setGuideImg(guideVo.getGuideImg());
        guideScenicDO.setListImg(guideVo.getListImg());
        guideScenicDO.setScenicId(guideVo.getScenicId() == null ? 0 : guideVo.getScenicId());
        guideScenicDO.setScenicName(guideVo.getScenicName());
        return guideScenicDO;
    }

    public static GuideScenicUpdateDTO guideSceniceVO2GuideScenicUpdateDTO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideScenicUpdateDTO guideScenicUpdateDTO = new GuideScenicUpdateDTO();
        if (guideVo.getGuideId() != null && guideVo.getGuideId() > 0) {
            guideScenicUpdateDTO.setId(guideVo.getGuideId());
        }
        guideScenicUpdateDTO.setGuideAudio(guideVo.getGuideAudio());
        guideScenicUpdateDTO.setGuideImg(guideVo.getGuideImg());
        guideScenicUpdateDTO.setListImg(guideVo.getListImg());
        guideScenicUpdateDTO.setScenicId(guideVo.getScenicId() == null ? 0 : guideVo.getScenicId());
        guideScenicUpdateDTO.setAudioTime(guideVo.getAudioTime() == null ? 0 : guideVo.getAudioTime());
        return guideScenicUpdateDTO;
    }

    public static GuideScenicTipsDO guideSceniceVO2GuideScenicTipsDO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideScenicTipsDO guideScenicTipsDO = new GuideScenicTipsDO();
        if (guideVo.getGuideTipsId() != null && guideVo.getGuideTipsId() > 0) {
            guideScenicTipsDO.setId(guideVo.getGuideTipsId());
        }
        guideScenicTipsDO.setCares(guideVo.getCares());
        guideScenicTipsDO.setGuideId(guideVo.getGuideId() == null ? 0 : guideVo.getGuideId());
        guideScenicTipsDO.setHaveTo(guideVo.getHaveTo());
        guideScenicTipsDO.setTicketInfo(guideVo.getTicketInfo());
        guideScenicTipsDO.setOpenTime(guideVo.getOpenTime());
        guideScenicTipsDO.setTips(guideVo.getTips());
        guideScenicTipsDO.setTraffic(guideVo.getTraffic());
        return guideScenicTipsDO;
    }

    public static GuideScenicVO guideScenicTipsDO2guideSceniceVO2GuideScenicTipsDO(GuideScenicVO guideScenicVO, GuideScenicTipsDO guideScenicTipsDO) {
        if (guideScenicTipsDO == null) {
            return null;
        }
        if (guideScenicVO == null) {
            guideScenicVO = new GuideScenicVO();
        }
        if (guideScenicTipsDO.getId() > 0) {
            guideScenicVO.setGuideTipsId(guideScenicTipsDO.getId());
        }
        guideScenicVO.setCares(guideScenicTipsDO.getCares());
        guideScenicVO.setGuideId(guideScenicTipsDO.getGuideId());
        guideScenicVO.setHaveTo(guideScenicTipsDO.getHaveTo());
        guideScenicVO.setTicketInfo(guideScenicTipsDO.getTicketInfo());
        guideScenicVO.setOpenTime(guideScenicTipsDO.getOpenTime());
        guideScenicVO.setTips(guideScenicTipsDO.getTips());
        guideScenicVO.setTraffic(guideScenicTipsDO.getTraffic());

        return guideScenicVO;
    }

    public static GuideTipsUpdateDTO guideSceniceVO2GuideScenicTipsDTO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideTipsUpdateDTO guideTipsUpdateDTO = new GuideTipsUpdateDTO();
        if (guideVo.getGuideTipsId() > 0) {
            guideTipsUpdateDTO.setId(guideVo.getGuideTipsId());
        }
        guideTipsUpdateDTO.setCares(guideVo.getCares());
        guideTipsUpdateDTO.setGuideId(guideVo.getGuideId() == null ? 0 : guideVo.getGuideId());
        guideTipsUpdateDTO.setHaveTo(guideVo.getHaveTo());
        guideTipsUpdateDTO.setTicketInfo(guideVo.getTicketInfo());
        guideTipsUpdateDTO.setOpenTime(guideVo.getOpenTime());
        guideTipsUpdateDTO.setTips(guideVo.getTips());
        guideTipsUpdateDTO.setTraffic(guideVo.getTraffic());
        return guideTipsUpdateDTO;
    }

    public static GuideScenicPageQueryDTO guideListQuery2GuideScenicPageQueryDTO(GuideScenicListQuery guideScenicListQuery) {
        if (guideScenicListQuery == null) {
            return null;
        }
        GuideScenicPageQueryDTO guideScenicPageQueryDTO = new GuideScenicPageQueryDTO();
        if (guideScenicListQuery.getStatus() == -1) {
            guideScenicPageQueryDTO.setStatus(null);
        } else {
            guideScenicPageQueryDTO.setStatus(guideScenicListQuery.getStatus());
        }
        if (guideScenicListQuery.getScenicResourceNum() != null && guideScenicListQuery.getScenicResourceNum() > 0) {
            List<Long> ids = new ArrayList<Long>();
            ids.add(guideScenicListQuery.getScenicResourceNum());
            guideScenicPageQueryDTO.setScenicIdList(ids);
        }
        if (!Strings.isNullOrEmpty(guideScenicListQuery.getScenicName())) {
            guideScenicPageQueryDTO.setScenicNameLike(guideScenicListQuery.getScenicName().trim());
        }
        guideScenicPageQueryDTO.setPageNo(guideScenicListQuery.getPageNumber());
        guideScenicPageQueryDTO.setPageSize(guideScenicListQuery.getPageSize());
        return guideScenicPageQueryDTO;
    }

    public static GuideScenicVO guideScenicDTO2GuideScenicVO(GuideScenicDTO guideScenicDTO) {
        if (guideScenicDTO == null) {
            return null;
        }
        GuideScenicVO guideScenicVO = new GuideScenicVO();
        /**
         * 导览信息
         */
        GuideScenicDO guideDO = guideScenicDTO.getGuideDO();

        /**
         * 景区信息
         */
        ScenicDO scenicDO = guideScenicDTO.getScenicDO();

        if (guideDO != null) {
            guideScenicVO.setGuideId(guideDO.getId());
            guideScenicVO.setAudioTime(guideDO.getAudioTime());
            guideScenicVO.setStatus(guideDO.getStatus());
            guideScenicVO.setGuideAudio(guideDO.getGuideAudio());
            guideScenicVO.setGuideImg(guideDO.getGuideImg());
            guideScenicVO.setListImg(guideDO.getListImg());
            guideScenicVO.setScenicId(guideDO.getScenicId());
            guideScenicVO.setWeight(guideDO.getWeights());

        }
        if (scenicDO != null) {
   /*         guideScenicVO.setLevel(scenicDO.getLevel());
            guideScenicVO.setScenicName(scenicDO.getName());
            guideScenicVO.setScenicResourceNum(String.valueOf(scenicDO.getId()));
            guideScenicVO.setAddress(scenicDO.getLocationText());
            guideScenicVO.setLocation(scenicDO.getLocationProvinceName() + " " + scenicDO.getLocationCityName() + " " + scenicDO.getLocationTownName());
            guideScenicVO.setSubjectId(scenicDO.getSubjectId());
            guideScenicVO.setLocationX(scenicDO.getLocationX());
            guideScenicVO.setLocationY(scenicDO.getLocationY());*/
            guideScenicVO.setScenicVO(scenicDO2ScenicVO(scenicDO));
        }
        if (guideScenicVO.getGuideId() != null && guideScenicVO.getGuideId() > 0) {
            return guideScenicVO;
        } else {
            return null;
        }
    }

    public static List<ScenicVO> convertScenic(List<ScenicDO> scenicDOs) {

        List<ScenicVO> result = new ArrayList<ScenicVO>();
        for (ScenicDO scenicDO : scenicDOs) {
            result.add(scenicDO2ScenicVO(scenicDO));
        }
        return result;
    }


    public static ScenicVO scenicDO2ScenicVO(ScenicDO scenicDO) {
        if (scenicDO == null) {
            return null;
        }
        ScenicVO scenicVO = new ScenicVO();
        scenicVO.setId(scenicDO.getId());
        scenicVO.setGmtCreated(scenicDO.getGmtCreated());
        scenicVO.setGmtModified(scenicDO.getGmtModified());
        scenicVO.setLevel(scenicDO.getLevel());
        scenicVO.setLocationCityId(scenicDO.getLocationCityId());
        scenicVO.setLocationCityName(scenicDO.getLocationCityName());
        scenicVO.setLocationProvinceId(scenicDO.getLocationProvinceId());
        scenicVO.setLocationProvinceName(scenicDO.getLocationProvinceName());
        scenicVO.setLocationText(scenicDO.getLocationText());
        scenicVO.setLocationTownId(scenicDO.getLocationTownId());
        scenicVO.setLocationTownName(scenicDO.getLocationTownName());
        scenicVO.setLocationX(scenicDO.getLocationX());
        scenicVO.setLocationY(scenicDO.getLocationY());
        scenicVO.setName(scenicDO.getName());
        scenicVO.setSubjectId(scenicDO.getSubjectId());
        scenicVO.setOpenTime(scenicDO.getOpenTime());
        List<String> names = scenicDO.getScenicFeature().getSubjectNames();
        if (names != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for(String name :names) {
                stringBuffer.append(name+" ");
            }
            scenicVO.setSubjectName(stringBuffer.toString());
        }
        return scenicVO;
    }

    // 增加景点详情
    public static AttractionFocusAddDTO attractionVO2AttractionFocusAddDTO(GuideAttractionVO attractionVO) {
        if (attractionVO == null) {
            return null;
        }
        // 景点
        GuideAttractionDO guideAttractionDO = new GuideAttractionDO();
        if (attractionVO == null) {
            return null;
        }
        guideAttractionDO.setId(attractionVO.getId());
        guideAttractionDO.setGuideId(attractionVO.getGuideId());
        guideAttractionDO.setAttrImg(attractionVO.getAttrImg());
        guideAttractionDO.setName(attractionVO.getName());
        guideAttractionDO.setTourTime(attractionVO.getTourTime());
        guideAttractionDO.setTitle(attractionVO.getTitle());
        guideAttractionDO.setAttrNo(attractionVO.getAttrNo());

        // 景点and看点
        AttractionFocusAddDTO attractionFocusAddDTO = new AttractionFocusAddDTO();
        attractionFocusAddDTO.setAttractionDO(guideAttractionDO);
        attractionFocusAddDTO.setFocusAddList(JSONArray.parseArray(attractionVO.getFocusOrder(), GuideFocusDO.class));

        return attractionFocusAddDTO;
    }

    // 更新景点详情
    public static AttractionFocusUpdateDTO guideAttractionVO2AttractionFocusUpdateDTO(GuideAttractionVO attractionVO, AttractionFocusDTO attractionFocusDTO) {
        if (attractionVO == null) {
            return null;
        }
        // 景点
        GuideAttractionUpdateDTO guideAttractionUpdateDTO = new GuideAttractionUpdateDTO();
        if (attractionVO == null) {
            return null;
        }
        guideAttractionUpdateDTO.setId(attractionVO.getId());
        guideAttractionUpdateDTO.setGuideId(attractionVO.getGuideId());
        guideAttractionUpdateDTO.setAttrImg(attractionVO.getAttrImg());
        guideAttractionUpdateDTO.setName(attractionVO.getName());
        guideAttractionUpdateDTO.setTourTime(attractionVO.getTourTime());
        guideAttractionUpdateDTO.setTitle(attractionVO.getTitle());
        guideAttractionUpdateDTO.setAttrNo(attractionVO.getAttrNo());

        List<GuideFocusDO> oldList = attractionFocusDTO.getGuideFocusDOList();
        List<GuideFocusDO> newList = JSONArray.parseArray(attractionVO.getFocusOrder(), GuideFocusDO.class);
        // 更新的看点
        List<GuideFocusUpdateDTO> updateList = new ArrayList<GuideFocusUpdateDTO>();
        ;
        for (int i = 0; i < oldList.size(); i++) {
            GuideFocusDO oldGuideFocusDO = oldList.get(i);
            for (int j = 0; j < newList.size(); j++) {
                GuideFocusDO newGuideFocusDO = oldList.get(j);
                if (newGuideFocusDO.getId() == oldGuideFocusDO.getId()) {
                    GuideFocusUpdateDTO guideFocusUpdateDTO = new GuideFocusUpdateDTO();
                    guideFocusUpdateDTO.setId(newGuideFocusDO.getId());
                    guideFocusUpdateDTO.setName(newGuideFocusDO.getName());
                    guideFocusUpdateDTO.setAudio(newGuideFocusDO.getAudio());
                    guideFocusUpdateDTO.setAudioTime(newGuideFocusDO.getAudioTime());
                    updateList.add(guideFocusUpdateDTO);
                }
            }
        }
        // 删除的看点
        List<Long> deleteList = new ArrayList<Long>();
        for (int i = 0; i < oldList.size(); i++) {
            GuideFocusDO oldGuideFocusDO = oldList.get(i);
            for (int j = 0; j < updateList.size(); j++) {
                GuideFocusDO newGuideFocusDO = oldList.get(j);
                if (newGuideFocusDO.getId() != oldGuideFocusDO.getId()) {
                    deleteList.add(oldGuideFocusDO.getId());
                }
            }
        }
        // 新增的看点
        List<GuideFocusDO> focusAddList = new ArrayList<GuideFocusDO>();
        for (int i = 0; i < oldList.size(); i++) {
            GuideFocusDO oldGuideFocusDO = oldList.get(i);
            for (int j = 0; j < updateList.size(); j++) {
                GuideFocusDO newGuideFocusDO = oldList.get(j);
                if (newGuideFocusDO.getId() != oldGuideFocusDO.getId()) {
                    focusAddList.add(newGuideFocusDO);
                }
            }
        }
        AttractionFocusUpdateDTO attractionFocusUpdateDTO = new AttractionFocusUpdateDTO();
        attractionFocusUpdateDTO.setDeletedFocusIdList(deleteList);
        attractionFocusUpdateDTO.setFocusAddList(focusAddList);
        attractionFocusUpdateDTO.setFocusUpdateDTOList(updateList);
        attractionFocusUpdateDTO.setAttractionUpdateDTO(guideAttractionUpdateDTO);
        return attractionFocusUpdateDTO;
    }


    //

    public static GuideAttractionVO guideAttractionDO2GuideAttractionVO(GuideAttractionDO guideAttractionDO) {
        if (guideAttractionDO == null) {
            return null;
        }
        GuideAttractionVO guideAttractionVO = new GuideAttractionVO();
        BeanUtils.copyProperties(guideAttractionDO, guideAttractionVO);
        return guideAttractionVO;
    }


    public static AttractionFocusVO attractionFocusDTO2AttractionFocusVO(AttractionFocusDTO attractionFocusDTO) {
        if (attractionFocusDTO == null) {
            return null;
        }
        AttractionFocusVO attractionFocusVO = new AttractionFocusVO();
        GuideAttractionDO guideAttractionDO = attractionFocusDTO.getAttractionDO();
        List<GuideFocusDO> guideFocusDOList = attractionFocusDTO.getGuideFocusDOList();

        if (guideAttractionDO != null) {
            GuideAttractionVO guideAttractionVO = guideAttractionDO2GuideAttractionVO(guideAttractionDO);
            attractionFocusVO.setGuideAttractionVO(guideAttractionVO);
        }
        if (guideFocusDOList != null && guideFocusDOList.size() > 0) {
            List<GuideFocusVO> guideFocusVOList = new ArrayList<GuideFocusVO>();
            for (GuideFocusDO guideFocusDO : guideFocusDOList) {
                GuideFocusVO guideFocusVO = new GuideFocusVO();
                guideFocusVO.setFileKey(guideFocusDO.getAudio());
                guideFocusVO.setDuration(guideFocusDO.getAudioTime());
                guideFocusVO.setInputFileTitle(guideFocusDO.getName());
               /* GuideFocusVO guideFocusVO = guideFocusDO2GuideFocusVO(guideFocusDO);
                if(guideFocusDO!=null) {
                    guideFocusVOList.add(guideFocusVO);
                }*/
                guideFocusVO.setFocusOrder(JSON.toJSONString(guideFocusVO));
                guideFocusVOList.add(guideFocusVO);

            }
            attractionFocusVO.setGuideFocusVOList(guideFocusVOList);
        }
        return attractionFocusVO;
    }

    // 线路设置查询结果
    public static AttractionListGuideLineVO guideCascadeAttractionDTO2AttractionListGuideLineVO(GuideCascadeAttractionDTO guideCascadeAttractionDTO) {
        if (guideCascadeAttractionDTO == null) {
            return null;
        }
        AttractionListGuideLineVO attractionListGuideLineVO = new AttractionListGuideLineVO();
        if (guideCascadeAttractionDTO.getGuideLineDTO() == null){
            attractionListGuideLineVO.setGuideLine(null);
        }else {
            attractionListGuideLineVO.setGuideLine(guideCascadeAttractionDTO.getGuideLineDTO().getGuideLine());
        }
        List<GuideAttractionDO> guideAttractionDOList = new ArrayList<GuideAttractionDO>();
        for (int i = 0; i < guideCascadeAttractionDTO.getAttractionDTOList().size(); i++){
            guideAttractionDOList.add(guideCascadeAttractionDTO.getAttractionDTOList().get(i).getGuideAttractionDO());
        }
        attractionListGuideLineVO.setGuideAttractionDOList(guideAttractionDOList);
        return attractionListGuideLineVO;
    }
}
