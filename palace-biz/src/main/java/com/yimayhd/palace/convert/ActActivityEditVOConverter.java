package com.yimayhd.palace.convert;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.ActActivityVO;
import com.yimayhd.palace.model.PromotionVO;
import com.yimayhd.promotion.client.domain.PromotionDO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2016/2/18.
 */
public class ActActivityEditVOConverter {
    public static ActActivityEditVO getActActivityEditVO(ActPromotionDTO actPromotionDTO){
        if(actPromotionDTO == null){
            return null;
        }
        ActActivityEditVO actActivityEditVO = new ActActivityEditVO();

        ActActivityVO actActivityVO = new ActActivityVO();
        ActActivityDO actActivityDO = actPromotionDTO.getActActivityDO();
        BeanUtils.copyProperties(actActivityDO, actActivityVO);
        actActivityEditVO.setActActivityVO(actActivityVO);

        List<PromotionVO> promotionVOList = new ArrayList<PromotionVO>();
        List<PromotionDO> promotionDOList = actPromotionDTO.getPromotionDOList();
        if(CollectionUtils.isNotEmpty(promotionDOList)){
            for(PromotionDO promotionDO : promotionDOList){
                PromotionVO promotionVO = new PromotionVO();
                BeanUtils.copyProperties(promotionDO, promotionVO);
                promotionVO.setValueY(promotionDO.getValue() / 100);
                actActivityVO.setRequirementY(promotionDO.getRequirement() / 100);
                actActivityVO.setValueY(promotionDO.getValue() / 100);
//                promotionVO.setItemId();
//                promotionVO.setItemSkuId();
//                promotionVO.setItemTitle();
//                promotionVO.setItemStatus();
                promotionVOList.add(promotionVO);
            }
        }

        actActivityEditVO.setPromotionVOList(promotionVOList);
        //TODO

        return actActivityEditVO;

    }
}
