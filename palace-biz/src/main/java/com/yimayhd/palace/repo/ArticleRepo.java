package com.yimayhd.palace.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.model.result.item.SingleItemQueryResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.ResourcePageResult;
import com.yimayhd.resourcecenter.model.result.ResourceResult;
import com.yimayhd.resourcecenter.service.backend.ArticleBackEndService;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;

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
	private ArticleBackEndService articleBackEndServiceRef;
	@Autowired
	private MerchantService merchantService;

	public ResourcePageResult<ArticleDTO> pageQueryArticles(ArticleQueryDTO articleQueryDTO) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.pageQueryArticles", articleQueryDTO);
		ResourcePageResult<ArticleDTO> result = articleBackEndServiceRef.queryArticleList(articleQueryDTO);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.pageQueryArticles", result);
		return result;
	}

	public ResourceResult<Boolean> add(ArticleDTO articleDTO) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.add", articleDTO);
		ResourceResult<Boolean> result = articleBackEndServiceRef.insertOrUpdate(articleDTO);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.add", result);
		return result;
	}

	public ResourceResult<ArticleDTO> getArticleById(long id) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.getArticleById", id);
		ResourceResult<ArticleDTO> result = articleBackEndServiceRef.getArticleById(id);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.add", result);
		return result;
	}

	public ResourceResult<Boolean> updateByStatus(long id) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.updateByStatus", id);
		ResourceResult<Boolean> result = articleBackEndServiceRef.updateStatusById(id);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.updateByStatus", result);
		return result;
	}

	public ResourceResult<Boolean> updateStatusByIdList(List<Long> idList, int status) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.updateListByStatus", idList);
		ResourceResult<Boolean> result = articleBackEndServiceRef.updateStatusByIdList(idList, status);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.updateListByStatus", result);
		return result;
	}

	public ResourceResult<Boolean> modify(ArticleDTO articleDTO) {
		RepoUtils.requestLog(log, "articleBackEndServiceRef.modify", articleDTO);
		ResourceResult<Boolean> result = articleBackEndServiceRef.insertOrUpdate(articleDTO);
		RepoUtils.resultLog(log, "articleBackEndServiceRef.modify", result);
		return result;
	}

}
