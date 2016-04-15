package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ItemConverter;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.item.ItemListItemVO;
import com.yimayhd.palace.model.line.CityVO;
import com.yimayhd.palace.repo.CityRepo;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.service.ItemService;
import com.yimayhd.user.client.dto.CityDTO;

/**
 * 商品服务（实现）
 * 
 * @author yebin
 *
 */
public class ItemServiceImpl implements ItemService {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private CityRepo cityRepo;

	@Override
	public PageVO<ItemListItemVO> getItemList(long sellerId, ItemListQuery query) throws Exception{
			
		if (query == null) {
			query = new ItemListQuery();
		}
		ItemQryDTO itemQryDTO = ItemConverter.toItemQryDTO(sellerId, query);
		ItemPageResult itemPageResult = itemRepo.getItemList(itemQryDTO);
		
		List<ItemDO> itemDOList = itemPageResult.getItemDOList();
		List<ItemListItemVO> resultList = new ArrayList<ItemListItemVO>();
		if (CollectionUtils.isNotEmpty(itemDOList)) {
			List<Long> itemIds = new ArrayList<Long>();
			for (ItemDO itemDO : itemDOList) {
				resultList.add(ItemConverter.toItemListItemVO(itemDO));
				itemIds.add(itemDO.getId());
			}
			Map<Long, List<ComTagDO>> tagMap = commentRepo.getTagsByOutIds(itemIds, TagType.DESTPLACE);
			if(!org.springframework.util.CollectionUtils.isEmpty(tagMap)) {
				for (ItemListItemVO itemListItemVO : resultList) {
					long itemId = itemListItemVO.getId();
					if (tagMap.containsKey(itemId)) {
						List<ComTagDO> comTagDOs = tagMap.get(itemId);
						List<CityVO> dests = toCityVO(comTagDOs);
						itemListItemVO.setDests(dests);
					}
				}
			}

		}
		PageVO<ItemListItemVO> pageVO = new PageVO<ItemListItemVO>(query.getPageNumber(), query.getPageSize(),
				itemPageResult.getRecordCount(), resultList);
		return pageVO;
	}
	
	private List<CityVO> toCityVO(List<ComTagDO> tags) {
		if (CollectionUtils.isEmpty(tags)) {
			return new ArrayList<CityVO>(0);
		}
		List<String> codes = new ArrayList<String>();
		for (ComTagDO comTagDO : tags) {
			codes.add(comTagDO.getName());
		}
		Map<String, CityDTO> citiesByCodes = cityRepo.getCitiesByCodes(codes);
		List<CityVO> departs = new ArrayList<CityVO>();
		for (ComTagDO comTagDO : tags) {
			if (citiesByCodes.containsKey(comTagDO.getName())) {
				CityDTO cityDTO = citiesByCodes.get(comTagDO.getName());
				departs.add(new CityVO(comTagDO.getId(), cityDTO.getName(), cityDTO));
			}
		}
		return departs;
	}
	
	@Override
	public void shelve(long sellerId, long itemId) throws Exception{
		ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
		itemPublishDTO.setSellerId(sellerId);
		itemPublishDTO.setItemId(itemId);
		itemRepo.shelve(itemPublishDTO);
	}

	@Override
	public void unshelve(long sellerId, long itemId) throws Exception{
		ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
		itemPublishDTO.setSellerId(sellerId);
		itemPublishDTO.setItemId(itemId);
		itemRepo.unshelve(itemPublishDTO);
	}

	@Override
	public void delete(long sellerId, long itemId) throws Exception{
		ItemPublishDTO itemPublishDTO = new ItemPublishDTO();
		itemPublishDTO.setSellerId(sellerId);
		itemPublishDTO.setItemId(itemId);
		itemRepo.delete(itemPublishDTO);
	}
	
	@Override
	public void batchShelve(long sellerId, List<Long> itemIds) throws Exception{
		ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
		itemBatchPublishDTO.setSellerId(sellerId);
		itemBatchPublishDTO.setItemIdList(itemIds);
		itemRepo.batchShelve(itemBatchPublishDTO);
	}

	@Override
	public void batchUnshelve(long sellerId, List<Long> itemIds) throws Exception{
		ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
		itemBatchPublishDTO.setSellerId(sellerId);
		itemBatchPublishDTO.setItemIdList(itemIds);
		itemRepo.batchUnshelve(itemBatchPublishDTO);
	}

	@Override
	public void batchDelete(long sellerId, List<Long> itemIds) throws Exception{
		ItemBatchPublishDTO itemBatchPublishDTO = new ItemBatchPublishDTO();
		itemBatchPublishDTO.setSellerId(sellerId);
		itemBatchPublishDTO.setItemIdList(itemIds);
		itemRepo.batchDelete(itemBatchPublishDTO);
	}
}
