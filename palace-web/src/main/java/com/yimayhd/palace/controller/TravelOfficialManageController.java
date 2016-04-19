package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.TravelOfficial;
import com.yimayhd.palace.model.query.TravelOfficialListQuery;
import com.yimayhd.palace.model.travel.SnsTravelSpecialtyVO;
import com.yimayhd.palace.model.vo.TravelOfficialVO;
import com.yimayhd.palace.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.TravelJsonDO;

@Controller
@RequestMapping("/B2C/travelOfficialManage")
public class TravelOfficialManageController extends BaseController {
	@Autowired
	private TravelOfficialService travelOfficialService;
	
	/**
	 * 官方游记列表
	 * 
	 * @return 官方游记列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, TravelOfficialListQuery travelOfficialListQuery) throws Exception {
		TravelOfficialVO travelOfficialVO = new TravelOfficialVO();
		travelOfficialVO.setTravelOfficialListQuery(travelOfficialListQuery);
		PageVO<SnsTravelSpecialtyVO> pageVo = travelOfficialService.getList(travelOfficialListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("travelOfficialListQuery", travelOfficialListQuery);
		model.addAttribute("travelOfficialList", pageVo.getItemList());
		return "/system/travelOfficial/list";
	}



	/**
	 * 根据官方游记ID获取官方游记详情
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getById(Model model, @PathVariable(value = "id") long id) throws Exception {
		TravelOfficial travelOfficial = travelOfficialService.getById(id);
		model.addAttribute("travelOfficial", travelOfficial);
		return "/system/travelOfficial/detail";
	}

	/**
	 * 新增官方游记
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd() throws Exception {
		return "/system/travelOfficial/detail";
	}

	/**
	 * 新增官方游记
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(TravelOfficial travelOfficial) throws Exception {
		updateTravelJson(travelOfficial);
		TravelOfficial db = travelOfficialService.add(travelOfficial);
		if(null == db ){
			return new ResponseVo(ResponseStatus.ERROR);	
		}
		return new ResponseVo(ResponseStatus.SUCCESS);
	}

	private void updateTravelJson(TravelOfficial travelOfficial){
		if( travelOfficial == null ){
			return;
		}
		String imgContentJson = travelOfficial.getImgContentJson();
		List<TravelJsonDO> contents = JSON.parseArray(imgContentJson, TravelJsonDO.class);
		
		if( CollectionUtils.isEmpty(contents) ){
			return ;
		}
		for( TravelJsonDO travelJsonDO : contents ){
			
			List<String> imgs = travelJsonDO.getTravelImg();
			if( !CollectionUtils.isEmpty(imgs) ){
				List<String> rs = new ArrayList<String>() ;
				for( String img : imgs){
					if( StringUtils.isNoneBlank(img) ){
						img = img.replace("[", "").replace("]", "") ;
						rs.add(img);
					}
				}
				travelJsonDO.setTravelImg(rs);
			}
		}
		String imgContentJsonResult = JSON.toJSONString(contents);
		travelOfficial.setImgContentJson(imgContentJsonResult);
	}
	
	/**
	 * 编辑官方游记
	 * 
	 * @return 官方游记详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {
		TravelOfficial travelOfficial = travelOfficialService.getById(id);
		System.err.println("get="+JSON.toJSONString(travelOfficial));
//		updateTravelJson(travelOfficial);
		model.addAttribute("travelOfficial", travelOfficial);
		return "/system/travelOfficial/detail";
	}

	/**
	 * 编辑官方游记
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(@PathVariable(value = "id") long id,TravelOfficial travelOfficial) throws Exception {
		travelOfficial.setId(id);

		updateTravelJson(travelOfficial);
		System.err.println("save="+JSON.toJSONString(travelOfficial));
		boolean flag = travelOfficialService.modify(travelOfficial);
		if(flag){
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

	/**
	 * 官方游记状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setJoinStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(@RequestParam("ids[]") ArrayList<Long> ids,int status) throws Exception {
		if(CollectionUtils.isEmpty(ids)){
			return new ResponseVo(ResponseStatus.INVALID_DATA);
		}
		boolean flag = travelOfficialService.batchUpOrDownStatus(ids, status);
		if(flag){
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}

}
