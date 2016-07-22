package com.yimayhd.palace.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.dto.RatePageListDTO;
import com.yimayhd.commentcenter.client.result.BaseResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.ComRateVO;
import com.yimayhd.palace.model.query.ComRateListQuery;

import java.util.List;

/**
 * Created by p on 7/18/16.
 */
public interface ComRateService {
    public PageVO<ComRateVO> getRatePageList(ComRateListQuery comRateListQuery);
    public BaseResult<Boolean> deletComRate(List<Long> ids);
    public BaseResult<Boolean> replayComRate(List<Long> ids, String Content);
//    public BaseResult<ComRateDO> replayComRate(Long id, String Content);
}
