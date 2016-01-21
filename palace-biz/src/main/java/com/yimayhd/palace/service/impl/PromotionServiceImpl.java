package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.vo.PromotionVO;
import com.yimayhd.palace.service.PromotionService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.result.BaseResult;
import com.yimayhd.promotion.client.service.PromotionPublishService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionPublishService promotionPublishServiceRef;
    @Override
    public PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception {
        //promotionPublishServiceRef.
        return null;
    }

    @Override
    public void modify(PromotionVO entity) throws Exception {
        //BaseResult<PromotionDO> baseResult = promotionPublishServiceRef.updPromotion();
    }

    @Override
    public boolean add(PromotionVO entity) throws Exception {
        //BaseResult<PromotionDO> baseResult = promotionPublishServiceRef.addPromotion();
        return false;
    }

    @Override
    public PromotionVO getById(long id) throws Exception {
        //BaseResult<PromotionDO> baseResult = promotionPublishServiceRef.
        return null;
    }
}
