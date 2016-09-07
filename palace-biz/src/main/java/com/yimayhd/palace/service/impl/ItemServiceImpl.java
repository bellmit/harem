package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.item.IcMerchantVO;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.UserOptions;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.param.item.ItemWeightDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ItemConverter;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.item.ItemInfoVO;
import com.yimayhd.palace.model.item.ItemVO;
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
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private MerchantService userMerchantServiceRef;
	@Override
	public PageVO<ItemInfoVO> getItemList(ItemListQuery query) throws Exception{
			
		if (query == null) {
			query = new ItemListQuery();
		}
		ItemQryDTO itemQryDTO = ItemConverter.toItemQryDTO(query);
		ICPageResult<ItemInfo> icPageResult = itemRepo.getItemList(itemQryDTO);
		
		List<ItemInfo> itemInfoList = icPageResult.getList();
		List<ItemInfoVO> resultList = new ArrayList<ItemInfoVO>();
		if (CollectionUtils.isNotEmpty(itemInfoList)) {
			List<Long> itemIds = new ArrayList<Long>();
			for (ItemInfo itemInfo : itemInfoList) {
				ItemInfoVO temInfoVO =  ItemConverter.toItemInfoVO(itemInfo);
				/**根据用户**型 获取店铺信息**/
				IcMerchantVO icVO  = temInfoVO.getIcMerchantVO();
				BaseResult<IcMerchantVO> result = getIcMerchantVO(itemInfo.getItemDTO().getSellerId());
				if(result.isSuccess()&&result.getValue()!=null){
					icVO.setUserNick(result.getValue().getUserNick());
					icVO.setMerchantName(result.getValue().getMerchantName());
				}
				/****/
				resultList.add(temInfoVO);
				ItemDTO itemDTO = itemInfo.getItemDTO();
				if(itemDTO != null){
					itemIds.add(itemDTO.getId());
				}
			}
			Map<Long, List<ComTagDO>> tagMap = commentRepo.getTagsByOutIds(itemIds, TagType.DESTPLACE);
			if(!org.springframework.util.CollectionUtils.isEmpty(tagMap)) {
				for (ItemInfoVO itemInfoVO : resultList) {
					ItemVO itemVO = itemInfoVO.getItemVO();
					if(itemVO == null){
						continue;
					}
					
					long itemId = itemVO.getId();
					if (tagMap.containsKey(itemId)) {
						List<ComTagDO> comTagDOs = tagMap.get(itemId);
						List<CityVO> dests = toCityVO(comTagDOs);
						itemVO.setDests(dests);
					}
				}
			}

		}
		PageVO<ItemInfoVO> pageVO = new PageVO<ItemInfoVO>(query.getPageNumber(), query.getPageSize(),
				icPageResult.getTotalCount(), resultList);
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

	@Override
	public boolean updateItemOrderNum(long itemId, int orderNum){
		ItemWeightDTO itemWeightDTO = new ItemWeightDTO();
		itemWeightDTO.setItemId(itemId);
		itemWeightDTO.setOrderNum(orderNum);
		try {
			ICResultSupport iCResultSupport = itemRepo.updateItemOrderNum(itemWeightDTO);
			if(null == iCResultSupport || !iCResultSupport.isSuccess()){
				return false;
			}
		} catch (Exception e) {
			log.error("ItemService.updateItemOrderNum error!params:{}", JSONObject.toJSONString(itemWeightDTO),e);
		}
		return true;
	}

	/**
	 * 查询 商户名称,达人昵称
	 * @param sellerId
	 * @return
	 */
	public BaseResult<IcMerchantVO> getIcMerchantVO(long sellerId){
		BaseResult<IcMerchantVO> result = new BaseResult<IcMerchantVO>();
		IcMerchantVO icMerchantVO = new IcMerchantVO();
		BaseResult<UserDO> sellerUserResult = userServiceRef.getUserDOByUserId(sellerId);
		if(!sellerUserResult.isSuccess()||sellerUserResult.getValue()==null){
			log.error("用户信息错误,sellerId={},errorMsg={}",sellerId,sellerUserResult.getErrorMsg());
			result.setErrorMsg(sellerUserResult.getErrorMsg());
			result.setSuccess(false);
			return result;
		}
		UserDO sellerUser = sellerUserResult.getValue();

		if(UserOptions.USER_TALENT.has(sellerUser.getOptions()) || UserOptions.CERTIFICATED.has(sellerUser.getOptions())){
			/**旧达人**/
			// 没有店铺名称,只有昵称
			//tcMerchantInfo.setMerchantName();
			icMerchantVO.setUserNick(sellerUser.getNickname());
		}
		if(UserOptions.COMMON_TELENT.has(sellerUser.getOptions())){
			/**新达人**/
			// 只有昵称
			icMerchantVO.setUserNick(sellerUser.getNickname());
		}
		if(UserOptions.COMMERCIAL_TENANT.has(sellerUser.getOptions())){
			/**商户**/
			//  店铺信息, 昵称用user
			BaseResult<MerchantUserDTO> merchantUserResult = userMerchantServiceRef.getMerchantAndUserBySellerId(sellerId, Constant.DOMAIN_JIUXIU);
			if(!merchantUserResult.isSuccess()||merchantUserResult.getValue()==null){
				log.error("查询商户信息错误,sellerId={},errorMsg={}",sellerId,merchantUserResult.getErrorMsg());
				result.setErrorMsg(merchantUserResult.getErrorMsg());
				result.setSuccess(false);
				return result;
			}
			MerchantDO merchantDO = merchantUserResult.getValue().getMerchantDO();
			icMerchantVO.setMerchantName(merchantDO.getName());
			icMerchantVO.setUserNick(sellerUser.getNickname());
		}
		result.setValue(icMerchantVO);
		result.setSuccess(true);
		return result;
	}
}
