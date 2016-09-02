package com.yimayhd.palace.service.impl;


import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicTipsDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.Paginator;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.model.guide.AttractionFocusVO;
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
        guideScenicPageQueryDTO.setNeedCount(true);
        ICPageResult<GuideScenicDTO> result = guideRepo.getGuidePageList(guideScenicPageQueryDTO);
        if (result == null) {
            return new PageVO<GuideScenicVO>();
        }
        List<GuideScenicVO> guideScenicVOList = new ArrayList<GuideScenicVO>();
        List<GuideScenicDTO> guideScenicDTOList = result.getList();
        for (GuideScenicDTO guideScenicDTO : guideScenicDTOList) {
            guideScenicVOList.add(GuideConverter.guideScenicDTO2GuideScenicVO(guideScenicDTO));
        }
        result.setPageSize(guideListQuery.getPageSize());
        return new PageVO<GuideScenicVO>(result.getPageNo(), result.getPageSize(), result.getTotalCount(), guideScenicVOList);
    }

    /**
     * 添加导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public boolean addGuide(GuideScenicVO guideVO) {
        GuideScenicDO guideScenicDO = GuideConverter.guideSceniceVO2GuideScenicDO(guideVO);
        GuideScenicDO result = guideRepo.addGuide(guideScenicDO);
        if (result == null) {
            return false;
        }
        guideVO.setGuideId(result.getId());

        return saveGuideScenicTips(guideVO);
    }

    private boolean saveGuideScenicTips(GuideScenicVO guideVO) {
        GuideScenicTipsDO guideScenicTipsDO = GuideConverter.guideSceniceVO2GuideScenicTipsDO(guideVO);
        GuideScenicTipsDO resultTips = guideRepo.saveGuideScenicTips(guideScenicTipsDO);
        if (resultTips == null) {
            //delete
            guideRepo.deleteGuide(guideVO.getGuideId());
            return false;
        }
        return true;
    }


    /**
     * 修改导览
     *
     * @param guideVO
     * @return
     */
    @Override
    public boolean updateGuide(GuideScenicVO guideVO)  {
        final long id = guideVO.getGuideId();
        GuideScenicDTO guideScenicDTO = guideRepo.queryGuideDetail(id);
        if (guideScenicDTO == null) {
            return false;
        }
        GuideScenicUpdateDTO guideScenicUpdateDTO = GuideConverter.guideSceniceVO2GuideScenicUpdateDTO(guideVO);
        boolean result = guideRepo.updateGuide(guideScenicUpdateDTO);
        boolean resultTips = false;
        GuideScenicTipsDO guideScenicTipsDO = guideRepo.queryGuideScenicTips(id);
        if (guideScenicTipsDO != null) {
            GuideTipsUpdateDTO guideTipsUpdateDTO = GuideConverter.guideSceniceVO2GuideScenicTipsDTO(guideVO);
            resultTips = guideRepo.updateGuideScenicTips(guideTipsUpdateDTO);
        } else {
            resultTips = saveGuideScenicTips(guideVO);
        }
        return result && resultTips;
    }

    /**
     * 根据id查询导览
     *
     * @param id
     * @return
     */
    @Override
    public GuideScenicVO getGuideById(final long id) {
        GuideScenicDTO guideScenicDTO = guideRepo.queryGuideDetail(id);
        if (guideScenicDTO == null) {
            return null;
        }
        GuideScenicVO guideScenicVO = GuideConverter.guideScenicDTO2GuideScenicVO(guideScenicDTO);
//        GuideScenicDTO scenicDTO = guideRepo.queryGuideDetailByScenicId(guideScenicVO.getScenicId());
        GuideScenicTipsDO guideScenicTipsDO = guideRepo.queryGuideScenicTips(id);
        if (guideScenicTipsDO != null) {
            guideScenicVO = GuideConverter.guideScenicTipsDO2guideSceniceVO2GuideScenicTipsDO(guideScenicVO, guideScenicTipsDO);
        }
        return guideScenicVO;
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
            return new PageVO<ScenicVO>();
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
        if (null == comTagDO) {
            scenicVO.setSubjectName("");
        } else {
            scenicVO.setSubjectName(comTagDO.getName());
        }
        return scenicVO;
    }

    /**
     * 根据景区id查询导览景区信息
     *
     * @param scenicId
     * @return
     */
    @Override
    public GuideScenicDTO queryGuideDetailByScenicId(final long scenicId) {
        return guideRepo.queryGuideDetailByScenicId(scenicId);
    }

    /**
     * 根据导览id查询景点列表
     *
     * @param guideId
     * @return
     * @parameter guideId
     */
    @Override
    public List<GuideAttractionDO> queryAttraction(final long guideId) {
        return guideRepo.queryAttraction(guideId);
    }

    /**
     * 根据景区id查询景点列表+看点列表
     *
     * @param scenicId
     * @return
     */
    @Override
    public GuideCascadeAttractionDTO queryGuideAttractionFocusInfo(final long scenicId) {
        return guideRepo.queryGuideAttractionFocusInfo(scenicId);
    }

    /**
     * 查询景点以及景点下的看点信息
     *
     * @param attractionId
     * @return
     */
    @Override
    public AttractionFocusVO queryAttractionDetail(long attractionId) {
        AttractionFocusDTO attractionFocusDTO =  guideRepo.queryAttractionDetail(attractionId);

       return GuideConverter.attractionFocusDTO2AttractionFocusVO(attractionFocusDTO) ;
    }
}
