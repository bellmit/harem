package com.yimayhd.palace.convert;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.palace.model.guide.ScenicVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 导览转换
 * Created by xushubing on 2016/8/18.
 */
public class GuideConverter {

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
