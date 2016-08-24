package com.yimayhd.palace.service.impl;


import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicPageQueryDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideScenicUpdateDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideTipsUpdateDTO;
import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.Paginator;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.guide.ScenicVO;
import com.yimayhd.palace.repo.CommentRepo;
import com.yimayhd.palace.repo.GuideRepo;
import com.yimayhd.palace.service.GuideManageService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideManageServiceImpl implements GuideManageService {
    @Resource
    private GuideRepo guideRepo;

    @Resource
    private CommentRepo commentRepo;

    /**
     * 分页查询导览
     *
     * @param guideListQuery
     * @return
     */
    @Override
    public PageVO<GuideScenicVO> getGuideList(GuideScenicListQuery guideListQuery) {
        GuideScenicPageQueryDTO guideScenicPageQueryDTO = GuideConverter.guideListQuery2GuideScenicPageQueryDTO(guideListQuery);
        ICPageResult<GuideScenicDTO> result = guideRepo.getGuidePageList(guideScenicPageQueryDTO);
        if (result == null) {
            return new PageVO<GuideScenicVO>();
        }
        List<GuideScenicVO> guideScenicVOList = new ArrayList<GuideScenicVO>();
        List<GuideScenicDTO> guideScenicDTOList = result.getList();
        for (GuideScenicDTO guideScenicDTO : guideScenicDTOList) {
            guideScenicVOList.add(GuideConverter.guideScenicDTO2GuideScenicVO(guideScenicDTO));
        }
        return new PageVO<GuideScenicVO>(result.getPageNo(), result.getPageSize(), result.getTotalCount(), guideScenicVOList);
    }

    /**
     * 添加导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public GuideScenicVO addGuide(GuideScenicVO guideVO) {
        GuideScenicDO guideScenicDO = GuideConverter.guideSceniceVO2GuideScenicDO(guideVO);
        GuideScenicDO result = guideRepo.addGuide(guideScenicDO);
        if (result == null) {
            return null;
        }
        guideVO.setGuideid(result.getId());
        GuideScenicTipsDO guideScenicTipsDO = GuideConverter.guideSceniceVO2GuideScenicTipsDO(guideVO);
        GuideScenicTipsDO resultTips = guideRepo.saveGuideScenicTips(guideScenicTipsDO);
        return guideVO;
    }

    /**
     * 修改导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public GuideScenicVO updateGuide(GuideScenicVO guideVO) {
        GuideScenicUpdateDTO guideScenicUpdateDTO = GuideConverter.guideSceniceVO2GuideScenicUpdateDTO(guideVO);
        boolean result = guideRepo.updateGuide(guideScenicUpdateDTO);
        GuideTipsUpdateDTO guideTipsUpdateDTO = GuideConverter.guideSceniceVO2GuideScenicTipsDTO(guideVO);
        boolean resultTips = guideRepo.updateGuideScenicTips(guideTipsUpdateDTO);
        return guideVO;
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
        return guideRepo.updateGuideWeight(weight, guideId);
    }

    /**
     * 上架
     *
     * @param guideId
     * @return
     */
    @Override
    public boolean upStatus(final long guideId) {
        return guideRepo.updateGuideStatus(GuideStatus.ONLINE.getCode(), guideId);
    }

    /**
     * 下架
     *
     * @param guideId
     * @return
     */
    @Override
    public boolean downStatus(final long guideId) {
        return guideRepo.updateGuideStatus(GuideStatus.OFFLINE.getCode(), guideId);
    }

    /**
     * 获得导览的可用景区列表
     *
     * @param scenicPageQuery
     * @return
     */
    @Override
    public PageVO<ScenicVO> getScenicList(ScenicPageQuery scenicPageQuery) {
        ICPageResult<ScenicDO> result = guideRepo.queryCanGuideScenic(scenicPageQuery);
        if (null == result) {
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
        ComTagDO comTagDO = commentRepo.selectById(scenicVO.getSubjectId());
        if(null==comTagDO) {
            scenicVO.setSubjectName("");
        } else {
            scenicVO.setSubjectName(comTagDO.getName());
        }
        return scenicVO;
    }
}
