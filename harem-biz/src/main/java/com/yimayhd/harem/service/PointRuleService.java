package com.yimayhd.harem.service;

import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.IMallPointRuleVO;
import com.yimayhd.tradecenter.client.model.result.imall.pointrule.IMallPointRuleResult;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface PointRuleService {
    /**
     * 获取正在使用的积分发放规则
     * @return 赠送积分规则
     * @throws Exception
     */
    IMallPointRuleResult getSendPointRuleNow()throws Exception;

    /**
     * 获取赠送积分规则的历史记录
     * @return 赠送积分列表
     * @throws Exception
     */
    PageVO<IMallPointRuleResult> getSendPointRuleHistory(BaseQuery baseQuery)throws Exception;

    /**
     * 新增积分赠送规则
     * @param iMallPointRuleVO
     * @return
     * @throws Exception
     */
    boolean add(IMallPointRuleVO iMallPointRuleVO)throws Exception;
}
