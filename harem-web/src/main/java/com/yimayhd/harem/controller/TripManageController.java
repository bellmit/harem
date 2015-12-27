package com.yimayhd.harem.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.TripBo;
import com.yimayhd.harem.model.TripBoQuery;
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
	
	//XXX:目的地 以及推荐相关的，入库设计的逻辑非常复杂，后期需要优化
	
	private static final Logger logger = LoggerFactory.getLogger(TripManageController.class);
	
	@Autowired TripService tripService;
	
	
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
		try {
			if (null != tripBo && 0 != tripBo.getCityCode()) {
				RegionDO regionDO = tripService.saveOrUpdateDetail(tripBo);
				if (null == regionDO) {
					return "/error";
				}
				return "/success";
			} 
		} catch (Exception e) {
			logger.error("trip+add,parameter[tripBo]="+JSON.toJSONString(tripBo)+" |error="+e.toString());
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
	public String relevance(Model model,int type,int cityCode,int[] resourceId){
		try {
			if ( null == resourceId || resourceId.length <= 0) {
				model.addAttribute("errMsg", "参数不正确");
				return "/error";
			}
			tripService.relevanceRecommended(type, cityCode, resourceId);
		} catch (Exception e) {
			logger.error("trip+relevance,parameter[type]="+type+",cityCode="+cityCode+",resourceId="+JSON.toJSONString(resourceId)+" |error="+e.toString());
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
			return new ResponseVo(ResponseStatus.SUCCESS);
		}
		return new ResponseVo(ResponseStatus.ERROR);
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
	public String list(Model model, TripBoQuery query){
		if(null == query || 0 == query.getType()){
			query = new TripBoQuery();
			query.setType(RegionType.DESC_REGION.getType());
		}
		PageVO<RegionDO> list = tripService.selectRegion(query);
		model.addAttribute("pageVo",list);
		model.addAttribute("regionList",list);
		
		if(RegionType.DEPART_REGION.getType() == query.getType() ){//出发地 3
			return "/system/trip/origin_list";
		}else if (RegionType.DESC_REGION.getType() == query.getType()){//目的地 4
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
	public ResponseVo selectDepartureList(Model model,int type){
		//TODO:去掉已经创建过的,返回list中可以创建的出发地
		// 1-酒店区域 2-景区区域 3-线路出发地 4-线路目的地
		List<RegionDO> list = tripService.selectRegion(RegionType.DESC_REGION.getType());
		if(CollectionUtils.isNotEmpty(list)){
			return new ResponseVo(list);			
		}
		return new ResponseVo(ResponseStatus.ERROR);
	}
	
	
	/**
	* @Title: block 
	* @Description:(停用某个目的地) 
	* @author create by yushengwei @ 2015年12月22日 上午10:12:09 
	* @param @param model
	* @param @param id
	* @param @param request
	* @param @return 
	* @return String 返回类型 
	* @throws
	 */
	@RequestMapping("/block/{id}")
	@ResponseBody
	public ResponseVo block(long id,int type,int cityCode, HttpServletRequest request){
		TripBo tripBo = new TripBo();
		tripBo.setCityCode(cityCode);
		boolean flag = false;
		try {
			flag = tripService.blockOrUnBlock(id, cityCode, type);
		} catch (Exception e) {
			logger.error("trip+block,parameter[type]="+type+",cityCode="+cityCode+",id="+id+" |error="+e.toString());
		}
		return new ResponseVo(flag);
	}
	
	@RequestMapping("/detail/{id}")
	public String detail(Model model,@PathVariable(value = "cityCode")long cityCode, HttpServletRequest request){
		int type=StringUtils.isEmpty(request.getParameter("type"))?RegionType.DESC_REGION.getType():Integer.parseInt(request.getParameter("type"));
		
		return null;
	}	
	
	@RequestMapping("/edit/{id}")
	public String edit(Model model,@PathVariable(value = "id")long id, HttpServletRequest request){
		int type=StringUtils.isEmpty(request.getParameter("type"))?RegionType.DESC_REGION.getType():Integer.parseInt(request.getParameter("type"));
		String cityCode=request.getParameter("cityCode");
		return cityCode;
	}
	
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
		return "/system/trip/add_destination/destination_base_info";
	}
	
		
}
