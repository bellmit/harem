package com.yimayhd.harem.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.query.TradeMemberQuery;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.service.UserService;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.session.manager.SessionUtils;

/**
 * 用户信息
 * 
 * @author czf
 */
@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param userListQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/userList", method = RequestMethod.GET)
	public String userList(Model model, UserListQuery userListQuery) throws Exception {
		// List<User> userList = userService.getUserList(null);
		model.addAttribute("userListQuery", userListQuery);
		// model.addAttribute("userList", userList);
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
	public String selectUserList(Model model, UserListQuery userListQuery) throws Exception {
		// List<User> userList = userService.getUserList(null);
		model.addAttribute("userListQuery", userListQuery);
		// model.addAttribute("userList", userList);
		return "/system/user/selectUserList";
	}

	@RequestMapping(value = "/trade/userManage/getUserList", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVo getUserList(Model model, UserListQuery userListQuery) throws Exception {
		// List<User> userList = userService.getUserList(null);
		return new ResponseVo(null);
	}

	// 商贸部分

	/**
	 * 会员列表
	 * 
	 * @param model
	 * @param tradeMemberQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade/userManage/memberList", method = RequestMethod.GET)
	public String getTradeMemberList(Model model, HttpServletRequest request, TradeMemberQuery tradeMemberQuery)
			throws Exception {

		long sellerId = Long.parseLong(SessionUtils.getUserId());
		PageVO<UserDO> pageVO = userService.getMemberByUserId(sellerId, tradeMemberQuery);
		model.addAttribute("tradeMemberQuery", tradeMemberQuery);
		model.addAttribute("userList", pageVO.getItemList());
		model.addAttribute("pageVo", pageVO);
		return "/system/tradeUser/list";
	}
}
