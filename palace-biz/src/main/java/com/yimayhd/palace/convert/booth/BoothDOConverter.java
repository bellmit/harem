package com.yimayhd.palace.convert.booth;

import com.yimayhd.palace.model.vo.booth.BoothVO;
import com.yimayhd.resourcecenter.domain.BoothDO;
import com.yimayhd.resourcecenter.model.enums.BoothStauts;
import com.yimayhd.resourcecenter.model.enums.CacheType;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by czf on 2016/4/13.
 */
public class BoothDOConverter {

    public static BoothDO getBoothDO(BoothVO boothVO){
        BoothDO boothDO = boothVO;
        boothDO.setStatus(BoothStauts.ONLINE.getStatus());
        //type;
        //boothDO.setIsCache(CacheType.TRUE.getValue());
        Date date = new Date();
        boothDO.setGmtCreated(date);
        boothDO.setGmtModified(date);
        return boothDO;
    }

    public static BoothDO BoothVOToBoothDO(BoothVO boothVO,BoothDO boothDO){
        BeanUtils.copyProperties(boothVO, boothDO);
        boothDO.setGmtModified(new Date());
        return boothDO;
    }
}
