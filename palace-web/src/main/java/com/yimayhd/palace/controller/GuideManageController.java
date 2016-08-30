package com.yimayhd.palace.controller;

import com.yimayhd.ic.client.model.enums.GuideStatus;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.biz.GuideBiz;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.helper.ResponseVoHelper;
import com.yimayhd.palace.model.guide.GuideScenicListQuery;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.model.guide.ScenicVO;
import com.yimayhd.palace.service.GuideManageService;
import com.yimayhd.palace.util.Enums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 导览
 * Created by xushubing on 2016/8/18.
 */
@Controller
@RequestMapping("/jiuxiu/guideManage")
public class GuideManageController extends BaseController {

    protected Logger log = LoggerFactory.getLogger(GuideManageController.class);
    @Resource
    private GuideManageService guideManageService;

    @Resource
    private GuideBiz guideBiz;

    /**
     * 导览列表  分页
     *
     * @param model
     * @param guideListQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(Model model, GuideScenicListQuery guideListQuery) {
        try {
            PageVO<GuideScenicVO> pageVO = guideManageService.getGuideList(guideListQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("guideListQuery", guideListQuery);
            model.addAttribute("itemList", pageVO.getItemList());
            model.addAttribute("guideStatusList", Enums.toList(GuideStatus.class));
            model.addAttribute("guideStatusMap", Enums.toMap(GuideStatus.class, null));
            return "/system/guide/guidelist";
        } catch (Exception e) {
            return "/error";
        }
    }

    /**
     * 添加导览跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd")
    public String toAdd() throws Exception {
        return "/system/guide/addGuide";
    }

    /**
     * 编辑导览跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEdit")
    public String toEdit(Model model, final long id) {
        try {
            GuideScenicVO guideScenicVO = guideManageService.getGuideById(id);
            if(guideScenicVO!=null) {
                model.addAttribute("guideScenicVO", guideScenicVO);
                return "/system/guide/editGuide";
            }else{
                return "/error";
            }
        } catch (Exception e) {
            return "/error";
        }
    }

    /**
     * 添加导览
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addGuide", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addGuide(Model model, GuideScenicVO guideVO) {
        try {
            CheckResult checkResult = guideBiz.addGuide(guideVO);
            //  boolean result = guideManageService.addGuide(guideVO);
            return ResponseVoHelper.returnResponseVo(checkResult);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }

    /**
     * 修改导览
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editGuide", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo editGuide(Model model, GuideScenicVO guideVO) {
        try {
            CheckResult checkResult = guideBiz.updateGuide(guideVO);
            return ResponseVoHelper.returnResponseVo(checkResult);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }

    /**
     * 设置权重
     *
     * @param model
     * @param guideVO
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/setWeight", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo setWeight(Model model, GuideScenicVO guideVO) {
        try {
            boolean result = guideManageService.setWeight(guideVO.getGuideId(), guideVO.getWeight());
            return ResponseVoHelper.returnResponseVo(result);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }

    /**
     * 上架
     *
     * @param model
     * @param guideId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/status/up")
    @ResponseBody
    public ResponseVo upStatus(Model model, long guideId) {
        try {
            boolean result = guideManageService.upStatus(guideId);
            return ResponseVoHelper.returnResponseVo(result);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }

    /**
     * 下架
     *
     * @param model
     * @param guideId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/status/down")
    @ResponseBody
    public ResponseVo downStatus(Model model, long guideId) {
        try {
            boolean result = guideManageService.downStatus(guideId);
            return ResponseVoHelper.returnResponseVo(result);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }

    @RequestMapping(value = "/scenic/list")
    public String list(Model model, ScenicPageQuery scenicPageQuery) {
        try {
            PageVO<ScenicVO> pageVO = guideManageService.getScenicList(scenicPageQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("itemList", pageVO.getItemList());
            return "/system/guide/selectscenic";
        } catch (Exception e) {
            return "/error";
        }
    }

    /**
     * @param model
     * @param scenicVO
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/scenic/selected")
    @ResponseBody
    public ResponseVo selected(Model model, ScenicVO scenicVO) {
        try {
            ScenicVO result = guideManageService.selectedScenic(scenicVO);
            return new ResponseVo(result);
        } catch (Exception e) {
            return new ResponseVo();
        }
    }


}
