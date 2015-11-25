package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HaMenuDO;
import com.yimayhd.harem.model.SendPointRule;
import com.yimayhd.harem.model.UsePointRule;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.UserListQuery;
import com.yimayhd.harem.model.vo.UserVO;
import com.yimayhd.harem.service.HaMenuService;
import com.yimayhd.harem.service.SendPointRuleService;
import com.yimayhd.harem.service.UsePointRuleService;
import com.yimayhd.harem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 用户信息
 * 
 * @author czf
 */
@Controller
@RequestMapping("/trade/userManage")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@Autowired
	private HaMenuService haMenuService;

	@Autowired
	private SendPointRuleService sendPointRuleService;

	@Autowired
	private UsePointRuleService usePointRuleService;

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
	@RequestMapping(value = "/toLogin", method = RequestMethod.GET)
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
	@RequestMapping(value = "/login", method = RequestMethod.POST)
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
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() throws Exception {
		return "/system/welcome";
	}

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param pageVo
	 *            分页条件
	 * @param userListQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userList", method = RequestMethod.GET)
	public String userList(Model model,UserListQuery userListQuery) throws Exception {
		UserVO userVO = new UserVO();
		List<User> userList = userService.getUserList(userVO.getUser());
		model.addAttribute("userListQuery", userListQuery);
		model.addAttribute("userList", userList);
		return "/system/user/list";
	}

	/**
	 * 选择用户列表
	 * 
	 * @param model
	 *            分页条件
	 * @param userListQuery
	 *            查询条件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUserList", method = RequestMethod.GET)
	public String selectUserList(Model model,UserListQuery userListQuery) throws Exception {
		UserVO userVO = new UserVO();
		List<User> userList = userService.getUserList(userVO.getUser());
		model.addAttribute("userListQuery", userListQuery);
		model.addAttribute("userList", userList);
		return "/system/user/selectUserList";
	}

	/**
	 * 积分发送规则
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/list", method = RequestMethod.GET)
	public String sendPointRule(Model model) throws Exception {
		SendPointRule sendPointRule = sendPointRuleService.getSendPointRuleNow();
		UsePointRule usePointRule = usePointRuleService.getUsePointRuleNow();
		List<SendPointRule> sendPointRuleList = sendPointRuleService.getSendPointRuleHistory();
		model.addAttribute("sendPointRule", sendPointRule);
		model.addAttribute("usePointRule", usePointRule);
		model.addAttribute("sendPointRuleList", sendPointRuleList);
		return "/system/user/sendPointRule";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/toAdd", method = RequestMethod.GET)
	public String sendPointRuleToAdd() throws Exception {
		return "/system/user/sendPointRuleAdd";
	}

	/**
	 * 新增积分发送规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/sendPointRule/add", method = RequestMethod.POST)
	public String sendPointRuleAdd(SendPointRule sendPointRule) throws Exception {
		sendPointRuleService.add(sendPointRule);
		return "/success";
	}

	/* *//**
			 * 查询用户的信息
			 *
			 * @param id
			 *            用户的主键
			 * @return 用户的信息
			 * @throws Exception
			 *//*
			 * @RequestMapping(value = "/{id}", method = RequestMethod.GET)
			 * public
			 * 
			 * @ResponseBody ResponseVo getById(@PathVariable("id") String id)
			 * throws Exception { return new
			 * ResponseVo(userService.getById(id)); }
			 */

	/* *//**
			 * 分页条件查询用户列表
			 *
			 * @param user
			 *            条件
			 * @param vo
			 *            分页信息
			 * @return 用户列表
			 * @throws Exception
			 */
	/*
	 * @RequestMapping(method = RequestMethod.GET) public
	 * 
	 * @ResponseBody ResponseVo index(User user, PageVo<User> vo) throws
	 * Exception { vo.setEntity(user); vo.setList(userService.getList(vo));
	 * vo.setSum(userService.getCount(user)); return new ResponseVo(vo); }
	 * 
	 * 
	 * 
	 *//**
		 * 获取用户基本信息
		 * 
		 * @param id
		 *            用户ID
		 * @return 用户信息
		 * @throws Exception
		 *//*
		 * @RequestMapping(value = "/detail/{id}",method = RequestMethod.GET)
		 * 
		 * @ResponseBody public ResponseVo getUserDetail(@PathVariable String
		 * id)throws Exception{
		 * 
		 * User user = userService.getUserDetail(id); return new
		 * ResponseVo(user); }
		 */
	/**
	 * 通过验证码修改密码
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 *//*
		 * @RequestMapping(value = "/passwordModify",method =
		 * RequestMethod.POST) public
		 * 
		 * @ResponseBody ResponseVo passwordModify(@RequestBody User user)
		 * throws Exception { long total =
		 * userService.getCountByTel(user.getTel()); if (total < 1) { throw new
		 * NoticeException("此用户不存在。"); }
		 * if(messageCodeService.checkCode(user.getTel(),user.getCode()) > 0) {
		 * if(user.getPassword()!=null&&!user.getPassword().equals("")) { //修改用户
		 * userService.passwordModify(user); return new ResponseVo(user); }else{
		 * throw new NoticeException("密码不能为空！"); } }else{ throw new
		 * NoticeException("验证码不正确或已过期！"); } }
		 */

}
