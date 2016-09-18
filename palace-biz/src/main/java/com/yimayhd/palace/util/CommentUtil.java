package com.yimayhd.palace.util;

import java.util.ArrayList;

import com.yimayhd.commentcenter.client.enums.BaseStatus;
import com.yimayhd.commentcenter.client.enums.CommentType;
import com.yimayhd.palace.enums.BizCommentStatus;
import com.yimayhd.resourcecenter.model.enums.ArticleType;

/**
 * 工具类
 * 
 * @author xiemingna
 *
 */
public class CommentUtil {
	public static String getCommentType(int type) {
		if (type <= 0) {
			return null;
		}
		CommentType commentType = CommentType.getByType(type);
		if (commentType != null) {
			return commentType.getDesc();
		}
		return "未知类型";
	}

	public static String getCommentStatus(int status) {
		if (status <= 0) {
			return null;
		}
		BizCommentStatus commentStatus = BizCommentStatus.get(status);
		if (commentStatus != null) {
			return commentStatus.getText();
		}
		return "未知状态";
	}

	public static ArrayList<CommentType> getCommentTypes() {
		ArrayList<CommentType> arrayList = new ArrayList<CommentType>();
		CommentType[] commentTypes = CommentType.values();
		for (CommentType commentType : commentTypes) {
			if (commentType.getType() != CommentType.DYNAMICCOM.getType()) {
				arrayList.add(commentType);
			}
		}
		return arrayList;
	}

	public static String getOption(int type) {
		if (type <= 0) {
			return null;
		}
		if (type == BaseStatus.AVAILABLE.getType()) {
			return BaseStatus.DELETED.name();
		}
		if (type == BaseStatus.DELETED.getType()) {
			return BaseStatus.AVAILABLE.name();
		}
		return "未知状态";
	}

	public static String getCommentTypeName(int type) {
		if (type <= 0) {
			return null;
		}
		ArticleType articleType = ArticleType.getArticleType(type);
		if (articleType != null) {
			return articleType.getDesc();
		}
		return "未知类型";
	}

}
