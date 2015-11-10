package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.model.UsePointRule;
import com.yimayhd.harem.service.UsePointRuleService;

/**
 * Created by Administrator on 2015/11/9.
 */
public class UsePointRuleServiceImpl implements UsePointRuleService {
    @Override
    public UsePointRule getUsePointRuleNow() throws Exception {
        UsePointRule usePointRule = new UsePointRule();
        usePointRule.setId((long) 1);
        usePointRule.setRatio((long) 100);
        return usePointRule;
    }
}
