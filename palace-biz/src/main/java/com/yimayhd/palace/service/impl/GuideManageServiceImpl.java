package com.yimayhd.palace.service.impl;


import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.repo.GuideRepo;
import com.yimayhd.palace.service.GuideManageService;

import javax.annotation.Resource;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideManageServiceImpl implements GuideManageService {
    @Resource
    private GuideRepo guideRepo;

    /**
     * 分页查询导览
     *
     * @param guideListQuery
     * @return
     */
    @Override
    public PageVO<GuideScenicVO> getGuideList(GuideScenicListQuery guideListQuery) {
        return null;
    }

    /**
     * 添加导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public GuideScenicVO addGuide(GuideScenicVO guideVO) {
        return null;
    }

    /**
     * 修改导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public GuideScenicVO updateGuide(GuideScenicVO guideVO) {
        return null;
    }

    /**
     * 设置权重
     *
     * @param guideId 导览id
     * @param weight  权重值
     * @return boolean
     */
    @Override
    public boolean setWeight(long guideId, int weight) {
        guideRepo.updateGuideWeight(weight, guideId);
        return false;
    }

    /**
     * 上架
     *
     * @param guideId
     * @return
     */
    @Override
    public boolean upStatus(final long guideId) {
        guideRepo.updateGuideStatus(GuideStatus.ONLINE.getCode(), guideId);
        return false;
    }

    /**
     * 下架
     *
     * @param guideId
     * @return
     */
    @Override
    public boolean downStatus(final long guideId) {
        guideRepo.updateGuideStatus(GuideStatus.OFFLINE.getCode(), guideId);
        return false;
    }

}
