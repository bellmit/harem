package com.yimayhd.palace.service;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;

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
    public PageVO<GuideScenicVO> getGuideList(GuideScenicListQuery guideListQuery);

    /**
     * 添加导览
     *
     * @param guideVO
     * @return
     */
    public GuideScenicVO addGuide(GuideScenicVO guideVO);

    /**
     * 修改导览
     *
     * @param guideVO
     * @return
     */
    public GuideScenicVO updateGuide(GuideScenicVO guideVO);

    /**
     * 设置权重
     *
     * @param guideId  导览id
     * @param weight 权重值
     * @return boolean
     */
    public boolean setWeight(final long guideId,final int weight);

    /**
     * 上架
     *
     * @param guideId
     * @return
     */
    public boolean upStatus(final long guideId);

    /**
     * 下架
     *
     * @param guideId
     * @return
     */
    public boolean downStatus(final long guideId);
}
