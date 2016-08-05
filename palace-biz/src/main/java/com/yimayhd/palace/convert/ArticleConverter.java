package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONArray;
import com.yimayhd.ic.client.model.domain.item.IcSubject;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemFeature;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.util.PicUrlsUtil;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.ArticleConsultServiceItemVO;
import com.yimayhd.palace.model.ArticleExpertManItemVO;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleProductItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.util.RegExpValidator;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.dto.ArticleConsultServiceItemDTO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.dto.ArticleExpertManItemDTO;
import com.yimayhd.resourcecenter.dto.ArticleItemDTO;
import com.yimayhd.resourcecenter.dto.ArticleProductItemDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.UserDTO;

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
		articleVO.setCachePv(articleDTO.getPv());
		articleVO.setArticleUrl(articleDTO.getArticleUrl());
		articleVO.setArticleItemList(articleItemVOList);
		return articleVO;
	}

	public static ArticleDTO getArticleDTO(ArticleVO articleVO) {
		ArticleDTO articleDTO = new ArticleDTO();
		ArticleDO articleDO = new ArticleDO();
		articleDO.setTitle(articleVO.getTitle());
		articleDO.setDomainId(Constant.DOMAIN_JIUXIU);
		articleDO.setFrontcover(articleVO.getFrontcover());
		articleDO.setPv(articleVO.getPv());
		articleDO.setSubTitle(articleVO.getSubTitle());
		articleDO.setType(articleVO.getType());
		if (articleVO.getStatus() != null && articleVO.getStatus() > 0) {
			articleDO.setStatus(articleVO.getStatus());
		}
		articleDO.setGmtCreated(new Date());
		if (articleVO.getId() != null) {
			articleDO.setId(articleVO.getId());
		}
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
				Long itemId = 0L;
				if (RegExpValidator.IsNumber(articleItemDO.getContent())) {
					itemId = Long.parseLong(articleItemDO.getContent());
				}
				ItemDO itemDO = itemDOMap.get(itemId);
				MerchantDO merchantDO = merchantDOMap.get(itemDO.getSellerId());
				ArticleProductItemVO articleProductItemVO = ItemDOToArticleProductItemVO(itemDO, merchantDO);
				articleItemVO.setArticleProductItemVO(articleProductItemVO);
			}
			articleItems.add(articleItemVO);
		}
		articleVO.setArticleItemList(articleItems);
		return articleVO;
	}

	public static ArticleProductItemVO ItemDOToArticleProductItemVO(ItemDO itemDO, MerchantDO merchantDO) {
		ArticleProductItemVO articleProductItemVO = new ArticleProductItemVO();
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
		return articleProductItemVO;
	}

	public static ArticleVO convertToArticleVO(ArticleVO articleVO) {
		String articleItems = articleVO.getArticleItems();
		JSONArray jsonarray = JSONArray.parseArray(articleItems);
		String json = jsonarray.toString();
		List<ArticleItemVO> list = JSONArray.parseArray(json, ArticleItemVO.class);
		articleVO.setArticleItemList(list);
		return articleVO;
	}

	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	public static ArticleExpertManItemVO getArticleExpertManItemVO(UserDTO userDTO) {
		ArticleExpertManItemVO articleExpertManItemVO = new ArticleExpertManItemVO();
		articleExpertManItemVO.setHeadPic(userDTO.getAvatar());
		articleExpertManItemVO.setNickName(userDTO.getNickname());
		articleExpertManItemVO.setSignatures(userDTO.getSignature());
		return articleExpertManItemVO;
	}

	public static ArticleVO convertToArticleVOByArticleDTO(ArticleDTO articleDTO) {
		if (articleDTO == null || articleDTO.getArticleDO() == null) {
			return null;
		}
		ArticleVO articleVO = new ArticleVO();
		ArticleDO articleDO = articleDTO.getArticleDO();
		BeanUtils.copyProperties(articleDO, articleVO);
		List<ArticleItemDTO> articleItemDTOs = articleDTO.getArticleItemDTOs();
		List<ArticleItemVO> articleItemVOs = new ArrayList<ArticleItemVO>();
		if (CollectionUtils.isEmpty(articleItemDTOs)) {
			return articleVO;
		}
		for (ArticleItemDTO articleItemDTO : articleItemDTOs) {
			ArticleItemVO articleItemVO = new ArticleItemVO();
			ArticleItemDO articleItemDO = articleItemDTO.getArticleItemDO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			switch (ArticleItemType.getByType(articleItemDO.getType())) {
			case PRODUCT:
				ArticleProductItemVO articleProductItemVO = getArticleItemDTOForProduct(articleItemDTO.getArticleProductItemDTO());
				articleItemVO.setArticleProductItemVO(articleProductItemVO);
				break;
			case EXPERTMAN:
				ArticleExpertManItemVO articleExpertManItemVO = getArticleItemDTOForExpertman(articleItemDTO.getArticleExpertManItemDTO());
				articleItemVO.setArticleExpertManItemVO(articleExpertManItemVO);
				break;
			case CONSULTSERVICE:
				ArticleConsultServiceItemVO articleConsultServiceItemVO = getArticleItemDTOForConsultService(articleItemDTO.getArticleConsultServiceItemDTO());
				articleItemVO.setArticleConsultServiceItemVO(articleConsultServiceItemVO);
				break;
			default:
				break;
			}
			if (articleItemVO != null) {
				articleItemVOs.add(articleItemVO);
			}
		}
		articleVO.setArticleItemList(articleItemVOs);
		return articleVO;
	}

	private static ArticleConsultServiceItemVO getArticleItemDTOForConsultService(ArticleConsultServiceItemDTO articleConsultServiceItemDTO) {
		if (articleConsultServiceItemDTO == null) {
			return null;
		}
		ArticleConsultServiceItemVO articleConsultServiceItemVO = new ArticleConsultServiceItemVO();
		articleConsultServiceItemVO.setConsultTime(articleConsultServiceItemDTO.getConsultTime());
		articleConsultServiceItemVO.setServiceCity(articleConsultServiceItemDTO.getServiceCity());
		articleConsultServiceItemVO.setServiceCurrentPrice(articleConsultServiceItemDTO.getServiceCurrentPrice());
		articleConsultServiceItemVO.setServiceHeadPic(articleConsultServiceItemDTO.getServiceHeadPic());
		articleConsultServiceItemVO.setServiceName(articleConsultServiceItemDTO.getServiceName());
		articleConsultServiceItemVO.setServiceOriginalPrice(articleConsultServiceItemDTO.getServiceOriginalPrice());
		// BeanUtils.copyProperties(articleConsultServiceItemDTO,
		// articleConsultServiceItemVO);
		return articleConsultServiceItemVO;
	}

	private static ArticleExpertManItemVO getArticleItemDTOForExpertman(ArticleExpertManItemDTO articleExpertManItemDTO) {
		if (articleExpertManItemDTO == null) {
			return null;
		}
		ArticleExpertManItemVO articleExpertManItemVO = new ArticleExpertManItemVO();
		articleExpertManItemVO.setHeadPic(articleExpertManItemDTO.getHeadPic());
		articleExpertManItemVO.setNickName(articleExpertManItemDTO.getNickName());
		articleExpertManItemVO.setSignatures(articleExpertManItemDTO.getSignatures());
		// BeanUtils.copyProperties(articleExpertManItemDTO,
		// articleExpertManItemVO);
		return articleExpertManItemVO;
	}

	private static ArticleProductItemVO getArticleItemDTOForProduct(ArticleProductItemDTO articleProductItemDTO) {
		if (articleProductItemDTO == null) {
			return null;
		}
		ArticleProductItemVO articleProductItemVO = new ArticleProductItemVO();
		articleProductItemVO.setItemPic(articleProductItemDTO.getItemPic());
		articleProductItemVO.setItemPrice(articleProductItemDTO.getItemPrice());
		articleProductItemVO.setItemTagList(articleProductItemDTO.getItemTag());
		articleProductItemVO.setItemTitle(articleProductItemDTO.getItemTitle());
		articleProductItemVO.setItemType(articleProductItemDTO.getItemType());
		articleProductItemVO.setMerchantLogo(articleProductItemDTO.getMerchantLogo());
		articleProductItemVO.setMerchantName(articleProductItemDTO.getMerchantName());
		// BeanUtils.copyProperties(articleProductItemDTO,
		// articleProductItemVO);
		return articleProductItemVO;
	}
}
