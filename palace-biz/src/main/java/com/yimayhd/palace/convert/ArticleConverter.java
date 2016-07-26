package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.yimayhd.ic.client.model.domain.item.IcSubject;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleProductItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.util.RegExpValidator;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.dto.ArticleItemDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.user.client.domain.MerchantDO;

/**
 * H5文章转换
 * 
 * @author xiemingna
 *
 */
public class ArticleConverter {
	public static ArticleVO getArticleVO(ArticleDTO articleDTO) {
		ArticleDO articleDO = articleDTO.getArticleDO();
		List<ArticleItemDTO> articleItemDTOs = articleDTO.getArticleItemDTOs();
		ArticleVO articleVO = new ArticleVO();
		BeanUtils.copyProperties(articleDO, articleVO);
		List<ArticleItemVO> articleItemVOList = new ArrayList<ArticleItemVO>();
		for (ArticleItemDTO articleItemDTO : articleItemDTOs) {
			ArticleItemDO articleItemDO = articleItemDTO.getArticleItemDO();
			ArticleItemVO articleItemVO = new ArticleItemVO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			articleItemVOList.add(articleItemVO);
		}
		articleVO.setArticleItemList(articleItemVOList);
		return articleVO;
	}

	public static ArticleDTO getArticleDTO(ArticleVO articleVO) {
		ArticleDTO articleDTO = new ArticleDTO();
		ArticleDO articleDO = new ArticleDO();
		articleDO.setTitle(articleVO.getTitle());
		articleDO.setDomainId(Long.valueOf(Constant.DOMAIN_JIUXIU));
		articleDO.setFrontcover(articleVO.getFrontcover());
		articleDO.setPv(articleVO.getPv());
		articleDO.setSubTitle(articleVO.getSubTitle());
		articleDO.setType(articleVO.getType());
		articleDO.setGmtCreated(new Date());
		List<ArticleItemVO> articleItems = articleVO.getArticleItemList();
		List<ArticleItemDTO> articleItemDTOs = new ArrayList<ArticleItemDTO>();
		if (CollectionUtils.isNotEmpty(articleItems)) {
			for (ArticleItemVO articleItemVO : articleItems) {
				ArticleItemDTO articleItemDTO = new ArticleItemDTO();
				ArticleItemDO articleItemDO = new ArticleItemDO();
				BeanUtils.copyProperties(articleItemVO, articleItemDO);
				articleItemDTO.setArticleItemDO(articleItemDO);
				articleItemDTOs.add(articleItemDTO);
			}
		}
		articleDTO.setArticleDO(articleDO);
		articleDTO.setArticleItemDTOs(articleItemDTOs);
		return articleDTO;
	}

	public static ArticleVO getArticleDetailVO(ArticleDTO articleDTO, HashMap<Long, ItemDO> itemDOMap, HashMap<Long, MerchantDO> merchantDOMap) {
		ArticleVO articleVO = new ArticleVO();
		List<ArticleItemVO> articleItems = new ArrayList<ArticleItemVO>();
		ArticleDO articleDO = articleDTO.getArticleDO();
		List<ArticleItemDTO> articleItemDTOs = articleDTO.getArticleItemDTOs();
		BeanUtils.copyProperties(articleDO, articleVO);
		for (ArticleItemDTO articleItemDTO : articleItemDTOs) {
			ArticleItemDO articleItemDO = articleItemDTO.getArticleItemDO();
			ArticleItemVO articleItemVO = new ArticleItemVO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			if (articleItemDO.getType() == ArticleItemType.PRODUCT.getValue()) {
				ArticleProductItemVO articleProductItemVO = new ArticleProductItemVO();
				Long itemId = 0L;
				if (RegExpValidator.IsNumber(articleItemDO.getContent())) {
					itemId = Long.parseLong(articleItemDO.getContent());
				}
				ItemDO itemDO = itemDOMap.get(itemId);
				MerchantDO merchantDO = merchantDOMap.get(itemDO.getSellerId());
				ItemDOToArticleProductItemVO(articleItemVO, articleProductItemVO, itemDO, merchantDO);
			}
			articleItems.add(articleItemVO);
		}
		articleVO.setArticleItemList(articleItems);
		return articleVO;
	}

	public static void ItemDOToArticleProductItemVO(ArticleItemVO articleItemVO, ArticleProductItemVO articleProductItemVO, ItemDO itemDO, MerchantDO merchantDO) {
		articleItemVO.setSubType(itemDO.getItemType());
		if (PicUrlsUtil.getItemMainPics(itemDO) != null) {
			articleProductItemVO.setItemPic(PicUrlsUtil.getItemMainPics(itemDO).get(0));
		}
		articleProductItemVO.setItemPrice(itemDO.getPrice());
		articleProductItemVO.setItemTitle(itemDO.getTitle());
		articleProductItemVO.setItemType(ItemType.get(itemDO.getItemType()).getText());
		articleProductItemVO.setMerchantLogo(merchantDO.getLogo());
		articleProductItemVO.setMerchantName(merchantDO.getName());
		ItemFeature itemFeature = itemDO.getItemFeature();
		if (itemFeature != null) {
			List<IcSubject> subjects = itemFeature.getSubjects();
			List<String> itemTagList = new ArrayList<String>();
			if (subjects != null) {
				for (IcSubject icSubject : subjects) {
					String txt = icSubject.getTxt();
					itemTagList.add(txt);
				}
				articleProductItemVO.setItemTagList(itemTagList);
			}
		}
		articleItemVO.setArticleProductItemVO(articleProductItemVO);
	}

	public static ArticleVO convertToArticleVO(ArticleVO articleVO) {
		String articleItems = articleVO.getArticleItems();
		JSONArray jsonarray = JSONArray.parseArray(articleItems);
		String json = jsonarray.toString();
		List<ArticleItemVO> list = JSONArray.parseArray(json, ArticleItemVO.class);
		articleVO.setArticleItemList(list);
		return articleVO;
	}

}
