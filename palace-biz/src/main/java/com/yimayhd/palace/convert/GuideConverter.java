package com.yimayhd.palace.convert;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicPageQueryDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicUpdateDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideTipsUpdateDTO;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.guide.ScenicVO;

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
        if (guideVo.getGuideId() > 0) {
            guideScenicDO.setId(guideVo.getGuideId());
        }
        guideScenicDO.setAudioTime(guideVo.getAudioTime());
        guideScenicDO.setStatus(guideVo.getStatus());
        guideScenicDO.setGuideAudio(guideVo.getGuideAudio());
        guideScenicDO.setGuideImg(guideVo.getGuideImg());
        guideScenicDO.setListImg(guideVo.getListImg());
        guideScenicDO.setScenicId(guideVo.getScenicId());

        return guideScenicDO;
    }

    public static GuideScenicUpdateDTO guideSceniceVO2GuideScenicUpdateDTO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideScenicUpdateDTO guideScenicUpdateDTO = new GuideScenicUpdateDTO();
        if (guideVo.getGuideId() > 0) {
            guideScenicUpdateDTO.setId(guideVo.getGuideId());
        }
        guideScenicUpdateDTO.setGuideAudio(guideVo.getGuideAudio());
        guideScenicUpdateDTO.setGuideImg(guideVo.getGuideImg());
        guideScenicUpdateDTO.setListImg(guideVo.getListImg());
        guideScenicUpdateDTO.setScenicId(guideVo.getScenicId());

        return guideScenicUpdateDTO;
    }

    public static GuideScenicTipsDO guideSceniceVO2GuideScenicTipsDO(GuideScenicVO guideVo) {
        if (guideVo == null) {
            return null;
        }
        GuideScenicTipsDO guideScenicTipsDO = new GuideScenicTipsDO();
        if (guideVo.getGuideTipsId() > 0) {
            guideScenicTipsDO.setId(guideVo.getGuideTipsId());
        }
        guideScenicTipsDO.setCares(guideVo.getCares());
        guideScenicTipsDO.setGuideId(guideVo.getGuideId());
        guideScenicTipsDO.setHaveTo(guideVo.getHaveTo());
        guideScenicTipsDO.setTicketInfo(guideVo.getTicketInfo());
        guideScenicTipsDO.setOpenTime(guideVo.getOpenTime());
        guideScenicTipsDO.setTips(guideVo.getTips());
        guideScenicTipsDO.setTraffic(guideVo.getTraffic());
        return guideScenicTipsDO;
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
        guideTipsUpdateDTO.setGuideId(guideVo.getGuideId());
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
        guideScenicPageQueryDTO.setPageNo(guideScenicListQuery.getPageNumber());
        guideScenicPageQueryDTO.setPageSize(guideScenicListQuery.getPageSize());
        return guideScenicPageQueryDTO;
    }

    public static GuideScenicVO guideScenicDTO2GuideScenicVO(GuideScenicVO guideScenicVO, GuideScenicDTO guideScenicDTO) {
        if (guideScenicDTO == null) {
            return null;
        }
        if (guideScenicVO == null) {
            guideScenicVO = new GuideScenicVO();
        }
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
            guideScenicVO.setLevel(scenicDO.getLevel());
            guideScenicVO.setScenicName(scenicDO.getName());
            guideScenicVO.setScenicResourceNum(String.valueOf(scenicDO.getId()));
            guideScenicVO.setLocation(scenicDO.getLocationText());
        }
        if (guideScenicVO.getGuideId() > 0) {
            return guideScenicVO;
        } else {
            return null;
        }
    }

    public static List<ScenicVO> convertScenic(List<ScenicDO> scenicDOs) {

        List<ScenicVO> result = new ArrayList<ScenicVO>();
        for (ScenicDO scenicDO : scenicDOs) {
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
            result.add(scenicVO);
        }
        return result;
    }
}
