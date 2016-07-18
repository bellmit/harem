package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

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
		List<ArticleItemDO> articleItemDOs = articleDTO.getArticleItemDOs();
		ArticleVO articleVO = new ArticleVO();
		BeanUtils.copyProperties(articleDO, articleVO);
		List<ArticleItemVO> articleItemVOList = new ArrayList<ArticleItemVO>();
		for (ArticleItemDO articleItemDO : articleItemDOs) {
			ArticleItemVO articleItemVO = new ArticleItemVO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			articleItemVOList.add(articleItemVO);
		}
		articleVO.setArticleItems(articleItemVOList);
		return articleVO;
	}

	public static ArticleDTO getArticleDTO(ArticleVO articleVO) {
		ArticleDTO articleDTO = new ArticleDTO();
		ArticleDO articleDO = new ArticleDO();
		BeanUtils.copyProperties(articleVO, articleDO);
		articleDO.setDomainId(Long.valueOf(Constant.DOMAIN_JIUXIU));
		List<ArticleItemVO> articleItems = articleVO.getArticleItems();
		List<ArticleItemDO> articleItemDOs = new ArrayList<ArticleItemDO>();
		if (CollectionUtils.isNotEmpty(articleItems)) {
			for (ArticleItemVO articleItemVO : articleItems) {
				ArticleItemDO articleItemDO = new ArticleItemDO();
				BeanUtils.copyProperties(articleItemVO, articleItemDO);
				articleItemDOs.add(articleItemDO);
			}
		}
		articleDTO.setArticleDO(articleDO);
		articleDTO.setArticleItemDOs(articleItemDOs);
		return articleDTO;
	}

	public static ArticleVO getArticleDetailVO(ArticleDTO articleDTO, HashMap<Long, ItemDO> itemDOMap,
			HashMap<Long, MerchantDO> merchantDOMap) {
		ArticleVO articleVO = new ArticleVO();
		List<ArticleItemVO> articleItems = new ArrayList<ArticleItemVO>();
		ArticleDO articleDO = articleDTO.getArticleDO();
		List<ArticleItemDO> articleItemDOs = articleDTO.getArticleItemDOs();
		BeanUtils.copyProperties(articleDO, articleVO);
		for (ArticleItemDO articleItemDO : articleItemDOs) {
			ArticleItemVO articleItemVO = new ArticleItemVO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			if (articleItemDO.getType()==ArticleItemType.PRODUCT.getValue()) {
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
		articleVO.setArticleItems(articleItems);
		return articleVO;
	}

	public static void ItemDOToArticleProductItemVO(ArticleItemVO articleItemVO,
			ArticleProductItemVO articleProductItemVO, ItemDO itemDO, MerchantDO merchantDO) {
		articleProductItemVO.setItemPic(PicUrlsUtil.getItemMainPics(itemDO).get(0));
		articleProductItemVO.setItemPrice(Float.valueOf(itemDO.getPrice()));
		articleProductItemVO.setItemTitle(itemDO.getTitle());
		articleProductItemVO.setItemType(ItemType.get(itemDO.getItemType()).getText());
		articleProductItemVO.setMerchantLogo(merchantDO.getLogo());
		articleProductItemVO.setMerchantName(merchantDO.getName());
		ItemFeature itemFeature = itemDO.getItemFeature();
		if (itemFeature != null) {
			List<IcSubject> subjects = itemFeature.getSubjects();
			String itemPicTitle = "";
			for (IcSubject icSubject : subjects) {
				String txt = icSubject.getTxt();
				itemPicTitle += txt + "   ";
			}
			articleProductItemVO.setItemTag(itemPicTitle);
		}
		articleItemVO.setArticleProductItemVO(articleProductItemVO);
	}
}
