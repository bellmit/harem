package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.ArticleClientService;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public class ArticleRepo {
	private Logger log = LoggerFactory.getLogger(getClass());
	public static final int STATUS_DISABLE = 2;
	public static final int STATUS_ENABLE = 1;
	@Autowired
	private  ArticleClientService articleClientServiceRef;

	public RCPageResult<ArticleDO> pageQueryArticles(ArticleQueryDTO articleQueryDTO) {
		RepoUtils.requestLog(log, "articleClientServiceRef.pageQueryArticles", articleQueryDTO);
		RCPageResult<ArticleDO> result = articleClientServiceRef.queryArticleList(articleQueryDTO);
		RepoUtils.resultLog(log, "articleClientServiceRef.pageQueryArticles", result);
		return result;
	}
}
