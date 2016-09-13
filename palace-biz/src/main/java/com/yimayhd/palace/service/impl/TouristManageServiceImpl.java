package com.yimayhd.palace.service.impl;

import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.model.guide.AttractionFocusVO;
import com.yimayhd.palace.model.guide.GuideCascadeAttractionVO;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.repo.PictureTextRepo;
import com.yimayhd.palace.service.TouristManageService;
import com.yimayhd.palace.repo.GuideRepo;
import com.yimayhd.palace.model.guide.GuideCascadeAttractionVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haozhu on 16/9/9.
 */
public class TouristManageServiceImpl implements TouristManageService {

    @Resource
    private GuideRepo guideRepo;

    // 图文
    @Autowired
    private PictureTextRepo pictureTextRepo;

    /**
     * 根据景区id查询景点列表+看点列表
     *
     * @param scenicId
     * @return
     */
    @Override
    public GuideCascadeAttractionVO queryGuideAttractionFocusInfo(long scenicId) {
        GuideCascadeAttractionDTO guideCascadeAttractionDTO = guideRepo.queryGuideAttractionFocusInfo(scenicId);
        if (guideCascadeAttractionDTO == null) {
            return new GuideCascadeAttractionVO();
        }
        List<AttractionCascadeFocusDTO> touristlist = new ArrayList<AttractionCascadeFocusDTO>(); // 景点列表
        List<GuideLineEntry> guideLine = new ArrayList<GuideLineEntry>(); // 线路顺序
        List<AttractionCascadeFocusDTO> attractionDTOList = guideCascadeAttractionDTO.getAttractionDTOList();
        List<GuideLineEntry> GuideLineEntryList = guideCascadeAttractionDTO.getGuideLineDTO().getGuideLine();
        touristlist.addAll(attractionDTOList);
        guideLine.addAll(GuideLineEntryList);
        GuideCascadeAttractionVO guideCascadeAttractionVO = new GuideCascadeAttractionVO();
        guideCascadeAttractionVO.setAttractionDTOList(attractionDTOList);
        guideCascadeAttractionVO.setGuideLine(guideLine);
        return guideCascadeAttractionVO;
    }

    /**
     * 新增景点+看点
     *
     * @param attractionFocusAddDTO
     * @return
     */
    @Override
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO) {
        return guideRepo.addAttractionAndFocus(attractionFocusAddDTO);
    }

    /**
     * 查询景点列表-不分页
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ICResult<List<GuideAttractionDO>> queryAttractionList(GuideAttractionQueryDTO queryDTO) {
        return guideRepo.queryAttractionList(queryDTO);
    }

    /**
     * 更新景点+看点信息
     *
     * @param attractionFocusUpdateDTO
     * @return
     */
    @Override
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO) {
        return guideRepo.updateAttractionAndFocus(attractionFocusUpdateDTO);
    }

    /**
     * 删除景点
     *
     * @param attractionId
     * @return
     */
    @Override
    public ICResult<Boolean> deleteAttraction(long attractionId) {
        return guideRepo.deleteAttraction(attractionId);
    }

    /**
     * 查询景点以及景点下的看点信息
     *
     * @param attractionId
     * @return
     */
    @Override
    public AttractionFocusDTO queryAttractionDetail(long attractionId) {
        AttractionFocusDTO attractionFocusDTO = guideRepo.queryAttractionDetail(attractionId);
        return attractionFocusDTO;
    }

    /**
     * 更新线路
     *
     * @param guideId
     * @param guideLineDTO
     * @return
     */
    @Override
    public ICResult<Boolean> updateGuideLine(long guideId, GuideLineDTO guideLineDTO) {
        return guideRepo.updateGuideLine(guideId, guideLineDTO);
    }

    /**
     * 根据导览id查询线路信息
     *
     * @param guideId
     * @return
     */
    @Override
    public ICResult<GuideLineDTO> queryGuideLine(long guideId) {
        return guideRepo.queryGuideLine(guideId);
    }

    // 更新或者保存 景点图文介绍
    public void savePictureText(long id, PictureTextVO pictureTextVO)  {
        ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(id, PictureText.SCENICSPOTS, pictureTextVO);
        pictureTextRepo.editPictureText(comentEditDTO);
    }

    // 获取 景点图文介绍
    public PictureTextVO getPictureText(long id)  {
        if (id == 0) {
            return null;
        }

        // 图文详情
        PicTextResult picTextResult = pictureTextRepo.getPictureText(id, PictureText.SCENICSPOTS);
        PictureTextVO pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
        return pictureTextVO;
    }
}
