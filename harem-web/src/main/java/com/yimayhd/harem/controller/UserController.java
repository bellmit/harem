package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.HaMenuService;
import com.yimayhd.harem.service.SendPointRuleService;
import com.yimayhd.harem.service.UsePointRuleService;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.user.client.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 用户信息
 * 
 * @author czf
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private HaMenuService haMenuService;

	/*
	 * @Autowired private MessageCodeService messageCodeService;
	 * 
	 */

	/**
	 * 登录页面
	 * 
	 * @return 登录页面
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/toLogin", method = RequestMethod.GET)
	public String toLogin(User user) throws Exception {
		return "/system/user/login";
	}

	/**
	 * 登录
	 * 
	 * @param user
	 *            用户的信息
	 * @return 成功信息
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/login", method = RequestMethod.POST)
	public String loginTest(User user, Model model) throws Exception {
		System.out.println(user.getUserName());
		System.out.println(user.getPassword());
		System.out.println(user.getGmtCreated());
		List<HaMenuDO> haMenuDOList = haMenuService.getMenuList();
		model.addAttribute("menuList", haMenuDOList);
		return "/system/layout/layout";
	}

	/**
	 * 登录后的欢迎页
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/welcome", method = RequestMethod.GET)
	public String welcome() throws Exception {
		return "/system/welcome";
	}

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param userListQuery 查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/userList", method = RequestMethod.GET)
	public String userList(Model model,UserListQuery userListQuery) throws Exception {
		List<User> userList = userService.getUserList(null);
		model.addAttribute("userListQuery", userListQuery);
		model.addAttribute("userList", userList);
		return "/system/user/list";
	}

	/**
	 * 选择用户列表
	 * 
	 * @param model
	 * @param userListQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/selectUserList", method = RequestMethod.GET)
	public String selectUserList(Model model,UserListQuery userListQuery) throws Exception {
		List<User> userList = userService.getUserList(null);
		model.addAttribute("userListQuery", userListQuery);
		model.addAttribute("userList", userList);
		return "/system/user/selectUserList";
	}
	
	@RequestMapping(value = "/trade/userManage/getUserList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVo getUserList(Model model,UserListQuery userListQuery) throws Exception {
		List<User> userList = userService.getUserList(null);
		return new ResponseVo(userList);
	}

	//商贸部分

	/**
	 * 会员列表
	 * @param model
	 * @param tradeMemberQuery 查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/memberList", method = RequestMethod.GET)
	public String getTradeMemberList(Model model,HttpServletRequest request,TradeMemberQuery tradeMemberQuery) throws Exception {
		//获取当前用户ID
		List<UserDO>  userDOList= userService.getMemberByUserId(tradeMemberQuery);
		model.addAttribute("tradeMemberQuery", tradeMemberQuery);
		model.addAttribute("userList", userDOList);
		return "/system/tradeUser/list";
	}
}
