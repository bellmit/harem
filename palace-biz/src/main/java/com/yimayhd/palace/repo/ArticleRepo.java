package com.yimayhd.palace.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yimayhd.ic.client.model.param.item.ItemOptionDTO;
import com.yimayhd.ic.client.model.result.item.ItemResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.util.RepoUtils;
import com.yimayhd.resourcecenter.dto.ArticleDTO;
import com.yimayhd.resourcecenter.model.query.ArticleQueryDTO;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ResourceResult;
import com.yimayhd.resourcecenter.service.ArticleClientService;
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
	private ArticleClientService articleClientServiceRef;
	@Autowired
	private ItemQueryService itemQueryServiceRef;
	@Autowired
	private MerchantService merchantService;

	public RCPageResult<ArticleDTO> pageQueryArticles(ArticleQueryDTO articleQueryDTO) {
		RepoUtils.requestLog(log, "articleClientServiceRef.pageQueryArticles", articleQueryDTO);
		RCPageResult<ArticleDTO> result = articleClientServiceRef.queryArticleList(articleQueryDTO);
		RepoUtils.resultLog(log, "articleClientServiceRef.pageQueryArticles", result);
		return result;
	}

	public ResourceResult<Boolean> add(ArticleDTO articleDTO) {
		RepoUtils.requestLog(log, "articleClientServiceRef.add", articleDTO);
		ResourceResult<Boolean> result = articleClientServiceRef.insertOrUpdate(articleDTO);
		RepoUtils.resultLog(log, "articleClientServiceRef.add", result);
		return result;
	}

	public ResourceResult<ArticleDTO> getArticleById(long id) {
		RepoUtils.requestLog(log, "articleClientServiceRef.getArticleById", id);
		ResourceResult<ArticleDTO> result = articleClientServiceRef.getArticleById(id);
		RepoUtils.resultLog(log, "articleClientServiceRef.add", result);
		return result;
	}

	public ResourceResult<Boolean> updateByStatus(long id) {
		RepoUtils.requestLog(log, "articleClientServiceRef.updateByStatus", id);
		ResourceResult<Boolean> result = articleClientServiceRef.updateStatusById(id);
		RepoUtils.resultLog(log, "articleClientServiceRef.updateByStatus", result);
		return result;
	}

	public ResourceResult<Boolean> updateStatusByIdList(List<Long> idList, int status) {
		RepoUtils.requestLog(log, "articleClientServiceRef.updateListByStatus", idList);
		ResourceResult<Boolean> result = articleClientServiceRef.updateStatusByIdList(idList, status);
		RepoUtils.resultLog(log, "articleClientServiceRef.updateListByStatus", result);
		return result;
	}

	public ResourceResult<Boolean> modify(ArticleDTO articleDTO) {
		RepoUtils.requestLog(log, "articleClientServiceRef.modify", articleDTO);
		ResourceResult<Boolean> result = articleClientServiceRef.insertOrUpdate(articleDTO);
		RepoUtils.resultLog(log, "articleClientServiceRef.modify", result);
		return result;
	}

	public ItemResult getItemById(long id) {
		RepoUtils.requestLog(log, "itemQueryServiceRef.getItemById", id);
		ItemOptionDTO itemOptionDTO = new ItemOptionDTO();
		ItemResult result = itemQueryServiceRef.getItem(id, itemOptionDTO);
		RepoUtils.resultLog(log, "itemQueryServiceRef.getItemById", result);
		return result;
	}

	public BaseResult<MerchantUserDTO> getMerchantBySellerId(long sellerId) {
		RepoUtils.requestLog(log, "merchantService.getMerchantBySellerId", sellerId);
		BaseResult<MerchantUserDTO> result = merchantService.getMerchantAndUserBySellerId(sellerId,
				Constant.DOMAIN_JIUXIU);
		RepoUtils.requestLog(log, "merchantService.getMerchantBySellerId", result);
		return result;
	}
}
