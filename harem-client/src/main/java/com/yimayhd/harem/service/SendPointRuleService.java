package com.yimayhd.harem.service;

import com.yimayhd.harem.model.SendPointRule;

import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface SendPointRuleService {
    /**
     * 获取正在使用的积分发放规则
     * @return 赠送积分规则
     * @throws Exception
     */
    SendPointRule getSendPointRuleNow()throws Exception;

    /**
     * 获取赠送积分规则的历史记录
     * @return 赠送积分列表
     * @throws Exception
     */
    List<SendPointRule> getSendPointRuleHistory()throws Exception;
}
