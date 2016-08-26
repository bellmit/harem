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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.ic.client.model.enums.ResourceType;
import com.yimayhd.membercenter.client.domain.CertificatesDO;
import com.yimayhd.membercenter.client.dto.TalentInfoDTO;
import com.yimayhd.membercenter.client.service.examine.ExamineDealService;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.biz.MerchantBiz;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.model.jiuxiu.helper.JiuxiuHelper;
import com.yimayhd.palace.model.query.JiuxiuMerchantListQuery;
import com.yimayhd.palace.model.vo.merchant.MerchantUserVo;
import com.yimayhd.palace.model.vo.merchant.MerchantVO;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.palace.result.BizResultSupport;
import com.yimayhd.resourcecenter.dto.DestinationNode;
import com.yimayhd.resourcecenter.model.enums.DestinationOutType;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.resourcecenter.service.DestinationService;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.user.client.domain.MerchantDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;
import com.yimayhd.user.client.enums.MerchantOption;
import com.yimayhd.user.client.enums.ServiceFacilityOption;
import com.yimayhd.user.client.query.MerchantPageQuery;
import com.yimayhd.user.client.result.BasePageResult;
import com.yimayhd.user.client.result.BaseResult;
import com.yimayhd.user.client.service.MerchantService;
import com.yimayhd.user.client.service.UserService;
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
	@Autowired
	private UserService userServiceRef;
	@Autowired
	private ExamineDealService examineDealServiceRef;
	@Autowired
	private DestinationService destinationServiceRef;
	private List<DestinationNode> getMerchantRegions() {
		/*RegionQuery query = new RegionQuery();
		query.setPageSize(Constant.PAGE_SIZE);
		query.setType(RegionType.JIUXIU_REGION.getType());
		query.setStatus(Constant.PAGEQUERY_STATUS);
		RCPageResult<RegionDO> cities = regionClientServiceRef.pageQuery(query);
		if (cities.isSuccess() ) {
			return cities.getList();
		}*/
		RcResult<List<DestinationNode>> destinationTree = destinationServiceRef.queryInlandDestinationTree(DestinationOutType.EAT.getCode(), Constant.DOMAIN_JIUXIU);
		if (destinationTree != null && destinationTree.isSuccess() && !CollectionUtils.isEmpty(destinationTree.getT())) {
			List<DestinationNode> destinationNodes = destinationTree.getT();
			return destinationNodes.get(0).getChildList();
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
				if (saveResult == null) {
					result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
					return result;
				}
				if (saveResult.isSuccess()) {
					result.initSuccess(saveResult.getMsg());
					result.setValue("/jiuxiu/merchant/toMerchantList");
				} else {
					
					result.setCode(saveResult.getCode());
					result.setMsg(saveResult.getMsg());
					result.setSuccess(false);
				}
			log.error("saveResult:{}",JSON.toJSONString(saveResult));
			
		}else {
			vo.setId(id);
			saveResult = merchantBiz.updateDeliciousFood(vo);
			if (saveResult == null) {
				result.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
				return result;
			}
			if (saveResult.isSuccess()) {
				result.initSuccess(saveResult.getMsg());
				result.setValue("/jiuxiu/merchant/toMerchantList");
			}else {
				result.setCode(saveResult.getCode());
				result.setMsg(saveResult.getMsg());
				result.setSuccess(false);
			}
			log.error("saveResult:{}",JSON.toJSONString(saveResult));
		}
		
		return result;
		
	}
	@RequestMapping(value="toAddDeliciousFood",method=RequestMethod.GET)
	public String toEditDeliciousFood(Model model,HttpServletRequest request,Long id) {
		model.addAttribute("cities", getMerchantRegions());
		if (id == null || id <= 0 ) {
			log.error("params error:id={}",id);
			return "system/food/addfoodcustom";
		}
		BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantById(id);
		if (merchant.isSuccess() && merchant.getValue() != null) {
			MerchantDO merchantDO = merchant.getValue();
			//MerchantVO merchantVO = new MerchantVO();
			new MerchantVO().bd_encrypt(merchantDO.getLat(), merchantDO.getLon(), merchantDO);
			//new MerchantVO().bd_encrypt(merchantDO.getLat(), merchantDO.getLon(), merchantDO);
			merchantDO.setName(merchantDO.getName().replaceAll("\"", "&quot;"));
			merchantDO.setServiceTime(merchantDO.getServiceTime().replaceAll("\"", "&quot;"));
			merchantDO.setAddress(merchantDO.getAddress().replaceAll("\"", "&quot;"));
//			merchantVO.setName(merchantDO.getName());
//			merchantVO.setServiceTime(merchantDO.getServiceTime());
//			merchantVO.setAddress(merchantDO.getAddress());
//			merchantVO.setLat(merchantDO.getLat());
//			merchantVO.setLon(merchantDO.getLon());
			long serviceFacility = merchantDO.getServiceFacility();
			if (serviceFacility >= 0 ) {
				List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
				model.addAttribute("containedOptions",containedOptions);
			}
			model.addAttribute("merchant",merchantDO );
			model.addAttribute("pictureText", merchantBiz.getPictureText(merchant.getValue().getSellerId()));
			//log.info("=============================="+JSON.toJSONString(merchantBiz.getPictureText(merchant.getValue().getSellerId())));

		}

		return "system/food/addfoodcustom";
		
	}
	@RequestMapping(value="toViewDeliciousFood",method=RequestMethod.GET)
	public String toViewDeliciousFood(Model model,HttpServletRequest request,Long id) {
		model.addAttribute("dmid", getRemoteHost(request));
		model.addAttribute("cities", getMerchantRegions());
		if (id == null || id <= 0 ) {
			log.error("params error:id={}",id);
			return "/system/food/foodcustomdt";
		}
		BaseResult<MerchantDO> merchant = userMerchantServiceRef.getMerchantById(id);
		if (merchant.isSuccess() && merchant.getValue() != null) {
			MerchantDO merchantDO = merchant.getValue();
			new MerchantVO().bd_encrypt(merchantDO.getLat(), merchantDO.getLon(), merchantDO);
			long serviceFacility = merchantDO.getServiceFacility();
			if (serviceFacility >= 0 ) {
				List<ServiceFacilityOption> containedOptions = ServiceFacilityOption.getContainedOptions(serviceFacility);
				model.addAttribute("containedOptions",containedOptions);
			}
			model.addAttribute("merchant",merchant.getValue() );
			model.addAttribute("pictureText", merchantBiz.getPictureText(merchant.getValue().getSellerId()));

		}
		
		return "/system/food/foodcustomdt";
		
	}
	
	private Object getRemoteHost(HttpServletRequest request) {
		String host = request.getHeader("host").split(":")[0];
		StringBuilder sb = new StringBuilder();
		String[] hostArr = host.split("\\.");
		sb.append(hostArr[hostArr.length-2]+"."+hostArr[hostArr.length-1]);
		return sb;
	}

	@RequestMapping(value="toMerchantList",method=RequestMethod.GET)
	public String toMerchantList(Model model) {
		try {
			model.addAttribute("cities", getMerchantRegions());
		} catch (Exception e) {
			log.error("get merchant list error:{}, ",e);
		}
		return "system/food/busineslist";
		
	}
	@RequestMapping(value="getMerchantList",method=RequestMethod.GET)
	public String getMerchantList(Model model,MerchantPageQuery merchantPageQuery) {
		//BizPageResult<MerchantVO> result = new BizPageResult<>() ;
				
		merchantPageQuery.setDomainId(Constant.DOMAIN_JIUXIU);
		merchantPageQuery.setOption(MerchantOption.EAT.getOption());
		if (StringUtils.isBlank(merchantPageQuery.getName())) {
			
			merchantPageQuery.setName(null);
		}else {
			merchantPageQuery.setName(merchantPageQuery.getName().trim());
		}
		try {
			BasePageResult<MerchantUserDTO> merchantUserList = userMerchantServiceRef.getMerchantUserList(merchantPageQuery);
			if (merchantUserList == null || !merchantUserList.isSuccess() || merchantUserList.getList() == null) {
				
				return "/error";
			}
			List<MerchantVO> merchantList = new ArrayList<>();
			for (MerchantUserDTO merchant : merchantUserList.getList()) {
				MerchantVO vo = new MerchantVO();
				vo.setId(merchant.getMerchantDO().getId());
				vo.setName(merchant.getMerchantDO().getName());
				vo.setAddress(merchant.getMerchantDO().getAddress());
				vo.setCityName(merchant.getMerchantDO().getCityName());
				vo.setServiceTel(merchant.getMerchantDO().getServiceTel());
				vo.setStatus(merchant.getMerchantDO().getStatus());
				vo.setCityCode(merchant.getMerchantDO().getCityCode());
				vo.setLogoImage(merchant.getMerchantDO().getLogo());
				merchantList.add(vo);
			}
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
		} catch (Exception e) {
			log.error("params:MerchantPageQuery={}  error:{}, ",JSON.toJSONString(merchantPageQuery),e);
		}
		return "/system/food/businesTable";
	}
	@RequestMapping(value="updateStatus",method=RequestMethod.POST)
	@ResponseBody
	public BizResult<String> updateStatus(String ids,Integer status) {
		BizResult<String> bizResult = new BizResult<String>();
		if (ids == null || status == null) {
			log.error("params error :ids={} status={} ",ids,status);
			bizResult.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return bizResult;
		}
		String[] idArr = ids.split(",");
		List<Long> idList = new ArrayList<>();
		for (String s : idArr) {
			idList.add(Long.parseLong(s));
		}
		BizResultSupport resultSupport = merchantBiz.batchUpdateMerchant(idList, status);
		if (resultSupport == null ) {
			bizResult.setPalaceReturnCode(PalaceReturnCode.SYSTEM_ERROR);
			
		}else if (!resultSupport.isSuccess()) {
			bizResult.setCode(resultSupport.getCode());
			bizResult.setMsg(resultSupport.getMsg());
			bizResult.setSuccess(false);
		}
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
			if(null!=result.getList()){
				List<MerchantUserVo> merchantUserVos = new ArrayList<MerchantUserVo>();
				for(int i=0;i<result.getList().size();i++){
					MerchantUserDTO merchantUserDTO = result.getList().get(i);
					MerchantDO merchantDO = merchantUserDTO.getMerchantDO();
					MerchantUserVo merchantUserVo = new MerchantUserVo();
					long sellerId = merchantDO==null ? 0 : merchantDO.getSellerId();
					UserDO userDO = userServiceRef.getUserDOById(sellerId);
					if(null!=userDO){
						merchantUserVo.setRegisPhone(userDO.getMobileNo());
					}
					merchantUserVo.setUserDO(merchantUserDTO.getUserDO());
					merchantUserVo.setMerchantDO(merchantUserDTO.getMerchantDO());
					merchantUserVos.add(merchantUserVo);
				}
				model.addAttribute("result", merchantUserVos);
			}
			
			model.addAttribute("jiuxiuMerchantListQuery", jiuxiuMerchantListQuery);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return "/system/merchant/merchantList";
	}
	
	/**
	 * 商家详情
	 * 
	 * @return 商家详情
	 * @throws Exception
	 */
	@RequestMapping(value="detail")
	public String getMerchantDetail(Model model,long id,long option){
		//UserDO user = sessionManager.getUser();
		if(MerchantOption.MERCHANT.getOption()==option){
			BaseResult<MerchantDO> meResult = merchantBiz.getMerchantBySellerId(id);
			model.addAttribute("nickName",meResult.getValue().getName());
			model.addAttribute("id", meResult.getValue().getId());
			model.addAttribute("name",meResult.getValue().getName());
			model.addAttribute("address",meResult.getValue().getAddress());
			if(null != meResult.getValue().getBackgroudImage()){
				model.addAttribute("ttImage", meResult.getValue().getBackgroudImage());
			}

			if(null != meResult.getValue().getLogo()){
				model.addAttribute("dbImage", meResult.getValue().getLogo());
			}
			model.addAttribute("merchantPrincipalTel", meResult.getValue().getMerchantPrincipalTel());
			model.addAttribute("serviceTel", meResult.getValue().getServiceTel());
			model.addAttribute("result", meResult.getValue());
			return "/system/merchant/merchantDetail";
		}else if(MerchantOption.TALENT.getOption()==option){
			BizResult<List<CertificatesDO>> serviceTypes = merchantBiz.getServiceTypes();
			if(null!=serviceTypes && serviceTypes.isSuccess() && serviceTypes.getValue().size()>0){
				model.addAttribute("serviceTypes", serviceTypes);
			}
			
			BizResult<TalentInfoDTO> dtoResult = merchantBiz.queryTalentInfoByUserId(id,Constant.DOMAIN_JIUXIU);
			if (dtoResult == null) {
				return "/system/error/500";
			}
			if (dtoResult.isSuccess()) {
				TalentInfoDTO talentInfoDTO = dtoResult.getValue();
				if (talentInfoDTO == null || talentInfoDTO.getTalentInfoDO() == null) {
					return "/system/error/500";
				}
				List<String> pictures = talentInfoDTO.getTalentInfoDO().getPictures();
				if (pictures == null ) {
					pictures = new ArrayList<String>();
				}
				//填充店铺头图集合
				while (pictures.size() < 5) {
					pictures.add("");
				}
				model.addAttribute("talentInfo", talentInfoDTO);
				model.addAttribute("nickName",talentInfoDTO.getTalentInfoDO().getNickName());
			}
			model.addAttribute("sellerId", id);
			return "/system/merchant/talentDetail";
		}else{
			return null;
		}
	}
	/**
	 * 
	* created by zhangxiaoyang
	* @date 2016年8月25日
	* @Title: modifyWeight 
	* @Description: 设置商户权重
	* @param  merchantId
	* @param  weightValue
	* @return BizResult<String>    返回类型 
	* @throws
	 */
	@RequestMapping(value="modifyMerchantWeight",method = RequestMethod.GET)
	@ResponseBody
	public BizResult<String> modifyMerchantWeight(long merchantId,int weightValue) {
		BizResult<String> result = new BizResult<String>();
		if (merchantId <= 0 || weightValue <= 0) {
			log.error("params:merchantId={},weightValue={}",merchantId,weightValue);
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result;
		}
		BizResult<Boolean> setResult = merchantBiz.modifyMerchantWeight(merchantId,weightValue);
		if (setResult == null || !setResult.isSuccess()) {
			log.error("params:sellerId={},weightVale={},result:{}",merchantId,weightValue,JSON.toJSONString(setResult));
			result.setPalaceReturnCode(PalaceReturnCode.UPDATE_WEIGHT_FAILED);
			return result;
		}
		return result;
	}
	
	
	
}
