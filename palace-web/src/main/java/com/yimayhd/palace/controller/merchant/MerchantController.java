package com.yimayhd.palace.controller.merchant;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
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
				result.setValue("toMerchantList");
			}else {
				result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
			}
		}else {
			vo.setId(id);
			saveResult = merchantBiz.updateDeliciousFood(vo);
			if (saveResult.isSuccess()) {
				result.initSuccess(saveResult.getMsg());
				result.setValue("toMerchantList");
			}else {
				result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
			}
		}
		
		return result;
		
	}
	@RequestMapping(value="toAddDeliciousFood",method=RequestMethod.GET)
	public String toEditDeliciousFood(Model model,HttpServletRequest request) {
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
//		System.out.println(request.getRemoteHost());
//		System.out.println(request.getRemoteAddr());
//		Enumeration<String> headerNames = request.getHeaderNames();
//		while (headerNames.hasMoreElements()) {
//			String string = (String) headerNames.nextElement();
//			System.out.println(string+"--------------"+request.getHeader(string));
//			
//		}
		
		model.addAttribute("dmid", getRemoteHost(request));
		//System.out.println(sb);
		model.addAttribute("cities", getMerchantRegions());
		return "system/food/addfoodcustom";
		
	}
	
	private Object getRemoteHost(HttpServletRequest request) {
		String host = request.getHeader("host").split(":")[0];
		StringBuilder sb = new StringBuilder();
		String[] hostArr = host.split("\\.");
		sb.append(hostArr[hostArr.length-2]+"."+hostArr[hostArr.length-1]);
		return sb;
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
	@RequestMapping(value="toMerchantList",method=RequestMethod.GET)
	public String toMerchantList(Model model) {
		//merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		try {
//			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
//			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
//				
//				return "system/businessManage/busineslist";
//			}
//			model.addAttribute("merchant", merchantUserList.getList());
			model.addAttribute("cities", getMerchantRegions());
			//model.addAttribute("merchantQuery", merchantPageQuery);
			
			return "system/food/busineslist";
		} catch (Exception e) {
			log.error("get merchant list error, ",e);
			return "system/food/busineslist";
		}
		
	}
	@RequestMapping(value="getMerchantList",method=RequestMethod.GET)
	@ResponseBody
	public String getMerchantList(Model model,MerchantPageQuery merchantPageQuery) {
		merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		//merchantPageQuery.
		try {
			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
				
				return null;
			}
			//model.addAttribute("merchant", merchantUserList.getList());
			model.addAttribute("cities", getMerchantRegions());
			model.addAttribute("merchantQuery", merchantPageQuery);
			System.out.println(JSON.toJSONString(merchantUserList.getList(), SerializerFeature.WriteNullStringAsEmpty));
			return "{total:"+merchantUserList.getList().size()+",rows:"+JSON.toJSONString(merchantUserList.getList(), SerializerFeature.WriteNullStringAsEmpty)+"}";
		} catch (Exception e) {
			log.error("get merchant list error, ",e);
			return null;
		}
	}
	@RequestMapping(value="updateStatus")
	@ResponseBody
	public String updateStatus() {
		//userMerchantServiceRef.
		return null;
		
	}
}
