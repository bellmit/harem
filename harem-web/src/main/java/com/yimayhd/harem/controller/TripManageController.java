package com.yimayhd.harem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.service.TripService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.enums.ColumnType;
import com.yimayhd.resourcecenter.model.enums.RegionType;
/** 
* @ClassName: DepartureManageController 
* @Description: (出发地、目的地管理，目的地关联相应的 推荐信息，如 必买 必去 酒店 直播) 
* @author create by yushengwei @ 2015年12月4日 上午11:03:29 
*/

@Controller
@RequestMapping("/B2C/trip")
public class TripManageController extends BaseController {

	@Autowired TripService tripService;
	
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
	public String toAdd(Model model,@ModelAttribute("TripBo") TripBo tripBo){
		//TODO:数据校验
		if(null != tripBo && StringUtils.isNotEmpty(tripBo.getCityCode())){
			long ids= tripService.saveTrip(tripBo);
			if(0==ids){
				return "/error";
			}
			if(RegionType.DEPART_REGION.getType()==tripBo.getType()){//线路出发地
				return "/success";
			}else if(RegionType.DESC_REGION.getType()==tripBo.getType()){//线路目的地
				model.addAttribute("id", ids);
				return "redirect:/departure/toAdd";
			}
		}
		return "error";
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
	@RequestMapping("/recommended/list")
	public String recommendedList(Model model,HttpServletRequest request,ScenicPageQuery scenicPageQuery){
		int type=StringUtils.isEmpty(request.getParameter("type"))?1:Integer.parseInt(request.getParameter("type"));
		String cityCode=StringUtils.isEmpty(request.getParameter("cityCode"))?"530100":request.getParameter("cityCode");
		try {
			if (type == ColumnType.NEED_BUY.getType()) {//必买推荐 10
				List<RegionIntroduceDO> list = tripService.getListShowCaseResult(type);
				model.addAttribute("recommendedList", list);
				return "/system/trip/add_destination/list_buy_recommended";
			
			
			
			} else if (type == ColumnType.GREAT_SCENIC.getType()) {//必去景点 8
			    model.addAttribute("scenicDOList", tripService.selectScenicDO(scenicPageQuery));
				return "/system/trip/add_destination/list_scenic";
			
			
			
			
			} else if (type == ColumnType.GREAT_HOTEL.getType()) {//酒店 ? 7 
				List<HotelDO> hotelDOList = tripService.getListHotelDO(cityCode);
				model.addAttribute("hotelDOList", hotelDOList);
				return "/system/trip/add_destination/list_hotel";
			
			
			
			
			} else if (type == ColumnType.LIVE_HEADLINES.getType()) {//直播 ? 17
				
				return "/system/trip/add_destination/list_live";
			} 
		} catch (Exception e) {
			
		}
		return "/error";
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
	@RequestMapping("/recommended/relevance")
	public String relevance(Model model,int type,String cityCode,int[] resourceId){
		try {
			if (StringUtils.isEmpty(cityCode) || null == resourceId || resourceId.length <= 0) {
				model.addAttribute("errMsg", "参数不正确");
				return "/error";
			}
			tripService.relevanceRecommended(type, cityCode, resourceId);
		} catch (Exception e) {
		}
		return "/success";
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
	public String list(Model model){
		int type=StringUtils.isEmpty(request.getParameter("type"))?RegionType.DESC_REGION.getType():Integer.parseInt(request.getParameter("type"));
		List<RegionDO> list = tripService.selectRegion(type);
		if(CollectionUtils.isNotEmpty(list)){
			model.addAttribute("regionList",list);
			if(RegionType.DEPART_REGION.getType() == type ){//出发地
				return "/system/trip/origin_list";
			}else if (RegionType.DESC_REGION.getType() == type){//目的地
				return "/system/trip/beautiful_local_list";
			}	
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
	public ResponseVo selectDepartureList(Model model,int type){
		//TODO:去掉已经创建过的,返回list中可以创建的出发地
		// 1-酒店区域 2-景区区域 3-线路出发地 4-线路目的地
		List<RegionDO> list = tripService.selectRegion(type);
		if(CollectionUtils.isNotEmpty(list)){
			return new ResponseVo(list);			
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public String edit(Model model, HttpServletRequest request){
		int type=StringUtils.isEmpty(request.getParameter("type"))?1:Integer.parseInt(request.getParameter("type"));
		String cityCode=StringUtils.isEmpty(request.getParameter("cityCode"))?"530100":request.getParameter("cityCode");
		
		return cityCode;
	}	
	
	
	
	
		
}
