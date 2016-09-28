package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.constant.AttachmentConstant;
import com.yimayhd.palace.convert.AttachmentConverter;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.helper.ResponseVoHelper;
import com.yimayhd.palace.model.attachment.AttachmentListQuery;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.palace.service.impl.AttachmentManageServiceImpl;
import com.yimayhd.palace.tair.CacheLockManager;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.resourcecenter.dto.MediaDTO;
import com.yimayhd.resourcecenter.model.enums.MediaFileScope;
import com.yimayhd.resourcecenter.model.enums.MediaFileStatus;
import com.yimayhd.resourcecenter.model.enums.MediaFileType;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger log = LoggerFactory.getLogger(AttachmentManageServiceImpl.class);

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
     * @param attachmentListQuery
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public String list(Model model, AttachmentListQuery attachmentListQuery) {

        try {
            PageVO<AttachmentVO> pageVO = attachmentManageService.getAttachmentList(attachmentListQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("itemList", pageVO.getItemList());
            model.addAttribute("mediaFileTypeList", Enums.toList(MediaFileType.class));
            model.addAttribute("mediaFileStatusList", Enums.toList(MediaFileStatus.class));
            model.addAttribute("mediaFileScopeList", Enums.toList(MediaFileScope.class));
            model.addAttribute("mediaPageQuery", attachmentListQuery);
            model.addAttribute("mediaFileStatusMap", Enums.toMap(MediaFileStatus.class, null));
            return "/system/attachment/list";
        } catch (Exception e) {
            log.error("list attachmentListQuery={}, exception={}", JSON.toJSONString(attachmentListQuery), e);
            return "/error";
        }
    }

    @RequestMapping(value = "/list/select")
    public String selectList(Model model, AttachmentListQuery attachmentListQuery, String type) {
        try {
            attachmentListQuery.setStatus(MediaFileStatus.ON.getValue());
            PageVO<AttachmentVO> pageVO = attachmentManageService.getAttachmentList(attachmentListQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("itemList", pageVO.getItemList());
            model.addAttribute("mediaFileTypeList", Enums.toList(MediaFileType.class));
            model.addAttribute("mediaFileStatusList", Enums.toList(MediaFileStatus.class));
            model.addAttribute("mediaFileScopeList", Enums.toList(MediaFileScope.class));
            model.addAttribute("mediaPageQuery", attachmentListQuery);
            if ("radio".equalsIgnoreCase(type)) {

                return "/system/article/selectList";
            }
            return "/system/attachment/selectList";
        } catch (Exception e) {
            log.error("selectList attachmentListQuery={}, exception={}", JSON.toJSONString(attachmentListQuery), e);
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
        return "/system/attachment/addAttachment";
    }

    /**
     * 编辑跳转
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEdit")
    public String toEdit(Model model, @RequestParam() final long id) {
        try {
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
        } catch (Exception e) {
            log.error("toEdit id={}, exception={}", JSON.toJSONString(id), e);
            return "/error";
        }
    }

    /**
     * 播放
     *
     * @param model
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/play")
    public String play(Model model, @RequestParam() final long id) {
        try {
            MediaDTO mediaDTO = attachmentManageService.getMediaById(id);
            if (mediaDTO == null) {
                return "/error";
            }
            AttachmentVO attachmentVO = AttachmentConverter.mediaDTO2AttachmentVO(mediaDTO);

            model.addAttribute("attachmentVO", attachmentVO);

            return "/system/attachment/play";
        } catch (Exception e) {
            log.error("play id={}, exception={}", JSON.toJSONString(id), e);
            return "/error";
        }
    }

    /**
     * 添加
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addAttachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addAttachment(Model model, AttachmentVO attachmentVO, @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            UserDO user = sessionManager.getUser();
            String key = AttachmentConstant.UPLOAD_ATTACHMENT_LOCK + "_" + user.getId();
            boolean result = false;
            String message = PalaceReturnCode.ADD_ATTACHMENT_ERROR.getErrorMsg();
            if (cacheLockManager.checkSubmitByCache(key)) {
                try {
                    if (file != null && !file.isEmpty()) {

                        String fileName = FilenameUtils.getBaseName(file.getOriginalFilename());
                        MediaDTO mediaDTO = attachmentManageService.getMediaByFileName(fileName);
                        if (mediaDTO == null) {
                            result = attachmentManageService.addAttachment(attachmentVO, file, user.getId());
                            if (result) {
                                message = PalaceReturnCode.ADD_ATTACHMENT_SUCCESS.getErrorMsg();
                            }
                        } else {
                            message = PalaceReturnCode.ADD_ATTACHMENT_ERROR_FILE_NAME_REPEAT.getErrorMsg();
                        }
                    } else {
                        message = PalaceReturnCode.ADD_ATTACHMENT_ERROR_FILE_NULL.getErrorMsg();
                    }
                } finally {
                    cacheLockManager.deleteKey(key);
                }
            } else {
                message = PalaceReturnCode.ADD_ATTACHMENT_ERROR_UPLOAD_REPEAT.getErrorMsg();
            }
            ResponseVo responseVo = ResponseVoHelper.returnResponseVo(result);
            responseVo.setMessage(message);
            return responseVo;
        } catch (Exception e) {
            log.error("addAttachment attachmentVO={}, exception={}", JSON.toJSONString(attachmentVO), e);
            return ResponseVoHelper.returnResponseVo(false);
        }

    }

    /**
     * 修改
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editAttachment", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo editAttachment(Model model, AttachmentVO attachmentVO) {
        try {
            MediaDTO mediaDTO = attachmentManageService.getMediaByFileName(attachmentVO.getInputFileTitle());
            if (mediaDTO != null && mediaDTO.getId() != attachmentVO.getId()) {
                ResponseVo responseVo = ResponseVoHelper.returnResponseVo(false);
                responseVo.setMessage(PalaceReturnCode.ADD_ATTACHMENT_ERROR_FILE_NAME_REPEAT.getErrorMsg());
                return responseVo;
            }
            CheckResult checkResult = attachmentManageService.updateAttachment(attachmentVO);
            return ResponseVoHelper.returnResponseVo(checkResult);
        } catch (Exception e) {
            log.error("editAttachment attachmentVO={}, exception={}", JSON.toJSONString(attachmentVO), e);
            return ResponseVoHelper.returnResponseVo(false);
        }
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
    public ResponseVo upStatus(Model model, long id) {
        try {
            CheckResult checkResult = attachmentManageService.upStatus(id);
            return ResponseVoHelper.returnResponseVo(checkResult);
        } catch (Exception e) {
            log.error("upStatus id={}, exception={}", JSON.toJSONString(id), e);
            return ResponseVoHelper.returnResponseVo(false);
        }
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
    public ResponseVo downStatus(Model model, long id) {
        try {
            CheckResult checkResult = attachmentManageService.downStatus(id);
            return ResponseVoHelper.returnResponseVo(checkResult);
        } catch (Exception e) {
            log.error("downStatus id={}, exception={}", JSON.toJSONString(id), e);
            return ResponseVoHelper.returnResponseVo(false);
        }
    }


}
