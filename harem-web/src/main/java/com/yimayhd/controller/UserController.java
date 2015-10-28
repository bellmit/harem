package com.yimayhd.controller;

import com.yimayhd.base.BaseController;
import com.yimayhd.base.PageVo;
import com.yimayhd.base.ResponseVo;
import com.yimayhd.exception.NoticeException;
import com.yimayhd.model.Menu;
import com.yimayhd.model.User;
import com.yimayhd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.yimayhd.service.MessageCodeService;

/**
 * 用户信息
 * @author czf
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

   /* @Autowired
    private MessageCodeService messageCodeService;

    */

    /**
     * 登录页面
     * @return 登录页面
     * @throws Exception
     */
    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public
    String toLogin(User user) throws Exception {
        return "/system/user/login";
    }
    /**
     * 登录
     * @param user 用户的信息
     * @return 成功信息
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    String loginTest(User user,Model model) throws Exception {
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        long userId = 1;
        List<Menu> menuList = new ArrayList<Menu>();

        Menu menu = new Menu(1,"交易管理","/tradeManage",1,0);
        List<Menu> menuListSub = new ArrayList<Menu>();
        menuListSub.add(new Menu(7,"交易流水","/tradeManage/list",2,1));
        menuListSub.add(new Menu(8,"退款记录","/refundManage/list",2,1));
        menu.setMenuList(menuListSub);

        Menu menu2 = new Menu(1,"会员管理","/userManage",1,0);
        menuList.add(menu);
        menuList.add(menu2);
        model.addAttribute("menuList", menuList);

        return "/system/layout/layout";
    }
    /**
     * 登录后的欢迎页
     * @throws Exception
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public
    String welcome() throws Exception {
        return "/system/welcome";
    }

    /**
     * 增加用户
     *
     * @param user 用户信息
     * @return 成功信息
     * @throws Exception
     *//*
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseVo save(@RequestBody User user) throws Exception {
        //默认性别
        user.setGender(3);
        if(messageCodeService.checkCode(user.getTel(),user.getCode()) > 0) {
            if(user.getPassword()!=null&&!user.getPassword().equals("")) {
                if(userService.getCountByTel(user.getTel())>0){
                    //验证唯一性
                    throw new NoticeException("此电话已注册！");
                }else {
                    //保存用户
                    userService.add(user);
                    return new ResponseVo(user);
                }
            }else{
                throw new NoticeException("密码不能为空！");
            }
        }else{
            throw new NoticeException("验证码不正确或已过期！");
        }
    }*/


   /* *//**
     * 查询用户的信息
     *
     * @param id 用户的主键
     * @return 用户的信息
     * @throws Exception
     *//*
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseVo getById(@PathVariable("id") String id) throws Exception {
        return new ResponseVo(userService.getById(id));
    }*/

   /* *//**
     * 分页条件查询用户列表
     *
     * @param user 条件
     * @param vo   分页信息
     * @return 用户列表
     * @throws Exception
     *//*
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseVo index(User user, PageVo<User> vo) throws Exception {
        vo.setEntity(user);
        vo.setList(userService.getList(vo));
        vo.setSum(userService.getCount(user));
        return new ResponseVo(vo);
    }

    *//**
     *
     * @param tel 注册电话检查
     * @return 是否已注册（0：未注册；1：已注册）
     * @throws Exception
     *//*
    @RequestMapping(value = "/tel/{tel}",method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseVo getUserBytel(@PathVariable("tel") String tel) throws Exception {
        //返回结果
        int success = 0;
        Map<String,String> hp = new HashMap<String,String>();
        //错误信息
        long total = userService.getCountByTel(tel);
        if(total>0){
            success = 1;
            //throw  new NoticeException("电话号码已注册");
        }
        hp.put("signup", String.valueOf(success));
        return new ResponseVo(hp);
    }

    *//**
     *
     * @param user 条件参数
     * @return 用户
     * @throws Exception
     *//*
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseVo login(@RequestBody User user) throws Exception {

        User _user = userService.login(user);

        if (_user == null) {
            //错误信息
            throw new NoticeException("用户名或密码错误！");
        }
        return new ResponseVo(_user);
    }

    *//**
     * 获取用户基本信息
     * @param id 用户ID
     * @return 用户信息
     * @throws Exception
     *//*
    @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseVo getUserDetail(@PathVariable String id)throws Exception{

        User user  = userService.getUserDetail(id);
        return new ResponseVo(user);
    }
*/
    /**
     * 通过验证码修改密码
     * @param user
     * @return
     * @throws Exception
     *//*
    @RequestMapping(value = "/passwordModify",method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseVo passwordModify(@RequestBody User user) throws Exception {
        long total = userService.getCountByTel(user.getTel());
        if (total < 1) {
            throw  new NoticeException("此用户不存在。");
        }
        if(messageCodeService.checkCode(user.getTel(),user.getCode()) > 0) {
            if(user.getPassword()!=null&&!user.getPassword().equals("")) {
                //修改用户
                userService.passwordModify(user);
                return new ResponseVo(user);
            }else{
                throw new NoticeException("密码不能为空！");
            }
        }else{
            throw new NoticeException("验证码不正确或已过期！");
        }
    }*/

}
