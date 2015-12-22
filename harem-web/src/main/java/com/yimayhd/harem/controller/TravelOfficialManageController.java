package com.yimayhd.harem.controller;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Activity;
import com.yimayhd.harem.model.Order;
import com.yimayhd.harem.model.TravelOfficial;
import com.yimayhd.harem.model.query.ActivityListQuery;
import com.yimayhd.harem.model.query.TravelOfficialListQuery;
import com.yimayhd.harem.model.vo.ActivityVO;
import com.yimayhd.harem.model.vo.TravelOfficialVO;
import com.yimayhd.harem.service.ActivityService;
import com.yimayhd.harem.service.TravelOfficialService;
import com.yimayhd.snscenter.client.domain.SnsTravelSpecialtyDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//import com.yimayhd.service.MessageCodeService;

/**
 * 官方游记管理
 * 
 * @author czf
 */
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
		PageVO<SnsTravelSpecialtyDO> pageVo = travelOfficialService.getList(travelOfficialListQuery);
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
		System.out.println("12313123");
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
		travelOfficialService.add(travelOfficial);
		return new ResponseVo();
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
		model.addAttribute("travelOfficial", travelOfficial);

		return "/system/activity/edit";
	}

	/**
	 * 编辑官方游记
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo edit(TravelOfficial travelOfficial) throws Exception {
		travelOfficialService.modify(travelOfficial);
		return new ResponseVo();
	}

	/**
	 * 官方游记状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setIsStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setJoinStatus(@PathVariable(value = "id") long id, int travelStatus) throws Exception {
		TravelOfficial travelOfficial = new TravelOfficial();
		travelOfficial.setId(id);
		travelOfficial.setTravelStatus(travelStatus);
		travelOfficialService.modify(travelOfficial);
		return new ResponseVo();
	}

}
