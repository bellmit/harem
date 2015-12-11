package com.yimayhd.harem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.Destination;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.ShowcaseService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.enums.RelatedType;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.model.result.ShowCaseResult;
import com.yimayhd.resourcecenter.service.RegionClientService;
/** 
* @ClassName: DepartureManageController 
* @Description: (出发地、目的地管理，目的地关联相应的 推荐信息，如 必买 必去 酒店 直播) 
* @author create by yushengwei @ 2015年12月4日 上午11:03:29 
*/

@Controller
@RequestMapping("/B2C/trip")
public class TripManageController {

	@Autowired RegionClientService regionClientServiceRef;
	
	@Autowired ShowcaseService showcaseService;
	
	@Autowired HotelService hotelService;
	
	@Autowired ScenicService scenicSpotService;
	
	
	
	/**
	* @Title: originToAdd 
	* @Description:(出发地新增) 
	* @author create by yushengwei @ 2015年12月09日 上午10:32:45 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/origin/toAdd")
	public String originToAdd(Model model){
		return "/system/trip/origin_edit";
	}
	
	
	/**
	* @Title: toAdd 
	* @Description:(新增目的地) 
	* @author create by yushengwei @ 2015年12月10日 下午3:46:59 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/departure/toAdd")
	public String toAdd(Model model){
		//TODO:edit页面的推荐关联需要走后台的 /list/recommended ，传type，到时候分开写，不放在tab切换里面。
		return "/system/trip/edit";
	}
	
	/**
	* @Title: add 
	* @Description:(新增出发地/目的地) 
	* @author create by yushengwei @ 2015年12月8日 下午3:10:08 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/add")
	public String toAdd(Model model,Destination destination){
		String name = destination.getCityName();
		System.out.println("name="+name);
		return "/success";
	}
	
	/**
	* @Title: recommendedList 
	* @Description:(获取买必推荐列表) 
	* @author create by yushengwei @ 2015年12月8日 上午9:42:05 
	* @param @param model
	* @param @param destinationId
	* @param @param showCaseId
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/list/recommended")
	public String recommendedList(Model model,HttpServletRequest request,HotelListQuery hotelListQuery,ScenicPageQuery scenicPageQuery){
		int type=StringUtils.isEmpty(request.getParameter("type"))?1:Integer.parseInt(request.getParameter("type"));
		try {
			List<ShowCaseResult> list = showcaseService.getListShowCaseResult(type);
			model.addAttribute("recommendedList", list);
			if (type == RelatedType.recommended_buy.getType()) {//必买推荐
				return "/system/trip/add_destination/list_buy_recommended";
			} else if (type == RelatedType.recommended_scenic.getType()) {//必去景点 ?
				PageVO<ScenicDO>  scenicDOList = scenicSpotService.getList(scenicPageQuery);
			    model.addAttribute("scenicDOList", scenicDOList);
				return "/system/trip/add_destination/list_scenic";
			} else if (type == RelatedType.recommended_hotel.getType()) {//酒店 ?
				List<HotelDO> hotelDOList = hotelService.getList(hotelListQuery);
				model.addAttribute("hotelDOList", hotelDOList);
				return "/system/trip/add_destination/list_hotel";
			} else if (type == RelatedType.recommended_live.getType()) {//直播 ?
				return "/system/trip/add_destination/list_live";
			} 
		} catch (Exception e) {
			
		}
		return "/error";
	}
	
	/**
	* @Title: del 
	* @Description:(删除出发地，目的地) 
	* @author create by yushengwei @ 2015年12月8日 下午3:11:21 
	* @param @param model
	* @param @param code
	* @param @return 
	* @return ResponseVo 返回类型 
	* @throws
	 */
	@RequestMapping("/del")
	@ResponseBody
	public ResponseVo del(Model model,String code){
		if(StringUtils.isNotEmpty(code)){
			//TODO:删除接口
			return new ResponseVo("success");
		}
		return new ResponseVo("error");
	}
		
	/**
	* @Title: list 
	* @Description:(出发地或最美当地) 
	* @author create by yushengwei @ 2015年12月7日 下午5:14:40 
	* @param @param model
	* @param @param type
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/list")
	public String list(Model model,int type){
		RCPageResult<RegionDO> res = regionClientServiceRef.getRegionDOListByType(type);
		if(null == res || !res.isSuccess() || CollectionUtils.isEmpty(res.getList())){
			return "/error";
		}
		model.addAttribute("regionList",res.getList());
		if(RegionType.DEPART_REGION.getType() == type ){//出发地
			return "/system/trip/origin_list";
		}else if (RegionType.DESC_REGION.getType() == type){//目的地
			return "/system/trip/beautiful_local_list";
		}
		return "/error";
	}
	
	/**
	* @Title: selectDepartureList 
	* @Description:(获取省市区列表) 
	* @author create by yushengwei @ 2015年12月7日 下午3:13:16 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/selectRegion")
	@ResponseBody
	public String selectDepartureList(Model model){
		//TODO:去掉已经创建过的,返回list中可以创建的出发地
		return null;
	}
	
	/**
	* @Title: specialRecommended 
	* @Description:(关联：目的地-买必推荐) 
	* @author create by yushengwei @ 2015年12月7日 下午5:17:41 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/relevance/recommended")
	public String specialRecommended(Model model,int destinationId,int showCaseId[]){
		if(destinationId <=0 || destinationId>Integer.MAX_VALUE || showCaseId.length<=0 ){
			return "/error";
		}
		//TODO:update showcase 表中的id中的business_code即可
		return "/success";
	}
	
	
	/**
	* @Title: relevance 
	* @Description:(目的地关联 线路，酒店，景区) 
	* @author create by yushengwei @ 2015年12月8日 上午10:57:14 
	* @param @param model
	* @param @param destinationId
	* @param @param lineCityCode
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/relevance")
	public String relevance(Model model,int type,int destinationId,int CityCode){
		return "/success";
	}
	
	/**
	* @Title: hotel 
	* @Description:(选择关联酒店) 
	* @author create by yushengwei @ 2015年12月8日 下午4:54:24 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/hotel")
	public String hotel(Model model){
		return "/system/trip/add_destination/best_hotel";
	}
	
	@RequestMapping("/scenic")
	public String scenic(Model model){
		return "/system/trip/add_destination/best_scenic";
	}
		
	@RequestMapping("/live")
	public String live(Model model){
		return "/system/trip/add_destination/best_live";
	}		
		
		
}
