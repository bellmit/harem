package com.yimayhd.palace.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.domain.item.IcDestination;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
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
import com.yimayhd.palace.repo.DestinationRepo;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.repo.user.TalentRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.dto.ArticleItemDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.resourcecenter.model.query.DestinationQueryDTO;
import com.yimayhd.resourcecenter.model.result.RcResult;
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
	@Autowired
	private DestinationRepo destinationRepo;

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

	/**
	 * 查询达人信息
	 * @param userId
	 * @return
	 */
	public UserDTO queryTalentInfo(long userId) {
		BaseResult<TalentDTO> result = talentRepo.queryTalentInfo(userId);
		if (result == null || !result.isSuccess() || result.getValue() == null) {
			return null;
		}
		TalentDTO talentDTO = result.getValue();
		UserDTO userDTO = talentDTO.getUserDTO();
		return userDTO;
	}

	/**
	 * 封装咨询服务
	 * @param itemDO
	 * @return
	 */
	public ArticleConsultServiceItemVO getArticleConsultServiceItemVO(ItemDO itemDO) {
		ArticleConsultServiceItemVO articleConsultServiceItemVO = new ArticleConsultServiceItemVO();
		if (PicUrlsUtil.getItemMainPics(itemDO) != null) {
			articleConsultServiceItemVO.setServiceHeadPic(PicUrlsUtil.getItemMainPics(itemDO).get(0));
		}
		articleConsultServiceItemVO.setServiceCurrentPrice(itemDO.getPrice());
		articleConsultServiceItemVO.setServiceOriginalPrice(itemDO.getOriginalPrice());
		articleConsultServiceItemVO.setServiceName(itemDO.getTitle());
		List<String> citys = getCityNameList(itemDO);
		articleConsultServiceItemVO.setServiceCity(citys);
		ItemFeature itemFeature = itemDO.getItemFeature();
		if (itemFeature != null) {
			articleConsultServiceItemVO.setConsultTime(itemFeature.getConsultTime());
		}
		return articleConsultServiceItemVO;

	}

	/**
	 * 获得城市列表
	 * @param itemDO
	 * @return
	 */
	private List<String> getCityNameList(ItemDO itemDO) {
		if (itemDO==null) {
			return null;
		}
		ItemFeature itemFeature = itemDO.getItemFeature();
		if (itemFeature == null) {
			return null;
		}
		List<IcDestination> destCities = itemFeature.getDestCities();
		ArrayList<Integer> cityCodeList = new ArrayList<Integer>();
		if (CollectionUtils.isEmpty(destCities)) {
			return null;
		}
		for (IcDestination icDestination : destCities) {
			String code = icDestination.getCode();
			if (StringUtils.isNumeric(code)) {
				int parseInt = Integer.parseInt(code);
				cityCodeList.add(parseInt);
			}
		}
		ArrayList<String> citys = new ArrayList<String>();
		DestinationQueryDTO aDestinationQueryDTO = new DestinationQueryDTO();
		aDestinationQueryDTO.setCodeList(cityCodeList);
		RcResult<List<DestinationDO>> result = destinationRepo.queryDestinationList(aDestinationQueryDTO);
		if (result == null || !result.isSuccess() || CollectionUtils.isEmpty(result.getT())) {
			return null;
		}
		List<DestinationDO> destinationDOs = result.getT();
		if (CollectionUtils.isNotEmpty(destinationDOs)) {
			for (DestinationDO destinationDO : destinationDOs) {
				citys.add(destinationDO.getName());
			}
		}
		return citys;
	}

}
