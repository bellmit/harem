package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.yimayhd.palace.model.*;
import com.yimayhd.resourcecenter.dto.*;
import com.yimayhd.solrsearch.client.domain.SolrScenicDO;

import com.yimayhd.user.client.enums.ServiceFacilityOption;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.alibaba.druid.support.logging.Log;
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
import com.yimayhd.palace.model.ArticleHotelResourceItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.vo.AudioVO;
import com.yimayhd.palace.util.RegExpValidator;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.domain.MediaDO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.resourcecenter.model.enums.MediaFileScope;
import com.yimayhd.resourcecenter.model.enums.MediaFileType;
import com.yimayhd.solrsearch.client.domain.SolrHotelDO;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.UserDTO;

/**
 * H5文章转换
 *
 * @author xiemingna
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
        
        articleDO.setAuthor(articleVO.getAuthor());
        
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
     * @param userDTO
     * @return
     */
    public static ArticleExpertManItemVO getArticleExpertManItemVO(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
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
        List<Long> hotelIdList = new ArrayList<Long>();
        for (ArticleItemDTO articleItemDTO : articleItemDTOs) {
            ArticleItemVO articleItemVO = new ArticleItemVO();
            ArticleItemDO articleItemDO = articleItemDTO.getArticleItemDO();
            BeanUtils.copyProperties(articleItemDO, articleItemVO);
            ArticleItemType byType = ArticleItemType.getByType(articleItemDO.getType());
            if (byType==null){
                continue;
            }
            switch (byType) {
                case PRODUCT:
                    ArticleProductItemVO articleProductItemVO = getArticleProductItemVO(articleItemDTO.getArticleProductItemDTO());
                    articleItemVO.setArticleProductItemVO(articleProductItemVO);
                    break;
                case EXPERTMAN:
                    ArticleExpertManItemVO articleExpertManItemVO = getArticleExpertManItemVO(articleItemDTO.getArticleExpertManItemDTO());
                    articleItemVO.setArticleExpertManItemVO(articleExpertManItemVO);
                    break;
                case CONSULTSERVICE:
                    ArticleConsultServiceItemVO articleConsultServiceItemVO = getArticleConsultServiceItemVO(articleItemDTO.getArticleConsultServiceItemDTO());
                    articleItemVO.setArticleConsultServiceItemVO(articleConsultServiceItemVO);
                    break;
                case HOTELRESOURCE:
                	if (StringUtils.isNotBlank(articleItemDTO.getArticleItemDO().getContent())) {
						
                		hotelIdList.add(Long.parseLong(articleItemDTO.getArticleItemDO().getContent()));
					}
                    ArticleHotelResourceItemVO articleHotelResourceItemVO = getArticleHotelResourceItemVO(articleItemDTO.getArticleHotelResourceItemDTO());
                    articleItemVO.setArticleHotelResourceItemVO(articleHotelResourceItemVO);
                    break;
                case AUDIO:
                    ArticleAudioItemVO articleAudioItemVO = getArticleAudioItemVO(articleItemDTO.getArticleAudioItemDTO());
                    articleItemVO.setArticleAudioItemVO(articleAudioItemVO);
                    break;
                case FOOD:
                    ArticleFoodItemVO articleFoodItemVO = getArticleFoodItemVO(articleItemDTO.getArticleFoodItemDTO());
                    articleItemVO.setArticleFoodItemVO(articleFoodItemVO);
                    break;
                default:
                    break;
            }
            articleItemVOs.add(articleItemVO);
        }
       // hotelIdList.add(256l);
        articleVO.setIdList(hotelIdList);
        articleVO.setArticleItemList(articleItemVOs);
        return articleVO;
    }
    private static ArticleFoodItemVO getArticleFoodItemVO(ArticleFoodItemDTO articleFoodItemDTO) {
        if (articleFoodItemDTO==null){
            return null;
        }
        ArticleFoodItemVO articleAudioItemVO=new ArticleFoodItemVO();
        articleAudioItemVO.setAvgPrice( articleFoodItemDTO.getAvgPrice());
        articleAudioItemVO.setId(articleFoodItemDTO.getId());
        articleAudioItemVO.setImage(articleFoodItemDTO.getImage());
        articleAudioItemVO.setName(articleFoodItemDTO.getName());
        articleAudioItemVO.setService(articleFoodItemDTO.getService());
        articleAudioItemVO.setTop(articleFoodItemDTO.getTop());
        articleAudioItemVO.setItemTitle(articleFoodItemDTO.getItemTitle());
        articleAudioItemVO.setCityName(articleFoodItemDTO.getCityName());

        return articleAudioItemVO;
    }
    private static ArticleAudioItemVO getArticleAudioItemVO(ArticleAudioItemDTO articleAudioItemDTO) {
        if (articleAudioItemDTO==null){
            return null;
        }
        ArticleAudioItemVO articleAudioItemVO=new ArticleAudioItemVO();
        articleAudioItemVO.setAudioName(articleAudioItemDTO.getAudioName());
        articleAudioItemVO.setAudioPic(articleAudioItemDTO.getAudioPic());
        articleAudioItemVO.setAudioTime(articleAudioItemDTO.getAudioTime());
        articleAudioItemVO.setAudioUrl(articleAudioItemDTO.getAudioUrl());
        return articleAudioItemVO;
    }

    private static ArticleHotelResourceItemVO getArticleHotelResourceItemVO(ArticleHotelResourceItemDTO articleHotelResourceItemDTO) {
        if (articleHotelResourceItemDTO == null) {
            return null;
        }
        ArticleHotelResourceItemVO articleHotelResourceItemVO = new ArticleHotelResourceItemVO();
        articleHotelResourceItemVO.setResourcePic(articleHotelResourceItemDTO.getResourcePic());
        articleHotelResourceItemVO.setResourceName(articleHotelResourceItemDTO.getResourceName());
        articleHotelResourceItemVO.setResourcePrice(articleHotelResourceItemDTO.getResourcePrice());
        articleHotelResourceItemVO.setTradeArea(articleHotelResourceItemDTO.getTradeArea());
        return articleHotelResourceItemVO;
    }

    private static ArticleConsultServiceItemVO getArticleConsultServiceItemVO(ArticleConsultServiceItemDTO articleConsultServiceItemDTO) {
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

    private static ArticleExpertManItemVO getArticleExpertManItemVO(ArticleExpertManItemDTO articleExpertManItemDTO) {
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

    private static ArticleProductItemVO getArticleProductItemVO(ArticleProductItemDTO articleProductItemDTO) {
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
        //
        articleProductItemVO.setOldPrice(articleProductItemDTO.getOldPrice());
        // BeanUtils.copyProperties(articleProductItemDTO,
        // articleProductItemVO);
        return articleProductItemVO;
    }

    public static ArticleHotelResourceItemVO getArticleHotelResourceItemVO(SolrHotelDO hotelDO) {
        if (hotelDO == null) {
            return null;
        }
        ArticleHotelResourceItemVO articleHotelResourceItemVO = new ArticleHotelResourceItemVO();
        articleHotelResourceItemVO.setResourceName(hotelDO.getHotelName());
        articleHotelResourceItemVO.setResourcePic(hotelDO.getIcon());
        articleHotelResourceItemVO.setResourcePrice(hotelDO.getPrice());
        articleHotelResourceItemVO.setTradeArea(hotelDO.getTradeArea());
        articleHotelResourceItemVO.setHotelType(hotelDO.getHotelType());
        return articleHotelResourceItemVO;
    }

    public static ArticleScenicResourceItemVO getArticleScenicResourceItemVO(SolrScenicDO solrScenicDO) {
        if (solrScenicDO == null) {
            return null;
        }
        ArticleScenicResourceItemVO articleScenicResourceItemVO = new ArticleScenicResourceItemVO();
        articleScenicResourceItemVO.setTradeArea(solrScenicDO.getCityName());
        articleScenicResourceItemVO.setResourcePrice(solrScenicDO.getPrice());
        articleScenicResourceItemVO.setResourceName(solrScenicDO.getScenicName());
        articleScenicResourceItemVO.setResourcePic(solrScenicDO.getIcon());
        return articleScenicResourceItemVO;
    }
    
    public static List<AudioVO> convertMedia2Audio(List<MediaDO> mediaList) {
    	List<AudioVO> audios = new ArrayList<AudioVO>();
    	for (MediaDO media : mediaList) {
			
    		AudioVO audio = new AudioVO();
    		audio.setInputFileName(media.getInputFileName());
    		if (media.getFileType() == MediaFileType.MP3.getValue()) {
    			audio.setFileTypeName(MediaFileType.MP3.getDesc());
    		}
    		audio.setGmtCreated(media.getGmtCreated());
    		if (media.getScope() == MediaFileScope.DEFAULT.getValue()) {
    			audio.setScopeName(MediaFileScope.DEFAULT.getDesc());
    		}else if (media.getScope() == MediaFileScope.GUIDE.getValue()) {
    			audio.setScopeName(MediaFileScope.GUIDE.getDesc());
    		}else if (media.getScope() == MediaFileScope.H5.getValue()) {
    			audio.setScopeName(MediaFileScope.H5.getDesc());
    		}
    		audio.setDuration(media.getDuration());
    		audio.setRemark(media.getRemark());
    		audios.add(audio);
		}
    	return audios;
    }

    public static ArticleFoodItemVO merchantDO2ArticleFoodItemVO(MerchantDO merchantDO){
        if(merchantDO==null){
            return null;
        }
        ArticleFoodItemVO articleFoodItemVO = new ArticleFoodItemVO();
        articleFoodItemVO.setId(merchantDO.getId());
        articleFoodItemVO.setName(merchantDO.getName());
        articleFoodItemVO.setImage(merchantDO.getLogo());
        articleFoodItemVO.setAvgPrice(merchantDO.getAvgprice());
        articleFoodItemVO.setSellerId(merchantDO.getSellerId());
        articleFoodItemVO.setCityName(merchantDO.getCityName());
//        ServiceFacilityOption
        long serviceFacility = merchantDO.getServiceFacility();
        List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
        StringBuilder sb = new StringBuilder();
        for (ServiceFacilityOption containedOption : containedOptions) {
            if(containedOption!=null){
                sb.append(containedOption.getDesc()).append("  ");
            }
        }
        articleFoodItemVO.setService(sb.toString());
        return articleFoodItemVO;
    }

}
