package com.yimayhd.harem.service;

import com.yimayhd.commentcenter.client.domain.ComCommentDO;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.ComCommentVO;
import com.yimayhd.harem.model.query.EvaluationListQuery;

/**
 * Created by czf on 2015/12/31.
 */
public interface EvaluationService {

    PageVO<ComCommentVO> getList(EvaluationListQuery evaluationListQuery)throws Exception;

}
