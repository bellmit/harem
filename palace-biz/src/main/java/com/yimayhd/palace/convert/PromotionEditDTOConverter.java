package com.yimayhd.palace.convert;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.PromotionVO;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.PromotionType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czf on 2016/2/18.
 */
public class PromotionEditDTOConverter {
    public static PromotionEditDTO getPromotionEditDTO(ActActivityEditVO actActivityEditVO) throws Exception {
        //活动能够时间格式转换
        actActivityEditVO.getActActivityVO().setStartDate(DateUtil.convertStringToDateUseringFormats(actActivityEditVO.getActActivityVO().getStartDateStr(),DateUtil.DAY_HORU_FORMAT));
        actActivityEditVO.getActActivityVO().setEndDate(DateUtil.convertStringToDateUseringFormats(actActivityEditVO.getActActivityVO().getEndDateStr(), DateUtil.DAY_HORU_FORMAT));

        PromotionEditDTO promotionEditDTO = new PromotionEditDTO();
        List<PromotionVO> promotionVOList = null;
        if(StringUtils.isNotBlank(actActivityEditVO.getPromotionVOListStr())) {
            promotionVOList = JSON.parseArray(actActivityEditVO.getPromotionVOListStr(), PromotionVO.class);
        }
        List<PromotionDO> addPromotionDOList = new ArrayList<PromotionDO>();
        List<PromotionDO> updPromotionDOList = new ArrayList<PromotionDO>();
        List<Long> delPromotionIdList = new ArrayList<Long>();
        if(CollectionUtils.isNotEmpty(promotionVOList)){
            /*switch (EntityType.getByType(actActivityEditVO.getActActivityVO().getEntityType())){
                case SHOP:
                    promotionVO.setEntityId(0);
                    PromotionFeature promotionFeature = new PromotionFeature(null);
                    List<Long> idList = new ArrayList<Long>();
                    if(!promotionVO.isDel()){
                        idList.add(promotionVO.getEntityId());
                    }
                    promotionFeature.put(PromotionFeatureKey.AVAILABLE_ITEM_IDS,idList);
                    //TODO
                    //promotionVO.setFeature(PromotionFeature);
                    break;
                default:
                    //promotionVO.setEntityId(promotionVO.getEntityId());
                    break;
            }*/
            for (PromotionVO promotionVO : promotionVOList){
                PromotionDO promotionDO = new PromotionDO();
                BeanUtils.copyProperties(promotionVO,promotionDO);
                promotionDO.setTitle(actActivityEditVO.getActActivityVO().getTitle());
                promotionDO.setDescription(actActivityEditVO.getActActivityVO().getDescription());

                //promotionVO.setEntityType();


                promotionDO.setPromotionType(actActivityEditVO.getActActivityVO().getPromotionType());
                switch (PromotionType.getByType(actActivityEditVO.getActActivityVO().getPromotionType())) {
                    case SUM_REDUCE:
                        promotionDO.setRequirement(Math.round(actActivityEditVO.getActActivityVO().getRequirementY()));
                        promotionDO.setValue(Math.round(promotionVO.getValueY() * 100));
                        break;
                    case DIRECT_REDUCE:
                        promotionDO.setRequirement(0);
                        promotionDO.setValue(Math.round(promotionVO.getValueY() * 100));
                        break;
                    default:
                        break;
                }
                promotionDO.setStartTime(actActivityEditVO.getActActivityVO().getStartDate());
                promotionDO.setEndTime(actActivityEditVO.getActActivityVO().getEndDate());
                promotionDO.setDomain(B2CConstant.GF_DOMAIN);
                //promotionVO.setStatus();
                //promotionVO.setFeatureV();
                //promotionVO.setUserTag();
                if(promotionVO.getId() == 0){
                    addPromotionDOList.add(promotionDO);
                }else if(promotionVO.isDel()){
                    delPromotionIdList.add(promotionDO.getId());
                }else{
                    updPromotionDOList.add(promotionDO);
                }
            }
        }
        //if()
        //TODO
        promotionEditDTO.setAddPromotionDOList(addPromotionDOList);
        promotionEditDTO.setDelPromotionIdList(delPromotionIdList);
        promotionEditDTO.setUpdPromotionDOList(updPromotionDOList);
        return promotionEditDTO;
    }

}
