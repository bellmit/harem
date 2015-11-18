package com.yimayhd.harem.controller.loginout;


import com.yimayhd.harem.controller.loginout.vo.LoginoutVO;
import com.yimayhd.user.api.LoginServiceHttpExport;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
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

    @RequestMapping("/login")
    public JsonResult login(LoginoutVO loginoutVO) {
        LOGGER.info("loginoutVO= {}", loginoutVO);

        String validateCode = SessionUtils.getValidateCode();
        if (StringUtils.isBlank(validateCode) ||
                !SessionUtils.getValidateCode().equalsIgnoreCase(loginoutVO.getVerifyCode())) {
            LOGGER.warn("SessionUtils.getValidateCode() = {}, but loginoutVO.getVerifyCode() = {}",
                    validateCode, loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }

        BaseResult<Long> result = importUserService.login(loginoutVO.getUsername(), loginoutVO.getPassword());
        String errorCode = result.getErrorCode();
        if (Integer.valueOf(AbstractReturnCode._C_SUCCESS) == Integer.valueOf(errorCode)) {
            LOGGER.info("loginoutVO= {} login success and userId = {}", loginoutVO, result.getValue());
            SessionUtils.setUserId(result.getValue().toString());
            return JsonResult.buildSuccessResult(result.getResultMsg(), null);
        }

        LOGGER.warn("loginoutVO= {} login fail and msg = {}", loginoutVO, result.getResultMsg());
        return JsonResult.buildFailResult(Integer.valueOf(errorCode), result.getResultMsg(), null);
    }
}
