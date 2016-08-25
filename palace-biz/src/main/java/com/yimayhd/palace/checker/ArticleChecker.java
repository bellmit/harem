package com.yimayhd.palace.checker;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.resourcecenter.model.enums.ArticleType;

/**
 * 文章checker
 * 
 * @author xiemingna
 *
 */
public class ArticleChecker {
	private static final Logger log = LoggerFactory.getLogger(ArticleChecker.class);

	private static final int SUBTITLE_LENGTH=36;//
	private static final int TITLE_LENGTH=12;

	public static CheckResult checkArticleVO(ArticleVO articleVO) {
		if (StringUtils.isBlank(articleVO.getTitle()) || articleVO.getTitle().length() > TITLE_LENGTH) {
			return CheckResult.error("标题不能为空，长度最多12个字符");
		}
		if (StringUtils.isBlank(articleVO.getSubTitle()) || articleVO.getSubTitle().length() > SUBTITLE_LENGTH) {
			return CheckResult.error("副标题不能为空，长度最多12个字符");
		}
		if (StringUtils.isBlank(articleVO.getFrontcover())) {
			return CheckResult.error("封面不能为空");
		}
		if (articleVO.getPv() < 0) {
			return CheckResult.error("阅读数不能小于0");
		}
		if (ArticleType.getArticleType(articleVO.getType()) == null) {
			return CheckResult.error("不支持的文章类型");
		}
		if (articleVO.getArticleItems() == null || articleVO.getArticleItems().isEmpty()) {
			return CheckResult.error("图文不能为空");
		}
		return CheckResult.success();
	}

}
