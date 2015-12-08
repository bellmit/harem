package com.yimayhd.harem.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.resourcecenter.domain.RegionDO;
import com.yimayhd.resourcecenter.model.enums.RegionType;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionClientService;
import com.yimayhd.resourcecenter.service.ShowcaseClientServer;
/** 
* @ClassName: DepartureManageController 
* @Description: (出发地、目的地管理) 
* @author create by yushengwei @ 2015年12月4日 上午11:03:29 
*/

@Controller
@RequestMapping("/B2C")
public class TripManageController {

	@Autowired RegionClientService regionClientServiceRef;
	
	@Autowired ShowcaseClientServer showcaseClientServerConsumer;
	
	
	@RequestMapping("/trip/departure/toAdd")
	public String add(Model model){
		return "/system/departure/edit";
	}
	
	/**
	* @Title: add 
	* @Description:(新增出发地) 
	* @author create by yushengwei @ 2015年12月8日 下午3:10:08 
	* @param @param model
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/trip/departure/add")
	public String toAdd(Model model,RegionDO regionDO){
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
	@RequestMapping("/trip/del")
	@ResponseBody
	public ResponseVo del(Model model,String code){
		if(StringUtils.isNotEmpty(code)){
			//TODO:删除接口
		}
		return null;
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
	@RequestMapping("/trip/list")
	public String list(Model model,int type){
		if(type<0 || type>4){
			return "/error";
		}
		RCPageResult<RegionDO> res = regionClientServiceRef.getRegionDOListByType(type);
		if(null != res && res.isSuccess() && CollectionUtils.isNotEmpty(res.getList())){
			//TODO:这里可能要根据省市区排序，即相同的省下，罗列出所有的市出来
			model.addAttribute("regionList",res.getList());
			if(RegionType.DEPART_REGION.getType() == type ){
				return "/system/departure/list";
			}else if (RegionType.DESC_REGION.getType() == type){
				return "/system/destination/beautiful_local_list";
			}
		}
		return "/system/error";
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
	@RequestMapping("/trip/selectRegion")
	@ResponseBody
	public String selectDepartureList(Model model){
		//TODO:去掉已经创建过的,返回list中可以创建的出发地
		return null;
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
	public String recommendedList(Model model,int destinationId,int showCaseId[]){
		
		return "/success";
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
	@RequestMapping("/trip/relevance/recommended")
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
	@RequestMapping("/trip/relevance")
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
	@RequestMapping("/trip/hotel")
	public String hotel(Model model){
		return "/system/destination/add_destination/best_hotel";
	}
	
	@RequestMapping("/trip/scenic")
	public String scenic(Model model){
		return "/system/destination/add_destination/best_scenic";
	}
		
	@RequestMapping("/trip/live")
	public String live(Model model){
		return "/system/destination/add_destination/best_live";
	}		
		
		
}
