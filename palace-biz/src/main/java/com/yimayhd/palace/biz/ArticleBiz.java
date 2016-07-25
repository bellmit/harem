package com.yimayhd.palace.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.ArticleConsultServiceItemVO;
import com.yimayhd.palace.model.ArticleExpertManItemVO;
import com.yimayhd.palace.model.ArticleProductItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.repo.user.TalentRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.dto.ArticleItemDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.dto.TalentDTO;
import com.yimayhd.user.client.dto.UserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;

/**
 * 达人故事
 * 
 * @author xiemingna
 *
 */
public class ArticleBiz {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ItemQueryService itemQueryService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ItemRepo itemRepo;
	@Autowired
	private MerchantRepo merchantRepo;
	@Autowired
	private TalentRepo talentRepo;

	public ArticleVO getArticle(ArticleDTO articleDTO) {
		// List<ArticleItemDO> articleItemDOs = articleDTO.getArticleItemDOs();
		List<ArticleItemDTO> articleItemDTOs = articleDTO.getArticleItemDTOs();
		HashMap<Long, ItemDO> itemDOMap = new HashMap<Long, ItemDO>();
		HashMap<Long, MerchantDO> merchantDOMap = new HashMap<Long, MerchantDO>();
		getItemDoMapAndMerchantDoMap(articleItemDTOs, itemDOMap, merchantDOMap);
		ArticleVO articleVO = ArticleConverter.getArticleDetailVO(articleDTO, itemDOMap, merchantDOMap);
		return articleVO;
	}

	private void getItemDoMapAndMerchantDoMap(List<ArticleItemDTO> articleItemDTOs, HashMap<Long, ItemDO> itemDOMap, HashMap<Long, MerchantDO> merchantDOMap) {
		HashSet<Long> itemIdSet = new HashSet<Long>();
		ArrayList<Long> list = new ArrayList<Long>();
		List<Long> sellerIdList = new ArrayList<Long>();
		// 封装商品详情
		for (ArticleItemDTO articleItemDTO : articleItemDTOs) {
			ArticleItemDO articleItemDO = articleItemDTO.getArticleItemDO();
			if (articleItemDO.getType() == ArticleItemType.PRODUCT.getValue()) {
				itemIdSet.add(Long.parseLong(articleItemDO.getContent()));
			}
		}
		list.addAll(itemIdSet);
		ICResult<List<ItemDO>> itemDOIcResult = null;
		if (!CollectionUtils.isEmpty(list)) {
			itemDOIcResult = itemQueryService.getItemByIds(list);
		}
		if (itemDOIcResult != null && itemDOIcResult.isSuccess()) {
			List<ItemDO> itemDOs = itemDOIcResult.getModule();
			for (ItemDO itemDO : itemDOs) {
				sellerIdList.add(itemDO.getSellerId());
				itemDOMap.put(itemDO.getId(), itemDO);
			}
		}
		BaseResult<Map<Long, MerchantUserDTO>> baseResult = null;
		if (!CollectionUtils.isEmpty(sellerIdList)) {
			baseResult = merchantService.getMerchantAndUserListBySellerId(sellerIdList, Constant.DOMAIN_JIUXIU);
		}

		if (baseResult != null && baseResult.isSuccess() && baseResult.getValue() != null) {
			Map<Long, MerchantUserDTO> map = baseResult.getValue();
			Collection<MerchantUserDTO> values = map.values();
			for (MerchantUserDTO merchantUserDTO : values) {
				MerchantDO merchantDO = merchantUserDTO.getMerchantDO();
				merchantDOMap.put(merchantDO.getSellerId(), merchantDO);
			}
		}
	}

	public ArticleProductItemVO getArticleProductItemVO(ItemDO itemDO) {
		if (itemDO == null) {
			return null;
		}
		long sellerId = itemDO.getSellerId();
		BaseResult<MerchantDO> result = merchantRepo.getMerchantBySellerId(sellerId);
		if (result == null || result.getValue() == null) {
			return null;
		}
		MerchantDO merchantDO = result.getValue();
		ArticleProductItemVO articleProductItemVO = ArticleConverter.ItemDOToArticleProductItemVO(itemDO, merchantDO);
		return articleProductItemVO;
	}

	public ItemDO getItemById(long id) {
		SingleItemQueryResult itemResult = itemRepo.querySingleItem(id);
		if (itemResult == null || !itemResult.isSuccess()) {
			return null;
		}
		ItemDO itemDO = itemResult.getItemDO();
		return itemDO;
	}

	public UserDTO queryTalentInfo(long userId) {
		BaseResult<TalentDTO> result = talentRepo.queryTalentInfo(userId);
		if (result == null || !result.isSuccess() || result.getValue() == null) {
			return null;
		}
		TalentDTO talentDTO = result.getValue();
		UserDTO userDTO = talentDTO.getUserDTO();
		return userDTO;
	}

	public ArticleConsultServiceItemVO getArticleConsultServiceItemVO(ItemDO itemDO) {
		// TODO 字段获取
		ArticleConsultServiceItemVO articleConsultServiceItemVO = new ArticleConsultServiceItemVO();
		if (PicUrlsUtil.getItemMainPics(itemDO) != null) {
			articleConsultServiceItemVO.setServiceHeadPic(PicUrlsUtil.getItemMainPics(itemDO).get(0));
		}
		articleConsultServiceItemVO.setServiceCurrentPrice(itemDO.getPrice());
		articleConsultServiceItemVO.setServiceOriginalPrice(itemDO.getOriginalPrice());
		articleConsultServiceItemVO.setServiceName(itemDO.getTitle());
		String feature = itemDO.getFeature();
		return articleConsultServiceItemVO;

	}

	public ArticleExpertManItemVO getArticleExpertManItemVO(UserDTO userDTO) {
		ArticleExpertManItemVO articleExpertManItemVO = new ArticleExpertManItemVO();
		articleExpertManItemVO.setHeadPic(userDTO.getAvatar());
		articleExpertManItemVO.setNickName(userDTO.getNickname());
		articleExpertManItemVO.setSignatures(userDTO.getSignature());
		return articleExpertManItemVO;
	}
}
