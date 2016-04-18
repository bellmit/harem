package com.yimayhd.palace.controller.merchant;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.biz.MerchantBiz;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.ServiceFacilityOption;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.query.MerchantQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.session.manager.SessionManager;
/**
 * 美食
 * 
 * @author zhangxy
 *
 */
@Controller
@RequestMapping("/jiuxiu/merchant")
public class MerchantController extends BaseController {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private MerchantBiz merchantBiz;
	@Autowired
	private MerchantService userMerchantServiceRef;
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private RegionClientService regionClientServiceRef;
//	@RequestMapping(value="toAddDeliciousFood",method=RequestMethod.GET)
//	public String toAddDeliciousFood(Model model) {
//		
//		model.addAttribute("cities", getMerchantRegions());
//		return "system/deliciousFood/addfoodcustom";
//		
//	}

	private List<RegionDO> getMerchantRegions() {
		RegionQuery query = new RegionQuery();
		query.setPageSize(Constant.PAGE_SIZE);
		query.setType(RegionType.JIUXIU_REGION.getType());
		query.setStatus(Constant.PAGEQUERY_STATUS);
		RCPageResult<RegionDO> cities = regionClientServiceRef.pageQuery(query);
		if (cities.isSuccess() && cities.getList() != null) {
			return cities.getList();
		}
		return null;
	}
	
	@RequestMapping(value="addDeliciousFood",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> addDeliciousFoodMerchant(MerchantVO vo,Model model,Long id) {
		BizResult<String> result = new BizResult<String>();
		BizResultSupport saveResult = null;
		if (id <= 0 || id == null) {
			
			saveResult = merchantBiz.addDeliciousFood(vo);
			if (saveResult.isSuccess()) {
				result.initSuccess(saveResult.getMsg());
				result.setValue("jiuxiu/merchant/toMerchantList");
			}else {
				result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
			}
		}else {
			vo.setId(id);
			saveResult = merchantBiz.updateDeliciousFood(vo);
			if (saveResult.isSuccess()) {
				result.initSuccess(saveResult.getMsg());
				result.setValue("jiuxiu/merchant/toMerchantList");
			}else {
				result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
			}
		}
		
		return result;
		
	}
	@RequestMapping(value="toAddDeliciousFood",method=RequestMethod.GET)
	public String toEditDeliciousFood(Model model) {
		BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantBySellerId(sessionManager.getUserId(), Constant.DOMAIN_JIUXIU);
		if (merchant.isSuccess() && merchant.getValue() != null) {
			MerchantDO merchantDO = merchant.getValue();
			long serviceFacility = merchantDO.getServiceFacility();
			if (serviceFacility >= 0 ) {
				List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
				model.addAttribute("containedOptions",containedOptions);
			}
			model.addAttribute("merchant",merchant.getValue() );
			
			
		}
		model.addAttribute("cities", getMerchantRegions());
		return "system/food/addfoodcustom";
		
	}
	
	/*@RequestMapping(value="updateDeliciousFood",method=RequestMethod.POST)
	public BizResult<String> updateDeliciousFoodMerchant(MerchantVO vo,Model model) {
		BizResult<String> result = new BizResult<String>();
		BizResultSupport updateDeliciousFoodResult = merchantBiz.updateDeliciousFood(vo);
		if (updateDeliciousFoodResult.isSuccess()) {
			result.initSuccess(updateDeliciousFoodResult.getMsg());
		}else {
			result.setPalaceReturnCode(updateDeliciousFoodResult.getPalaceReturnCode());
		}
		
		return result;
		
	}*/
	@RequestMapping(value="toMerchantList",method=RequestMethod.POST)
	public String getMerchantList(Model model,MerchantPageQuery merchantPageQuery) {
		//MerchantQuery merchantQuery = new MerchantQuery();
		merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		//BizResult<String> bizResult = new 
		try {
			//BaseResult<List<MerchantDO>> merchantList = 
			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
//			if (merchantList.isSuccess() && merchantList.getValue() != null) {
//				model.addAttribute("merchant", merchantList.getValue());
//			}
			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
				
				return "system/businessManage/busineslist";
			}
			
			model.addAttribute("merchant", merchantUserList.getList());
			model.addAttribute("cities", getMerchantRegions());
			model.addAttribute("merchantQuery", merchantPageQuery);
			return "system/businessManage/busineslist";
		} catch (Exception e) {
			log.error("get merchant list error and exception is  "+e);
			return "system/businessManage/busineslist";
		}
		
	}
}
