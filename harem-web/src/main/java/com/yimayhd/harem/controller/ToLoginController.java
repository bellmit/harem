package com.yimayhd.harem.controller;


import com.yimayhd.harem.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping
public class ToLoginController extends BaseController {
    /**
     * 登录页面
     * @return 登录页面
     * @throws Exception
     */
    @RequestMapping(value = "/toLogin")
    public
    String toLogin() throws Exception {
        return "/system/user/login";
    }
}
