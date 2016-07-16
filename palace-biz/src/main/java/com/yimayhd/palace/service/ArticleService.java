package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.palace.model.query.ArticleListQuery;
import com.yimayhd.resourcecenter.model.result.RcResult;

/**
 * H5文章
 * 
 * @author xiemingna
 *
 */
public interface ArticleService {
	/**
	 * 获取H5列表(可带查询条件)
	 * 
	 * @param liveListQuery
	 *            查询条件
	 * @return H5列表
	 * @throws Exception
	 */
	PageVO<ArticleVO> getList(ArticleListQuery articleListQuery) throws Exception;

	/**
	 * 获取H5详情
	 * 
	 * @param id
	 *            H5ID
	 * @return H5详情
	 */
	ArticleVO getArticleById(long id) throws Exception;

	/**
	 * 新增H5
	 * 
	 * @param ArticleVO
	 *            H5内容
	 * @return H5对象
	 * @throws Exception
	 */
	RcResult<Boolean> add(ArticleVO articleVO) throws Exception;

	/**
	 * 修改H5
	 * 
	 * @param ArticleVO
	 *            H5内容
	 * @throws Exception
	 */
	RcResult<Boolean> modify(ArticleVO articleVO) throws Exception;

	/**
	 * H5恢复
	 * 
	 * @param id
	 *            H5ID
	 */
	RcResult<Boolean> regain(long id) throws Exception;

	/**
	 * H5违规
	 * 
	 * @param id
	 *            H5ID
	 */
	RcResult<Boolean> violation(long id) throws Exception;

	/**
	 * H5违规（批量）
	 * 
	 * @param idList
	 * @param status
	 * @return date:2016年7月15日 author:xmn
	 */
	RcResult<Boolean> batchViolation(List<Long> idList, int status);

	ArticleItemVO getArticleItemDetailById(long id, int type);

}
