package com.yimayhd.palace.convert.booth;

import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.resourcecenter.domain.BoothDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/4/13.
 */
public class BoothVOConverter {
    public static BoothVO getBoothVO(BoothDO boothDO){
        BoothVO boothVO = new BoothVO();
        BeanUtils.copyProperties(boothDO,boothVO);
        return boothVO;
    }
}
