package com.yimayhd.palace.controller.touristlist;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.dto.ComentDTO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.guide.GuideAttractionDO;
import com.yimayhd.ic.client.model.domain.guide.GuideFocusDO;
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
import com.yimayhd.palace.model.ArticleItemVO;
import com.yimayhd.palace.model.guide.AttractionFocusVO;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextItemVo;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.resourcecenter.model.enums.ArticleItemType;
import com.yimayhd.palace.service.GuideManageService;
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
import com.yimayhd.palace.model.guide.AttractionListGuideLineVO;
import com.yimayhd.palace.model.guide.AttractionIntroducePicTextTitleVO;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
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
    @Resource
    private GuideManageService guideManageService;
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
            model.addAttribute("scenicId", scenicId); // 导览id
            System.out.println("touristlist=" + JSON.toJSONString(touristlist));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlist";
    }

    /**
     * delete 删除操作前 查询线路中景点的位置 ok
     **/
    @RequestMapping(value = "/queryGuideLine", method = RequestMethod.GET)
    @ResponseBody
    public BizResult<String> queryGuideLine(long guideId,long id) {
        try{
            BizResult<String> bizResult = new BizResult<String>();
            if (guideId < 0) {
                log.error("params error :attractionId={} ", guideId);
                bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
                return bizResult;
            }
            ICResult<GuideLineDTO> result = guideServiceRef.queryGuideLine(guideId);
            if (result == null) {
                bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);

            } else if (!result.isSuccess()) {
                bizResult.setCode(result.getResultCode());
                bizResult.setMsg(result.getResultMsg());
                bizResult.setSuccess(false);
            } else {
                bizResult.setCode(result.getResultCode());
                bizResult.setMsg(result.getResultMsg());
                int temp = -1;
                if(result.getModule() != null){
                    for (int i = 0 ; i < result.getModule().getGuideLine().size(); i++){
                        GuideLineEntry entry = result.getModule().getGuideLine().get(i);
                        if (entry.getAttractionId() == id){
                            temp = i;
                            break;
                        }
                    }
                }
                if (temp == -1){// 不在线路中
                    bizResult.setValue("0");
                }else if(temp == result.getModule().getGuideLine().size()-1){ //线路的最后一个节点
                    bizResult.setValue("2");
                }else {
                    bizResult.setValue("1");//线路的中间位置
                }
                bizResult.setSuccess(true);
            }
            return bizResult;

        }catch (Exception e){

            log.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * delete 删除操作 删除景点 调试
     **/
    @RequestMapping(value = "/deleteAttraction", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> deleteAttraction(long id,long status) {
        BizResult<String> bizResult = new BizResult<String>();

        if (id < 0) {
            log.error("params erruor :attractionId={} ", id);
            bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
            return bizResult;
        }
        ICResult<Boolean> result = trouistlistBiz.deleteAttraction(id);
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

            // TODO: 16/9/2     重置线路
            if(status == 1){
                updateGuideLine(id,"");
            }
        }
        return bizResult;
    }

    /**
     * select线路设置前 查询景点列表和线路信息 ok
     **/
    @RequestMapping(value = "/queryGuideAttractionFocusInfo")
    @ResponseBody
    public ResponseVo getArticleItemDetailById(long scenicId)
            throws Exception {
        try {
            ResponseVo responseVo = new ResponseVo();

            if (scenicId <= 0) {
                return new ResponseVo(ResponseStatus.INVALID_DATA);
            }
            ICResult<GuideCascadeAttractionDTO> guideCascadeAttractionDTO = guideServiceRef.queryGuideAttractionFocusInfo(scenicId);
            if (guideCascadeAttractionDTO.getModule() == null){
                return new ResponseVo(ResponseStatus.INVALID_DATA);
            }

            AttractionListGuideLineVO attractionListGuideLineVO = GuideConverter.guideCascadeAttractionDTO2AttractionListGuideLineVO(guideCascadeAttractionDTO.getModule());
            if (guideCascadeAttractionDTO == null) {
                return new ResponseVo(ResponseStatus.NOT_FOUND);
            }
            responseVo.setData(attractionListGuideLineVO);
            return responseVo;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseVo.error(e);
        }
    }

    /**
     * update线路设置 保存线路 ok
     **/
    @RequestMapping(value = "/updateGuideLine", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateGuideLine(long guideId, String lineListJson) {

        BizResult<String> result = new BizResult<String>();
        ArrayList<GuideLineEntry> guideLine = new ArrayList<GuideLineEntry>();

        lineListJson = lineListJson.replaceAll("\\s*\\\"\\s*", "\\\"");
        guideLine.addAll(JSONArray.parseArray(lineListJson, GuideLineEntry.class));

        GuideLineDTO guideLineDTO = new GuideLineDTO();
        guideLineDTO.setGuideLine(guideLine);

        if (guideId != 0 && lineListJson != null) {
            ICResult<Boolean> saveResult = trouistlistBiz.updateGuideLine(guideId, guideLineDTO);
            if (saveResult == null) {
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }
            if (saveResult.isSuccess()) {
                result.initSuccess(saveResult.getResultMsg());
                result.setValue(guideId + "");
                result.setSuccess(saveResult.isSuccess());
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
     * select  编辑页面 带景点详情和景点介绍 ok
     **/
    @RequestMapping(value = "/touristEditDetail", method = RequestMethod.GET)
    public String touristEditDetail(Model model, long attractionId,long guideId) throws Exception {
        try {
            AttractionFocusVO attractionFocusVO = guideManageService.queryAttractionDetail(attractionId);
            // 景点信息和看点信息
            if (null != attractionFocusVO) {
                model.addAttribute("attractionDO", attractionFocusVO.getGuideAttractionVO());
                model.addAttribute("guideFocusDOList", attractionFocusVO.getGuideFocusVOList());
            }
            // 获取景点介绍的图文
            PictureTextVO picTextVO = trouistlistBiz.getPictureText(attractionId);
            if(picTextVO!=null) {
                List<PictureTextItemVo> list = picTextVO.getPictureTextItems();
                if (list.size() > 0) {
                    model.addAttribute("pictureTextItems", list);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "/system/touristlist/touristlistEditDetail";
    }

    /**
     * select新增页面  不带景点详情和景点介绍 ok
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
     * update 景点详情和景点介绍 编辑保存 ok
     **/
    @RequestMapping(value = "/updateTourist", method = RequestMethod.POST)
    @ResponseBody
    public BizResult<String> updateTourist(GuideAttractionVO guideAttractionVO) {

        BizResult<String> result = new BizResult<String>();

        try {
            ICResult<Boolean> saveResult = null;

            // 查询景点
            ICResult<AttractionFocusDTO> attractionFocusDTOResult = guideServiceRef.queryAttractionDetail(guideAttractionVO.getId());

            if (attractionFocusDTOResult == null){
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }

            AttractionFocusUpdateDTO attractionFocusUpdateDTO = GuideConverter.guideAttractionVO2AttractionFocusUpdateDTO(guideAttractionVO,attractionFocusDTOResult.getModule());

            saveResult = trouistlistBiz.updateAttractionAndFocus(attractionFocusUpdateDTO);

            if (saveResult == null) {
                result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
                return result;
            }
            if (saveResult.isSuccess()) {
                result.initSuccess(saveResult.getResultMsg());
                result.setValue("1");
                result.setSuccess(true);
            } else {
                result.setCode(saveResult.getResultCode());
                result.setMsg(saveResult.getResultMsg());
                result.setSuccess(false);
            }
            log.error("saveResult:{}", JSON.toJSONString(saveResult));
        } catch (Exception e) {
            result.setSuccess(false);
        }

        return result;
    }

    //3
    /**
     * 保存 景点图文详情（资源）  调试
     */
    @RequestMapping(value = "/savePictureText", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo savePictureText(AttractionIntroducePicTextTitleVO attractionIntroducePicTextTitleVO) throws Exception {

        String key = Constant.APP + "_repeat_" + sessionManager.getUserId() + attractionIntroducePicTextTitleVO.getUuidPicText();
        boolean rs = tcCacheManager.addToTair(key, true, 2, 24 * 60 * 60);

        ResponseVo responseVo = new ResponseVo();

       // if (rs) {
            try {
                if (StringUtils.isBlank(attractionIntroducePicTextTitleVO.getPicTextString())) {
                    log.warn("json is null");
                    return ResponseVo.error();
                }

                String json = attractionIntroducePicTextTitleVO.getPicTextString().replaceAll("\\s*\\\"\\s*", "\\\"");
                PictureTextVO pictureTextVO = (PictureTextVO)JSONObject.parseObject(json, PictureTextVO.class);

                trouistlistBiz.savePictureText(attractionIntroducePicTextTitleVO.getAttractionId(), pictureTextVO);

                //// TODO: 16/9/2 更新标题
                GuideAttractionVO guideAttractionVO = new GuideAttractionVO();
                guideAttractionVO.setId(attractionIntroducePicTextTitleVO.getAttractionId());
                guideAttractionVO.setTitle(attractionIntroducePicTextTitleVO.getTitle());
                updateTourist(guideAttractionVO);

                return ResponseVo.success();

            } catch (Exception e) {
                log.error(e.getMessage(), e);
                ResponseVo resVO = ResponseVo.error(e);
                resVO.setData(UUID.randomUUID().toString());
                return resVO;
            }
        //}
       // return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
    }
}
