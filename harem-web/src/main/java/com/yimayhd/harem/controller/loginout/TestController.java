package com.yimayhd.harem.controller.loginout;

import com.yimayhd.harem.controller.loginout.vo.LoginoutVO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.enums.UserType;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.SessionUtils;
import net.pocrd.util.Md5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15-11-9.
 */
@RestController
public class TestController {

    @Resource
    private UserService userServiceRef;

    @RequestMapping("/test/test1")
    public Map<String, Object> testOne() {
        Map<String, Object> map = new HashMap<String, Object>();

        HttpServletRequest httpServletRequest = SessionUtils.getRequest();
        if (null == httpServletRequest) {
            return map;
        }

        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setAttribute("user","123");

        map.put("status", "123");
        return map;
    }

    @RequestMapping("/test/test2")
    public Map<String, Object> testTwo() {
        Map<String, Object> map = new HashMap<String, Object>();

        HttpServletRequest httpServletRequest = SessionUtils.getRequest();
        if (null == httpServletRequest) {
            return map;
        }

        HttpSession httpSession = httpServletRequest.getSession();
        Object test = httpSession.getAttribute("user");
        if (null == test) {
            map.put("status", "not login");
            return map;
        }

        map.put("status", "has logined");
        return map;
    }

    @RequestMapping("/extern/test/createUser")
    public Map<String, Object> createUser(LoginoutVO loginoutVO) {
        Map<String, Object> map = new HashMap<String, Object>();

        UserDO userDO = new UserDO();
        userDO.setMobile(loginoutVO.getUsername());
        userDO.setPassword(loginoutVO.getPassword());

        BaseResult<UserDO> baseResult = userServiceRef.createUserAndPutCache(userDO);


        map.put("status", "create success");
        map.put("baseResult", baseResult);
        return map;
    }
}
