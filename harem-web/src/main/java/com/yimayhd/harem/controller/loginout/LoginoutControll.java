package com.yimayhd.harem.controller.loginout;


import com.yimayhd.harem.controller.loginout.vo.LoginoutVO;
import com.yimayhd.user.api.LoginServiceHttpExport;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.ImageVerifyCodeValidate;
import com.yimayhd.user.session.manager.JsonResult;
import com.yimayhd.user.session.manager.SessionUtils;
import net.pocrd.entity.AbstractReturnCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by root on 15-11-12.
 */
@RestController
public class LoginoutControll {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginoutControll.class);

    @Resource
    private UserService importUserService;

    @Resource
    private ImageVerifyCodeValidate imageVerifyCodeValidate;

    @RequestMapping("/login")
    public JsonResult login(LoginoutVO loginoutVO) {
        LOGGER.info("login loginoutVO= {}", loginoutVO);

//        if (!imageVerifyCodeValidate.validateImageVerifyCode(loginoutVO.getVerifyCode())) {
//            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
//            return JsonResult.buildFailResult(1, "验证码错误!", null);
//        }

        BaseResult<Long> result = importUserService.login(loginoutVO.getUsername(), loginoutVO.getPassword());
        String errorCode = result.getErrorCode();
        if (Integer.valueOf(AbstractReturnCode._C_SUCCESS).equals(Integer.valueOf(errorCode))) {
            LOGGER.info("loginoutVO= {} login success and userId = {}", loginoutVO, result.getValue());
            SessionUtils.setUserId(result.getValue().toString());
            return JsonResult.buildSuccessResult(result.getResultMsg(), null);
        }

        LOGGER.warn("loginoutVO= {} login fail and msg = {}", loginoutVO, result.getResultMsg());
        return JsonResult.buildFailResult(Integer.valueOf(errorCode), result.getResultMsg(), null);
    }


    @RequestMapping("/validateCode")
    public JsonResult validateCode(LoginoutVO loginoutVO) {
        LOGGER.info("validateCode loginoutVO= {}", loginoutVO);

        if (!imageVerifyCodeValidate.validateImageVerifyCode(loginoutVO.getVerifyCode())) {
            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }

        LOGGER.info("validateCode success loginoutVO= {}", loginoutVO);
        return JsonResult.buildSuccessResult("", null);
    }
}
