package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yimayhd.msgcenter.client.domain.PushRecordDO;
import com.yimayhd.msgcenter.client.enums.PushSendType;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.item.IcMerchantVO;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.UserOptions;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.param.item.ItemBatchPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemKeyWordDTO;
import com.yimayhd.ic.client.model.param.item.ItemPublishDTO;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.ic.client.model.param.item.ItemWeightDTO;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.ic.client.model.result.item.ItemCloseResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.MerchantBiz;
import com.yimayhd.palace.convert.ItemConverter;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.item.ItemInfoVO;
import com.yimayhd.palace.model.item.ItemVO;
import com.yimayhd.palace.model.line.CityVO;
import com.yimayhd.palace.repo.CityRepo;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.result.BizResult;
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
//	private static final String PUSH = "很抱歉的通知您，您在九休商家中心申请的入驻因以下问题未审核通过，请尽快登陆九休商家中心，修改相关信息后可再次提交申请。" + PARTTEN
//            + "如您有任何疑问，请直接联系：4000-696-888。";

    private static final String PUSH_TITLE = "商品下架通知";

//    private static final String SPLIT = ";";
    
    private static final int BIZ_TYPE = 1;
    
    private static final int BIZ_SUB_TYPE = 3001;
    
    private static final int APPLICATION_ID = 21;
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
	@Autowired
	private MerchantBiz merchantBiz;
	@Override
	public PageVO<ItemInfoVO> getItemList(ItemListQuery query) throws Exception{
			
		if (query == null) {
			query = new ItemListQuery();
		}
		List<ItemInfoVO> resultList = new ArrayList<ItemInfoVO>();
		ItemQryDTO itemQryDTO = ItemConverter.toItemQryDTO(query);
		if (StringUtils.isNotBlank(query.getSellerName())) {
			
			BizResult<List<Long>> userIds = merchantBiz.getUserIds(query.getSellerName(), query.getMerchantName());
			List<Long> userIdList = userIds.getValue();
			if (CollectionUtils.isEmpty(userIdList)) {
				PageVO<ItemInfoVO> pageVO = new PageVO<ItemInfoVO>(0, query.getPageSize(),
						0, resultList);
				return pageVO;
			}
			itemQryDTO.setUserIds(userIdList);
		}
		ICPageResult<ItemInfo> icPageResult = itemRepo.getItemList(itemQryDTO);
		
		List<ItemInfo> itemInfoList = icPageResult.getList();
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
		ItemCloseResult unshelveResult = itemRepo.unshelve(itemPublishDTO);
		if (unshelveResult != null && unshelveResult.isSuccess()) {
			List<Long> idList = new ArrayList<Long>();
			idList.add(itemId);
			List<ItemDO> itemDOList = itemRepo.getItemByIds(idList);
			if (CollectionUtils.isNotEmpty(itemDOList)) {
				String pushContent =  "您的商品["+itemId+"]["+itemDOList.get(0).getTitle()+"]，操作[下线]，请悉知，如有疑问，请联系九休客服。";
				sendPush(pushContent,sellerId);
			}
		}
		
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
		ItemCloseResult itemCloseResult = itemRepo.batchUnshelve(itemBatchPublishDTO);
		if (itemCloseResult != null && itemCloseResult.isSuccess()) {
//			List<Long> idList = new ArrayList<Long>();
//			idList.add(itemId);
			List<ItemDO> itemDOList = itemRepo.getItemByIds(itemIds);
			if (CollectionUtils.isNotEmpty(itemDOList)) {
				for (ItemDO itemDO : itemDOList) {
					
					String pushContent = "您的商品["+itemDO.getId()+"]["+itemDO.getTitle()+"]，操作[下线]，请悉知，如有疑问，请联系九休客服。"; 
					sendPush(pushContent,sellerId);
				}
			}
		}
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

		if(UserOptions.USER_TALENT.has(sellerUser.getOptions()) || UserOptions.CERTIFICATED.has(sellerUser.getOptions())
				|| UserOptions.COMMON_TELENT.has(sellerUser.getOptions())){
			/**旧达人**/
			// 没有店铺名称,只有昵称
			//tcMerchantInfo.setMerchantName();
			icMerchantVO.setUserNick(sellerUser.getNickname());
		}
//		if(UserOptions.COMMON_TELENT.has(sellerUser.getOptions())){
//			/**新达人**/
//			// 只有昵称
//			icMerchantVO.setUserNick(sellerUser.getNickname());
//		}
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

	/* (non-Javadoc)
	 * created by zhangxiaoyang
	 * <p>Title: updateConsultKeyWord</p> 
	 * <p>Description: </p> 
	 * @param itemId
	 * @param keyWord
	 * @return 
	 * @see com.yimayhd.palace.service.ItemService#updateConsultKeyWord(long, java.lang.String)
	 */
	@Override
	public boolean updateConsultKeyWord(long itemId, String keyWord) {
		if (itemId <= 0 || StringUtils.isBlank(keyWord)) {
			log.error("params:itemId={},keyWord={}",itemId,keyWord);
			return false;
		}
		ItemKeyWordDTO itemKeyWordDTO = new ItemKeyWordDTO();
		itemKeyWordDTO.setItemId(itemId);
		itemKeyWordDTO.setKeyWord(keyWord);
		ICResult<Boolean> updateResult = itemRepo.updateConsultKeyWord(itemKeyWordDTO);
		if (updateResult == null || !updateResult.isSuccess()) {
			log.error("itemRepo.updateConsultKeyWord result:{}",JSON.toJSONString(updateResult));
			return false;
		}
		return true;
	}
	
	private boolean sendPush(String pushContent,long sellerId) {
		PushRecordDO record = new PushRecordDO();
		record.setPushContent(pushContent);
		record.setPushTitle(PUSH_TITLE);
		record.setBizType(BIZ_TYPE);
		record.setBizSubtype(BIZ_SUB_TYPE);
		record.setApplicationId(APPLICATION_ID);
		record.setUserId(sellerId);
		record.setOutId(System.currentTimeMillis());
		record.setSendType(PushSendType.REGISTRATION_ID.getType());
		com.yimayhd.msgcenter.client.result.BaseResult<Boolean> pushResult = itemRepo.PushMsg(record);
		if (pushContent == null || !pushResult.isSuccess()) {
			log.error("push fail,result:{}",JSON.toJSONString(pushResult));
			return false;
		}
		return true;
		
	}
	
}
