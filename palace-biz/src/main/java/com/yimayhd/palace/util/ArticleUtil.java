package com.yimayhd.palace.util;

import com.yimayhd.resourcecenter.model.enums.ArticleStatus;
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
		ArticleStatus articleStauts = ArticleStatus.getByStatus(status);
		if (articleStauts != null) {
			return articleStauts.getDesc();
		}
		return "未知状态";
	}
	public static String getOption(int status) {
		if (status <= 0) {
			return null;
		}
		if (status==ArticleStatus.OFFLINE.getValue()) {
			return ArticleStatus.ONLINE.getDesc();
		}
		if (status==ArticleStatus.ONLINE.getValue()) {
			return ArticleStatus.OFFLINE.getDesc();
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
