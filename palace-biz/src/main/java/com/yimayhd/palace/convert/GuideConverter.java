package com.yimayhd.palace.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideFocusDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.enums.ScenicLevelType;
import com.yimayhd.ic.client.model.enums.StarLevelType;
import com.yimayhd.palace.model.Coordinate;
import com.yimayhd.palace.model.guide.*;
import com.yimayhd.palace.util.Common;
import org.springframework.beans.BeanUtils;
import com.yimayhd.palace.util.DateUtil;


import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.*;

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
            guideScenicVO.setScenicName(scenicDO.getName());
            guideScenicVO.setScenicResourceNum(scenicDO.getId() + "");
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

    public static String formatDoubleNumber(double d) {
        DecimalFormat df = new DecimalFormat("0.######");
        return df.format(d);

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
        if (scenicDO.getScenicFeature() != null) {
            List<String> names = scenicDO.getScenicFeature().getSubjectNames();
            if (names != null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (String name : names) {
                    stringBuffer.append(name + " ");
                }
                scenicVO.setSubjectName(stringBuffer.toString());
            }
        }
       // scenicVO.setLevelDesc(StarLevelType.getByTypeWithDefault(scenicDO.getLevel()).getDesc());

        ScenicLevelType level = ScenicLevelType.getByType(scenicDO.getLevel());
        if(level != null){
            scenicVO.setLevelDesc( level.getDesc());
        }
        Coordinate cdt = Common.gcjToBd(scenicVO.getLocationY(), scenicVO.getLocationX());
        scenicVO.setLocationX(cdt.getLongitude());
        scenicVO.setLocationY(cdt.getLatitude());
        scenicVO.setLongitude(formatDoubleNumber(scenicVO.getLocationX()));
        scenicVO.setLatitude(formatDoubleNumber(scenicVO.getLocationY()));
        return scenicVO;
    }

    // 增加景点详情
    public static AttractionFocusAddDTO attractionVO2AttractionFocusAddDTO(GuideAttractionVO attractionVO) {
        if (attractionVO == null) {
            return null;
        }
        // 景点
        GuideAttractionDO guideAttractionDO = new GuideAttractionDO();

        guideAttractionDO.setId(attractionVO.getId());
        guideAttractionDO.setGuideId(attractionVO.getGuideId());
        guideAttractionDO.setAttrImg(attractionVO.getAttrImg());
        guideAttractionDO.setName(attractionVO.getName());
        guideAttractionDO.setTourTime(attractionVO.getTourTime());
//        guideAttractionDO.setTitle(attractionVO.getTitle().trim());
//        guideAttractionDO.setTitle(attractionVO.getSubTitle().trim());
        guideAttractionDO.setAttrNo(attractionVO.getAttrNo());

        // 景点and看点
        AttractionFocusAddDTO attractionFocusAddDTO = new AttractionFocusAddDTO();
        attractionFocusAddDTO.setAttractionDO(guideAttractionDO);
        // 看点去重处理
        attractionFocusAddDTO.setFocusAddList(removeDuplicte(JSONArray.parseArray(attractionVO.getFocusOrder(), GuideFocusDO.class)));

        return attractionFocusAddDTO;
    }

    private static HashMap<String, GuideFocusDO> getMapFromList(List<GuideFocusDO> guideFocusDOList) {
        HashMap hashMap = new HashMap();
        for (GuideFocusDO guideFocusDO : guideFocusDOList) {
            hashMap.put(guideFocusDO.getAudio(), guideFocusDO);
        }
        return hashMap;
    }

    private static List<String> getKeyFromList(List<GuideFocusDO> guideFocusDOList) {
        List<String> strings = new ArrayList<String>();
        for (GuideFocusDO guideFocusDO : guideFocusDOList) {
            strings.add(guideFocusDO.getAudio());
        }
        return strings;
    }

    private static List<GuideFocusUpdateDTO> getGuideFocusUpdateDTO(HashMap<String, GuideFocusDO> guideFocusDOHashMap, List<String> keys) {
        if (keys == null || guideFocusDOHashMap == null) {
            return null;
        }
        List<GuideFocusUpdateDTO> result = new ArrayList<GuideFocusUpdateDTO>();
        for (String key : keys) {
            GuideFocusDO guideFocusDO = guideFocusDOHashMap.get(key);
            GuideFocusUpdateDTO guideFocusUpdateDTO = new GuideFocusUpdateDTO();
            guideFocusUpdateDTO.setId(guideFocusDO.getId());
            guideFocusUpdateDTO.setName(guideFocusDO.getName());
            guideFocusUpdateDTO.setAudio(guideFocusDO.getAudio());
            guideFocusUpdateDTO.setAudioTime(guideFocusDO.getAudioTime());
            result.add(guideFocusUpdateDTO);
        }
        return result;
    }

    private static List<GuideFocusDO> getGuideFocusDO(HashMap<String, GuideFocusDO> guideFocusDOHashMap, List<String> keys) {
        if (keys == null || guideFocusDOHashMap == null) {
            return null;
        }
        List<GuideFocusDO> result = new ArrayList<GuideFocusDO>();
        for (String key : keys) {
            GuideFocusDO guideFocusDO = guideFocusDOHashMap.get(key);

            result.add(guideFocusDO);
        }
        return result;
    }

    public static ArrayList<GuideFocusDO> removeDuplicte(List<GuideFocusDO> guideFocusDOList) {
        Set<GuideFocusDO> s = new TreeSet<GuideFocusDO>(new Comparator<GuideFocusDO>() {

            @Override
            public int compare(GuideFocusDO o1, GuideFocusDO o2) {
                return o1.getAudio().compareTo(o2.getAudio());
            }

        });

        s.addAll(guideFocusDOList);
        return new ArrayList<GuideFocusDO>(s);
    }

    // 更新景点详情
    public static AttractionFocusUpdateDTO guideAttractionVO2AttractionFocusUpdateDTO(GuideAttractionVO attractionVO, AttractionFocusDTO attractionFocusDTO) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (attractionVO == null) {
            return null;
        }
        // 景点
        GuideAttractionUpdateDTO guideAttractionUpdateDTO = new GuideAttractionUpdateDTO();

        guideAttractionUpdateDTO.setId(attractionVO.getId());
        guideAttractionUpdateDTO.setGuideId(attractionVO.getGuideId());
        guideAttractionUpdateDTO.setAttrImg(attractionVO.getAttrImg());
        guideAttractionUpdateDTO.setName(attractionVO.getName());
        guideAttractionUpdateDTO.setTourTime(attractionVO.getTourTime());
//      guideAttractionUpdateDTO.setTitle(attractionVO.getTitle().trim());
//      guideAttractionUpdateDTO.setSubTitle(attractionVO.getSubTitle().trim());
        guideAttractionUpdateDTO.setAttrNo(attractionVO.getAttrNo());

        List<GuideFocusDO> oldList = new ArrayList<GuideFocusDO>();
        if (attractionFocusDTO.getGuideFocusDOList().size() > 0) {
            oldList.addAll(attractionFocusDTO.getGuideFocusDOList());
        }
        List<GuideFocusDO> newList = new ArrayList<GuideFocusDO>();
        if (attractionVO.getFocusOrder() != null) {
            if (JSONArray.parseArray(attractionVO.getFocusOrder(), GuideFocusDO.class).size() > 0) {
                newList.addAll(JSONArray.parseArray(attractionVO.getFocusOrder(), GuideFocusDO.class));
            }
        }
        //去重
        oldList = removeDuplicte(oldList);
        newList = removeDuplicte(newList);
        //
        List<String> oldKeys = getKeyFromList(oldList);
        List<String> newKeys = getKeyFromList(newList);

        List<String> allKeys = new ArrayList<String>();
        allKeys.addAll(oldKeys);
        allKeys.addAll(newKeys);

        HashMap<String, GuideFocusDO> oldMap = getMapFromList(oldList);
        HashMap<String, GuideFocusDO> newMap = getMapFromList(newList);

        // 更新的看点
        List<GuideFocusUpdateDTO> updateList = new ArrayList<GuideFocusUpdateDTO>();
        List<String> updateKeys = new ArrayList<String>();
        updateKeys.addAll(newKeys);
        updateKeys.retainAll(oldKeys);//交集
        updateList = getGuideFocusUpdateDTO(newMap, updateKeys);

        // 删除的看点
        List<Long> deleteList = new ArrayList<Long>();
        List<String> deleteKeys = new ArrayList<String>();
        deleteKeys.addAll(allKeys);
        deleteKeys.removeAll(newKeys);//交集
        for (GuideFocusDO guideFocusDO : oldList) {
            Iterator d = deleteKeys.iterator();
            while (d.hasNext()) {
                String key = (String) d.next();
                if (key.equals(guideFocusDO.getAudio())) {
                    deleteList.add(guideFocusDO.getId());
                    d.remove();
                }
            }
        }
        // 新增的看点
        List<GuideFocusDO> focusAddList = new ArrayList<GuideFocusDO>();
        List<String> addKeys = new ArrayList<String>();
        addKeys.addAll(allKeys);
        addKeys.removeAll(oldKeys);
        focusAddList = getGuideFocusDO(newMap, addKeys);

        AttractionFocusUpdateDTO attractionFocusUpdateDTO = new AttractionFocusUpdateDTO();
        attractionFocusUpdateDTO.setDeletedFocusIdList(deleteList);
        attractionFocusUpdateDTO.setFocusAddList(focusAddList);
        attractionFocusUpdateDTO.setFocusUpdateDTOList(updateList);
        attractionFocusUpdateDTO.setAttractionUpdateDTO(guideAttractionUpdateDTO);
        return attractionFocusUpdateDTO;
    }

    public static AttractionFocusUpdateDTO convertAttrattionVO2UpdateDTO(AttractionIntroducePicTextTitleVO attractionIntroducePicTextTitleVO, AttractionFocusDTO attractionFocusDTO) {
        AttractionFocusUpdateDTO attractionFocusUpdateDTO = new AttractionFocusUpdateDTO();
        GuideAttractionUpdateDTO guideAttractionUpdateDTO = new GuideAttractionUpdateDTO();
        guideAttractionUpdateDTO.setAttrImg(attractionFocusDTO.getAttractionDO().getAttrImg());
        guideAttractionUpdateDTO.setAttrNo(attractionFocusDTO.getAttractionDO().getAttrNo());
        guideAttractionUpdateDTO.setGuideId(attractionFocusDTO.getAttractionDO().getGuideId());
        guideAttractionUpdateDTO.setId(attractionFocusDTO.getAttractionDO().getId());
        guideAttractionUpdateDTO.setName(attractionFocusDTO.getAttractionDO().getName());
        guideAttractionUpdateDTO.setTourTime(attractionFocusDTO.getAttractionDO().getTourTime());
        guideAttractionUpdateDTO.setWeights(attractionFocusDTO.getAttractionDO().getWeights());
        guideAttractionUpdateDTO.setTitle(attractionIntroducePicTextTitleVO.getTitle().trim());
        guideAttractionUpdateDTO.setSubTitle(attractionIntroducePicTextTitleVO.getSubTitle().trim());
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
                guideFocusVO.setId(guideFocusDO.getId());
                guideFocusVO.setDurationStr(DateUtil.parseLong2TimeString(guideFocusDO.getAudioTime()));
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
    public static AttractionListGuideLineVO guideCascadeAttractionDTO2AttractionListGuideLineVO(GuideCascadeAttractionVO guideCascadeAttractionVO) {
        if (guideCascadeAttractionVO == null) {
            return null;
        }
        AttractionListGuideLineVO attractionListGuideLineVO = new AttractionListGuideLineVO();
        if (guideCascadeAttractionVO.getGuideLine() == null) {
            attractionListGuideLineVO.setGuideLine(null);
        } else {
            attractionListGuideLineVO.setGuideLine(guideCascadeAttractionVO.getGuideLine());
        }
        List<GuideAttractionDO> guideAttractionDOList = new ArrayList<GuideAttractionDO>();
        for (int i = 0; i < guideCascadeAttractionVO.getAttractionDTOList().size(); i++) {
            guideAttractionDOList.add(guideCascadeAttractionVO.getAttractionDTOList().get(i).getGuideAttractionDO());
        }
        attractionListGuideLineVO.setGuideAttractionDOList(guideAttractionDOList);
        return attractionListGuideLineVO;
    }
}
