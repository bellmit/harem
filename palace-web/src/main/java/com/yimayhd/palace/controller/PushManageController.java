package com.yimayhd.palace.controller;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseQuery;
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
        return "/system/push/msg/add";
    }
}
