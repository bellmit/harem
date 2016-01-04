package com.yimayhd.harem.controller;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.harem.base.BaseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.BatchSetUpParameter;
import com.yimayhd.harem.model.Club;
import com.yimayhd.harem.model.ClubAdd;
import com.yimayhd.harem.model.User;
import com.yimayhd.harem.model.query.ClubInfo;
import com.yimayhd.harem.model.query.ClubListQuery;
import com.yimayhd.harem.service.ClubService;
import com.yimayhd.harem.service.UserRPCService;
import com.yimayhd.snscenter.client.domain.ClubInfoDO;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.snscenter.client.dto.ClubDOInfoDTO;
import com.yimayhd.snscenter.client.dto.ClubInfoAddDTO;

//import com.yimayhd.service.MessageCodeService;

/**
 * 俱乐部管理
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/clubManage")
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
	public String list(Model model, ClubDOInfoDTO query, Integer pageNumber,Integer pageSize) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		} else {
			query.setPageNo(BaseQuery.DEFAULT_PAGE);
		}
		if(pageSize!= null) {
			query.setPageSize(pageSize);
		} else{
			query.setPageSize(BaseQuery.DEFAULT_SIZE);
		}
		PageVO<ClubDO> pageVo = clubService.pageQueryClub(query);
		model.addAttribute("clubListQuery", query);
		model.addAttribute("clubList", pageVo.getItemList());
		model.addAttribute("pageVo", pageVo);
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
		ClubInfoDO club = clubService.getClubInfoDOById(id);
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
	@ResponseBody
	public ResponseVo add(ClubAdd clubAdd, @RequestParam("themeIds[]") List<Long> themeIds) throws Exception {
		// String[] rr = request.getParameterValues("themeIds");
		ClubAdd club = clubService.saveOrUpdate(clubAdd, themeIds);
		if (null == club) {
			return new ResponseVo(ResponseStatus.ERROR);
		}
		return new ResponseVo(ResponseStatus.SUCCESS);
	}

	/**
	 * 编辑俱乐部
	 * 
	 * @return 俱乐部详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		ClubInfo club = clubService.getClubInfoDOById(id);
		model.addAttribute("thList", club.getListThemeId());
		model.addAttribute("club", club);
		return "/system/club/edit";
	}

	/**
	 * 编辑俱乐部
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public @ResponseBody ResponseVo edit(@PathVariable(value = "id") long id,ClubAdd club,@RequestParam("themeIds[]") List<Long> themeIds) throws Exception {
		club.setId(id);
		ClubAdd dbClub = clubService.saveOrUpdate(club,themeIds);
		if(null == dbClub){
			return new ResponseVo(ResponseStatus.ERROR);
		}else{
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
	}

	/**
	 * 俱乐部加入状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setJoinStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(BatchSetUpParameter batchSetUpParameter, int status) throws Exception {
		List<Long> ids = null;
		if (null != batchSetUpParameter) {
			ids = batchSetUpParameter.getIds();
		}
		boolean flag = clubService.batchUpOrDownStatus(ids, status);
		if (flag) {
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
		return new ResponseVo(ResponseStatus.ERROR);
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
