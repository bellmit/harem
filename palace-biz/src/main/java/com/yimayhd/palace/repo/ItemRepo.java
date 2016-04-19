package com.yimayhd.palace.repo;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPageResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;

public class ItemRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemBizQueryService itemBizQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;

	public ICPageResult<ItemInfo> getItemList(ItemQryDTO itemQryDTO) {
		if (itemQryDTO == null) {
			throw new BaseException("参数为null,查询商品列表失败 ");
		}
		itemQryDTO.setDomains(Arrays.asList(Constant.DOMAIN_JIUXIU));
		RepoUtils.requestLog(log, "itemBizQueryServiceRef.getItem", itemQryDTO);
		ICPageResult<ItemInfo> pageResult = itemBizQueryServiceRef.getItem(itemQryDTO);
		RepoUtils.resultLog(log, "itemBizQueryServiceRef.getItem", pageResult);
		return pageResult;
	}
	
	public void shelve(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.shelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.publish", itemPublishDTO);
		ItemPubResult itemPubResult = itemPublishServiceRef.publish(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.publish", itemPubResult);
	}

	public void unshelve(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.unshelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.close", itemPublishDTO);
		ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.close", itemCloseResult);
	}

	public void delete(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.delete(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.delete", itemPublishDTO);
		ItemDeleteResult delete = itemPublishServiceRef.delete(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.delete", delete);
	}

	public void batchShelve(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchShelve(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchPublish", itemBatchPublishDTO);
		ItemPubResult batchPublish = itemPublishServiceRef.batchPublish(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchPublish", batchPublish);
	}

	public void batchUnshelve(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchUnshelve(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchClose", itemBatchPublishDTO);
		ItemCloseResult batchClose = itemPublishServiceRef.batchClose(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchClose", batchClose);
	}

	public void batchDelete(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchDelete(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchClose", itemBatchPublishDTO);
		ItemDeleteResult batchDelete = itemPublishServiceRef.batchDelete(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchClose", batchDelete);
	}

}
