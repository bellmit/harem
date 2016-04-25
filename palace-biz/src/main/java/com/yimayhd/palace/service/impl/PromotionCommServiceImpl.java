package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.activitycenter.domain.ActActivityDO;
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
import com.yimayhd.palace.model.ActActivityEditVO;
import com.yimayhd.palace.model.PromotionVO;
import com.yimayhd.palace.model.query.ActPromotionPageQuery;
import com.yimayhd.palace.service.PromotionCommService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.NumUtil;
import com.yimayhd.promotion.client.domain.PromotionDO;
import com.yimayhd.promotion.client.dto.BaseItemDTO;
import com.yimayhd.promotion.client.dto.PromotionEditDTO;
import com.yimayhd.promotion.client.enums.EntityType;
import com.yimayhd.promotion.client.enums.PromotionType;
import com.yimayhd.promotion.client.result.ItemPromotionQueryResult;
import com.yimayhd.promotion.client.result.PcBaseItem;
import com.yimayhd.promotion.client.result.PromotionDTO;
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
    public PageVO<ActActivityDO> getList(ActPromotionPageQuery actPromotionPageQuery) throws Exception {
        PageVO<ActActivityDO> promotionDOPageVO = null;
        //构建查询条件
        com.yimayhd.activitycenter.query.ActPromotionPageQuery actPromotionPageQueryRef = new com.yimayhd.activitycenter.query.ActPromotionPageQuery();
        BeanUtils.copyProperties(actPromotionPageQuery,actPromotionPageQueryRef);
        actPromotionPageQueryRef.setPageNo(actPromotionPageQuery.getPageNumber());

        if(StringUtils.isNotBlank(actPromotionPageQuery.getEndTime())) {
            Date endTime = DateUtil.formatMaxTimeForDate(actPromotionPageQuery.getEndTime());
            actPromotionPageQuery.setEndTime(DateUtil.date2String(endTime));
        }
        ActPageResult<ActActivityDO> basePageResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQueryRef);
        if(basePageResult == null){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions result is null and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException("返回结果错误");
        } else if(!basePageResult.isSuccess()){
            log.error("PromotionCommService.getList-PromotionQueryService.queryPromotions error:" + JSON.toJSONString(basePageResult) + "and parame: " + JSON.toJSONString(actPromotionPageQuery) + "and promotionListQuery: " + actPromotionPageQuery);
            throw new BaseException(basePageResult.getMsg());
        }
        promotionDOPageVO = new PageVO<ActActivityDO>(actPromotionPageQuery.getPageNumber(),actPromotionPageQuery.getPageSize(),basePageResult.getTotalCount(),basePageResult.getList());
        return promotionDOPageVO;
    }

    public void modify(ActActivityEditVO actActivityEditVO) throws Exception {
        ActResult<ActPromotionDTO> result = activityPromotionServiceRef.getActPromotionById(actActivityEditVO.getActActivityVO().getId());

        if (result != null && result.getT()!= null && result.getT().getActActivityDO() != null && PromotionStatus.NOTBEING.getStatus() != result.getT().getActActivityDO().getStatus()){
            throw new BaseException("只有未开始活动允许修改");
        }

        ActPromotionEditDTO actPromotionEditDTO = ActPromotionEditDTOConverter.getActPromotionEditDTO(actActivityEditVO);
        List<PromotionDO> addPromotionDOList = actPromotionEditDTO.getAddPromotionDOList() ;
        boolean exist = isItemSkuHasDirectReduce(addPromotionDOList);
        if( exist ){
        	throw new BaseException("存在已拥有直降优惠的商品或者sku了，不能重复添加");
        }
        
        ActResultSupport baseResult = activityPromotionServiceRef.updateActivityPromotion(actPromotionEditDTO);
        if(baseResult == null){
            log.error("PromotionCommService.modify error: " + actPromotionEditDTO);
            throw new BaseException("返回结果错误");
        } else if(!baseResult.isSuccess()){
            log.error("PromotionCommService.modify-promotionPublishService.updPromotion error:" + actPromotionEditDTO);
            throw new BaseException(baseResult.getMsg());
        }
    }
    
    private boolean isItemSkuHasDirectReduce(List<PromotionDO> addPromotionDOList){
    	
    	if( org.springframework.util.CollectionUtils.isEmpty(addPromotionDOList) ){
    		return false;
    	}
//    	List<Long> itemIds = new ArrayList<Long>();
//    	List<Long> skuIds = new ArrayList<Long>();
//    	for( PromotionDO promotionDO : addPromotionDOList ){
//    		int entityType =promotionDO.getEntityType() ;
//    		long entityId = promotionDO.getEntityId() ;
//    		if( EntityType.ITEM.getType() == entityType ){
//    			itemIds.add(entityId) ;
//    		}else{
//    			skuIds.add(entityId) ;
//    		}
//    	}
    	//FIMXE
    	for( PromotionDO promotionDO : addPromotionDOList ){
    		int entityType =promotionDO.getEntityType() ;
    		long entityId = promotionDO.getEntityId() ;
    		BaseItemDTO baseItemDTO = new BaseItemDTO() ;
    		if( EntityType.ITEM.getType() == entityType ){
    			baseItemDTO.setItemId(entityId);
    		}else{
    			baseItemDTO.setItemSkuId(entityId);
    		}
    		//FIXME
//    		ItemPromotionQueryResult queryResult = promotionQueryService.getAvailableItemPromotions(baseItemDTO) ;
//    		if( queryResult == null || !queryResult.isSuccess() || queryResult.getPcBaseItem() == null){
//        		log.error("getAvailableItemPromotions failed!  dto={},  result={}", JSON.toJSONString(baseItemDTO), JSON.toJSONString(queryResult));
//        		return false;
//        	}
//    		boolean exit = existDirectReducePromotion(queryResult);
//    		if( exit ){
//    			return true ;
//    		}
    	}
    	return false;
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
        List<PromotionDO> addPromotionDOList = promotionEditDTO.getAddPromotionDOList() ;
        boolean exist = isItemSkuHasDirectReduce(addPromotionDOList);
        if( exist ){
        	throw new BaseException("存在已拥有直降优惠的商品或者sku了，不能重复添加");
        }
        
        ActResultSupport baseResult = activityPromotionServiceRef.saveActivityPromotion(promotionEditDTO);
//        System.err.println(JSON.toJSONString(promotionEditDTO));
//        System.err.println(JSON.toJSONString(baseResult));
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
        boolean res = activityPromotionServiceRef.closeActPromotion(id).isSuccess();
        return res;
    }

    @Override
    public boolean isActivityNameRepeat(String name, int type, long activityId) {
    	com.yimayhd.activitycenter.query.ActPromotionPageQuery actPromotionPageQuery = new com.yimayhd.activitycenter.query.ActPromotionPageQuery() ;
    	actPromotionPageQuery.setType(type);
    	actPromotionPageQuery.setTitle(name);
    	ActPageResult<ActActivityDO> queryResult = activityPromotionServiceRef.queryActPromotions(actPromotionPageQuery);
    	if( queryResult == null || !queryResult.isSuccess() ){
    		log.error("queryActPromotions failed!  query={}  result={}",JSON.toJSONString(actPromotionPageQuery), JSON.toJSONString(queryResult));
    		return false;
    	}
    	List<ActActivityDO> actActivityDOs = queryResult.getList();
    	if( org.springframework.util.CollectionUtils.isEmpty( actActivityDOs ) ){
    		return false;
    	}
    	if( activityId > 0 ){
    		for( ActActivityDO actActivityDO : actActivityDOs ){
    			if( actActivityDO.getId() == activityId ){
    				return false;
    			}
    		}
    	}
    	return true ;
//        ActActivityDO actActivityDO = new ActActivityDO();
//        actActivityDO.setTitle(name);
//        actActivityDO.setType(type);
//        return activityPromotionServiceRef.checkDuplicationActivityName(actActivityDO);
    }
}
