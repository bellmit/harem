package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.activitycenter.domain.ActActivityDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.activitycenter.query.ActPromotionPageQuery;
import com.yimayhd.activitycenter.result.ActPageResult;
import com.yimayhd.activitycenter.result.ActResult;
import com.yimayhd.activitycenter.result.ActResultSupport;
import com.yimayhd.activitycenter.service.ActivityPromotionService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.query.PromotionListQuery;
import com.yimayhd.palace.model.vo.PromotionVO;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.query.PromotionPageQuery;
import com.yimayhd.promotion.client.result.BasePageResult;
import com.yimayhd.promotion.client.result.BaseResult;
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
    private ActivityPromotionService activityPromotionServiceRef;

    private static final Logger log = LoggerFactory.getLogger(PromotionCommServiceImpl.class);

    @Override
    public PageVO<ActActivityDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        PageVO<ActActivityDO> promotionDOPageVO = null;
        //构建查询条件

        if(StringUtils.isNotBlank(actPromotionPageQuery.getEndTime())) {
            Date endTime = DateUtil.formatMaxTimeForDate(actPromotionPageQuery.getEndTime());
            actPromotionPageQuery.setEndTime(DateUtil.date2String(endTime));
        }
        ActPageResult<ActActivityDO> basePageResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQuery);
        if(basePageResult == null){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException("返回结果错误");
        } else if(!basePageResult.isSuccess()){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException(basePageResult.getMsg());
        }
        promotionDOPageVO = new PageVO<ActActivityDO>(actPromotionPageQuery.getPageNo(),actPromotionPageQuery.getOldPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
    }

    @Override
    public void modify(ActPromotionEditDTO actPromotionEditDTO) throws Exception {
        ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.modify error: " + actPromotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.modify-promotionPublishService.updPromotion error:" + actPromotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
    }

    @Override
    public boolean add(PromotionEditDTO promotionEditDTO) throws Exception {
        ActResultSupport baseResult = activityPromotionServiceRef.saveActivityPromotion(promotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.add error: " + promotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.add error:" + promotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
        return true;
    }

    @Override
    public ActResult<ActPromotionDTO> getById(long id) throws Exception {
        return activityPromotionServiceRef.getActPromotionById(id);
    }

    @Override
    public boolean publish(long id) throws Exception {
        return activityPromotionServiceRef.openActPromotion(id).isSuccess();
    }

    @Override
    public boolean close(long id) throws Exception {
        return activityPromotionServiceRef.closeActPromotion(id).isSuccess();
    }
}
