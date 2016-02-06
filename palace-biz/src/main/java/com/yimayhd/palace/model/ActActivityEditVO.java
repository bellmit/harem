package com.yimayhd.palace.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.palace.model.vo.PromotionVO;

import java.util.List;

/**
 * Created by czf on 2016/2/6.
 */
public class ActActivityEditVO extends ActActivityDO {
    private ActActivityVO actActivityVO;
    private List<PromotionVO> promotionVOList;
    private String promotionVOListStr;

    public ActActivityVO getActActivityVO() {
        return actActivityVO;
    }
    public String getPromotionVOListJsonStr() {
        return JSON.toJSONString(this.promotionVOList, SerializerFeature.DisableCircularReferenceDetect);
    }

    public void setActActivityVO(ActActivityVO actActivityVO) {
        this.actActivityVO = actActivityVO;
    }

    public List<PromotionVO> getPromotionVOList() {
        return promotionVOList;
    }

    public void setPromotionVOList(List<PromotionVO> promotionVOList) {
        this.promotionVOList = promotionVOList;
    }

    public String getPromotionVOListStr() {
        return promotionVOListStr;
    }

    public void setPromotionVOListStr(String promotionVOListStr) {
        this.promotionVOListStr = promotionVOListStr;
    }
}
