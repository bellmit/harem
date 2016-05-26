package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.model.ScenicAddVO;
import com.yimayhd.palace.model.ScenicVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.ScenicListQuery;
import com.yimayhd.palace.service.ScenicService;

/**
 * 景区管理（资源）
 * 
 * @author xzj
 */
@Controller
@RequestMapping("/jiuniu/scenicSpotManage")
public class JiuniuScenicManageController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ScenicManageController.class);
	@Autowired
	private ScenicService scenicSpotService;
	

	/**
	 * 景区（资源）列表
	 * 
	 * @return 景区（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ScenicListQuery scenicListQuery) throws Exception {
		PageVO<ScenicDO> pageVo = scenicSpotService.getList(scenicListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("scenicPageQuery", scenicListQuery);
		return "/system/scenicSpot/list-jiuniu";
	}
	
	/**
	 * 新增景区（资源）
	 * 
	 * @return 景区（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		
		List<ComTagDO> allLineThemes = scenicSpotService.getAllLineThemes();
		put("themes", allLineThemes);
		return "/system/scenicSpot/edit-jiuniu";
	}
	
	/**
	 * 查看景区（资源）
	 * 
	 * @return 景区（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String toView(Model model, @PathVariable(value = "id") long id) throws Exception {
		ScenicVO scenicVO = scenicSpotService.getById(id);
		model.addAttribute("VO", scenicVO);
		model.addAttribute("pictureList", scenicVO.getPictureList());
		return "/system/scenicSpot/view";
	}

	/**
	 * 编辑景区（资源）
	 * 
	 * @return 景区（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		ScenicAddVO scenicAddVO = scenicSpotService.getDetailById(id);
		model.addAttribute("VO", scenicAddVO);
		
		List<ComTagDO> allLineThemes = scenicSpotService.getAllLineThemes();
		put("themes", allLineThemes);
		return "/system/scenicSpot/edit-jiuniu";
	}

	/**
	 * 保存景区（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(ScenicAddVO scenicAddVO) throws Exception {
		try {
			ICResult<ScenicDO> result = scenicSpotService.save(scenicAddVO);
			return  ResponseVo.success(result.getModule());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}

	/**
	 * enableStatus
	 * 启用
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/enableStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo enableStatus(@PathVariable("id") long id) throws Exception {
		try {
			boolean enableScenicItem = scenicSpotService.enableScenic(id);
			return ResponseVo.success(enableScenicItem);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
		
	}
	
	/**
	 * 停用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/disableStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo disableStatus(@PathVariable("id") int id) throws Exception {
		scenicSpotService.disableScenicItem(id);
		return new ResponseVo();
	}

	/**
	 * 启用
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchEnableStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchEnableStatus(@RequestParam("scenicIdList[]") ArrayList<Integer> scenicIdList) throws Exception {
		scenicSpotService.batchEnableStatus(scenicIdList);
		return new ResponseVo();
	}
	
	
	/**
	 * 动态状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchDisableStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchDisableStatus(@RequestParam("scenicIdList[]") ArrayList<Integer> scenicIdList) throws Exception {
		scenicSpotService.batchDisableStatus(scenicIdList);
		return new ResponseVo();
	}
	
	/**
	 * 保存景区图文详情（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePictureText/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo savePictureText(@PathVariable("id") long id, String json) throws Exception {
		try {
			
			if (StringUtils.isBlank(json)) {
				log.warn("json is null");
				return ResponseVo.error();
			}
			
			json = json.replaceAll("\\s*\\\"\\s*", "\\\"");
			PictureTextVO pictureTextVO = (PictureTextVO) JSONObject.parseObject(json, PictureTextVO.class);
			
			scenicSpotService.savePictureText(id, pictureTextVO);
			return  ResponseVo.success();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
}
