package com.yimayhd.palace.controller;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.vo.DelayPushPageQuery;
import com.yimayhd.palace.model.vo.PushQueryVO;
import com.yimayhd.palace.model.vo.PushVO;
import com.yimayhd.palace.service.PushService;
import com.yimayhd.palace.service.RcDelayPushService;
import com.yimayhd.palace.service.ShowcaseService;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.palace.util.Enums;
import com.yimayhd.palace.util.StringUtil;
import com.yimayhd.resourcecenter.model.enums.RcDelaySendTargetType;
import com.yimayhd.resourcecenter.model.enums.RcDelaySendType;
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

import java.util.*;

/**
 * @author create by yushengwei on 2016/10/10
 * @Description 消息推送管理
 */

@Controller
@RequestMapping("/push")
public class PushManageController extends BaseController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired PushService pushService;
    @Autowired private RcDelayPushService rcDelayPushService;
    @Autowired ShowcaseService showcaseService;
    @Autowired private SessionManager sessionManager;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getMsgAdd(Model model,int pushType) throws Exception {
        model.addAttribute("isEdit","add");
        if(Constant.PUSH_MSG == pushType){//1短信2push
            return "/system/push/appMsg/edit";
        }else{
            return "/system/push/appPush/edit";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo msgAdd(Model model,PushVO pushVO) throws Exception {
       //校验
        setOperationUserId(pushVO);
        if(!verifyPushVO(pushVO)){
            return new ResponseVo().error(ResponseStatus.INVALID_DATA);
       }
        boolean checkFlag = pushService.hasSubmitPushVO(pushVO);
        if(checkFlag){
            return new ResponseVo().error(ResponseStatus.REPEATSUBMIT);
        }
        boolean flag = pushService.saveOrUpdate(pushVO);
        if(flag){
            return new ResponseVo(ResponseStatus.SUCCESS);
        }
        return new ResponseVo(ResponseStatus.UNSUCCESSFUL);
    }

    public PushVO setOperationUserId(PushVO pushVO){
        UserDO user = sessionManager.getUser();
        pushVO.setOperationUserId(user.getId());
        return pushVO;
    }

    //短信推送列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String msgList(Model model,PushQueryVO pushQueryVO) throws Exception {
        setQueryDate(pushQueryVO);
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

    public PushQueryVO setQueryDate(PushQueryVO pv){
        if(null == pv){
            return pv;
        }
        String bd = pv.getBeginPushDate();
        String ed = pv.getEndPushDate();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(bd)){
            pv.setBeginPushDate(bd + " 00:00:00");
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(ed)){
            pv.setEndPushDate(ed + " 23:59:59");
        }
        if(StringUtils.isNotEmpty(bd) && org.apache.commons.lang3.StringUtils.isEmpty(ed)){
            Date d = DateUtil.formatMaxTimeForDate(new Date());
            pv.setEndPushDate(DateUtil.date2String(d));
        }
        return pv;
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String msgDetail(Model model,@PathVariable(value = "id") long id) throws Exception {
        PushVO pushVO = pushService.getDetail(id);
        model.addAttribute("entity",pushVO);
        model.addAttribute("isEdit","detail");
        return "/system/push/appMsg/edit";
    }

    //短信推送详情
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String toEdit(Model model,@PathVariable(value = "id") long id) throws Exception {
        PushVO pushVO = pushService.getDetail(id);
        model.addAttribute("entity",pushVO);
        model.addAttribute("isEdit","edit");
        return "/system/push/appMsg/edit";
    }

    //短信推送编辑
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo edit(Model model,PushVO pushVO) throws Exception {
        setOperationUserId(pushVO);
        boolean flag = pushService.saveOrUpdate(pushVO);
        if(flag){
            return new ResponseVo(ResponseStatus.SUCCESS);
        }
        return new ResponseVo(ResponseStatus.UNSUCCESSFUL);
    }

    //短信推送取消
    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.POST)
    @ResponseBody
    public ResponseVo cancel(Model model,@PathVariable(value = "id") long id) throws Exception {
        boolean flag = pushService.cancel(id);
        if(flag){
            return new ResponseVo(ResponseStatus.SUCCESS);
        }
        return new ResponseVo(ResponseStatus.UNSUCCESSFUL);
    }


    //短信推送列表
    @RequestMapping(value = "/appPush/list", method = RequestMethod.GET)
    public String pushList(Model model,DelayPushPageQuery delayPushPageQuery) throws Exception {
        try {
            PageVO<PushVO> pageVO = rcDelayPushService.getPushList(delayPushPageQuery);
            if(pageVO!=null) {
                model.addAttribute("pageVo", pageVO);
                model.addAttribute("itemList", pageVO.getItemList());
            }
            model.addAttribute("rcDelayStatus", Enums.toList(RcDelayStatus.class));
            model.addAttribute("rcDelaySendTargetType", Enums.toList(RcDelaySendTargetType.class));
            model.addAttribute("rcDelayPushPageQuery", delayPushPageQuery);
            model.addAttribute("query",delayPushPageQuery);

            return "/system/push/appPush/list";
        } catch (Exception e) {
            log.error("pushList rcDelayPushPageQuery={}, exception={}", JSON.toJSONString(delayPushPageQuery), e);
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
        List<DomainType> domainType = new ArrayList<>();
        domainType.add(DomainType.DOMAIN_JX);
        model.addAttribute("domainTypeList", domainType);
//        model.addAttribute("domainTypeList", Enums.toList(DomainType.class));
        model.addAttribute("pushTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("rcDelaySendTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",0);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("pushVO",new PushVO());
        model.addAttribute("operation","add");
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
        List<DomainType> domainType = new ArrayList<>();
        domainType.add(DomainType.DOMAIN_JX);
        model.addAttribute("domainTypeList", domainType);
        model.addAttribute("rcDelaySendTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("pushTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",pushVO.getOperationDetailId()==null?0:Long.parseLong(pushVO.getOperationDetailId()));
        model.addAttribute("pushVO",pushVO);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("operation","edit");
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
        List<DomainType> domainType = new ArrayList<>();
        domainType.add(DomainType.DOMAIN_JX);
        model.addAttribute("domainTypeList", domainType);
        model.addAttribute("pushTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("rcDelaySendTypeList", Enums.toList(RcDelaySendType.class));
        model.addAttribute("pushTypeMap", Enums.toMap(RcDelayType.class, null));
        model.addAttribute("operationDetailId",pushVO.getOperationDetailId()==null?0:pushVO.getOperationDetailId());
        model.addAttribute("pushVO",pushVO);
        List<OperactionVO> operationDOs = showcaseService.getAllOperations();
        Collections.sort(operationDOs);
        model.addAttribute("operationDOs",operationDOs);
        model.addAttribute("operation","view");
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
            return ResponseVo.error(e);
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
        if (null == pushVo.getPushDateStr()){
            return false;
        }
        Date pushDate = DateUtil.string2Date(pushVo.getPushDateStr());
        if(pushDate.before(new Date())){
            return false;
        }
        return true;
    }

    public static void main(String[] ars ){
        PushVO p1 = new PushVO();
        p1.setDomain(1100);
        p1.setSubject("aaa");//主题
        p1.setPushType(1);//推送类型
        p1.setMsgTitle("bbb");//消息栏标题
        p1.setPushContent("ccc");//推送内容
        p1.setPushModelType(1);//推广对象类型
        p1.setPushModelFilePath("fdsfsfsdfsdf.txt");//特定的推广对象文件地址
        p1.setPushDate(new Date());//推送时间
        p1.setOperationUserId(231231);//操作人ID


        PushVO p2 = new PushVO();
        p2.setDomain(1100);
        p2.setSubject("aaa");//主题
        p2.setPushType(1);//推送类型
        p2.setMsgTitle("bbb");//消息栏标题
        p2.setPushContent("ccc");//推送内容
        p2.setPushModelType(1);//推广对象类型
        p2.setPushModelFilePath("fdsfsfsdfsdfa.txt");//特定的推广对象文件地址
        p2.setPushDate(new Date());//推送时间
        p2.setOperationUserId(231231);//操作人ID

        PushVO p3 = new PushVO();
        p3.setDomain(1100);
        p3.setSubject("aaa");//主题
        p3.setPushType(1);//推送类型
        p3.setMsgTitle("bbbs");//消息栏标题
        p3.setPushContent("ccc");//推送内容
        p3.setPushModelType(1);//推广对象类型
        p3.setPushModelFilePath("fdsfsfsdfsdf.txt");//特定的推广对象文件地址
        p3.setPushDate(new Date());//推送时间
        p3.setOperationUserId(231231);//操作人ID

        int ha1 = p1.hashCode();
        int ha2 = p2.hashCode();
        int ha3 = p3.hashCode();

        System.out.println(ha1);
        System.out.println(ha2);
        System.out.println(ha3);
        System.out.println("--------------------------------------");
        System.out.println(ha1== ha2);
        System.out.println(ha2== ha3);
        System.out.println(ha1== ha3);
    }

}
