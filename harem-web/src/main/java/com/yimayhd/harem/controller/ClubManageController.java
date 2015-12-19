package com.yimayhd.harem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.model.vo.ClubVO;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.service.UserRPCService;

//import com.yimayhd.service.MessageCodeService;

/**
 * 俱乐部管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/clubManage")
public class ClubManageController extends BaseController {
	@Autowired
	private ClubService clubService;

	@Autowired
	private UserRPCService userService;

	/**
	 * 俱乐部列表
	 * 
	 * @return 俱乐部列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ClubListQuery query) throws Exception {
		ClubVO clubVO = new ClubVO();
		clubVO.setClubListQuery(query);
		List<Club> clubList = clubService.getList(clubVO.getClub());
		PageVO<Club> pageVo = new PageVO<Club>(query.getPageNumber(), query.getPageSize(), 14800);
		// pageVo.setCurrentPage(60);

		model.addAttribute("pageVo", pageVo);
		model.addAttribute("clubListQuery", query);
		model.addAttribute("clubList", clubList);

		return "/system/club/list";
	}

	/**
	 * 根据俱乐部ID获取俱乐部详情
	 * 
	 * @return 俱乐部详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		Club club = clubService.getById(id);
		model.addAttribute("club", club);
		return "/system/club/detail";
	}

	/**
	 * 新增俱乐部
	 * 
	 * @return 俱乐部详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/club/edit";
	}

	/**
	 * 新增俱乐部
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Club club) throws Exception {
		clubService.add(club);
		return "/success";
	}

	/**
	 * 编辑俱乐部
	 * 
	 * @return 俱乐部详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		Club club = clubService.getById(id);
		model.addAttribute("club", club);
		return "/system/club/edit";
	}

	/**
	 * 编辑俱乐部
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(Club club) throws Exception {
		clubService.modify(club);
		return new ResponseVo();
	}

	/**
	 * 俱乐部加入状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setJoinStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(@PathVariable(value = "id") long id, int joinStatus) throws Exception {
		Club club = new Club();
		club.setId(id);
		club.setJoinStatus(joinStatus);
		clubService.modify(club);
		return new ResponseVo();
	}

	/**
	 * 查看俱乐部成员
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/clubMember/{clubId}", method = RequestMethod.GET)
	public String clubMemberList(Model model, @PathVariable(value = "clubId") long clubId) throws Exception {
		List<User> userList = userService.getClubMemberListByClubId(clubId);
		model.addAttribute("userList", userList);
		model.addAttribute("clubId", clubId);
		PageVO<User> pageVo = new PageVO<User>(1, 20, 14800);
		// pageVo.setCurrentPage(60);
		model.addAttribute("pageVo", pageVo);
		return "/system/club/memberList";
	}

}
