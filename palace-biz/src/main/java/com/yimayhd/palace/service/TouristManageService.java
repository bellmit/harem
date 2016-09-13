package com.yimayhd.palace.service;

import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.palace.model.guide.GuideCascadeAttractionVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;

import java.util.List;

/**
 * Created by haozhu on 16/9/9.
 */
public interface TouristManageService {

    /**
     * 根据景区id查询景点列表+看点列表
     * @param scenicId
     * @return
     */
    public GuideCascadeAttractionVO queryGuideAttractionFocusInfo(long scenicId);

    /**
     * 新增景点+看点
     * @param attractionFocusAddDTO
     * @return
     */
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO);

    /**
     * 查询景点列表-不分页
     * @param queryDTO
     * @return
     */
    public ICResult<List<GuideAttractionDO>> queryAttractionList(GuideAttractionQueryDTO queryDTO);

    /**
     * 更新景点+看点信息
     * @param attractionFocusUpdateDTO
     * @return
     */
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO);

    /**
     * 删除景点
     * @param attractionId
     * @return
     */
    public ICResult<Boolean> deleteAttraction(long attractionId);

    /**
     * 查询景点以及景点下的看点信息
     * @param attractionId
     * @return
     */
    public AttractionFocusDTO queryAttractionDetail(long attractionId);

    /**
     * 更新线路
     * @param guideId
     * @param guideLineDTO
     * @return
     */
    public ICResult<Boolean> updateGuideLine(long guideId,GuideLineDTO guideLineDTO);

    /**
     * 根据导览id查询线路信息
     * @param guideId
     * @return
     */
    public ICResult<GuideLineDTO> queryGuideLine(long guideId);


    /**
     * 根据景点id保存或者更新景点图文信息
     * @param pictureTextVO
     * @return
     */
    public void savePictureText(long id, PictureTextVO pictureTextVO);

    /**
     * 根据景点id获取景点图文信息
     * @param id
     * @return
     */
    public PictureTextVO getPictureText(long id) ;
}

