package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.service.RcDelayPushService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.resourcecenter.model.enums.RcDelaySendTargetType;
import com.yimayhd.resourcecenter.model.enums.RcDelayStatus;
import com.yimayhd.resourcecenter.model.enums.RcDelayType;
import com.yimayhd.resourcecenter.model.query.RcDelayPushPageQuery;
import com.yimayhd.resourcecenter.model.resource.vo.OperactionVO;
import com.yimayhd.stone.enums.DomainType;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    @Autowired
    ShowcaseService showcaseService;
    @Autowired
    private SessionManager sessionManager;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getMsgAdd(Model model,int pushType) throws Exception {
        if(Constant.PUSH_MSG == pushType){//1短信2push
            return "/system/push/appMsg/edit";
        }else{
            return "/system/push/appPush/edit";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String msgAdd(Model model,PushVO pushVO) throws Exception {
       //校验
        if(!verifyPushVO(pushVO)){
        throw new Exception("参数校验失败");
       }
        boolean flag = pushService.saveOrUpdate(pushVO);
        if(flag){
            return "success";
        }
        return "error";
    }


    //短信推送列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String msgList(Model model,PushQueryVO pushQueryVO) throws Exception {
        PageVO<PushVO> pageVO = pushService.getList(pushQueryVO);
        model.addAttribute("pageVo",pageVO);
        model.addAttribute("query",pushQueryVO);
        if(Constant.PUSH_MSG == pushQueryVO.getPushType()){
            return "/system/push/appMsg/list";
        }
        /*else if(Constant.PUSH_PUSH == pushQueryVO.getPushType()){
            return "/system/push/appPush/list";
        }*/
        return "error";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String msgDetail(Model model,@PathVariable(value = "id") long id) throws Exception {
        PushVO pushVO = pushService.getDetail(id);
        model.addAttribute("entity",pushVO);
        model.addAttribute("isEdit",false);
        return "/system/push/appMsg/edit";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        PushVO pushVO = pushService.getDetail(id);
        model.addAttribute("entity",pushVO);
        model.addAttribute("isEdit",true);
        return "/system/push/appMsg/edit";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String edit(Model model,PushVO pushVO) throws Exception {
        boolean flag = pushService.saveOrUpdate(pushVO);
        if(flag){
            return "success";
        }
        return "error";
    }

    //短信推送列表
    @RequestMapping(value = "/appPush/list", method = RequestMethod.GET)
    public String pushList(Model model,RcDelayPushPageQuery rcDelayPushPageQuery) throws Exception {
        try {
            PageVO<PushVO> pageVO = rcDelayPushService.getPushList(rcDelayPushPageQuery);
            if(pageVO!=null) {
                model.addAttribute("pageVo", pageVO);
                model.addAttribute("itemList", pageVO.getItemList());
            }
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


    @RequestMapping(value = "/appPush/toAdd", method = RequestMethod.GET)
    public String toPushAdd(Model model) throws Exception {

        model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
        model.addAttribute("domainTypeList", Enums.toList(DomainType.class));
        model.addAttribute("pushTypeList", Enums.toList(RcDelayType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",0);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("pushVO",new PushVO());
        return "/system/push/appPush/edit";
    }
    @RequestMapping(value = "/appPush/toEdit", method = RequestMethod.GET)
    public String toPushEdit(Model model,long id) throws Exception {
        PushVO pushVO = rcDelayPushService.getById(id);
        if(pushVO==null){
            log.error("toPushEdit pushVO is null id={} ",id );
            return "/error";
        }
        model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
        model.addAttribute("domainTypeList", Enums.toList(DomainType.class));
        model.addAttribute("pushTypeList", Enums.toList(RcDelayType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",pushVO.getOperationDetailId());
        model.addAttribute("pushVO",pushVO);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        return "/system/push/appPush/edit";
    }
    @RequestMapping(value = "/appPush/toView", method = RequestMethod.GET)
    public String toPushView(Model model,long id) throws Exception {
        PushVO pushVO = rcDelayPushService.getById(id);
        if(pushVO==null){
            log.error("toPushEdit pushVO is null id={} ",id );
            return "/error";
        }
        model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
        model.addAttribute("domainTypeList", Enums.toList(DomainType.class));
        model.addAttribute("pushTypeList", Enums.toList(RcDelayType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",pushVO.getOperationDetailId());
        model.addAttribute("pushVO",pushVO);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        return "/system/push/appPush/view";
    }
    @RequestMapping(value = "/appPush/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo addPush(Model model, PushVO pushVO) throws Exception {
        LOGGER.debug("", pushVO);

        try {
            if (pushVO == null) {

                return ResponseVo.error();
            }
            PushVO result = null;
            UserDO user = sessionManager.getUser();
            pushVO.setOperationUserId(user.getId());
            if (pushVO.getId() > 0) {
                result = rcDelayPushService.updatePush(pushVO);
            } else {

                result = rcDelayPushService.insertPush(pushVO);
            }
            LOGGER.debug("", result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVo.error();
        }
        return  ResponseVo.success();
    }

    @RequestMapping(value = "/appPush/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo cancelPush(Model model, long id) throws Exception {
       boolean result =  rcDelayPushService.cancelPush(id);
        if(result) {
            return ResponseVo.success();
        }else {
            return ResponseVo.error();
        }
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
