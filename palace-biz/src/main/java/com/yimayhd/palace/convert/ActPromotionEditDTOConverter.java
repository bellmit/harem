package com.yimayhd.palace.convert;

import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/2/18.
 */
public class ActPromotionEditDTOConverter {
    public static ActPromotionEditDTO getActPromotionEditDTO(ActActivityEditVO actActivityEditVO) throws Exception {
        ActPromotionEditDTO actPromotionEditDTO = new ActPromotionEditDTO();
        PromotionEditDTO promotionEditDTO = PromotionEditDTOConverter.getPromotionEditDTO(actActivityEditVO);

        actPromotionEditDTO.setAddPromotionDOList(promotionEditDTO.getAddPromotionDOList());
        actPromotionEditDTO.setDelPromotionIdList(promotionEditDTO.getDelPromotionIdList());
        actPromotionEditDTO.setUpdPromotionDOList(promotionEditDTO.getUpdPromotionDOList());

        ActActivityPromotionDO actActivityPromotionDO = new ActActivityPromotionDO();
        BeanUtils.copyProperties(actActivityEditVO.getActActivityVO(),actActivityPromotionDO);
        actActivityPromotionDO.setTitle(actActivityEditVO.getActActivityVO().getTitle());
        actActivityPromotionDO.setSummary(actActivityEditVO.getActActivityVO().getDescription());
        int lotteryType = EntityType.ITEM.getType();
        if(actActivityEditVO.getActActivityVO().getEntityType() == EntityType.SHOP.getType()){
            lotteryType = EntityType.SHOP.getType();
        }
        actActivityPromotionDO.setLotteryType(lotteryType);
        actActivityPromotionDO.setStartDate(actActivityEditVO.getActActivityVO().getStartDate());
        actActivityPromotionDO.setEndDate(actActivityEditVO.getActActivityVO().getEndDate());

        actPromotionEditDTO.setActActivityPromotionDO(actActivityPromotionDO);

        return actPromotionEditDTO;
    }
}
