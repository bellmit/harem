package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.result.BizResult;

/**
 * @author create by yushengwei on 2016/10/13
 * @Description
 */
public interface PushService {

    PageVO<PushVO> getList(PushQueryVO pushQueryVO)throws Exception;

    boolean saveOrUpdate(PushVO pushVO) throws Exception;

    PushVO getDetail(long id)throws Exception;

}
