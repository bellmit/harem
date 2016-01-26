package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.vo.PromotionVO;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.query.PromotionPageQuery;
import com.yimayhd.promotion.client.service.PromotionPublishService;
import com.yimayhd.promotion.client.service.PromotionQueryService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionCommServiceImpl implements PromotionCommService {
    @Autowired
    private PromotionPublishService promotionPublishServiceRef;
    @Autowired
    private PromotionQueryService promotionQueryServiceRef;
    @Override
    public PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception {
        PromotionPageQuery promotionPageQuery = new PromotionPageQuery();
        promotionPageQuery.setPageNo(promotionListQuery.getPageNumber());
        promotionPageQuery.setPageSize(promotionListQuery.getPageSize());
        //TODO
        promotionQueryServiceRef.queryPromotions(promotionPageQuery);
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
