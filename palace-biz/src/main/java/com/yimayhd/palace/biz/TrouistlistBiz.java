package com.yimayhd.palace.biz;

import java.util.List;

import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.commentcenter.client.dto.ComentEditDTO;
import com.yimayhd.commentcenter.client.enums.PictureText;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.dto.guide.AttractionFocusAddDTO;
import com.yimayhd.ic.client.model.dto.guide.AttractionFocusDTO;
import com.yimayhd.ic.client.model.dto.guide.AttractionFocusUpdateDTO;
import com.yimayhd.ic.client.model.dto.guide.GuideLineDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.palace.repo.GuideRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;

import com.yimayhd.commentcenter.client.result.PicTextResult;
import com.yimayhd.commission.convert.PictureTextConverter;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.palace.repo.PictureTextRepo;



/**
 * 景点列表
 * <p>
 * Created by haozhu on 16/8/18.
 */
public class TrouistlistBiz {

    private static final Logger log = LoggerFactory.getLogger("TrouistlistBiz");

    @Autowired
    private GuideRepo guideRepo;

    @Autowired
    private PictureTextRepo	pictureTextRepo;

    // 1
    // select获取景点列表...

    // select获取线路集合

    // update保存线路集合
    public ICResult<Boolean> updateGuideLine(long guideId,GuideLineDTO guideLineDTO) {
        return guideRepo.updateGuideLine(guideId,guideLineDTO);
    }

    // delete删除单个景点
    public ICResult<Boolean> deleteAttraction(long attractionId) {
        return guideRepo.deleteAttraction(attractionId);
    }

    // select查询景点详情
    public ICResult<AttractionFocusDTO> queryAttractionDetail(long attractionId) {
        return guideRepo.queryAttractionDetail(attractionId);
    }

    // 2
    // add增加保存景点
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO) {
        return guideRepo.addAttractionAndFocus(attractionFocusAddDTO);
    }

    // update更新保存景点
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO) {
        return guideRepo.updateAttractionAndFocus(attractionFocusUpdateDTO);
    }

    // 3
    // 更新或者保存 景点图文介绍
    public void savePictureText(long id, PictureTextVO pictureTextVO) throws Exception {
        ComentEditDTO comentEditDTO = PictureTextConverter.toComentEditDTO(id, PictureText.SCENICSPOTS, pictureTextVO);
        pictureTextRepo.editPictureText(comentEditDTO);
    }

    // 获取 景点图文介绍
    public PictureTextVO getPictureText(long id) throws Exception {
        if(id == 0){
            return null;
        }

        // 图文详情
        PicTextResult picTextResult = pictureTextRepo.getPictureText(id, PictureText.SCENICSPOTS);
        PictureTextVO pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
        return pictureTextVO;
    }
}

