package com.yimayhd.palace.convert;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.resourcecenter.domain.ArticleDO;
import com.yimayhd.resourcecenter.domain.ArticleItemDO;
import com.yimayhd.resourcecenter.dto.ArticleDTO;

/**
 * H5文章转换
 * 
 * @author xiemingna
 *
 */
public class ArticleConverter {
	public static ArticleVO getArticleVO(ArticleDTO articleDTO) {
		ArticleDO articleDO = articleDTO.getArticleDO();
		List<ArticleItemDO> articleItemDOs = articleDTO.getArticleItemDOs();
		ArticleVO articleVO = new ArticleVO();
		BeanUtils.copyProperties(articleDO, articleVO);
		List<ArticleItemVO> articleItemVOList = new ArrayList<ArticleItemVO>();
		for (ArticleItemDO articleItemDO : articleItemDOs) {
			ArticleItemVO articleItemVO = new ArticleItemVO();
			BeanUtils.copyProperties(articleItemDO, articleItemVO);
			articleItemVOList.add(articleItemVO);
		}
		articleVO.setArticleItems(articleItemVOList);
		return articleVO;
	}
}
