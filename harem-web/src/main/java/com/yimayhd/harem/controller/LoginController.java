package com.yimayhd.harem.controller;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.controller.loginout.vo.LoginoutVO;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.service.HaMenuService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.ImageVerifyCodeValidate;
import com.yimayhd.user.session.manager.JsonResult;
import com.yimayhd.user.session.manager.SessionUtils;

import net.pocrd.entity.AbstractReturnCode;

/**
 * Created by Administrator on 2015/10/23.
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private UserService userServiceRef;

    @Resource
    private ImageVerifyCodeValidate imageVerifyCodeValidate;

    @Autowired
    private HaMenuService haMenuService;



    /**
     * 登录页面
     *
     * @return 登录页面
     * @throws Exception
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() throws Exception {
        return "/login";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult login(LoginoutVO loginoutVO) {
        LOGGER.info("login loginoutVO= {}", loginoutVO);

        /*if (!imageVerifyCodeValidate.validateImageVerifyCode(loginoutVO.getVerifyCode())) {
            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }*/

        BaseResult<UserDO> result = userServiceRef.login(loginoutVO.getUsername(), loginoutVO.getPassword());
        int errorCode = result.getErrorCode();
        if (Integer.valueOf(AbstractReturnCode._C_SUCCESS).equals(Integer.valueOf(errorCode))) {
            LOGGER.info("loginoutVO= {} login success and userId = {}", loginoutVO, result.getValue());
            SessionUtils.setUserId(String.valueOf(result.getValue().getId()));
            return JsonResult.buildSuccessResult(result.getResultMsg(), null);
        }

        LOGGER.warn("loginoutVO= {} login fail and msg = {}", loginoutVO, result.getResultMsg());
        return JsonResult.buildFailResult(Integer.valueOf(errorCode), result.getResultMsg(), null);
    }


    @RequestMapping("/validateCode")
    @ResponseBody
    public JsonResult validateCode(LoginoutVO loginoutVO) {
        LOGGER.info("validateCode loginoutVO= {}", loginoutVO);

        if (!imageVerifyCodeValidate.validateImageVerifyCode(loginoutVO.getVerifyCode())) {
            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }

        LOGGER.info("validateCode success loginoutVO= {}", loginoutVO);
        return JsonResult.buildSuccessResult("", null);
    }

    /**
     * 登录后的成功页
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String toMain(Model model) throws Exception {
        long userId = Long.parseLong(SessionUtils.getUserId()) ;
        List<HaMenuDO> haMenuDOList = haMenuService.getMenuListByUserId(userId);
        model.addAttribute("menuList", haMenuDOList);
        return "/system/layout/layout";
    }

    /**
     * 登录后的欢迎页
     *
     * @throws Exception
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() throws Exception {
        return "/system/welcome";
    }

    /**
     * 没有权限页面
     *
     * @return 错误页面
     * @throws Exception
     */
    @RequestMapping(value = "/user/noPower", method = RequestMethod.GET)
    public String toNoPower(Model model,String urlName) throws Exception {
        model.addAttribute("message", "没有" + urlName + "权限，请联系管理员");
        return "/system/error";
    }
}
