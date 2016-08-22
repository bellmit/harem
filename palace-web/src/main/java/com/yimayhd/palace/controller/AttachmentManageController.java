package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.model.guide.GuideScenicVO;
import com.yimayhd.palace.service.AttachmentManageService;
import com.yimayhd.resourcecenter.model.query.MediaPageQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 附件中心 音频管理 导览
 * Created by xushubing on 2016/8/18.
 */
@Controller
@RequestMapping("/jiuniu/attachmentManage")
public class AttachmentManageController {
    @Resource
    private AttachmentManageService attachmentManageService;

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
    public String toEdit() throws Exception {
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
        attachmentManageService.addAttachment(attachmentVO);
        return null;
    }

    /**
     * 修改
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editAttachment", method = RequestMethod.POST)
    public String editAttachment(Model model, AttachmentVO attachmentVO) throws Exception {
        return null;
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
    public String upStatus(Model model, long id) throws Exception {
        return null;
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
    public String downStatus(Model model, long id) throws Exception {
        return null;
    }
}
