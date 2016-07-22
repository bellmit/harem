package com.yimayhd.palace.checker;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleVO;

/**
 * 文章checker
 * 
 * @author xiemingna
 *
 */
public class ArticleChecker {
	private static final Logger log = LoggerFactory.getLogger(ArticleChecker.class);

	public static ArticleVO convertToArticleVO(ArticleVO articleVO) {
		String articleItems = articleVO.getArticleItems();
		JSONArray jsonarray = JSONArray.parseArray(articleItems);
		String json = jsonarray.toString();
		List<ArticleItemVO> list = JSONArray.parseArray(json, ArticleItemVO.class);
		articleVO.setArticleItemList(list);
		return articleVO;
	}

}
