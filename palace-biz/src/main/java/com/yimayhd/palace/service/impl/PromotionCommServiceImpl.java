package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.vo.PromotionVO;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.query.PromotionPageQuery;
import com.yimayhd.promotion.client.result.BasePageResult;
import com.yimayhd.promotion.client.service.PromotionPublishService;
import com.yimayhd.promotion.client.service.PromotionQueryService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionCommServiceImpl implements PromotionCommService {
    @Autowired
    private PromotionPublishService promotionPublishServiceRef;
    @Autowired
    private PromotionQueryService promotionQueryServiceRef;

    private static final Logger log = LoggerFactory.getLogger(PromotionCommServiceImpl.class);

    @Override
    public PageVO<PromotionDO> getList(PromotionListQuery promotionListQuery) throws Exception {
        PageVO<PromotionDO> promotionDOPageVO = null;
        PromotionPageQuery promotionPageQuery = new PromotionPageQuery();
        //构建查询条件
        promotionPageQuery.setPageNo(promotionListQuery.getPageNumber());
        promotionPageQuery.setPageSize(promotionListQuery.getPageSize());
        promotionPageQuery.setNeedCount(true);
        if(StringUtils.isNotBlank(promotionListQuery.getBeginDate())) {
            Date beginTime = DateUtil.formatMinTimeForDate(promotionListQuery.getBeginDate());
            promotionPageQuery.setStartTime(beginTime);
        }
        if(StringUtils.isNotBlank(promotionListQuery.getEndDate())) {
            Date endTime = DateUtil.formatMaxTimeForDate(promotionListQuery.getEndDate());
            promotionPageQuery.setEndTime(endTime);
        }
        promotionPageQuery.setStatus(promotionListQuery.getStatus());
        promotionPageQuery.setPromotionType(promotionListQuery.getPromotionType());
        if(StringUtils.isNotBlank(promotionListQuery.getTitle())) {
            promotionPageQuery.setTitle(promotionListQuery.getTitle());
        }
        BasePageResult<PromotionDO> basePageResult = promotionQueryServiceRef.queryPromotions(promotionPageQuery);
        if(basePageResult == null){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(promotionPageQuery) + "and promotionListQuery: " + promotionListQuery);
            throw new BaseException("返回结果错误");
        } else if(!basePageResult.isSuccess()){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(promotionPageQuery) + "and promotionListQuery: " + promotionListQuery);
            throw new BaseException(basePageResult.getResultMsg());
        }
        promotionDOPageVO = new PageVO<PromotionDO>(promotionListQuery.getPageNumber(),promotionListQuery.getPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
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
