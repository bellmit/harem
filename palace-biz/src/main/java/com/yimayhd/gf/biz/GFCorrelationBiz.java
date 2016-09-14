package com.yimayhd.gf.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yimayhd.gf.model.CorrelationResultVO;
import com.yimayhd.gf.model.CorrelationVO;
import com.yimayhd.gf.repo.GFCorrelationRepo;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.constant.B2CConstant;
import com.yimayhd.palace.model.CategoryVO;
import com.yimayhd.palace.model.ItemVO;
import com.yimayhd.palace.model.query.CommodityListQuery;
import com.yimayhd.resourcecenter.domain.CorrelationDO;
import com.yimayhd.resourcecenter.domain.RecommendDO;
import com.yimayhd.resourcecenter.dto.RecommendDTO;
import com.yimayhd.resourcecenter.model.enums.BaseStatus;
import com.yimayhd.resourcecenter.model.enums.CorrelationType;
import com.yimayhd.resourcecenter.model.enums.StatusType;
import com.yimayhd.resourcecenter.model.query.CorrelationQuery;
import com.yimayhd.resourcecenter.model.result.RcResult;

public class GFCorrelationBiz {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GFCorrelationBiz.class);

	@Autowired
	private GFCorrelationRepo correlationRepo;

	public CorrelationResultVO getItemList(CommodityListQuery commodityListQuery,long id) throws Exception {
		
		String log = "UUID="+UUID.randomUUID()+" id="+id;
		long start = System.currentTimeMillis() ;
		LOGGER.info(log);
		
		ItemQryDTO itemQryDTO = new ItemQryDTO();
        List<Integer> domainList = new ArrayList<Integer>();
        domainList.add(B2CConstant.GF_DOMAIN);
        itemQryDTO.setDomains(domainList);
        itemQryDTO.setPageNo(commodityListQuery.getPageNumber());
        itemQryDTO.setPageSize(commodityListQuery.getPageSize());

        if(null != commodityListQuery.getId()){
        	itemQryDTO.setId(commodityListQuery.getId());
        }
        
        
        if(!StringUtils.isBlank(commodityListQuery.getCommName())) {
            itemQryDTO.setName(commodityListQuery.getCommName());
        }
        List<Integer> status = new ArrayList<Integer>();
        if(0 != commodityListQuery.getCommStatus()){
            status.add(commodityListQuery.getCommStatus());
        }else{
        	status.add(ItemStatus.create.getValue());
            status.add(ItemStatus.valid.getValue());
            status.add(ItemStatus.invalid.getValue());
        }
        itemQryDTO.setStatus(status);
        if (commodityListQuery.getItemType() != 0) {
            itemQryDTO.setItemType(commodityListQuery.getItemType());
        }

        ItemPageResult itemPageResult = correlationRepo.getItemList(itemQryDTO);
        if(null == itemPageResult){
        	LOGGER.error("GFCorrelationBiz.getItemList-ItemQueryService.getItemList result is null and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException("查询商品信息失败！");
        } else if(!itemPageResult.isSuccess()){
        	LOGGER.error("GFCorrelationBiz.getItemList-ItemQueryService.getItemList error:" + JSON.toJSONString(itemPageResult) + "and parame: " + JSON.toJSONString(itemQryDTO));
            throw new BaseException(itemPageResult.getResultMsg());
        }
        
        LOGGER.info(log+"  correlationRepo.getItemList  cost="+(System.currentTimeMillis()-start));

        
        List<ItemDO> itemDOList = itemPageResult.getItemDOList();
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        
        
        CorrelationQuery correlationQuery = new CorrelationQuery();
        correlationQuery.setOutId(id);
        correlationQuery.setOutType(CorrelationType.GF.getType());
        correlationQuery.setStatus(StatusType.ON_SHELF.getValue());
        
		RcResult<RecommendDTO> recommendDTOResult = correlationRepo.getRecommendItem(correlationQuery );
		LOGGER.info(log+"  correlationRepo.getRecommendItem  cost="+(System.currentTimeMillis()-start));
        if(null == recommendDTOResult || !recommendDTOResult.isSuccess()){
        	LOGGER.error("correlationRepo.getRecommendItem is error:" + JSON.toJSONString(recommendDTOResult) + "and parame: " + JSON.toJSONString(correlationQuery));
            throw new BaseException(recommendDTOResult.getResultMsg());
        }
        
       
    	for(ItemDO itemDO:itemDOList){
        	long itemId = itemDO.getId();
        	itemDO.setSource(0);
        	if(null != recommendDTOResult.getT()){
     	        List<RecommendDO> recommendList = recommendDTOResult.getT().getRecommendList();
     	        
     	        if(CollectionUtils.isNotEmpty(recommendList)){
	            	for (RecommendDO recommendDO : recommendList) {
						if(Long.valueOf(recommendDO.getRecommendContent()) == itemId){
							itemDO.setSource(1);
							break;
						}
	            	}
     	        }
     	    }
        	if(itemId != id){
        		itemVOList.add(ItemVO.getItemVO(itemDO,new CategoryVO()));
        	}
    	}
    	LOGGER.info(log+"  foreach  cost="+(System.currentTimeMillis()-start));
        PageVO<ItemVO> pageVO = new PageVO<ItemVO>(commodityListQuery.getPageNumber(),commodityListQuery.getPageSize(),itemPageResult.getRecordCount(),itemVOList);
        
        CorrelationResultVO correlationResultVO = new CorrelationResultVO();
        
        correlationResultVO.setPageVO(pageVO);
        correlationResultVO.setRecommendDTO(recommendDTOResult.getT());
        
        SingleItemQueryResult querySingleItem = correlationRepo.querySingleItem(id);
        if(null != querySingleItem && querySingleItem.isSuccess()){
        	ItemVO masterItemVO = ItemVO.getItemVO(querySingleItem.getItemDO(), new CategoryVO());
			correlationResultVO.setMasterItemVO(masterItemVO );
        }
        
        LOGGER.info(log+"  correlationRepo.querySingleItem  cost="+(System.currentTimeMillis()-start));
        
        return correlationResultVO;
	}

	public List<ItemVO> getRecommendList(long id) throws Exception {
		CorrelationQuery correlationQuery = new CorrelationQuery();
        correlationQuery.setOutId(id);
        correlationQuery.setOutType(CorrelationType.GF.getType());
        correlationQuery.setStatus(StatusType.ON_SHELF.getValue());
        
		RcResult<RecommendDTO> recommendDTOResult = correlationRepo.getRecommendItem(correlationQuery );
        
        if(null == recommendDTOResult || !recommendDTOResult.isSuccess()){
        	LOGGER.error("correlationRepo.getRecommendItem is error:" + JSON.toJSONString(recommendDTOResult) + "and parame: " + JSON.toJSONString(correlationQuery));
            throw new BaseException(recommendDTOResult.getResultMsg());
        }
        
        List<ItemVO> itemVOList = new ArrayList<ItemVO>();
        SingleItemQueryResult querySingleItem = correlationRepo.querySingleItem(id);
        
        if(null == querySingleItem || !querySingleItem.isSuccess() || null == querySingleItem.getItemDO()){
        	LOGGER.error("correlationRepo.querySingleItem is error:" + JSON.toJSONString(querySingleItem) + "and parame: " + JSON.toJSONString(id));
            throw new BaseException(querySingleItem.getResultMsg());
        }
        itemVOList.add(ItemVO.getItemVO(querySingleItem.getItemDO(), new CategoryVO()));
        
        if(null != recommendDTOResult.getT() && CollectionUtils.isNotEmpty(recommendDTOResult.getT().getRecommendList())){
        	
        	List<RecommendDO> recommendList = recommendDTOResult.getT().getRecommendList();
        	
        	List<Long> recommendIds = getRecommendIds(recommendList);
            
            ItemQryDTO itemQryDTO = new ItemQryDTO();
            List<Integer> domainList = new ArrayList<Integer>();
            domainList.add(B2CConstant.GF_DOMAIN);
            itemQryDTO.setDomains(domainList);
            itemQryDTO.setIds(recommendIds);
    		ItemPageResult itemListResult = correlationRepo.getItemList(itemQryDTO);
            
    		if(null == itemListResult || !itemListResult.isSuccess()){
    			LOGGER.error("correlationRepo.getItemList is error:" + JSON.toJSONString(recommendDTOResult) + "and parame: " + JSON.toJSONString(itemQryDTO));
                throw new BaseException(recommendDTOResult.getResultMsg());
    		}
    		
			Map<String, ItemVO> iteVOMap = getItemDOMap(itemListResult.getItemDOList());
	
			for (RecommendDO recommendItem : recommendList) {
				ItemVO itemVO = iteVOMap.get(recommendItem.getRecommendContent());
				itemVO.setOutId(recommendItem.getId());
				itemVOList.add(itemVO);
			}
        }
        
		
        return itemVOList;
	}
	

	private Map<String, ItemVO> getItemDOMap(List<ItemDO> itemDOList) throws Exception {
		
		Map<String, ItemVO> map = new HashMap<String, ItemVO>();
		
		for (ItemDO itemDO : itemDOList) {
			
			map.put(itemDO.getId() + "", ItemVO.getItemVO(itemDO, new CategoryVO()));
		}
		
		return map;
	}

	private List<Long> getRecommendIds(List<RecommendDO> recommendList) {
		
		List<Long> ids = new ArrayList<Long>();
		
		for (RecommendDO recommendDO : recommendList) {
			ids.add(Long.valueOf(recommendDO.getRecommendContent()));
		}
		
		return ids;
	}
	
	public RcResult<Boolean> batchInsertItem(CorrelationVO correlationVO){
		
		if(null == correlationVO || CollectionUtils.isEmpty(correlationVO.getRecommendIds())){
			return null;
		}
		
		RecommendDTO recommendDTO = new RecommendDTO();
		
		CorrelationDO correlationDO = new CorrelationDO();
		correlationDO.setDomain(B2CConstant.GF_DOMAIN);
		correlationDO.setOutId(correlationVO.getMasterId());
		correlationDO.setOutType(CorrelationType.GF.getType());
		correlationDO.setStatus(StatusType.ON_SHELF.getValue());
		
		List<RecommendDO> recommendList = new ArrayList<RecommendDO>();
		for (long itemId : correlationVO.getRecommendIds()) {
			RecommendDO recommendDO = new RecommendDO();
			recommendDO.setDomain(B2CConstant.GF_DOMAIN);
			recommendDO.setRecommendType(CorrelationType.GF.getType());
			recommendDO.setRecommendContent(itemId + "");
			recommendDO.setStatus(StatusType.ON_SHELF.getValue());
			recommendList.add(recommendDO);
		}
		
		recommendDTO.setCorrelation(correlationDO);
		
		recommendDTO.setRecommendList(recommendList);
		
		RcResult<Boolean> saveItemResult = correlationRepo.saveCorrelationRecommends(recommendDTO );
		
		return saveItemResult;
	}
	
	public RcResult<Boolean> batchDelRecommend(List<Long> idList){
		return  correlationRepo.batchDeleteRecommend(idList);
	}

}
