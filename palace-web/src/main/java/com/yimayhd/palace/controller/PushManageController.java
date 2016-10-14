package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.service.impl.RcDelayPushServiceImpl;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.resourcecenter.model.enums.*;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author create by yushengwei on 2016/10/10
 * @Description 消息推送管理
 */

@Controller
@RequestMapping("/push")
public class PushManageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private RcDelayPushServiceImpl rcDelayPushService;

    //短信推送列表
    @RequestMapping(value = "/msg/list", method = RequestMethod.GET)
    public String msgList(Model model,BaseQuery baseQuery) throws Exception {
        return "/system/push/msg/list";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String msgDetail(Model model,@PathVariable(value = "id") long id) throws Exception {
        return "/system/push/msg/detail";
    }

    @RequestMapping(value = "/msg/add", method = RequestMethod.GET)
    public String msgAdd(Model model) throws Exception {
        return "/system/push/msg/edit";
    }

    //短信推送列表
    @RequestMapping(value = "/push/list", method = RequestMethod.GET)
    public String pushList(Model model,RcDelayPushPageQuery rcDelayPushPageQuery) throws Exception {

        try {
            PageVO<PushVO> pageVO = rcDelayPushService.getPushList(rcDelayPushPageQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("itemList", pageVO.getItemList());
            model.addAttribute("rcDelayStatus", Enums.toList(RcDelayStatus.class));
            model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
            model.addAttribute("rcDelayPushPageQuery", rcDelayPushPageQuery);
            return "/system/push/push/list";
        } catch (Exception e) {
            log.error("pushList rcDelayPushPageQuery={}, exception={}", JSON.toJSONString(rcDelayPushPageQuery), e);
            return "/error";
        }
    }


    @RequestMapping(value = "/push/add", method = RequestMethod.GET)
    public String pushAdd(Model model) throws Exception {
        return "/system/push/push/add";
    }
}
