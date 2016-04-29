package com.yimayhd.palace.controller.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yimayhd.commentcenter.client.domain.ComTagDO;
import com.yimayhd.commentcenter.client.dto.TagRelationDomainDTO;
import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.commentcenter.client.service.ComTagCenterService;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.MerchantBiz;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.helper.NumberFormatHelper;
import com.yimayhd.palace.model.jiuxiu.helper.JiuxiuHelper;
import com.yimayhd.palace.model.query.JiuxiuMerchantListQuery;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizPageResult;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.palace.util.Common;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.query.RegionQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.user.client.cache.CityDataCacheClient;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.dto.CityDTO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.enums.MerchantStatus;
import com.yimayhd.user.client.enums.ServiceFacilityOption;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.DataCacheService;
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
//	@Autowired
//	private ComTagCenterService comTagCenterServiceRef;
//	@Autowired
//	private DataCacheService dataCacheService;
//	@Autowired
//	private CityDataCacheClient cityCacheClient;
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
//		TagRelationDomainDTO dto = new TagRelationDomainDTO();
//		dto.setDomain(Constant.DOMAIN_JIUXIU); 
//		dto.setOutType(TagType.DESTPLACE.name());
//		com.yimayhd.commentcenter.client.result.BaseResult<List<ComTagDO>> tagList = comTagCenterServiceRef.getTagListByTagType(dto);
		RCPageResult<RegionDO> cities = regionClientServiceRef.pageQuery(query);
		if (cities.isSuccess() ) {
			return cities.getList();
		}
//		if (tagList != null && tagList.isSuccess() ) {
//			//return tagList.getValue();
//			List<RegionDO> cityList = new ArrayList<>();
//			for (ComTagDO tagDo : tagList.getValue()) {
//				List<String> codeList = new ArrayList<>();
//				codeList.add(tagDo.getName());
//				CityDTO cityDTO = cityCacheClient.getCities(codeList).get(tagDo.getName());
//				RegionDO regionDO = new RegionDO();
//				regionDO.setCityCode(Integer.parseInt(tagDo.getName()));
//				regionDO.setCityName(cityDTO.getName());
//				cityList.add(regionDO);
//			}
//			return cityList;
//		}
		return null;
	}
	
	@RequestMapping(value="addDeliciousFood",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> addDeliciousFoodMerchant(MerchantVO vo,Model model,Long id) {
		BizResult<String> result = new BizResult<String>();
		BizResultSupport saveResult = null;
		if (id <= 0 || id == null) {
			
			saveResult = merchantBiz.addDeliciousFood(vo);
				if (saveResult == null) {
					result.init(false, -1,
							 "保存失败");
					return result;
				}
				if (saveResult.isSuccess()) {
					result.initSuccess(saveResult.getMsg());
					result.setValue("/jiuxiu/merchant/toMerchantList");
				} else {
					//result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
					String errorMsg = saveResult.getPalaceReturnCode() == null ? null
							: saveResult.getPalaceReturnCode().getErrorMsg();
					result.init(false, -1,
							StringUtils.isBlank(errorMsg) ? "保存失败" : errorMsg);
				}
			
		}else {
			vo.setId(id);
			saveResult = merchantBiz.updateDeliciousFood(vo);
			if (saveResult == null) {
				result.init(false, -1,
						 "保存失败");
				return result;
			}
			if (saveResult.isSuccess()) {
				result.initSuccess(saveResult.getMsg());
				result.setValue("/jiuxiu/merchant/toMerchantList");
			}else {
				//result.setPalaceReturnCode(saveResult.getPalaceReturnCode());
				//saveResult
				String errorMsg = saveResult.getPalaceReturnCode() == null?null:saveResult.getPalaceReturnCode().getErrorMsg();
				result.init(false, -1, StringUtils.isBlank(errorMsg)?"保存失败":errorMsg);
			}
		}
		
		return result;
		
	}
	@RequestMapping(value="toAddDeliciousFood",method=RequestMethod.GET)
	public String toEditDeliciousFood(Model model,HttpServletRequest request,Long id) {
//		model.addAttribute("dmid", getRemoteHost(request));
		model.addAttribute("cities", getMerchantRegions());
		if (id == null || id <= 0 ) {
			
			return "system/food/addfoodcustom";
		}
		BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantById(id);
		//BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantBySellerId(sessionManager.getUserId(), Constant.DOMAIN_JIUXIU);
		if (merchant.isSuccess() && merchant.getValue() != null) {
			MerchantDO merchantDO = merchant.getValue();
//			if (merchantDO.getName().contains("\"")) {
//				//merchantDO.setName(merchantDO.getName().replace("\"", "\\\""));
//				model.addAttribute("nameResult", true);
//			}else {
//				model.addAttribute("nameResult", false);
//				
//			}
			new MerchantVO().bd_encrypt(merchantDO.getLat(), merchantDO.getLon(), merchantDO);
			merchantDO.setName(merchantDO.getName().replaceAll("\"", "&quot;"));
			merchantDO.setServiceTime(merchantDO.getServiceTime().replaceAll("\"", "&quot;"));
			merchantDO.setAddress(merchantDO.getAddress().replaceAll("\"", "&quot;"));
			//System.out.println(merchantDO.getName().replaceAll("\"", "'"+'\"'+"'"));
			long serviceFacility = merchantDO.getServiceFacility();
			if (serviceFacility >= 0 ) {
				List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
				model.addAttribute("containedOptions",containedOptions);
			}
			model.addAttribute("merchant",merchantDO );
			
			
		}

		model.addAttribute("numberHelper",new NumberFormatHelper() );
		return "system/food/addfoodcustom";
		
	}
	@RequestMapping(value="toViewDeliciousFood",method=RequestMethod.GET)
	public String toViewDeliciousFood(Model model,HttpServletRequest request,Long id) {
		model.addAttribute("dmid", getRemoteHost(request));
		model.addAttribute("cities", getMerchantRegions());
		if (id == null || id <= 0 ) {
			
			return "/system/food/foodcustomdt";
		}
		BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantById(id);
		//BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantBySellerId(sessionManager.getUserId(), Constant.DOMAIN_JIUXIU);
		if (merchant.isSuccess() && merchant.getValue() != null) {
			MerchantDO merchantDO = merchant.getValue();
			new MerchantVO().bd_encrypt(merchantDO.getLat(), merchantDO.getLon(), merchantDO);
			long serviceFacility = merchantDO.getServiceFacility();
			if (serviceFacility >= 0 ) {
				List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
				model.addAttribute("containedOptions",containedOptions);
			}
			model.addAttribute("merchant",merchant.getValue() );
			
			
		}
		model.addAttribute("numberHelper",new NumberFormatHelper() );
		
		return "/system/food/foodcustomdt";
		
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
		try {
//			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
//			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
//				
//				return "system/businessManage/busineslist";
//			}
//			model.addAttribute("merchant", merchantUserList.getList());
			model.addAttribute("cities", getMerchantRegions());
			//model.addAttribute("merchantQuery", merchantPageQuery);
			
			//return "system/food/busineslist";
		} catch (Exception e) {
			log.error("get merchant list error, ",e);
		}
		return "system/food/busineslist";
		
	}
	@RequestMapping(value="getMerchantList",method=RequestMethod.GET)
	//@ResponseBody
	public String getMerchantList(Model model,MerchantPageQuery merchantPageQuery) {
		BizPageResult<MerchantVO> result = new BizPageResult<>() ;
				
		merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantPageQuery.setOption(MerchantOption.EAT.getOption());
		if (StringUtils.isBlank(merchantPageQuery.getName())) {
			
			merchantPageQuery.setName(null);
		}
		try {
			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
				
				return null;
			}
			//model.addAttribute("cities", getMerchantRegions());
			//model.addAttribute("merchantQuery", merchantPageQuery);
			System.out.println(JSON.toJSONString(merchantUserList.getList(), SerializerFeature.WriteNullStringAsEmpty));
			List<MerchantVO> merchantList = new ArrayList<>();
			for (MerchantUserDTO merchant : merchantUserList.getList()) {
				MerchantVO vo = new MerchantVO();
				vo.setId(merchant.getMerchantDO().getId());
				vo.setName(merchant.getMerchantDO().getName());
				vo.setAddress(merchant.getMerchantDO().getAddress());
				vo.setCityName(merchant.getMerchantDO().getCityName());
				//vo.setMerchantPrincipalTel(merchant.getMerchantDO().getMerchantPrincipalTel());
				vo.setServiceTel(merchant.getMerchantDO().getServiceTel());
				vo.setStatus(merchant.getMerchantDO().getStatus());
				vo.setCityCode(merchant.getMerchantDO().getCityCode());
				vo.setLogoImage(merchant.getMerchantDO().getLogo());
				merchantList.add(vo);
			}
//			result.setList(merchantList);
//			result.setTotalCount(merchantUserList.getTotalCount());
//			return result ;
			model.addAttribute("merchantList", merchantList);
			int totalPage = 0;
			if (merchantUserList.getTotalCount()%merchantPageQuery.getPageSize() > 0) {
				totalPage += merchantUserList.getTotalCount()/merchantPageQuery.getPageSize()+1;
			}else {
				
				totalPage += merchantUserList.getTotalCount()/merchantPageQuery.getPageSize();
			}
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("pageNo", merchantUserList.getPageNo());
			model.addAttribute("totalCount", merchantUserList.getTotalCount());
			//model.addAttribute("pageSize", merchantUserList.getTotalCount());
//			return "/system/food/businesTable";
//			return "{'total':'"+merchantUserList.getList().size()+"','rows':'"+JSON.toJSONString(merchantList, SerializerFeature.WriteNullStringAsEmpty)+"'}";
		} catch (Exception e) {
			log.error("get merchant list error, ",e);
		}
		return "/system/food/businesTable";
	}
	@RequestMapping(value="updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> updateStatus(String ids,Integer status) {
		if (ids == null || status == null) {
			log.error("params error and params is "+ids);
			throw new BaseException("参数错误");
		}
		BizResult<String> bizResult = new BizResult<String>();
		String[] idArr = ids.split(",");
//		if (idArr.length < 2 ) {
//			MerchantVO  vo = new MerchantVO();
//			if (status == 1) {
//				vo.setStatus(MerchantStatus.INVALID.getCode());
//				vo.setId(Integer.parseInt(idArr[0]));
//			}else if (status == 2) {
//				//MerchantVO  vo = new MerchantVO();
//				vo.setStatus(MerchantStatus.OFFLINE.getCode());
//				vo.setId(Integer.parseInt(idArr[0]));
//				
//			}else if (status == 3) {
//				//MerchantVO  vo = new MerchantVO();
//				
//			}
//			vo.setStatus(status);
//			vo.setId(Integer.parseInt(idArr[0]));
//			
//			BizResultSupport updateResult = merchantBiz.updateDeliciousFood(vo);
//			if (updateResult == null || !updateResult.isSuccess()) {
//				String msg = updateResult == null?"操作失败":updateResult.getMsg();
//				bizResult.init(false, -1, msg);
//			}
//			return bizResult;
//			}
		List<Long> idList = new ArrayList<>();
		for (String s : idArr) {
			idList.add(Long.parseLong(s));
		}
		//BizResultSupport resultSupport = null;
//		if (status == 1) {
//			
//			//resultSupport = merchantBiz.batchUpdateMerchant(idList, MerchantStatus.INVALID.getCode());
//		}else if (status == 2) {
//			//resultSupport = merchantBiz.batchUpdateMerchant(idList, MerchantStatus.OFFLINE.getCode());
//		}else if (status == 3) {
//			
//		}
		BizResultSupport resultSupport = merchantBiz.batchUpdateMerchant(idList, status);
		if (resultSupport == null || !resultSupport.isSuccess()) {
			String msg = resultSupport == null?"操作失败":resultSupport.getMsg();
			bizResult.init(false, -1, msg);
		}
		//userMerchantServiceRef.
		return bizResult;
		
	}
	
	
	
	/**
	 * 商家列表
	 * 
	 * @return 商家列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/merchantList", method = RequestMethod.GET)
	public String goodsOrderList(Model model, JiuxiuMerchantListQuery jiuxiuMerchantListQuery) throws Exception {
		try {
			MerchantPageQuery merchantPageQuery = new MerchantPageQuery();
			JiuxiuHelper.fillMerchantListQuery(merchantPageQuery, jiuxiuMerchantListQuery);
			BasePageResult<MerchantUserDTO> result = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
			List<MerchantUserDTO> merchantUserList = new ArrayList<MerchantUserDTO>();
			int totalCount = 0;
			if(result.isSuccess() && null!=result.getList() && result.getList().size()>0){
				totalCount = (int)result.getTotalCount();
				merchantUserList = result.getList();
			}
			PageVO<MerchantUserDTO> orderPageVO = new PageVO<MerchantUserDTO>(jiuxiuMerchantListQuery.getPageNumber(),jiuxiuMerchantListQuery.getPageSize(),
					totalCount,merchantUserList);
			model.addAttribute("pageVo", orderPageVO);
			model.addAttribute("result", result.getList());
			model.addAttribute("jiuxiuMerchantListQuery", jiuxiuMerchantListQuery);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return "/system/merchant/merchantList";
	}
}
