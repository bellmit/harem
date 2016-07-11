package com.yimayhd.palace.convert;

import org.springframework.beans.BeanUtils;

import com.yimayhd.palace.model.ArticleVO;
import com.yimayhd.resourcecenter.domain.ArticleDO;

/**
 * H5文章转换
 * @author xiemingna
 *
 */
public class ArticleConverter {
    public static ArticleVO getArticleVO(ArticleDO articleDO){
    	ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(articleDO,articleVO);
        return articleVO;
    }
}
