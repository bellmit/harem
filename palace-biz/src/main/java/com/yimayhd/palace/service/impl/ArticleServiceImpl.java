package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.ArticleBiz;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleProductItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.repo.ArticleRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ResourcePageResult;
import com.yimayhd.resourcecenter.model.result.ResourceResult;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.result.BaseResult;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public class ArticleServiceImpl implements ArticleService {

	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private ArticleBiz articleBiz;

	@Override
	public PageVO<ArticleVO> getList(ArticleListQuery articleListQuery) throws Exception {
		// 查询条件对接
		ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
		articleQueryDTO.setPageNo(articleListQuery.getPageNumber());
		articleQueryDTO.setPageSize(articleListQuery.getPageSize());
		// 状态
		articleQueryDTO.setStatus(articleListQuery.getStatus());
		articleQueryDTO.setType(articleListQuery.getType());
		articleQueryDTO.setTitle(articleListQuery.getTitle());
		// 开始结束时间
		if (StringUtils.isNotBlank(articleListQuery.getStartTime())) {
			articleQueryDTO.setStartTime(DateUtil.formatMinTimeForDate(articleListQuery.getStartTime()));
		}
		if (StringUtils.isNotBlank(articleListQuery.getEndTime())) {
			articleQueryDTO.setEndTime(DateUtil.formatMaxTimeForDate(articleListQuery.getEndTime()));
		}
		ResourcePageResult<ArticleDTO> result = articleRepo.pageQueryArticles(articleQueryDTO);
		if (null == result) {
			log.error("articleClientServiceRef.pageQueryArticles result is null and parame: "
					+ JSON.toJSONString(articleQueryDTO));
			throw new BaseException("返回结果错误,新增失败 ");
		} else if (!result.isSuccess()) {
			log.error("articleClientServiceRef.pageQueryArticles error:" + JSON.toJSONString(result) + "and parame: "
					+ JSON.toJSONString(articleQueryDTO));
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
		return new PageVO<ArticleVO>(articleListQuery.getPageNumber(), articleListQuery.getPageSize(), totalCount,
				articleList);
	}

	@Override
	public ArticleVO getArticleById(long id) throws Exception {
		ResourceResult<ArticleDTO> ResourceResult = articleRepo.getArticleById(id);
		if (ResourceResult == null || !ResourceResult.isSuccess() || ResourceResult.getT() == null) {
			return null;
		}
		ArticleDTO articleDTO = ResourceResult.getT();
		ArticleVO articleVO = articleBiz.getArticle(articleDTO);
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
		ResourceResult<Boolean> result = articleRepo.updateByStatus(id);
		return result;
	}

	@Override
	public ResourceResult<Boolean> violation(long id) throws Exception {
		ResourceResult<Boolean> result = articleRepo.updateByStatus(id);
		return result;
	}

	@Override
	public ResourceResult<Boolean> batchViolation(List<Long> idList, int status) {
		ResourceResult<Boolean> result = new ResourceResult<Boolean>();
		if (CollectionUtils.isEmpty(idList)) {
			return result;
		}
		result = articleRepo.updateStatusByIdList(idList, status);
		return result;
	}

	@Override
	public ArticleItemVO getArticleItemDetailById(long id, int type) {
		ArticleItemVO articleItemVO = new ArticleItemVO();
		if (type == ArticleItemType.PRODUCT.getValue()) {
			ArticleProductItemVO articleProductItemVO = new ArticleProductItemVO();
			ItemResult itemResult = articleRepo.getItemById(id);
			if (itemResult == null || !itemResult.isSuccess() || itemResult.getItem() == null) {
				return null;
			}
			ItemDO itemDO = itemResult.getItem();
			long sellerId = itemDO.getSellerId();
			BaseResult<MerchantUserDTO> result = articleRepo.getMerchantBySellerId(sellerId);
			if (result == null || result.getValue() == null) {
				return null;
			}
			MerchantUserDTO merchantUserDTO = result.getValue();
			ArticleConverter.ItemDOToArticleProductItemVO(articleItemVO, articleProductItemVO, itemDO,
					merchantUserDTO.getMerchantDO());
		}
		return articleItemVO;
	}
}
