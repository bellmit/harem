package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.guide.GuideListQuery;
import com.yimayhd.palace.model.guide.GuideVO;

/**
 * 导览service
 * Created by xushubing on 2016/8/18.
 */
public interface GuideManageService {
    /**
     * 分页查询导览
     *
     * @param guideListQuery
     * @return
     */
    public PageVO<GuideVO> getGuideList(GuideListQuery guideListQuery);
}
