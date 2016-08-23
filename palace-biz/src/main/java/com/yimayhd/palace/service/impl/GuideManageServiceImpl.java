package com.yimayhd.palace.service.impl;


import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.Paginator;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.guide.ScenicVO;
import com.yimayhd.palace.repo.GuideRepo;
import com.yimayhd.palace.service.GuideManageService;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 获得导览的可用景区列表
     * @param scenicPageQuery
     * @return
     */
    @Override
    public PageVO<ScenicVO> getScenicList(ScenicPageQuery scenicPageQuery) {
        ICPageResult<ScenicDO> result =  guideRepo.queryCanGuideScenic(scenicPageQuery);
        if(null==result) {
            return null;
        } else {
            List<ScenicVO> scenicVOList = GuideConverter.convertScenic(result.getList());
            PageVO<ScenicVO> pageVO = new PageVO<ScenicVO>();
            pageVO.setItemList(scenicVOList);
            Paginator paginator = new Paginator(result.getPageNo(), result.getPageSize(), result.getTotalCount());
            pageVO.setPaginator(paginator);
            return pageVO;
        }
    }

    @Override
    public ScenicVO selectedScenic(ScenicVO scenicVO) {
        return null;
    }
}
