package com.yimayhd.palace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;

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
	public PageVO<ArticleVO> getList(ArticleListQuery articleListQuery) {
		ArticleQueryDTO articleQueryDTO = null;
		RCPageResult<ArticleDO> result = articleRepo.pageQueryArticles(articleQueryDTO);
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
		List<ArticleDO> itemList = result.getList();
		List<ArticleVO> articleList = new ArrayList<ArticleVO>();
		if (CollectionUtils.isNotEmpty(result.getList())) {
			for (ArticleDO articleDO : itemList) {
				articleList.add(ArticleConverter.getArticleVO(articleDO));
			}
		}
		return new PageVO<ArticleVO>(articleListQuery.getPageNumber(), articleListQuery.getPageSize(),
				result.getTotalCount(), articleList);
	}

	@Override
	public ArticleVO getById(long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArticleVO add(ArticleVO articleVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modify(ArticleVO articleVO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void regain(long id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void violation(long id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void batchViolation(List<Long> idList) {
		// TODO Auto-generated method stub

	}

}
