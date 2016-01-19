package com.yimayhd.palace.service.impl;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.vo.PromotionVO;
import com.yimayhd.palace.service.PromotionService;
import com.yimayhd.promotion.client.domain.PromotionDO;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionServiceImpl implements PromotionService {
    @Override
    public PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception {
        return null;
    }

    @Override
    public void modify(PromotionVO entity) throws Exception {

    }

    @Override
    public boolean add(PromotionVO entity) throws Exception {
        return false;
    }

    @Override
    public PromotionVO getById(long id) throws Exception {
        return null;
    }
}
