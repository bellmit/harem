package com.yimayhd.palace.convert;

import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.ActActivityVO;
import com.yimayhd.palace.model.PromotionVO;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.domain.PromotionFeature;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
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
        if(EntityType.SHOP.getType() == actActivityVO.getLotteryType()){
            if(CollectionUtils.isNotEmpty(promotionDOList)){
                //店铺优惠只有一跳记录
                PromotionDO promotionDO = promotionDOList.get(0);
                PromotionFeature promotionFeature = new PromotionFeature(promotionDO.getFeature());
                //TODO 没有区分item和sku
                List<Long> itemIdList = promotionFeature.getAvailableItemIds();
                for(Long itemId : itemIdList){
                    PromotionVO promotionVO = new PromotionVO();
                    BeanUtils.copyProperties(promotionDO, promotionVO);
                    promotionVO.setEntityId(itemId);
                    promotionVO.setEntityType(EntityType.ITEM.getType());
                    promotionVO.setValueY(promotionDO.getValue() / 100);
                    promotionVOList.add(promotionVO);
                }

            }
        }else{
            if(CollectionUtils.isNotEmpty(promotionDOList)){
                for(PromotionDO promotionDO : promotionDOList){
                    PromotionVO promotionVO = new PromotionVO();
                    BeanUtils.copyProperties(promotionDO, promotionVO);
                    promotionVO.setValueY(promotionDO.getValue() / 100);
                    //非直降的情况下设置优惠条件和优惠额度
                    if(PromotionType.DIRECT_REDUCE.getType() != promotionDO.getPromotionType()) {
                        actActivityVO.setRequirementY(promotionDO.getRequirement() / 100);
                        actActivityVO.setValueY(promotionDO.getValue() / 100);
                    }
//                promotionVO.setItemId();
//                promotionVO.setItemSkuId();
//                promotionVO.setItemTitle();
//                promotionVO.setItemStatus();
                    promotionVOList.add(promotionVO);
                }
            }
        }


        actActivityEditVO.setPromotionVOList(promotionVOList);
        //TODO

        return actActivityEditVO;

    }
}
