package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.ArticleConverter;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.palace.repo.ArticleRepo;
import com.yimayhd.palace.service.ArticleService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.RcResult;

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

	@Override
	public PageVO<ArticleVO> getList(ArticleListQuery articleListQuery) throws Exception {
		// 查询条件对接
		ArticleQueryDTO articleQueryDTO = new ArticleQueryDTO();
		articleQueryDTO.setPageNo(articleListQuery.getPageSize());
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
		RCPageResult<ArticleDTO> result = articleRepo.pageQueryArticles(articleQueryDTO);
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
		return new PageVO<ArticleVO>(articleListQuery.getPageNumber(), articleListQuery.getPageSize(),totalCount, articleList);
	}

	@Override
	public ArticleVO getById(long id) throws Exception {
		return null;
	}

	@Override
	public RcResult<Boolean> add(ArticleVO articleVO) throws Exception {
		ArticleDTO articleDTO = new ArticleDTO();
		ArticleDO articleDO=new ArticleDO();
		articleDTO.setArticleDO(articleDO);
		List<ArticleItemDO> articleItemDOs=new ArrayList<ArticleItemDO>();
		articleDTO.setArticleItemDOs(articleItemDOs);
		RcResult<Boolean> result=articleRepo.add(articleDTO);
		return result;
	}

	@Override
	public void modify(ArticleVO articleVO) throws Exception {

	}

	@Override
	public void regain(long id) throws Exception {

	}

	@Override
	public void violation(long id) throws Exception {

	}

	@Override
	public void batchViolation(List<Long> idList) {

	}

}
