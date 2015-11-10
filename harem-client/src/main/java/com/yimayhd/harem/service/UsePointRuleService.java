package com.yimayhd.harem.service;

import com.yimayhd.harem.model.UsePointRule;

/**
 * Created by Administrator on 2015/11/9.
 */
public interface UsePointRuleService {
    /**
     * 获取正在使用的积分使用规则
     * @return
     * @throws Exception
     */
    UsePointRule getUsePointRuleNow()throws Exception;
}
