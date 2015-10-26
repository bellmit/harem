package com.yimayhd.controller;

import com.yimayhd.base.BaseController;
import com.yimayhd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import com.yimayhd.service.MessageCodeService;

/**
 * 用户信息
 * @author liuhaiming
 */
@Controller
@RequestMapping("/view")
public class ViewEditController extends BaseController {

    @Autowired
    private UserService userService;

   /* @Autowired
    private MessageCodeService messageCodeService;

    */

    /**
     * 富文本编辑页面
     * @return 富文本编辑页面
     * @throws Exception
     */
    @RequestMapping(value = "/toViewEditTest", method = RequestMethod.GET)
    public
    String toViewEdit(Model model) throws Exception {
        model.addAttribute("message","请输入帐号和密码");
        return "/demo/editView";
    }
}
