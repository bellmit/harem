package com.yimayhd.gf.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.ICErrorCode;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.domain.RecommendDO;
import com.yimayhd.resourcecenter.dto.RecommendDTO;
import com.yimayhd.resourcecenter.model.query.CorrelationQuery;
import com.yimayhd.resourcecenter.model.query.RecommendQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.CorrelationClientService;
import com.yimayhd.resourcecenter.service.RecommendClientService;

public class GFCorrelationRepo {

	private static final Logger LOGGER = LoggerFactory.getLogger(GFCorrelationRepo.class);
	
	@Autowired
    private ItemQueryService itemQueryService;
	
	@Autowired
	private CorrelationClientService correlationClientService;
	
	@Autowired
	private RecommendClientService recommendClientService;
	
	public ItemPageResult getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		RepoUtils.requestLog(LOGGER, "GFCorrelationRepo.getItem", itemQryDTO);
		ItemPageResult itemPageResult = itemQueryService.getItem(itemQryDTO);
		RepoUtils.resultLog(LOGGER, "GFCorrelationRepo.getItem", itemPageResult);
		return itemPageResult;
	}

	public RcResult<RecommendDTO> getRecommendItem(CorrelationQuery correlationQuery){
		
		RepoUtils.requestLog(LOGGER, "GFCorrelationRepo.getRecommendItem", correlationQuery);
		RcResult<RecommendDTO> recommendDTOResult = recommendClientService.getRecommendDTOByCorrelation(correlationQuery);
		RepoUtils.resultLog(LOGGER, "GFCorrelationRepo.getRecommendItem", recommendDTOResult);
		
		return recommendDTOResult;
	}
	
	public RcResult<Boolean> saveCorrelationRecommends(RecommendDTO recommendDTO){
		
		RepoUtils.requestLog(LOGGER, "GFCorrelationRepo.saveCorrelationRecommends", recommendDTO);
		RcResult<Boolean> saveResult = correlationClientService.saveCorrelationRecommends(recommendDTO);
		RepoUtils.resultLog(LOGGER, "GFCorrelationRepo.saveCorrelationRecommends", saveResult);
		return saveResult;
	}
	
	public RcResult<Boolean> batchDeleteRecommend(List<Long> recommendIds){
		RepoUtils.requestLog(LOGGER, "GFCorrelationRepo.batchDeleteRecommend", recommendIds);
		RcResult<Boolean> deleteResult = recommendClientService.batchDeleteRecommend(recommendIds);
		RepoUtils.resultLog(LOGGER, "GFCorrelationRepo.batchDeleteRecommend", deleteResult);
		return deleteResult;
	}
	
	public SingleItemQueryResult querySingleItem(long itemId) {
		ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		SingleItemQueryResult singleItemQueryResult  = itemQueryService.querySingleItem(itemId,itemOptionDTO );
		RepoUtils.resultLog(LOGGER, "itemBizQueryService.querySingleItem", singleItemQueryResult);
		return singleItemQueryResult;
	}
}
