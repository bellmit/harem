package com.yimayhd.palace.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.controller.loginout.vo.LoginoutVO;
import com.yimayhd.palace.model.HaMenuDO;
import com.yimayhd.palace.service.HaMenuService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.result.login.LoginResult;
import com.yimayhd.user.client.service.UserService;
import com.yimayhd.user.session.manager.ImageVerifyCodeValidate;
import com.yimayhd.user.session.manager.JsonResult;
import com.yimayhd.user.session.manager.SessionManager;

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
    @Autowired
    private SessionManager sessionManager;



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
    public JsonResult login(LoginoutVO loginoutVO, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("login loginoutVO= {}", loginoutVO);
        sessionManager.removeToken(request);

        /*if (!imageVerifyCodeValidate.validateImageVerifyCode(loginoutVO.getVerifyCode())) {
            LOGGER.warn("loginoutVO.getVerifyCode() = {} is not correct", loginoutVO.getVerifyCode());
            return JsonResult.buildFailResult(1, "验证码错误!", null);
        }*/

//        BaseResult<UserDO> result = userServiceRef.login(loginoutVO.getUsername(), loginoutVO.getPassword());
        LoginResult result = userServiceRef.loginV2(loginoutVO.getUsername(), loginoutVO.getPassword());
        int errorCode = result.getErrorCode();
        if (Integer.valueOf(AbstractReturnCode._C_SUCCESS).equals(Integer.valueOf(errorCode))) {
            LOGGER.info("loginoutVO= {} login success and userId = {}", loginoutVO, result.getValue());
            String token = result.getToken();
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            //FIXME
//            SessionUtils.setUserId(String.valueOf(result.getValue().getId()));
//            HttpSession httpSession = request.getSession();
//            Object userIdObject = httpSession.getAttribute("userId");
//            httpSession.setAttribute("userNickName", result.getValue().getNickname());
           
            return JsonResult.buildSuccessResult(result.getResultMsg(), null);
        }
        
        
        LOGGER.warn("loginoutVO= {} login fail and msg = {}", loginoutVO, result.getResultMsg());
        return JsonResult.buildFailResult(Integer.valueOf(errorCode), result.getResultMsg(), null);
    }
    
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo logout(HttpServletRequest request,Model model) {
    	sessionManager.removeToken(request);
    	
//        String userIdStr = SessionUtils.getUserId();
//
//        if(StringUtils.isBlank(userIdStr)){
//            //没有去到userId，直接返回成功
//            return new ResponseVo();
//        }
//        long userId = Long.parseLong(userIdStr) ;
//        SessionUtils.removeUserId();
        return new ResponseVo();
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
//        long userId = Long.parseLong(SessionUtils.getUserId()) ;
    	UserDO user = sessionManager.getUser();
    	if( user == null ){
    		//FIXME 曹张锋
    	}
        List<HaMenuDO> haMenuDOList = haMenuService.getMenuListByUserId(user.getId());
        model.addAttribute("menuList", haMenuDOList);
        model.addAttribute("userNickName", user.getNick());
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
