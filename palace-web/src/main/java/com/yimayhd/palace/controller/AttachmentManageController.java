package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.AttachmentConstant;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.convert.AttachmentConverter;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.palace.tair.CacheLockManager;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.resourcecenter.dto.MediaDTO;
import com.yimayhd.resourcecenter.model.enums.ArticleStatus;
import com.yimayhd.resourcecenter.model.enums.MediaFileScope;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.enums.MediaFileType;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import com.yimayhd.resourcecenter.model.result.ResourceResult;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 附件中心 音频管理 导览
 * Created by xushubing on 2016/8/18.
 */
@Controller
@RequestMapping("/jiuxiu/attachmentManage")
public class AttachmentManageController extends BaseController {


    @Resource
    private AttachmentManageService attachmentManageService;
    @Resource
    private CacheLockManager cacheLockManager;
    @Autowired
    private SessionManager sessionManager;

    /**
     * 分页
     *
     * @param model
     * @param mediaPageQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(Model model, MediaPageQuery mediaPageQuery) throws Exception {
        PageVO<AttachmentVO> pageVO = attachmentManageService.getAttachmentList(mediaPageQuery);

        model.addAttribute("pageVo", pageVO);
        model.addAttribute("itemList", pageVO.getItemList());
        model.addAttribute("mediaFileTypeList", Enums.toList(MediaFileType.class));
        model.addAttribute("mediaFileStatusList", Enums.toList(MediaFileStatus.class));
        model.addAttribute("mediaFileScopeList", Enums.toList(MediaFileScope.class));
        model.addAttribute("mediaPageQuery", mediaPageQuery);
        return "/system/attachment/list";
    }

    /**
     * 添加导览跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toAdd")
    public String toAdd() throws Exception {
        return "/system/attachment/addAttachment";
    }

    /**
     * 编辑跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEdit")
    public String toEdit(Model model, @RequestParam() final long id) throws Exception {
        MediaDTO mediaDTO = attachmentManageService.getMediaById(id);
        if (mediaDTO == null) {
            return "/error";
        }
        AttachmentVO attachmentVO = AttachmentConverter.mediaDTO2AttachmentVO(mediaDTO);

        model.addAttribute("attachmentVO", attachmentVO);
        model.addAttribute("mediaFileTypeList", Enums.toList(MediaFileType.class));
        model.addAttribute("mediaFileStatusList", Enums.toList(MediaFileStatus.class));
        model.addAttribute("mediaFileScopeList", Enums.toList(MediaFileScope.class));
        return "/system/attachment/editAttachment";
    }

    /**
     * 添加
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addAttachment", method = RequestMethod.POST)
    public String addAttachment(Model model, AttachmentVO attachmentVO, @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        String key = AttachmentConstant.UPLOAD_ATTACHMENT_LOCK;
        if (cacheLockManager.checkSubmitByCache(key)) {
            try {
                UserDO user = sessionManager.getUser();
                attachmentManageService.addAttachment(attachmentVO, file, user.getId());
            } finally {
                cacheLockManager.deleteKey(key);
            }
        } else {
            return "redirect:/jiuxiu/attachmentManage/list";
        }
        return "redirect:/jiuxiu/attachmentManage/list";
    }

    /**
     * 修改
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editAttachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo editAttachment(Model model, AttachmentVO attachmentVO) throws Exception {
        boolean result = attachmentManageService.updateAttachment(attachmentVO);
        return returnResponseVo(result);
    }


    /**
     * 上架
     *
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/status/up")
    @ResponseBody
    public ResponseVo upStatus(Model model, long id) throws Exception {
        boolean result = attachmentManageService.upStatus(id);
        return returnResponseVo(result);
    }

    /**
     * 下架
     *
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/status/down")
    @ResponseBody
    public ResponseVo downStatus(Model model, long id) throws Exception {
        boolean result = attachmentManageService.downStatus(id);
        return returnResponseVo(result);
    }

    private ResponseVo returnResponseVo(boolean result) {
        ResponseVo responseVo = new ResponseVo();
        if (result) {
            responseVo.setMessage(ResponseStatus.SUCCESS.MESSAGE);
            responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
        } else {
            responseVo.setMessage(ResponseStatus.ERROR.MESSAGE);
            responseVo.setStatus(ResponseStatus.ERROR.VALUE);
        }
        return responseVo;
    }
}
