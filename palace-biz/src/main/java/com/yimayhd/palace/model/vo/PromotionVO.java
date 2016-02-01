package com.yimayhd.palace.model.vo;


import com.yimayhd.promotion.client.domain.PromotionDO;
import org.springframework.beans.BeanUtils;

/**
 * Created by czf on 2016/1/11.
 */
public class PromotionVO extends PromotionDO {

    //TODO

    private String title;

    private int entityType;

    private long entityId;

    private int promotionType;

    private String requirementY;

    private String value;

    private int domain;




    public static PromotionVO getPromotionVO(PromotionDO promotionDO){
        PromotionVO promotionVO = new PromotionVO();
        BeanUtils.copyProperties(promotionDO,promotionVO);
        return promotionVO;
    }
}
