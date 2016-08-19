package com.yimayhd.palace.service.impl;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.ArticleBiz;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.*;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.model.ArticleScenicResourceItemVO;
import com.yimayhd.palace.repo.ArticleRepo;
import com.yimayhd.palace.repo.ItemRepo;
import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.resourcecenter.model.enums.ArticleStatus;
import com.yimayhd.resourcecenter.model.enums.ArticleType;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.ResourcePageResult;
import com.yimayhd.resourcecenter.model.result.ResourceResult;
import com.yimayhd.solrsearch.client.domain.SolrHotelDO;
import com.yimayhd.solrsearch.client.domain.SolrScenicDO;
import com.yimayhd.user.client.dto.UserDTO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ArticleServiceImpl implements ArticleService {

    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    private ArticleRepo articleRepo;
    @Autowired
    private ArticleBiz articleBiz;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private MerchantRepo merchantRepo;

    @Override
    public PageVO<ArticleVO> getList(ArticleListQuery articleListQuery) throws Exception {
        // 查询条件对接
        ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
        articleQueryDTO.setPageNo(articleListQuery.getPageNumber());
        articleQueryDTO.setPageSize(articleListQuery.getPageSize());
        // 状态
        articleQueryDTO.setStatus(ArticleStatus.getByStatus(articleListQuery.getStatus()));
        articleQueryDTO.setType(ArticleType.getArticleType(articleListQuery.getType()));

        articleQueryDTO.setTitle(articleListQuery.getTitle());
        // 开始结束时间
        if (StringUtils.isNotBlank(articleListQuery.getStartTime())) {
            articleQueryDTO.setStartTime(DateUtil.formatMinTimeForDate(articleListQuery.getStartTime()));
        }
        if (StringUtils.isNotBlank(articleListQuery.getEndTime())) {
            articleQueryDTO.setEndTime(DateUtil.formatMaxTimeForDate(articleListQuery.getEndTime()));
        }
        articleQueryDTO.setNeedCount(true);
        ResourcePageResult<ArticleDTO> result = articleRepo.pageQueryArticles(articleQueryDTO);
        if (null == result) {
            log.error("articleClientServiceRef.pageQueryArticles result is null and parame: " + JSON.toJSONString(articleQueryDTO));
            throw new BaseException("返回结果错误,新增失败 ");
        } else if (!result.isSuccess()) {
            log.error("articleClientServiceRef.pageQueryArticles error:" + JSON.toJSONString(result) + "and parame: " + JSON.toJSONString(articleQueryDTO));
            throw new BaseException(result.getResultMsg());
        }
        int totalCount = result.getTotalCount();
        List<ArticleDTO> itemList = result.getList();
        List<ArticleVO> articleList = new ArrayList<ArticleVO>();
        if (CollectionUtils.isNotEmpty(result.getList())) {
            for (ArticleDTO articleDTO : itemList) {
                articleList.add(ArticleConverter.getArticleVO(articleDTO));
            }
        }
        return new PageVO<ArticleVO>(articleListQuery.getPageNumber(), articleListQuery.getPageSize(), totalCount, articleList);
    }

    @Override
    public ArticleVO getArticleById(long id) throws Exception {
        ResourceResult<ArticleDTO> ResourceResult = articleRepo.getArticleById(id);
        if (ResourceResult == null || !ResourceResult.isSuccess() || ResourceResult.getT() == null) {
            throw new BaseException(ResourceResult.getResultMsg());
        }
        ArticleDTO articleDTO = ResourceResult.getT();
        ArticleVO articleVO = ArticleConverter.convertToArticleVOByArticleDTO(articleDTO);
        return articleVO;
    }

    @Override
    public ResourceResult<Boolean> add(ArticleVO articleVO) throws Exception {
        ArticleDTO articleDTO = ArticleConverter.getArticleDTO(articleVO);
        ResourceResult<Boolean> result = articleRepo.add(articleDTO);
        return result;
    }

    @Override
    public ResourceResult<Boolean> modify(ArticleVO articleVO) throws Exception {
        ArticleDTO articleDTO = ArticleConverter.getArticleDTO(articleVO);
        ResourceResult<Boolean> result = articleRepo.modify(articleDTO);
        return result;
    }

    @Override
    public ResourceResult<Boolean> regain(long id) throws Exception {
        ResourceResult<Boolean> result = articleRepo.updateByStatus(id, ArticleStatus.ONLINE);
        return result;
    }

    @Override
    public ResourceResult<Boolean> violation(long id) throws Exception {
        ResourceResult<Boolean> result = articleRepo.updateByStatus(id, ArticleStatus.OFFLINE);
        return result;
    }

    @Override
    public ResourceResult<Boolean> batchViolation(List<Long> idList, ArticleStatus status) {
        ResourceResult<Boolean> result = new ResourceResult<Boolean>();
        if (CollectionUtils.isEmpty(idList)) {
            return result;
        }
        result = articleRepo.updateStatusByIdList(idList, ArticleStatus.OFFLINE);
        return result;
    }

    @Override
    public ArticleItemVO getArticleItemDetailById(long id, int type) {
        ArticleItemVO articleItemVO = new ArticleItemVO();
        switch (ArticleItemType.getByType(type)) {
            case PRODUCT:
                ItemDO itemDO = articleBiz.getItemById(id);

                ArticleProductItemVO articleProductItemVO = articleBiz.getArticleProductItemVO(itemDO);
                if (articleProductItemVO == null) {
                    return null;
                } else {
                    articleItemVO.setSubType(itemDO.getItemType());
                    articleItemVO.setArticleProductItemVO(articleProductItemVO);
                }
                break;
            case EXPERTMAN:
                UserDTO userDTO = articleBiz.queryTalentInfo(id);
                ArticleExpertManItemVO articleExpertManItemVO = ArticleConverter.getArticleExpertManItemVO(userDTO);
                if (articleExpertManItemVO == null) {
                    return null;
                } else {
                    articleItemVO.setArticleExpertManItemVO(articleExpertManItemVO);
                    articleItemVO.setSubType((int) userDTO.getOptions());
                }
                break;
            case CONSULTSERVICE:
                itemDO = articleBiz.getItemById(id);
                ArticleConsultServiceItemVO articleConsultServiceItemVO = articleBiz.getArticleConsultServiceItemVO(itemDO);
                if (articleConsultServiceItemVO == null) {
                    return null;
                } else {
                    articleItemVO.setSubType(itemDO.getItemType());
                    articleItemVO.setArticleConsultServiceItemVO(articleConsultServiceItemVO);
                }
                break;
            case HOTELRESOURCE:
                SolrHotelDO hotelDO = articleBiz.getSolrHotelDOById(id);
                ArticleHotelResourceItemVO articleHotelResourceItemVO = ArticleConverter.getArticleHotelResourceItemVO(hotelDO);
                if (articleHotelResourceItemVO == null) {
                    return null;
                } else {
                    articleItemVO.setArticleHotelResourceItemVO(articleHotelResourceItemVO);
                }
                break;
            case SCENICRESOURCE:
                SolrScenicDO solrScenicDO = articleBiz.getSolrScenicDOById(id);
                ArticleScenicResourceItemVO articleScenicResourceItemVO = ArticleConverter.getArticleScenicResourceItemVO(solrScenicDO);
                if (articleScenicResourceItemVO == null) {
                    return null;
                } else {
                    articleItemVO.setArticleScenicResourceItemVO(articleScenicResourceItemVO);
                }
                break;
            case AUDIO:
                //TODO 音频文件封装
//                ArticleAudioItemVO articleAudioItemVO = ArticleConverter.getArticleAudioItemVO();
//                if (articleAudioItemVO == null) {
//                    return null;
//                } else {
//                    articleItemVO.setArticleAudioItemVO(articleAudioItemVO);
//                }
                break;
            default:
                break;
        }
        articleItemVO.setType(type);
        articleItemVO.setId(id);
        return articleItemVO;
    }

}
