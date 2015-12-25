package com.yimayhd.harem.controller;

import java.util.ArrayList;

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

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.param.item.ScenicAddNewDTO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.ic.client.model.result.ICResult;

/**
 * 景区管理（资源）
 * 
 * @author xzj
 */
@Controller
@RequestMapping("/B2C/scenicSpotManage")
public class ScenicManageController extends BaseController {
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
	public String list(Model model, ScenicPageQuery scenicPageQuery,Integer pageNumber) throws Exception {
		if(pageNumber!=null){
			scenicPageQuery.setPageNo(pageNumber);
		}
		
		PageVO<ScenicDO> pageVo = scenicSpotService.getList(scenicPageQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("scenicPageQuery", scenicPageQuery);
		return "/system/scenicSpot/list";
	}


	
	
	

	
	/**
	 * 新增景区（资源）
	 * 
	 * @return 景区（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/scenicSpot/edit";
	}
	
	
	/**
	 * 查看景区（资源）
	 * 
	 * @return 景区（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String toView(Model model, @PathVariable(value = "id") long id) throws Exception {
		ScenicAddNewDTO scenicDO = scenicSpotService.getById(id);
		MasterRecommend recommend = null;
		try {	
			recommend  = JSON.parseObject(scenicDO.getScenic().getRecommend(), MasterRecommend.class);;
		} catch (Exception e) {
			logger.error("toEdit|recommend="+scenicDO.getScenic().getRecommend()+"|error="+e.toString());
		}
		model.addAttribute("VO", scenicDO);
		model.addAttribute("recommend", recommend);
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
		ScenicAddNewDTO scenicDO = scenicSpotService.getById(id);
		MasterRecommend recommend = null;
		try {	
			recommend  = JSON.parseObject(scenicDO.getScenic().getRecommend(), MasterRecommend.class);;
		} catch (Exception e) {
			logger.error("toEdit|recommend="+scenicDO.getScenic().getRecommend()+"|error="+e.toString());
		}
		model.addAttribute("VO", scenicDO);
		model.addAttribute("recommend", recommend);
		return "/system/scenicSpot/edit";
	}

	/**
	 * 保存景区（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo save(ScenicAddNewDTO addNewDTO,MasterRecommend recommend) throws Exception {
		ResponseVo responseVo = new ResponseVo();
		try {	
			String jsonString = JSON.toJSONString(recommend);  
			addNewDTO.getScenic().setRecommend(jsonString);
		} catch (Exception e) {
			logger.error("ScenicAddNewDTO  save|error"+e);
		}
		ICResult<ScenicDO> result =scenicSpotService.save(addNewDTO);
		if(result.isSuccess()){
			responseVo.setMessage("添加成功！");
			responseVo.setStatus(ResponseStatus.SUCCESS.VALUE);
		}else{
			responseVo.setMessage(result.getResultMsg());
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}
		
		return responseVo;
	}

	/**
	 * 景区（资源）状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo updateStatus(@PathVariable("id") int id, int scenicStatus) throws Exception {
		scenicSpotService.updateStatus(id,scenicStatus);
		return new ResponseVo();
	}

	/**
	 * 动态状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchupdateStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo batchupdateStatus(@RequestParam("scenicIdList[]") ArrayList<Integer> scenicIdList,
			int scenicStatus) throws Exception {
		scenicSpotService.batchupdateStatus(scenicIdList,scenicStatus);
		return new ResponseVo();
	}
	
	
	

}
