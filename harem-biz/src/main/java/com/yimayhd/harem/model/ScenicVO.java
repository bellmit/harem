package com.yimayhd.harem.model;

import org.springframework.beans.BeanUtils;

import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.resourcecenter.entity.MasterRecommend;

/**
 * Created by czf on 2015/12/25.
 */
public class ScenicVO extends ScenicDO {
    private MasterRecommend masterRecommend;
    private String picList;//图片集的str
    private NeedKnow needKnowOb;

    public static ScenicDO getScenicDO(ScenicVO scenicVO){
        ScenicDO scenicDO = new ScenicVO();
        BeanUtils.copyProperties(scenicVO,scenicDO);
        //masterRecommend
        //NeedKnowOb 在serviceImpl中处理
        return scenicDO;
    }
    public static ScenicVO getScenicVO(ScenicDO scenicDO){
        ScenicVO scenicVO = new ScenicVO();
        BeanUtils.copyProperties(scenicDO,scenicVO);
        return scenicVO;
    }

    public String getPicList() {
        return picList;
    }

    public void setPicList(String picList) {
        this.picList = picList;
    }

    public MasterRecommend getMasterRecommend() {
        return masterRecommend;
    }

    public void setMasterRecommend(MasterRecommend masterRecommend) {
        this.masterRecommend = masterRecommend;
    }

    public NeedKnow getNeedKnowOb() {
        return needKnowOb;
    }

    public void setNeedKnowOb(NeedKnow needKnowOb) {
        this.needKnowOb = needKnowOb;
    }
}
