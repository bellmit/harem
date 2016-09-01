package com.yimayhd.palace.controller.touristlist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideScenicDO;
import com.yimayhd.ic.client.model.dto.guide.*;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.guide.GuideService;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.biz.TrouistlistBiz;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.convert.GuideConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.helper.ResponseVoHelper;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yimayhd.palace.model.guide.GuideAttractionVO;
import com.yimayhd.palace.tair.TcCacheManager;

import java.util.ArrayList;
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
    @Autowired
    private TrouistlistBiz trouistlistBiz;
    @Autowired
    private GuideService guideServiceRef;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private TcCacheManager tcCacheManager;

    //1

    /**
     * select获取景点列表  ok
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String touristlList(Model model, Long attractionId, Long scenicId) throws Exception {
        try {
            ICResult<GuideCascadeAttractionDTO> result = guideServiceRef.queryGuideAttractionFocusInfo(scenicId);
            // 景点列表
            List<AttractionCascadeFocusDTO> touristlist = new ArrayList<AttractionCascadeFocusDTO>();
            // 线路顺序
            List<GuideLineEntry> guideLine = new ArrayList<GuideLineEntry>();
            int totalCount = 0;
            if (result != null && result.isSuccess() && result.getModule() != null && result.getModule().getAttractionDTOList() != null && result.getModule().getAttractionDTOList().size() > 0) {
                totalCount = result.getModule().getAttractionDTOList().size();
                touristlist = result.getModule().getAttractionDTOList();

                if (result.getModule().getGuideLineDTO() != null) {
                    guideLine = result.getModule().getGuideLineDTO().getGuideLine();
                }
            }
            if (null != touristlist) {
                model.addAttribute("touristlist", touristlist);  // 景点列表
                if (guideLine != null) {
                    model.addAttribute("guideLine", guideLine);  // 线路列表
                }
            }
            model.addAttribute("attractionId", attractionId); // 导览id
            System.out.println("touristlist=" + JSON.toJSONString(touristlist));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlist";
    }

    /**
     * delete删除单个景点
     **/
    @RequestMapping(value = "/deleteAttraction", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateStatus(long attractionId) {

        BizResult<String> bizResult = new BizResult<String>();

        if (attractionId < 0) {
            log.error("params error :attractionId={} ", attractionId);
            bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
            return bizResult;
        }

        ICResult<Boolean> result = trouistlistBiz.deleteAttraction(attractionId);
        if (result == null) {
            bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);

        } else if (!result.isSuccess()) {
            bizResult.setCode(result.getResultCode());
            bizResult.setMsg(result.getResultMsg());
            bizResult.setSuccess(false);
        } else {
            bizResult.setCode(result.getResultCode());
            bizResult.setMsg(result.getResultMsg());
            bizResult.setSuccess(true);
        }
        return bizResult;
    }

    /**
     * update 更新线路
     **/
    @RequestMapping(value = "/updateGuideLine", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateGuideLine(long guideId, GuideLineDTO guideLineDTO) {

        BizResult<String> result = new BizResult<String>();

        if (guideId != 0 && guideLineDTO != null) {
            ICResult<Boolean> saveResult = trouistlistBiz.updateGuideLine(guideId, guideLineDTO);
            if (saveResult == null) {
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }
            if (saveResult.isSuccess()) {
                result.initSuccess(saveResult.getResultMsg());
                result.setValue("/jiuxiu/merchant/toMerchantList");
            } else {
                result.setCode(saveResult.getResultCode());
                result.setMsg(saveResult.getResultMsg());
                result.setSuccess(false);
            }
            log.error("saveResult:{}", JSON.toJSONString(saveResult));
        }
        return result;
    }

    //2
    /**
     * select  编辑页面 带景点详情和景点介绍
     **/
    @RequestMapping(value = "/touristEditDetail", method = RequestMethod.GET)
    public String touristEditDetail(Model model, long attractionId) throws Exception {
        try {
            ICResult<AttractionFocusDTO> AttractionDO = guideServiceRef.queryAttractionDetail(attractionId);

            if (null != AttractionDO.getModule().getAttractionDO()) {

                model.addAttribute("AttractionDO", AttractionDO.getModule().getAttractionDO());
            }

            ICResult<AttractionFocusDTO> FocusOrder = guideServiceRef.queryAttractionDetail(attractionId);

            if (null != FocusOrder.getModule().getAttractionDO()) {

                model.addAttribute("FocusOrder", FocusOrder.getModule().getAttractionDO().getFocusOrder());
            }

            // 获取景点介绍的图文
            PictureTextVO picTextVO = trouistlistBiz.getPictureText(attractionId);
            List<PictureTextItemVo> list = picTextVO.getPictureTextItems();
            if (list.size() > 0) {
                model.addAttribute("getPictureTextItems", list);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlistEditDetail";
    }

    /**
     * select新增页面  不带景点详情和景点介绍
     **/
    @RequestMapping(value = "/touristaddDetail", method = RequestMethod.GET)
    public String touristaddDetail(Model model, long attractionId) throws Exception {
        try {

            // 导览id
            if (attractionId > 0) {
                model.addAttribute("guideAttractionid", attractionId);
                // 防止图文24小时重复提交
                model.addAttribute("UUIDPicText", UUID.randomUUID().toString());

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlistaddDetail";
    }

    // 新增或者更新保存  两个接口1 保存景点详情 2 保存图文

    /**
     * add 景点详情和景点介绍 新增保存  ok
     **/
    @RequestMapping(value = "/addTourist", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> addTourist(Model model, GuideAttractionVO guideAttractionVO) {

        try {
            BizResult<String> result = new BizResult<String>();
            // 新增
            ICResult<GuideAttractionDO> saveResult = null;

            AttractionFocusAddDTO attractionFocusAddDTO = GuideConverter.attractionVO2AttractionFocusAddDTO(guideAttractionVO);

            saveResult = trouistlistBiz.addAttractionAndFocus(attractionFocusAddDTO);

            if (saveResult == null) {
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }
            if (saveResult.isSuccess()) {
                result.initSuccess(saveResult.getResultMsg());
                //  传入景点id
                if (saveResult.getModule() != null) {
                    result.setValue(saveResult.getModule().getId() + "");
                }
                result.setSuccess(true);
            } else {
                result.setCode(saveResult.getResultCode());
                result.setMsg(saveResult.getResultMsg());
                result.setSuccess(false);
            }
            log.error("saveResult:{}", JSON.toJSONString(saveResult));

            return result;

        } catch (Exception e) {

            log.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * update 景点详情和景点介绍 编辑保存
     **/
    @RequestMapping(value = "/updateTourist", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> addTourist(long attractionId, AttractionFocusUpdateDTO attractionFocusUpdateDTO) {

        BizResult<String> result = new BizResult<String>();

        // 更新
        ICResult<Boolean> saveResult = null;

        saveResult = trouistlistBiz.updateAttractionAndFocus(attractionFocusUpdateDTO);
        if (saveResult == null) {
            result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
            return result;
        }
        if (saveResult.isSuccess()) {
            result.initSuccess(saveResult.getResultMsg());
            result.setValue("/jiuxiu/merchant/toMerchantList");
        } else {
            result.setCode(saveResult.getResultCode());
            result.setMsg(saveResult.getResultMsg());
            result.setSuccess(false);
        }
        log.error("saveResult:{}", JSON.toJSONString(saveResult));

        return result;
    }

    //3

    /**
     * 保存 景点图文详情（资源）  待调试
     */
    @RequestMapping(value = "/savePictureText/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo savePictureText(@PathVariable("id") long id, String json, String uuidPicText) throws Exception {

        String key = Constant.APP + "_repeat_" + sessionManager.getUserId() + uuidPicText;
        boolean rs = tcCacheManager.addToTair(key, true, 2, 24 * 60 * 60);
        if (rs) {
            try {
                if (StringUtils.isBlank(json)) {
                    log.warn("json is null");
                    return ResponseVo.error();
                }

                json = json.replaceAll("\\s*\\\"\\s*", "\\\"");
                PictureTextVO pictureTextVO = (PictureTextVO) JSONObject.parseObject(json, PictureTextVO.class);

                trouistlistBiz.savePictureText(id, pictureTextVO);
                return ResponseVo.success();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                ResponseVo resVO = ResponseVo.error(e);
                resVO.setData(UUID.randomUUID().toString());
                return resVO;
            }
        }
        return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
    }
}
