package com.yimayhd.palace.controller.touristlist;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.dto.guide.AttractionFocusDTO;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.biz.TrouistlistBiz;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.guide.*;
import com.yimayhd.palace.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.service.TouristManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

//
/**
 * 景点列表
 * <p>
 * Created by haozhu on 16/8/18.
 **/
@Controller
@RequestMapping("/jiuxiu/scenicManage")
public class TouristlistController extends BaseController {

    protected Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private TrouistlistBiz trouistlistBiz;
    @Resource
    private TouristManageService touristManageService;

    /**
     * select获取景点列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String touristlList(Model model, Long attractionId, Long scenicId) throws Exception {
        try {
            GuideCascadeAttractionVO guideCascadeAttractionVO = touristManageService.queryGuideAttractionFocusInfo(scenicId);
            if (guideCascadeAttractionVO != null) {
                model.addAttribute("touristlist", guideCascadeAttractionVO.getAttractionDTOList());  // 景点列表
                model.addAttribute("guideLine", guideCascadeAttractionVO.getGuideLine());  // 线路列表
                model.addAttribute("attractionId", attractionId); // 导览id
                model.addAttribute("scenicId", scenicId); // 导览id
            } else {
                return "/error";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "/error";
        }
        return "/system/touristlist/touristlist";
    }

    /**
     * delete 删除操作前 查询线路中景点的位置
     **/
    @RequestMapping(value = "/queryGuideLine", method = RequestMethod.GET)
    @ResponseBody
    public GuideLineVO queryGuideLine(Long guideId, Long id) {
        try {
            GuideLineVO guideLineVO = trouistlistBiz.queryGuideLine(guideId, id);
            return guideLineVO;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            GuideLineVO guideLineVO = new GuideLineVO();
            guideLineVO.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return guideLineVO;
        }
    }

    /**
     * delete 删除操作 删除景点
     **/
    @RequestMapping(value = "/deleteAttraction", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> deleteAttraction(Long guideId, Long id, GuideLineVO.Position position) {
        BizResult<String> bizResult = new BizResult<>();
        try {
            bizResult = trouistlistBiz.deleteAttraction(guideId, id, position);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return bizResult;
        }
    }

    /**
     * select线路设置前 查询景点列表和线路信息
     **/
    @RequestMapping(value = "/queryGuideAttractionFocusInfo")
    @ResponseBody
    public BizResult<AttractionListGuideLineVO> queryGuideAttractionFocusInfo(Long scenicId) throws Exception {
        BizResult<AttractionListGuideLineVO> bizResult = new BizResult<AttractionListGuideLineVO>();
        try {
            bizResult = trouistlistBiz.queryGuideAttractionFocusInfo(scenicId);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setSuccess(false);
            return bizResult;
        }
    }

    /**
     * update线路设置 保存线路
     **/
    @RequestMapping(value = "/updateGuideLine", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateGuideLine(Long guideId, String lineListJson) {
        BizResult<String> bizResult = new BizResult<String>();
        try {
            bizResult = trouistlistBiz.updateGuideLine(guideId, lineListJson);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setSuccess(false);
            return bizResult;
        }
    }

    /**
     * select  编辑页面 带景点详情和景点介绍
     **/
    @RequestMapping(value = "/touristEditDetail", method = RequestMethod.GET)
    public String touristEditDetail(Model model, Long attractionId, Long guideId) throws Exception {
        try {
            AttractionFocusDTO attractionFocusDTOICResult = touristManageService.queryAttractionDetail(attractionId);
            AttractionFocusVO attractionFocusVO = GuideConverter.attractionFocusDTO2AttractionFocusVO(attractionFocusDTOICResult);
            if (null != attractionFocusVO) {
                model.addAttribute("attractionDO", attractionFocusVO.getGuideAttractionVO());
                model.addAttribute("guideFocusDOList", attractionFocusVO.getGuideFocusVOList());
            }
            PictureTextVO picTextVO = touristManageService.getPictureText(attractionId);
            if (picTextVO != null && picTextVO.getPictureTextItems().size() > 0) {
                List<PictureTextItemVo> list = picTextVO.getPictureTextItems();
                model.addAttribute("picTextVO", picTextVO);
                model.addAttribute("picTextVOJson", JSON.toJSONString(list));
            }
            model.addAttribute("uuidPicText", UUID.randomUUID().toString());
            model.addAttribute("attractionId", attractionId);
            model.addAttribute("guideId", guideId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlistEditDetail";
    }

    /**
     * select新增页面  不带景点详情和景点介绍
     **/
    @RequestMapping(value = "/touristaddDetail", method = RequestMethod.GET)
    public String touristaddDetail(Model model, Long attractionId) throws Exception {
        try {
            if (attractionId > 0) {
                model.addAttribute("guideAttractionid", attractionId);
                model.addAttribute("uuidPicText", UUID.randomUUID().toString()); // 防止图文24小时重复提交
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlistaddDetail";
    }

    // 新增或者更新保存  两个接口1 保存景点详情 2 保存图文
    /**
     * add 景点详情和景点介绍 新增保存
     **/
    @RequestMapping(value = "/addTourist", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> addTourist(GuideAttractionVO guideAttractionVO) {
        BizResult<String> bizResult = new BizResult<String>();
        try {
            bizResult = trouistlistBiz.addTourist(guideAttractionVO);
            return bizResult;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            bizResult.setSuccess(false);
            return bizResult;
        }
    }

    /**
     * update 景点详情和景点介绍 编辑保存
     **/
    @RequestMapping(value = "/updateTourist", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<Boolean> updateTourist(GuideAttractionVO guideAttractionVO) {
        BizResult<Boolean> result = new BizResult<Boolean>();
        try {
            result = trouistlistBiz.updateTourist(guideAttractionVO);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            return result;
        }
    }

    /**
     * 保存 景点图文详情（资源）
     */
    @RequestMapping(value = "/savePictureText", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<Boolean> savePictureText(AttractionIntroducePicTextTitleVO attractionIntroducePicTextTitleVO, Model model) throws Exception {
        BizResult<Boolean> result = new BizResult<Boolean>();
        try {
            result = trouistlistBiz.savePictureText(attractionIntroducePicTextTitleVO);
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            return result;
        }
    }
}
