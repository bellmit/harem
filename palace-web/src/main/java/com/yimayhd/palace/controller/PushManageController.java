package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.commission.client.enums.Domain;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.attachment.AttachmentVO;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.service.RcDelayPushService;
import com.yimayhd.palace.service.impl.RcDelayPushServiceImpl;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.resourcecenter.model.enums.*;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import org.apache.commons.lang.StringUtils;
import com.yimayhd.stone.enums.DomainType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author create by yushengwei on 2016/10/10
 * @Description 消息推送管理
 */

@Controller
@RequestMapping("/push")
public class PushManageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    PushService pushService;
    @Autowired
    private RcDelayPushService rcDelayPushService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getMsgAdd(Model model,int type) throws Exception {
        if(1==type){//1短信2push
            return "/system/push/appMsg/edit";
        }else{
            return "/system/push/appPush/edit";
        }
    }

    @RequestMapping(value = "/msg/add", method = RequestMethod.POST)
    public String msgAdd(Model model,PushVO pushVO) throws Exception {
       //校验
        if(!verifyPushVO(pushVO)){
        throw new Exception("参数校验失败");
       }
        pushService.saveOrUpdate(pushVO);
        return null;
    }


    //短信推送列表
    @RequestMapping(value = "/msg/list", method = RequestMethod.GET)
    public String msgList(Model model,PushQueryVO pushQueryVO) throws Exception {
        PageVO<PushVO> pageVO = pushService.getList(pushQueryVO);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("query",pushQueryVO);
        return "/system/push/appMsg/list";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String msgDetail(Model model,@PathVariable(value = "id") long id) throws Exception {
        return "/system/push/appMsg/detail";
    }

    //短信推送列表
    @RequestMapping(value = "/appPush/list", method = RequestMethod.GET)
    public String pushList(Model model,RcDelayPushPageQuery rcDelayPushPageQuery) throws Exception {

        try {
            PageVO<PushVO> pageVO = rcDelayPushService.getPushList(rcDelayPushPageQuery);

            model.addAttribute("pageVo", pageVO);
            model.addAttribute("itemList", pageVO.getItemList());
            model.addAttribute("rcDelayStatus", Enums.toList(RcDelayStatus.class));
            model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
            model.addAttribute("rcDelayPushPageQuery", rcDelayPushPageQuery);
            return "/system/push/appPush/list";
        } catch (Exception e) {
            log.error("pushList rcDelayPushPageQuery={}, exception={}", JSON.toJSONString(rcDelayPushPageQuery), e);
            return "/error";
        }
    }



    @RequestMapping(value = "/msg/test", method = RequestMethod.GET)
    public String test(Model model,BaseQuery baseQuery) throws Exception {
        return "/system/push/appMsg/test";
    }


    @RequestMapping(value = "/appPush/add", method = RequestMethod.GET)
    public String pushAdd(Model model) throws Exception {

        model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
        model.addAttribute("domainType", Enums.toList(DomainType.class));

        return "/system/push/appPush/edit";
    }

    public boolean verifyPushVO(PushVO pushVo){
        if(StringUtils.isEmpty(pushVo.getSubject())){
            return false;
        }
        if(StringUtils.isEmpty(pushVo.getPushContent())){
            return false;
        }
        if (new Date().after(pushVo.getPushDate())){//1.1 1.2
            return false;
        }
        return true;
    }

}
