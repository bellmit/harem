package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.item.ItemInfoVO;

/**
 * 商品服务
 * 
 * @author yebin
 *
 */
public interface ItemService {
	/**
	 * 查询商家商品列表
	 * 
	 * @param query
	 * @return
	 */
	PageVO<ItemInfoVO> getItemList(ItemListQuery query) throws Exception;
	
	/**
	 * 下架
	 * 
	 * @param itemId
	 * @return
	 */
	void shelve(long sellerId, long itemId) throws Exception;

	/**
	 * 上架
	 * 
	 * @param itemId
	 * @return
	 */
	void unshelve(long sellerId, long itemId) throws Exception;

	/**
	 * 删除
	 * 
	 * @param sellerId
	 * @param itemId
	 * @return
	 */
	void delete(long sellerId, long itemId) throws Exception;
	
	/**
	 * 批量下架
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	void batchShelve(long sellerId, List<Long> itemIds) throws Exception;

	/**
	 * 批量下架
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	void batchUnshelve(long sellerId, List<Long> itemIds) throws Exception;

	/**
	 * 批量删除
	 * 
	 * @param sellerId
	 * @param itemIds
	 * @return
	 */
	void batchDelete(long sellerId, List<Long> itemIds) throws Exception;

}
