//package com.yimayhd.palace.controller;
//
//import java.util.List;
//
//import com.yimayhd.palace.base.BaseController;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.yimayhd.commentcenter.client.domain.ComTagDO;
//import com.yimayhd.commentcenter.client.dto.TagNameTypeDTO;
//import com.yimayhd.palace.base.PageVO;
//import com.yimayhd.palace.base.ResponseVo;
//import com.yimayhd.palace.constant.ResponseStatus;
//import com.yimayhd.palace.model.ThemeVo;
//import com.yimayhd.palace.model.query.ThemeVoQuery;
//import com.yimayhd.palace.service.ThemeService;
//import com.yimayhd.commentcenter.client.enums.TagType;
//
///** 
//* @ClassName: ThemeManageController 
//* @Description: 主题管理。包含俱乐部主题和活动主题的管理及设置
//* @author create by yushengwei @ 2015年12月1日 上午10:07:56 
//*/
//@Controller
//@RequestMapping("/B2C/themeManage")
//public class ThemeManageController extends BaseController {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeManageController.class);
//	
//	@Autowired
//	private ThemeService themeService;
//	
//	/**
//	* @Title: list 
//	* @Description:(主题列表) 
//	* @author create by yushengwei @ 2015年12月1日 上午10:10:15 
//	* @param @param model
//	* @param @param query
//	* @param @return
//	* @param @throws Exception 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list(Model model, ThemeVoQuery query){
//		try {
//			PageVO<ComTagDO> pageVo = themeService.getPageTheme(query);
//			TagType[] tagTypeList = TagType.values();
//			put("tagTypeList", tagTypeList);
//	        model.addAttribute("themeVoQuery", query);
//			model.addAttribute("themeList", pageVo.getItemList());
//			model.addAttribute("pageVo", pageVo);
//			return "/system/theme/list";
//		} catch (Exception e) {
//			LOGGER.error(">>>>", e);
//			return "/error";
//		}
//	}
//	
//	@RequestMapping(value = "/list/json", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseVo jsonList(int type){
//			try {
//				List<ComTagDO> list = themeService.getListTheme(type);
//				if (CollectionUtils.isNotEmpty(list)) {
//					return new ResponseVo(list);
//				} 
//			} catch (Exception e) {
//				LOGGER.error("jsonList error,query="+type,e);
//			}
//				return new ResponseVo(ResponseStatus.ERROR);
//	}
//	
//	
//	/**
//	* @Title: detail 
//	* @Description:(主题详情) 
//	* @author create by yushengwei @ 2015年12月1日 下午4:39:22 
//	* @param @param model
//	* @param @param id
//	* @param @return 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public String detail(Model model, @PathVariable(value = "id") long id) {
//		try {
//			model.addAttribute("theme", themeService.getById(1));
//			return "/system/theme/detail";
//		} catch (Exception e) {
//			LOGGER.error(">>>>", e);
//			return "/error";
//		}
//	}
//	
//	/**
//	* @Title: toEdit 
//	* @Description:(编辑主题) 
//	* @author create by yushengwei @ 2015年12月1日 下午4:39:57 
//	* @param @param model
//	* @param @param id
//	* @param @return 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
//	public String toEdit(Model model, @PathVariable(value = "id") long id){
//		try {
//			ThemeVo theme = themeService.getById(id);
//			model.addAttribute("theme", theme);
//			return "/system/theme/edit";
//		} catch (Exception e) {
//			LOGGER.error(">>>>", e);
//			return "/error";
//		}
//	}
//	
//	/**
//	* @Title: edit 
//	* @Description:(保存编辑主题) 
//	* @author create by yushengwei @ 2015年12月1日 下午4:40:19 
//	* @param @param themeVo
//	* @param @return 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
//	public String edit(ThemeVo themeVo, @PathVariable(value = "id") long id){
//		try {
//			themeVo.setId(id);
//			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
//			if(null != dbThemeVo ){
//				return "/success";	
//			}
//		} catch (Exception e) {
//			LOGGER.error(">>>>", e);
//		}
//		return "/error";
//	}
//
//
//	/**
//	* @Title: toAdd 
//	* @Description:(新增主题) 
//	* @author create by yushengwei @ 2015年12月1日 下午4:40:37 
//	* @param @return 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
//	public String toAdd() {
//		return "/system/theme/edit";
//	}
//
//	/**
//	* @Title: add 
//	* @Description:(新增主题) 
//	* @author create by yushengwei @ 2015年12月1日 下午4:40:57 
//	* @param @param themeVo
//	* @param @return 
//	* @return String 返回类型 
//	* @throws
//	 */
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseVo add(ThemeVo themeVo) {
//		ResponseVo response = new ResponseVo();
//		try {
//			TagNameTypeDTO tagNameTypeDTO = new TagNameTypeDTO();
//			tagNameTypeDTO.setDomain(themeVo.getDomain());
//			tagNameTypeDTO.setName(themeVo.getName());
//			tagNameTypeDTO.setOutType("LIVESUPTAG");
//			ComTagDO comTagDO = themeService.getTagByName(tagNameTypeDTO);
//			if (comTagDO != null) {
//				//return "/error";
//				response.setMessage("数据重复！");
//				response.setStatus(ResponseStatus.ERROR.VALUE);
//				return response;
//			}
//			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
//			if (null != dbThemeVo) {
//				//return "/success";
//				response.setMessage("添加成功！");
//				response.setStatus(ResponseStatus.SUCCESS.VALUE);
//				return response;
//			}
//		} catch (Exception e) {
//			LOGGER.error(">>>>", e);
//			return ResponseVo.error(e);
//		}
//		return response;
//	}
//	
//	@RequestMapping(value = "/checkTagName", method = RequestMethod.POST)
//	@ResponseBody
//	public ResponseVo checkTagName(String name){
//		ResponseVo response = new ResponseVo();
//		final int domain = 1200;
//		if(StringUtils.isBlank(name)){
//			return new ResponseVo(ResponseStatus.ERROR);
//		}
//		TagNameTypeDTO tagNameTypeDTO = new TagNameTypeDTO();
//		tagNameTypeDTO.setDomain(domain);
//		tagNameTypeDTO.setName(name);
//		tagNameTypeDTO.setOutType("LIVESUPTAG");
//		ComTagDO comTagDO = themeService.getTagByName(tagNameTypeDTO);
//		if( null == comTagDO ){
//			response.setData("success");
//			return response;
//		}
//		response.setData("faile");
//		return response;
//	} 
//}

package com.yimayhd.palace.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.palace.base.BaseController;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagNameTypeDTO;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.ThemeVo;
import com.yimayhd.palace.model.query.ThemeVoQuery;
import com.yimayhd.palace.service.ThemeService;

/** 
* @ClassName: ThemeManageController 
* @Description: 主题管理。包含俱乐部主题和活动主题的管理及设置
* @author create by yushengwei @ 2015年12月1日 上午10:07:56 
*/
@Controller
@RequestMapping("/B2C/themeManage")
public class ThemeManageController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThemeManageController.class);
	
	@Autowired
	private ThemeService themeService;
	
	/**
	* @Title: list 
	* @Description:(主题列表) 
	* @author create by yushengwei @ 2015年12月1日 上午10:10:15 
	* @param @param model
	* @param @param query
	* @param @return
	* @param @throws Exception 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, ThemeVoQuery query){
		try {
			PageVO<ComTagDO> pageVo = themeService.getPageTheme(query);
			model.addAttribute("themeListQuery", query);
			model.addAttribute("themeList", pageVo.getItemList());
			model.addAttribute("pageVo", pageVo);
			return "/system/theme/list";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	@RequestMapping(value = "/list/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo jsonList(int type){
			try {
				List<ComTagDO> list = themeService.getListTheme(type);
				if (CollectionUtils.isNotEmpty(list)) {
					return new ResponseVo(list);
				} 
			} catch (Exception e) {
				LOGGER.error("jsonList error,query="+type,e);
			}
				return new ResponseVo(ResponseStatus.ERROR);
	}
	
	
	/**
	* @Title: detail 
	* @Description:(主题详情) 
	* @author create by yushengwei @ 2015年12月1日 下午4:39:22 
	* @param @param model
	* @param @param id
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(Model model, @PathVariable(value = "id") long id) {
		try {
			model.addAttribute("theme", themeService.getById(1));
			return "/system/theme/detail";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	/**
	* @Title: toEdit 
	* @Description:(编辑主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:39:57 
	* @param @param model
	* @param @param id
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id){
		try {
			ThemeVo theme = themeService.getById(id);
			model.addAttribute("theme", theme);
			return "/system/theme/edit";
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return "/error";
		}
	}
	
	/**
	* @Title: edit 
	* @Description:(保存编辑主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:19 
	* @param @param themeVo
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String edit(ThemeVo themeVo, @PathVariable(value = "id") long id){
		try {
			themeVo.setId(id);
			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
			if(null != dbThemeVo ){
				return "/success";	
			}
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
		}
		return "/error";
	}


	/**
	* @Title: toAdd 
	* @Description:(新增主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:37 
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() {
		return "/system/theme/edit";
	}

	/**
	* @Title: add 
	* @Description:(新增主题) 
	* @author create by yushengwei @ 2015年12月1日 下午4:40:57 
	* @param @param themeVo
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(ThemeVo themeVo) {
		ResponseVo response = new ResponseVo();
		try {
			TagNameTypeDTO tagNameTypeDTO = new TagNameTypeDTO();
			tagNameTypeDTO.setDomain(themeVo.getDomain());
			tagNameTypeDTO.setName(themeVo.getName());
			//tagNameTypeDTO.setOutType("LIVESUPTAG");
			tagNameTypeDTO.setOutType(TagType.getByType( themeVo.getOutType()).name());
			ComTagDO comTagDO = themeService.getTagByName(tagNameTypeDTO);
			if (comTagDO != null) {
				//return "/error";
				response.setMessage("数据重复！");
				response.setStatus(ResponseStatus.ERROR.VALUE);
				return response;
			}
			ThemeVo dbThemeVo = themeService.saveOrUpdate(themeVo);
			if (null != dbThemeVo) {
				//return "/success";
				response.setMessage("添加成功！");
				response.setStatus(ResponseStatus.SUCCESS.VALUE);
				return response;
			}
		} catch (Exception e) {
			LOGGER.error(">>>>", e);
			return ResponseVo.error(e);
		}
		return response;
	}
	
	@RequestMapping(value = "/checkTagName", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo checkTagName(String name){
		ResponseVo response = new ResponseVo();
		final int domain = 1200;
		if(StringUtils.isBlank(name)){
			return new ResponseVo(ResponseStatus.ERROR);
		}
		TagNameTypeDTO tagNameTypeDTO = new TagNameTypeDTO();
		tagNameTypeDTO.setDomain(domain);
		tagNameTypeDTO.setName(name);
		tagNameTypeDTO.setOutType("LIVESUPTAG");
		ComTagDO comTagDO = themeService.getTagByName(tagNameTypeDTO);
		if( null == comTagDO ){
			response.setData("success");
			return response;
		}
		response.setData("faile");
		return response;
	} 
	
	/**
	 * 下架主题
	 * @param themeId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/themeOff", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo themeOff(long themeId, HttpServletRequest request) throws Exception {
		boolean flag = themeService.themeOff(themeId);
		if(flag){
			return new ResponseVo();
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
}
