package com.yimayhd.palace.service;

import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.guide.ScenicVO;

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
    public boolean addGuide(GuideScenicVO guideVO);

    /**
     * 修改导览
     *
     * @param guideVO
     * @return
     */
    public boolean updateGuide(GuideScenicVO guideVO);

    /**
     * 根据id查询导览
     *
     * @param id
     * @return
     */
    public GuideScenicVO getGuideById(long id);

    /**
     * 设置权重
     *
     * @param guideId 导览id
     * @param weight  权重值
     * @return boolean
     */
    public boolean setWeight(final long guideId, final int weight);

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

    /**
     * 获得导览的可用景区列表
     *
     * @param scenicPageQuery
     * @return
     */
    public PageVO<ScenicVO> getScenicList(ScenicPageQuery scenicPageQuery);

    /**
     * 获得景区的主题名称
     *
     * @param scenicVO
     * @return
     */
    public ScenicVO selectedScenic(ScenicVO scenicVO);

    public GuideScenicDTO queryGuideDetailByScenicId(final long scenicId);
}
