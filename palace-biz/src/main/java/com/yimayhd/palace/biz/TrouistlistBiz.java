package com.yimayhd.palace.biz;

import java.util.List;

import com.yimayhd.commentcenter.client.dto.ComentDTO;
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


/**
 * 景点列表
 * <p>
 * Created by haozhu on 16/8/18.
 */
public class TrouistlistBiz {

    private static final Logger log = LoggerFactory.getLogger("TrouistlistBiz");

    @Autowired
    private GuideRepo guideRepo;

    // 1
    // select获取景点列表...

    // delete删除单个景点
    public ICResult<Boolean> deleteAttraction(long attractionId) {
        return guideRepo.deleteAttraction(attractionId);
    }

    // select查询景点详情
    public ICResult<AttractionFocusDTO> queryAttractionDetail(long attractionId) {
        return guideRepo.queryAttractionDetail(attractionId);
    }

    // add增加保存景点
    public ICResult<GuideAttractionDO> addAttractionAndFocus(AttractionFocusAddDTO attractionFocusAddDTO) {
        return guideRepo.addAttractionAndFocus(attractionFocusAddDTO);
    }

    // update更新保存景点
    public ICResult<Boolean> updateAttractionAndFocus(AttractionFocusUpdateDTO attractionFocusUpdateDTO) {
        return guideRepo.updateAttractionAndFocus(attractionFocusUpdateDTO);
    }

    // 2
    // select获取景点图文介绍
    public PictureTextVO getPictureText(long id) {
        PictureTextVO pictureTextVO = null;
        try {

            log.info("==============================id"+id);
            PicTextResult picTextResult = guideRepo.getPictureText(id);
            log.info("=============================="+JSON.toJSONString(picTextResult));

            pictureTextVO = PictureTextConverter.toPictureTextVO(picTextResult);
        } catch (Exception e) {
            log.error("params:id={} ,exception:{}",id,e);
        }
        return pictureTextVO;
    }

    // update保存景点图文介绍
    // 保存景点图文
    public void savePictureText(ComentDTO comentDTO) {
        PictureTextVO pictureTextVO = null;
        try {
            guideRepo.savePictureText(comentDTO);
        } catch (Exception e) {
            log.error("params:comentDTO={} ,exception:{}",comentDTO,e);
        }
        return ;
    }

    //3
    // select获取线路集合

    // update保存线路集合
    public ICResult<Boolean> updateGuideLine(long guideId,GuideLineDTO guideLineDTO) {
        return guideRepo.updateGuideLine(guideId,guideLineDTO);
    }
}

