package com.yimayhd.palace.util;

import com.yimayhd.resourcecenter.model.enums.ArticleStauts;
import com.yimayhd.resourcecenter.model.enums.ArticleType;

/**
 * 工具类
 * 
 * @author xiemingna
 *
 */
public class ArticleUtil {
	public static String getArticleStatus(int status) {
		if (status <= 0) {
			return null;
		}
		ArticleStauts articleStauts = ArticleStauts.getByStatus(status);
		if (articleStauts != null) {
			return articleStauts.getDesc();
		}
		return "未知状态";
	}
	public static String getOption(int status) {
		if (status <= 0) {
			return null;
		}
		if (status==ArticleStauts.OFFLINE.getValue()) {
			return ArticleStauts.ONLINE.getDesc();
		}
		if (status==ArticleStauts.ONLINE.getValue()) {
			return ArticleStauts.OFFLINE.getDesc();
		}
		return "未知状态";
	}
	public static String getArticleTypeName(int type) {
		if (type <= 0) {
			return null;
		}
		ArticleType articleType = ArticleType.getTypeName(type);
		if (articleType != null) {
			return articleType.getDesc();
		}
		return "未知类型";
	}
	
}
