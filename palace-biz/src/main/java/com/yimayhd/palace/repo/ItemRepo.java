package com.yimayhd.palace.repo;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.fhtd.logger.annot.MethodLogger;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.ic.client.model.enums.ItemSkuStatus;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemKeyWordDTO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.param.item.ItemSkuQueryDTO;
import com.yimayhd.ic.client.model.param.item.ItemWeightDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.ic.client.model.result.item.ItemDeleteResult;
import com.yimayhd.ic.client.model.result.item.ItemPubResult;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemBizQueryService;
import com.yimayhd.ic.client.service.item.ItemPublishService;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.service.item.ItemSkuService;
import com.yimayhd.msgcenter.client.domain.PushRecordDO;
import com.yimayhd.msgcenter.client.result.BaseResult;
import com.yimayhd.msgcenter.client.service.MsgCenterService;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;

public class ItemRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private ItemBizQueryService itemBizQueryServiceRef;
	@Autowired
	private ItemPublishService itemPublishServiceRef;
	@Autowired
	private ItemSkuService itemSkuService ;
	@Autowired
	private ItemQueryService itemQueryServiceRef;

	@Autowired
	private MsgCenterService msgCenterServiceRef;
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

	public ItemCloseResult unshelve(ItemPublishDTO itemPublishDTO) {
		if (itemPublishDTO == null) {
			log.warn("ItemRepo.unshelve(itemPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.close", itemPublishDTO);
		ItemCloseResult itemCloseResult = itemPublishServiceRef.close(itemPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.close", itemCloseResult);
		return itemCloseResult;
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

	public ItemCloseResult batchUnshelve(ItemBatchPublishDTO itemBatchPublishDTO) {
		if (itemBatchPublishDTO == null) {
			log.warn("ItemRepo.batchUnshelve(itemBatchPublishDTO) error: itemPublishDTO is null");
			throw new BaseException("参数为null ");
		}
		RepoUtils.requestLog(log, "itemQueryServiceRef.batchClose", itemBatchPublishDTO);
		ItemCloseResult batchClose = itemPublishServiceRef.batchClose(itemBatchPublishDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.batchClose", batchClose);
		return batchClose;
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
	public boolean changeToFItem(long id) {
		ItemPubResult result = itemPublishServiceRef.changeToFItem(id);
		if(!result.isSuccess()){
			log.error("changeToFItem itemPublishService id: " + id + "ItemPubResult" + result);
			return false;
		}
		return true;
	}
	/**
	 * 获取item sku的库存，（所有sku的库存之和）
	 * @return
	 */
	public int getItemSkuSumStock(long itemId){
		 List<ItemSkuDO> skus = getItemSkus(itemId);
		 int stock = 0 ;
		 if( !CollectionUtils.isEmpty(skus) ){
			 for( ItemSkuDO sku : skus ){
				 stock += sku.getStockNum() ;
			 }
		 }
		 return stock ;
	}
	
	public List<ItemSkuDO> getItemSkus(long itemId){
		ItemSkuQueryDTO itemSkuQueryDTO = new ItemSkuQueryDTO() ;
		itemSkuQueryDTO.setItemId(itemId);
		itemSkuQueryDTO.setStatus(ItemSkuStatus.valid.getValue());
		ICPageResult<ItemSkuDO> queryResult = itemSkuService.getItemSkuPage(itemSkuQueryDTO);
		if( queryResult == null || !queryResult.isSuccess() ){
			return null;
		}
		return queryResult.getList() ;
	}

	public List<ItemDO> getItemByIds(List<Long> ids) {
		ICResult<List<ItemDO>> icResult = itemQueryServiceRef.getItemByIds(ids);
		if(null != icResult && icResult.isSuccess() && !CollectionUtils.isEmpty(icResult.getModule()) ){
			return icResult.getModule();
		}
		return null;
	}
	public SingleItemQueryResult querySingleItem(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItemById", id);
		ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		SingleItemQueryResult result = itemQueryServiceRef.querySingleItem(id, itemOptionDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItemById", result);
		return result;
	}
	public ItemResult getItem(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItemById", id);
		ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		ItemResult result = itemQueryServiceRef.getItem(id, itemOptionDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItemById", result);
		return result;
	}

	@MethodLogger
	public ICResultSupport updateItemOrderNum(ItemWeightDTO itemWeightDTO){
		if(null == itemWeightDTO || itemWeightDTO.getItemId() <= 0 || itemWeightDTO.getOrderNum() <=0){
			return null;
		}
		return itemPublishServiceRef.updateItemOrderNum(itemWeightDTO);
	}

	/**
	 * 
	* created by zhangxiaoyang
	* @date 2016年9月8日
	* @Description:更新咨询商品关键字
	* @param itemKeyWordDTO
	* @return ICResult<Boolean>
	* @throws
	 */
	public ICResult<Boolean> updateConsultKeyWord(ItemKeyWordDTO itemKeyWordDTO) {
		log.debug("itemPublishServiceRef.updateConsultItemKeyWord param:ItemKeyWordDTO={}",JSON.toJSONString(itemKeyWordDTO));
		ICResult<Boolean> updateResult = itemPublishServiceRef.updateConsultItemKeyWord(itemKeyWordDTO);
		log.debug("itemPublishServiceRef.updateConsultItemKeyWord result:{}",JSON.toJSONString(updateResult));
		return updateResult;
	}
	/**
	 * 
	* created by zhangxiaoyang
	* @date 2016年9月30日
	* @Description:发送短信
	* @param 
	* @return BaseResult<Boolean>
	* @throws
	 */
	public BaseResult<Boolean> PushMsg(PushRecordDO paramPushRecordDO) {
		log.info("param PushRecordDO={}",JSON.toJSONString(paramPushRecordDO));
		BaseResult<Boolean> sendSmsResult = msgCenterServiceRef.sendPush(paramPushRecordDO);
		log.info("msgCenterServiceRef.sendPush result:{}",JSON.toJSONString(sendSmsResult));
		return sendSmsResult;
	}
}
