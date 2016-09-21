package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.common.json.JSONObject;
import com.yimayhd.activitycenter.query.ActivityPromotionQueryDTO;
import com.yimayhd.activitycenter.result.track.ActivityResult;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.*;
import com.yimayhd.palace.util.MoneyUtil;
import com.yimayhd.promotion.client.domain.FullGiveFeature;
import com.yimayhd.promotion.client.domain.PromotionFeature;
import com.yimayhd.promotion.client.enums.PromotionFeatureKey;
import com.yimayhd.promotion.client.result.*;
import com.yimayhd.promotion.client.service.PromotionPublishService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.fastjson.JSON;
import com.yimayhd.activitycenter.domain.ActActivityPromotionDO;
import com.yimayhd.activitycenter.dto.ActPromotionDTO;
import com.yimayhd.activitycenter.dto.ActPromotionEditDTO;
import com.yimayhd.activitycenter.enums.PromotionStatus;
import com.yimayhd.activitycenter.result.ActPageResult;
import com.yimayhd.activitycenter.result.ActResult;
import com.yimayhd.activitycenter.result.ActResultSupport;
import com.yimayhd.activitycenter.service.ActivityPromotionService;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.param.item.ItemSkuMixDTO;
import com.yimayhd.ic.client.model.query.BatchRichSkuQuery;
import com.yimayhd.ic.client.model.query.Pair;
import com.yimayhd.ic.client.model.result.item.ItemSkuMixResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ActActivityEditVOConverter;
import com.yimayhd.palace.convert.ActPromotionEditDTOConverter;
import com.yimayhd.palace.convert.PromotionEditDTOConverter;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.dto.BaseItemDTO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import com.yimayhd.promotion.client.query.EntityQueryPair;
import com.yimayhd.promotion.client.query.PromotionPageQuery;
import com.yimayhd.promotion.client.service.PromotionQueryService;

/**
 * Created by czf on 2016/1/19.
 */
public class PromotionCommServiceImpl implements PromotionCommService {

    @Autowired
    private ActivityPromotionService activityPromotionServiceRef;
    @Autowired
    private PromotionQueryService promotionQueryService;
    @Autowired
    private ItemQueryService itemQueryServiceRef;

    private static final Logger log = LoggerFactory.getLogger(PromotionCommServiceImpl.class);

    @Override
    public PageVO<ActActivityPromotionDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        PageVO<ActActivityPromotionDO> promotionDOPageVO = null;
        //构建查询条件
        com.yimayhd.activitycenter.query.ActPromotionPageQuery actPromotionPageQueryRef = new com.yimayhd.activitycenter.query.ActPromotionPageQuery();

        if(StringUtils.isNotBlank(actPromotionPageQuery.getTitle())) {
            actPromotionPageQueryRef.setTitle(actPromotionPageQuery.getTitle());
        }
        actPromotionPageQueryRef.setStatus(actPromotionPageQuery.getStatus());
        actPromotionPageQueryRef.setLotteryType(actPromotionPageQuery.getLotteryType());
        actPromotionPageQueryRef.setType(actPromotionPageQuery.getType());
        actPromotionPageQueryRef.setPageNo(actPromotionPageQuery.getPageNumber());
        actPromotionPageQueryRef.setPageSize(actPromotionPageQuery.getPageSize());
//        BeanUtils.copyProperties(actPromotionPageQuery,actPromotionPageQueryRef);
        if(StringUtils.isNotBlank(actPromotionPageQuery.getBeginTime())) {
            Date startTime = DateUtil.formatMinTimeForDate(actPromotionPageQuery.getBeginTime());
            actPromotionPageQueryRef.setStart(startTime);
            actPromotionPageQueryRef.setBeginTime(DateUtil.date2String(startTime));
        }
        if(StringUtils.isNotBlank(actPromotionPageQuery.getEndTime())) {
            Date endTime = DateUtil.formatMaxTimeForDate(actPromotionPageQuery.getEndTime());
            actPromotionPageQueryRef.setEnd(endTime);
            actPromotionPageQueryRef.setEndTime(DateUtil.date2String(endTime));
        }
        if(StringUtils.isNotBlank(actPromotionPageQuery.getStartTimeStart())) {
            Date startTimeStart = DateUtil.formatMinTimeForDate(actPromotionPageQuery.getStartTimeStart());
            actPromotionPageQueryRef.setStartTimeStart(startTimeStart);
        }
        if(StringUtils.isNotBlank(actPromotionPageQuery.getStartTimeEnd())) {
            Date startTimeEnd = DateUtil.formatMaxTimeForDate(actPromotionPageQuery.getStartTimeEnd());
            actPromotionPageQueryRef.setStartTimeEnd(startTimeEnd);
        }
        ActPageResult<ActActivityPromotionDO> basePageResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQueryRef);
        if(basePageResult == null){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException("返回结果错误");
        } else if(!basePageResult.isSuccess()){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException(basePageResult.getMsg());
        }
        promotionDOPageVO = new PageVO<ActActivityPromotionDO>(actPromotionPageQuery.getPageNumber(),actPromotionPageQuery.getPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
    }

    public void modify(ActActivityEditVO actActivityEditVO) throws Exception {

        ActPromotionEditDTO actPromotionEditDTO = ActPromotionEditDTOConverter.getActPromotionEditDTO(actActivityEditVO);
//        List<PromotionDO> addPromotionDOList = actPromotionEditDTO.getAddPromotionDOList() ;
//		String tips = getItemSkuHasDirectReduceTip(addPromotionDOList);
//        if( StringUtils.isNotBlank(tips) ){
//        	throw new BaseException(tips);
//        }
//        
        ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.modify error: " + actPromotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.modify-promotionPublishService.updPromotion error:" + actPromotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
    }
    
    private boolean existDirectReducePromotion(ItemPromotionQueryResult queryResult){
    	if( queryResult == null || !queryResult.isSuccess() || queryResult.getPcBaseItem() == null){
    		return false;
    	}
    	PcBaseItem baseItem = queryResult.getPcBaseItem();
    	List<PromotionDTO> promotionDTOs = baseItem.getPromotionDTOList();
    	if( CollectionUtils.isEmpty(promotionDTOs) ){
    		return false;
    	}
    	for( PromotionDTO dto : promotionDTOs ){
    		int type = dto.getType() ;
    		if( PromotionType.DIRECT_REDUCE.getType() == type && dto.isAvailable() ){
    			return true;
    		}
    	}
    	return false;
    }
    

    @Override
    public boolean add(ActActivityEditVO actActivityEditVO) throws Exception {
        PromotionEditDTO promotionEditDTO = PromotionEditDTOConverter.getPromotionEditDTO(actActivityEditVO);
//        List<PromotionDO> addPromotionDOList = promotionEditDTO.getAddPromotionDOList() ;
//        String tips = getItemSkuHasDirectReduceTip(addPromotionDOList);
//        if( StringUtils.isNotBlank(tips) ){
//        	throw new BaseException(tips);
//        }
        
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
	public BizResultSupport check(ActActivityEditVO actActivityEditVO) {
    	BizResultSupport result = new BizResultSupport() ;
    	ActActivityPromotionDO actActivityDO = actActivityEditVO.getActActivityVO() ;
    	PromotionEditDTO promotionEditDTO = PromotionEditDTOConverter.getPromotionEditDTO(actActivityEditVO);
        List<PromotionDO> addPromotionDOList = promotionEditDTO.getAddPromotionDOList() ;
//    	String tips = getItemSkuHasDirectReduceTip(addPromotionDOList);
    	
    	if( org.springframework.util.CollectionUtils.isEmpty(addPromotionDOList) ){
    		return result;
    	}
    	
    	List<Long> itemIds = new ArrayList<Long>();
    	List<Long> skuIds = new ArrayList<Long>();
    	for( PromotionDO promotionDO : addPromotionDOList ){
    		int entityType =promotionDO.getEntityType() ;
    		long entityId = promotionDO.getEntityId() ;
    		if( EntityType.ITEM.getType() == entityType ){
    			itemIds.add(entityId) ;
    		}else{
    			skuIds.add(entityId) ;
    		}
    	}
    	List<EntityQueryPair> entityQueryPairList = new ArrayList<EntityQueryPair>();
    	if( CollectionUtils.isNotEmpty(itemIds) ){
    		EntityQueryPair pair = new EntityQueryPair();
    		pair.setEntityIds(itemIds);
    		pair.setEntityType(EntityType.ITEM.getType());
    		entityQueryPairList.add(pair) ;
    	}
    	if( CollectionUtils.isNotEmpty(skuIds) ){
    		EntityQueryPair pair = new EntityQueryPair();
    		pair.setEntityIds(skuIds);
    		pair.setEntityType(EntityType.SKU.getType());;
    		entityQueryPairList.add(pair) ;
    	}
    	List<Integer>  statusList = new ArrayList<Integer>() ;
    	statusList.add(com.yimayhd.promotion.client.enums.PromotionStatus.valid.getValue()) ;
    	statusList.add(com.yimayhd.promotion.client.enums.PromotionStatus.create.getValue()) ;
    	
    	List<Integer> promotionTypeList = new ArrayList<Integer>();
    	promotionTypeList.add(PromotionType.DIRECT_REDUCE.getType()) ;
    	
    	PromotionPageQuery promotionPageQuery = new PromotionPageQuery() ;
    	promotionPageQuery.setEntityQueryPairList(entityQueryPairList);
    	promotionPageQuery.setStatusList(statusList);
    	promotionPageQuery.setPromotionTypeList(promotionTypeList);
    	promotionPageQuery.setEndTime(new Date());
    	BasePageResult<PromotionDO> queryResult = promotionQueryService.queryPromotions(promotionPageQuery);
    	if( queryResult == null || !queryResult.isSuccess() ){
    		log.error("queryPromotions failed!  query={},  Result={}", JSON.toJSONString(promotionPageQuery), JSON.toJSONString(queryResult));
    		//查询出错，认为已经有直降优惠了，不创建成功
    		return result;
    	}
    	List<PromotionDO> promotionDOs = queryResult.getList() ;
    	if( CollectionUtils.isEmpty(promotionDOs) ){
    		return result;
    	}
    	
    	long activityId = actActivityDO.getId() ;
    	String tips = null ;
    	for( PromotionDO promotionDO : promotionDOs ){
//    		if( activityId <=0 || ( activityId > 0 && promotionDO.getEntityId() != activityId) ){
    			tips = "您选中的部分商品已经存在直降活动了，见直降活动【"+promotionDO.getTitle()+"】";
    			break ;
//    		}
    	}
    	
        if( StringUtils.isNotBlank(tips) ){
        	 result.setMsg(tips);
        	 result.setSuccess(false);
        }
		return result;
	}

	@Override
    public ActActivityEditVO getById(long id) throws Exception {

        ActResult<ActPromotionDTO> actResult = activityPromotionServiceRef.getActPromotionById(id);
        if(actResult == null){
            log.error("activityPromotionServiceRef.getActPromotionById return null and param : " + id);
            throw new BaseException("返回结果为空");
        } else if(!actResult.isSuccess()){
            log.error("activityPromotionServiceRef.getActPromotionById error:" + actResult + "and param :" + id);
            throw new BaseException(actResult.getMsg());
        }
        ActActivityEditVO actActivityEditVO = ActActivityEditVOConverter.getActActivityEditVO(actResult.getT());
        //组合item和sku信息
        combineItem(actActivityEditVO);
        return actActivityEditVO;
    }
    public void combineItem(ActActivityEditVO actActivityEditVO){
        if(actActivityEditVO != null && CollectionUtils.isNotEmpty(actActivityEditVO.getPromotionVOList())){
            BatchRichSkuQuery batchRichSkuQuery = new BatchRichSkuQuery();
            List<Pair<Long,Long>> itemIdSkuIdPairList = new ArrayList<Pair<Long, Long>>();
            for(PromotionVO promotionVO : actActivityEditVO.getPromotionVOList()){
                if(EntityType.ITEM.getType() == promotionVO.getEntityType()){
                    itemIdSkuIdPairList.add(new Pair<Long, Long>(promotionVO.getEntityId(),null));
                }else if(EntityType.SKU.getType() == promotionVO.getEntityType()){
                    itemIdSkuIdPairList.add(new Pair<Long, Long>(null,promotionVO.getEntityId()));
                }
            }
            batchRichSkuQuery.setItemIdSkuIdPairs(itemIdSkuIdPairList);
            ItemSkuMixResult itemSkuMixResult = itemQueryServiceRef.batchQueryItemSku(batchRichSkuQuery);
            if(itemSkuMixResult == null){
                log.error("itemQueryServiceRef.batchQueryItemSku return null and param : " + JSON.toJSONString(batchRichSkuQuery));
                throw new BaseException("返回结果为空");
            } else if(!itemSkuMixResult.isSuccess()){
                log.error("itemQueryServiceRef.batchQueryItemSku error:" + itemSkuMixResult + "and param :" + JSON.toJSONString(batchRichSkuQuery));
                throw new BaseException(itemSkuMixResult.getResultMsg());
            }
            List<ItemSkuMixDTO> itemSkuMixDTOList = itemSkuMixResult.getItemSkuMixDTOList();
            if(CollectionUtils.isNotEmpty(itemSkuMixDTOList)){
                //转map
                Map<String,ItemSkuMixDTO> map = new HashMap<String, ItemSkuMixDTO>();
                for(ItemSkuMixDTO itemSkuMixDTO : itemSkuMixDTOList){
                    String key = "";
                    if (itemSkuMixDTO.getItemSkuDO() == null){
                        key = String.valueOf(itemSkuMixDTO.getItemDO().getId()) + "_";
                    }else{
                        key = "_" + String.valueOf(itemSkuMixDTO.getItemSkuDO().getId());
                    }
                    map.put(key,itemSkuMixDTO);
                }

                //组合值
                for(PromotionVO promotionVO : actActivityEditVO.getPromotionVOList()){
                    String key = "";
                    if(EntityType.ITEM.getType() == promotionVO.getEntityType()){
                        key = String.valueOf(promotionVO.getEntityId()) + "_";
                    }else if(EntityType.SKU.getType() == promotionVO.getEntityType()){
                        key =  "_" + String.valueOf(promotionVO.getEntityId());
                    }

                    ItemSkuMixDTO itemSkuMixDTO = map.get(key);
                    if(itemSkuMixDTO != null){
                        ItemDO itemDO = itemSkuMixDTO.getItemDO();
                        ItemSkuDO itemSkuDO = itemSkuMixDTO.getItemSkuDO();
                        promotionVO.setItemId(itemDO.getId());
                        promotionVO.setItemTitle(itemDO.getTitle());
                        promotionVO.setItemStatus(itemDO.getStatus());
                        int stock = 0 ;
                        long price =  0 ;
                        if(EntityType.ITEM.getType() == promotionVO.getEntityType()){
                        	price = itemDO.getPrice();
                        	stock = itemDO.getStockNum();
                        }else if(EntityType.SKU.getType() == promotionVO.getEntityType()){
                        	stock = itemSkuDO.getStockNum() ;
                        	promotionVO.setSkuTitle(itemSkuDO.getTitle());
                        	price = itemSkuDO.getPrice() ;
                        }
                        promotionVO.setPriceY(NumUtil.moneyTransformDouble(price));
                        promotionVO.setStockNum(stock);
                        promotionVO.setTitle( itemDO.getTitle() );
                        promotionVO.setResultPriceY(NumUtil.moneyTransformDouble( price-promotionVO.getValue()));
                        if(itemSkuDO != null){
                            promotionVO.setItemSkuId(itemSkuDO.getId());
                            if (StringUtils.isNotEmpty(itemSkuDO.getTitle())){
                                promotionVO.setItemTitle(itemDO.getTitle() + "_" +itemSkuDO.getTitle());
                            }
                            promotionVO.setPriceY(NumUtil.moneyTransformDouble(itemSkuDO.getPrice()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean publish(long id) throws Exception {
        return activityPromotionServiceRef.openActPromotion(id).isSuccess();
    }

    @Override
    public boolean close(long id) throws Exception {
        ActResultSupport actResultSupport= activityPromotionServiceRef.closeActPromotion(id);
        if(!actResultSupport.isSuccess()) {
            log.error("closeActPromotion :"+JSON.toJSONString(actResultSupport)+" isSuccess : "+actResultSupport.isSuccess());
        }
        return actResultSupport.isSuccess();
    }

    @Override
    public boolean isActivityNameRepeat(String name, int type, long activityId) {
    	com.yimayhd.activitycenter.query.ActPromotionPageQuery actPromotionPageQuery = new com.yimayhd.activitycenter.query.ActPromotionPageQuery() ;
    	actPromotionPageQuery.setType(type);
    	actPromotionPageQuery.setTitle(name);
    	ActPageResult<ActActivityPromotionDO> queryResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQuery);
    	if( queryResult == null || !queryResult.isSuccess() ){
    		log.error("queryActPromotions failed!  query={}  result={}",JSON.toJSONString(actPromotionPageQuery), JSON.toJSONString(queryResult));
    		return false;
    	}
    	List<ActActivityPromotionDO> actActivityDOs = queryResult.getList();
    	if( org.springframework.util.CollectionUtils.isEmpty( actActivityDOs ) ){
    		return false;
    	}
    	if( activityId > 0 ){
    		for( ActActivityPromotionDO actActivityDO : actActivityDOs ){
    			if( actActivityDO.getId() == activityId ){
    				return false;
    			}
    		}
    	}
    	return true ;
//        ActActivityPromotionDO actActivityDO = new ActActivityPromotionDO();
//        actActivityDO.setTitle(name);
//        actActivityDO.setType(type);
//        return activityPromotionServiceRef.checkDuplicationActivityName(actActivityDO);
    }
    public ActActivityEditVO getGiftById(long id) throws Exception {

        ActResult<ActPromotionDTO> actResult = activityPromotionServiceRef.getActPromotionById(id);
        if(actResult == null){
            log.error("activityPromotionServiceRef.getActPromotionById return null and param : " + id);
            throw new BaseException("返回结果为空");
        } else if(!actResult.isSuccess()){
            log.error("activityPromotionServiceRef.getActPromotionById error:" + actResult + "and param :" + id);
            throw new BaseException(actResult.getMsg());
        }
        ActActivityEditVO actActivityEditVO = new ActActivityEditVO();
        ActActivityVO actActivityVO = new ActActivityVO();
        List<PromotionVO> promotionVOList = new ArrayList<PromotionVO>();
        ActPromotionDTO actPromotionDTO = actResult.getT();
        List<PromotionDO> promotionDOList = actPromotionDTO.getPromotionDOList();
        ActActivityPromotionDO actActivityPromotionDO = actPromotionDTO.getActActivityPromotionDO();

        actActivityVO.setId(actActivityPromotionDO.getId());
        actActivityVO.setTitle(actActivityPromotionDO.getTitle());
        actActivityVO.setStartDate(actActivityPromotionDO.getStartDate());
        actActivityVO.setEndDate(actActivityPromotionDO.getEndDate());
        actActivityVO.setStatus(actActivityPromotionDO.getStatus());

        for(PromotionDO promotionDO:promotionDOList) {
            PromotionVO promotionVO = new PromotionVO();
            promotionVO.setId(promotionDO.getId());
            String requirement = MoneyUtil.centToYuanMoneyFormat(promotionDO.getRequirement());
            Double requirementY = Double.valueOf(requirement.toString());
            promotionVO.setRequirementY(MoneyUtil.moneyY(requirementY));
            List<GiftVO> gifts = new ArrayList<GiftVO>();
            if (StringUtils.isNotBlank(promotionDO.getFeature())) {
                PromotionFeature promotionFeature = new PromotionFeature(promotionDO.getFeature());
                List<FullGiveFeature> fullGiveFeatures = promotionFeature.getFreeGiftList();
                for (FullGiveFeature fullGiveFeature : fullGiveFeatures) {
                    GiftVO giftVO = new GiftVO();
                    String giftPrice = MoneyUtil.centToYuanMoneyFormat(fullGiveFeature.getPrice());
                    Double money = Double.valueOf(giftPrice.toString());
                    giftVO.setPriceY(MoneyUtil.moneyY(money));
                    giftVO.setPrice(fullGiveFeature.getPrice());
                    giftVO.setTitle(fullGiveFeature.getGiftName());
                    giftVO.setImgUrl(fullGiveFeature.getGiftPicture());
                    gifts.add(giftVO);
                }
            }
            promotionVO.setGifts(gifts);
            promotionVOList.add(promotionVO);
        }
        actActivityEditVO.setActActivityVO(actActivityVO);
        actActivityEditVO.setPromotionVOList(promotionVOList);
        return actActivityEditVO;
    }

    @Override
    public boolean addGift(ActActivityEditVO actActivityEditVO) throws Exception {
        ActActivityVO actActivityVO = actActivityEditVO.getActActivityVO();
        List<PromotionVO> promotionVOList = actActivityEditVO.getPromotionVOList();
        Map<Long, String> features = new HashMap<Long, String>();
        if(actActivityVO.getId()> 0){
            features = getPromotionFeatures(actActivityVO.getId());
        }
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = DateUtil.convertStringToDateUseringFormats(actActivityVO.getStartDateStr(), DateUtil.DAY_HORU_FORMAT);
            endDate = DateUtil.convertStringToDateUseringFormats(actActivityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PromotionDO> addPromotionDOList = new ArrayList<PromotionDO>();
        List<PromotionDO> updPromotionDOList = new ArrayList<PromotionDO>();
        List<Long> delPromotionDOIds = new ArrayList<Long>();

        for(PromotionVO promotionVO:promotionVOList) {
            if(promotionVO.getId()>0 && promotionVO.isDel()){
                delPromotionDOIds.add(promotionVO.getId());
                continue;
            }
            PromotionDO promotionDO = new PromotionDO();

            promotionDO.setId(promotionVO.getId());
            promotionDO.setTitle(actActivityVO.getTitle());
            Double requirementY = Double.valueOf(promotionVO.getRequirementY().toString());
            promotionDO.setRequirement(Math.round(requirementY*100));
            promotionDO.setPromotionType(PromotionType.FREE_GIFT.getType());
            promotionDO.setEntityType(EntityType.SHOP.getType());
            promotionDO.setEntityId(B2CConstant.GF_OFFICIAL_ID);
            //PromotionFeatureKey
            List<FullGiveFeature> fullGiveFeatures = new ArrayList<FullGiveFeature>();
            for(GiftVO gift: promotionVO.getGifts()) {
                FullGiveFeature fullGiveFeature = new FullGiveFeature();
                Double priceY = Double.valueOf(gift.getPriceY().toString());
                fullGiveFeature.setPrice(Math.round(priceY*100));
                fullGiveFeature.setGiftName(gift.getTitle());
                fullGiveFeature.setGiftPicture(gift.getImgUrl());
                fullGiveFeatures.add(fullGiveFeature);
            }
            String feature = "";
            if(promotionVO.getId()>0) {
                feature = features.get(promotionVO.getId());
            }
            PromotionFeature promotionFeature = new PromotionFeature(feature);
            promotionFeature.put(PromotionFeatureKey.FREE_GIFT, fullGiveFeatures);
            String new_feature = promotionFeature.getFeature();
            promotionDO.setFeature(new_feature);

            promotionDO.setStartTime(startDate);
            promotionDO.setEndTime(endDate);
            if(promotionVO.getId()>0){
                updPromotionDOList.add(promotionDO);
            } else {
                addPromotionDOList.add(promotionDO);
            }
        }

        if(actActivityVO.getId()>0){
            ActPromotionEditDTO actPromotionEditDTO = new ActPromotionEditDTO();
            ActActivityPromotionDO actActivityPromotionDO = new ActActivityPromotionDO();
            actActivityPromotionDO.setId(actActivityVO.getId());
            actActivityPromotionDO.setStartDate(startDate);
            actActivityPromotionDO.setEndDate(endDate);
            actActivityPromotionDO.setTitle(actActivityVO.getTitle());
            actPromotionEditDTO.setActActivityPromotionDO(actActivityPromotionDO);

            actPromotionEditDTO.setUpdPromotionDOList(updPromotionDOList);
            actPromotionEditDTO.setAddPromotionDOList(addPromotionDOList);
            actPromotionEditDTO.setDelPromotionIdList(delPromotionDOIds);
            ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
            if(baseResult == null){
                log.error("PromotionCommService.addGift update error: " + actPromotionEditDTO +"baseResult:"+baseResult);
                throw new BaseException("返回结果错误");
            } else if(!baseResult.isSuccess()){
                log.error("PromotionCommService.addGift update error:" + actPromotionEditDTO +"baseResult:"+baseResult);
                throw new BaseException(baseResult.getMsg());
            }
        } else {
            PromotionEditDTO promotionEditDTO = new PromotionEditDTO();
            promotionEditDTO.setAddPromotionDOList(addPromotionDOList);
            ActResultSupport baseResult = activityPromotionServiceRef.saveActivityPromotion(promotionEditDTO);
            if(baseResult == null){
                log.error("PromotionCommService.addGift save  error: " + promotionEditDTO);
                throw new BaseException("返回结果错误");
            } else if(!baseResult.isSuccess()){
                log.error("PromotionCommService.addGift save error:" + promotionEditDTO);
                throw new BaseException(baseResult.getMsg());
            }
        }

        return true;
    }

    public boolean updateGiftEndTime(ActActivityVO actActivityVO) throws Exception {
        Boolean checkResult = checkGift(actActivityVO);
        if(!checkResult){
            log.error("PromotionCommService.updateGiftEndTime error: " + actActivityVO);
            throw new BaseException("有重复活动");
        }
        Date endDate = new Date();
        try {
            endDate = DateUtil.convertStringToDateUseringFormats(actActivityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActResult<ActPromotionDTO> actResult = activityPromotionServiceRef.getActPromotionById(actActivityVO.getId());
        ActPromotionDTO actPromotionDTO = actResult.getT();
        List<PromotionDO> promotionDOList = actPromotionDTO.getPromotionDOList();
        ActActivityPromotionDO actActivityPromotionDO = actPromotionDTO.getActActivityPromotionDO();
        List<PromotionDO> updPromotionDOList = new ArrayList<PromotionDO>();

        for(PromotionDO promotionDO:promotionDOList) {
            promotionDO.setEndTime(endDate);
            updPromotionDOList.add(promotionDO);
        }

        ActPromotionEditDTO actPromotionEditDTO = new ActPromotionEditDTO();
        actActivityPromotionDO.setEndDate(endDate);
        actPromotionEditDTO.setActActivityPromotionDO(actActivityPromotionDO);
        actPromotionEditDTO.setUpdPromotionDOList(updPromotionDOList);

        ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.updateGiftEndTime error: " + actPromotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.updateGiftEndTime error:" + actPromotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
        return true;
    }

    public boolean checkGift(ActActivityVO actActivityVO){
        ActivityPromotionQueryDTO activityPromotionQueryDTO = new ActivityPromotionQueryDTO();
        Date startDate = new Date();
        Date endDate = new Date();
        try {
            startDate = DateUtil.convertStringToDateUseringFormats(actActivityVO.getStartDateStr(), DateUtil.DAY_HORU_FORMAT);
            endDate = DateUtil.convertStringToDateUseringFormats(actActivityVO.getEndDateStr(), DateUtil.DAY_HORU_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] statuses = {
                PromotionStatus.BEING.getStatus()
                , PromotionStatus.NOTBEING.getStatus()
        };
        activityPromotionQueryDTO.setStatuses(statuses);
        activityPromotionQueryDTO.setStart(startDate);
        activityPromotionQueryDTO.setEnd(endDate);
        activityPromotionQueryDTO.setType(PromotionType.FREE_GIFT.getType());
        ActResult<List<ActActivityPromotionDO>> actResult = activityPromotionServiceRef.queryActivityPromotionsByActivityDate(activityPromotionQueryDTO);
        if(!actResult.isSuccess()){
            log.error("queryActivityPromotionsByActivityDate actActivityVO: "+actActivityVO+" result : "+actResult);
        }
        List<ActActivityPromotionDO> actActivityPromotionDOs = actResult.getT();
        if(null == actActivityPromotionDOs) {
            return true;
        }
        if(actActivityPromotionDOs.size()>0){
            if(actActivityPromotionDOs.size()>1){
                return false;
            }
            if(actActivityPromotionDOs.size()==1){
                ActActivityPromotionDO actActivityPromotionDO = actActivityPromotionDOs.get(0);
                if(actActivityPromotionDO.getId()==actActivityVO.getId()){
                    return true;
                }
                return false;
            }
        }
        return true;
    }
    private Map<Long, String> getPromotionFeatures(long id){
        Map<Long, String> promotionFeatures = new HashMap<Long, String>();
        ActResult<ActPromotionDTO> actResult = activityPromotionServiceRef.getActPromotionById(id);
        if(actResult == null){
            log.error("activityPromotionServiceRef.getActPromotionById return null and param : " + id);
            return null;
        } else if(!actResult.isSuccess()){
            log.error("activityPromotionServiceRef.getActPromotionById error:" + actResult + "and param :" + id);
            return null;
        }
        ActPromotionDTO actPromotionDTO = actResult.getT();
        List<PromotionDO> promotionDOList = actPromotionDTO.getPromotionDOList();
        for(PromotionDO promotionDO:promotionDOList) {
            promotionFeatures.put(promotionDO.getId(), promotionDO.getFeature());
        }
        return promotionFeatures;
    }
}
