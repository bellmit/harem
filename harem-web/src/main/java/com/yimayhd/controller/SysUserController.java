package com.yimayhd.controller;

import com.yimayhd.base.BaseController;
import com.yimayhd.base.PageVo;
import com.yimayhd.base.ResponseVo;
import com.yimayhd.exception.NoticeException;
import com.yimayhd.model.SysUser;
import com.yimayhd.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员信息
 * @author liuhaiming
 */
@Controller
@RequestMapping("/sysusers")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 增加管理员
     *
     * @param sysUser 管理员信息
     * @return 成功信息
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseVo save(@RequestBody SysUser sysUser) throws Exception {
        // 用户名唯一
        int countUsername = sysUserService.countByUsername(sysUser.getUsername());
        if (countUsername > 0) {
            throw new NoticeException("用户名已经存在！");
        } else {
            sysUserService.add(sysUser);
        }
        return new ResponseVo();
    }




    /**
     * 根据主键查询用户信息
     *
     * @param id 用户的主键
     * @return 用户的信息
     * @throws Exception
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseVo getById(@PathVariable("id") String id) throws Exception {
        return new ResponseVo(sysUserService.getById(id));
    }

    /**
     * 分页条件查询管理员信息
     *
     * @param sysUser 管理员信息
     * @param vo      分页条件
     * @return 用户信息
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseVo index(SysUser sysUser, PageVo<SysUser> vo) throws Exception {
        vo.setEntity(sysUser);
        vo.setList(sysUserService.getList(vo));
        vo.setTotalSum(sysUserService.getCount(sysUser));
        return new ResponseVo(vo);
    }

    /**
     * 登录
     *
     * @param sysUser 用户信息
     * @return 成功信息
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseVo login(@RequestBody SysUser sysUser) throws Exception {

        SysUser _sysUser = sysUserService.login(sysUser);
        if (_sysUser == null) {
            throw new NoticeException("用户名或密码错误！");
        }
        return new ResponseVo(_sysUser);
    }

}
