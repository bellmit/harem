package com.yimayhd.harem.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HaRoleDO;
import com.yimayhd.harem.model.HaRoleDetail;
import com.yimayhd.harem.model.query.RoleListQuery;
import com.yimayhd.harem.service.OrderService;
import com.yimayhd.harem.service.SystemManageService;

@Controller
@RequestMapping("/systemManage")
public class SystemManageController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SystemManageController.class);

	@Autowired
	private OrderService orderService;
	
	@Autowired
	SystemManageService systemManageService;

	@RequestMapping(value = "/roleList", method = RequestMethod.GET)
	public String roleListRedirect(Model model) throws Exception {

		RoleListQuery roleListQuery = new RoleListQuery();

		if (roleListQuery.getPageNumber() != null) {
			
			int pageNumber = roleListQuery.getPageNumber();
			int pageSize = roleListQuery.getPageSize();
			int pageBegin = (pageNumber - 1) * pageSize;
			roleListQuery.setPageBegin(pageBegin);
			
		}
		
		PageVO<HaRoleDO> pageVo = systemManageService.getListNew(roleListQuery);
		List<HaRoleDO> roleList = pageVo.getItemList();		
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("roleList", roleList);
		
		return "/system/systemManage/roleList";
	}
	
	@RequestMapping(value = "/roleListPOST", method = RequestMethod.POST)
	public String roleListPost(Model model, RoleListQuery roleListQuery) throws Exception {
				
		if (roleListQuery.getPageNumber() != null) {
			
			int pageNumber = roleListQuery.getPageNumber();
			int pageSize = roleListQuery.getPageSize();
			int pageBegin = (pageNumber - 1) * pageSize;
			roleListQuery.setPageBegin(pageBegin);
			
		}

		PageVO<HaRoleDO> pageVo = systemManageService.getListNew(roleListQuery);		
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("roleListQuery", roleListQuery);
		model.addAttribute("roleList", pageVo.getItemList());
		return "/system/systemManage/roleList";
	}
	
	@RequestMapping(value = "/roleDetail/{roleId}", method = RequestMethod.GET)
	public String roleDetail(Model model, @PathVariable(value = "roleId") long roleId, RoleListQuery roleListQuery) throws Exception {
		
		if (roleListQuery.getPageNumber() != null) {
			
			int pageNumber = roleListQuery.getPageNumber();
			int pageSize = roleListQuery.getPageSize();
			int pageBegin = (pageNumber - 1) * pageSize;
			roleListQuery.setPageBegin(pageBegin);
			
		}

		roleListQuery.setRoleId(roleId);
		PageVO<HaRoleDetail> pageVo = systemManageService.roleDetailById(roleListQuery);
		
		model.addAttribute("roleId", roleId);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("roleListQuery", roleListQuery);
		model.addAttribute("roleDetailList", pageVo.getItemList());
		
		return "/system/systemManage/roleDetail";
	}
	
}
