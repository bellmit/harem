package com.yimayhd.harem.controller;

import java.util.*;

import com.yimayhd.harem.model.query.*;
import com.yimayhd.harem.service.*;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.query.LinePageQuery;
import com.yimayhd.snscenter.client.domain.SnsActivityDO;
import com.yimayhd.snscenter.client.domain.SnsSubjectDO;
import com.yimayhd.snscenter.client.dto.ActivityQueryDTO;
import com.yimayhd.snscenter.client.dto.SubjectInfoDTO;
import com.yimayhd.snscenter.client.result.BasePageResult;
import com.yimayhd.snscenter.client.service.SnsCenterService;
import com.yimayhd.snscenter.errorcode.SnsCenterReturnCode;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.BaseQuery;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.model.ItemVO;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.enums.ItemType;
import com.yimayhd.ic.client.model.query.ScenicPageQuery;
import com.yimayhd.membercenter.client.domain.TravelKaVO;
import com.yimayhd.membercenter.client.query.TravelkaPageQuery;
import com.yimayhd.resourcecenter.domain.RegionIntroduceDO;
import com.yimayhd.resourcecenter.model.query.RegionIntroduceQuery;
import com.yimayhd.resourcecenter.model.result.RCPageResult;
import com.yimayhd.resourcecenter.service.RegionIntroduceClientService;
import com.yimayhd.snscenter.client.domain.result.ClubDO;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.domain.UserDOPageQuery;

/**
 * 资源选择理
 *
 * @author yebin
 *
 */
@Controller
@RequestMapping("/B2C/resourceForSelect")
public class ResourceForSelectController extends BaseController {
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private ScenicService scenicService;
	@Autowired
	private HotelRPCService hotelService;
	@Autowired
	private UserRPCService userService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private TripService tripService;
	@Autowired
	private CommTravelService commTravelService;
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 选择景点
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRestaurantForSelect")
	public @ResponseBody ResponseVo queryRestaurantForSelect(RestaurantListQuery query) {
		PageVO<RestaurantDO> pageVo = restaurantService.pageQueryRestaurant(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	/**
	 * 选择餐厅
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRestaurant")
	public String selectRstaurant() throws Exception {
		return "/system/resource/forSelect/selectRestaurant";
	}

	/**
	 * 选择景区
	 *
	 * @return
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryScenicForSelect")
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, ScenicListQuery scenicPageQuery) throws Exception {

		PageVO<ScenicDO> pageVo = scenicService.getList(scenicPageQuery);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", scenicPageQuery);
		return new ResponseVo(result);
	}

	/**
	 * 选择景区
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectScenic")
	public String selectScenic() throws Exception {
		return "/system/resource/forSelect/selectScenic";
	}

	/**
	 * 选择景区
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneScenic")
	public String selectOneScenic() throws Exception {
		return "/system/resource/forSelect/selectOneScenic";
	}

	/**
	 * 选择活动商品
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneActivityComm")
	public String selectOneActivityComm(Model model) throws Exception {
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		model.addAttribute("itemTypeList", itemTypeList);
		return "/system/resource/forSelect/selectOneActivityComm";
	}

	/**
	 * 选择活动商品
	 *
	 * @return
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityComm")
	public @ResponseBody ResponseVo queryScenicForSelect(Model model, CommodityListQuery commodityListQuery)
			throws Exception {
		PageVO<ItemVO> pageVO = commodityService.getList(commodityListQuery);
		List<ItemType> itemTypeList = Arrays.asList(ItemType.values());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("itemTypeList", itemTypeList);
		result.put("pageVo", pageVO);
		result.put("query", commodityListQuery);
		return new ResponseVo(result);
	}

	/**
	 * 选择酒店
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHotelForSelect")
	public @ResponseBody ResponseVo queryHotelForSelect(HotelListQuery query) throws Exception {
		PageVO<HotelDO> pageVo = hotelService.pageQueryHotel(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	/**
	 * 选择酒店
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectHotel")
	public String selectHotel() throws Exception {
		return "/system/resource/forSelect/selectHotel";
	}

	/**
	 * 选择用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUserForSelect")
	public @ResponseBody ResponseVo queryUserForSelect(UserDOPageQuery query, Integer pageNumber) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
		PageVO<UserDO> pageVo = userService.getUserListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	/**
	 * 选择一个用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneUser")
	public String selectOneUser() throws Exception {
		return "/system/resource/forSelect/selectOneUser";
	}

	/**
	 * 选择用户
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUser")
	public String selectUser() throws Exception {
		return "/system/resource/forSelect/selectUser";
	}

	/**
	 * 选择旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTravelKaForSelect")
	public @ResponseBody ResponseVo queryTravelKaForSelect(TravelkaPageQuery query) throws Exception {
		Integer pageNumber = getInteger("pageNumber");
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
		PageVO<TravelKaVO> pageVo = userService.getTravelKaListByPage(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	/**
	 * 选择旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneTravelKa")
	public String selectOneTravelKa() throws Exception {
		return "/system/resource/forSelect/selectOneTravelKa";
	}

	/**
	 * 选择一个旅游咖
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTravelKa")
	public String selectTravelKa() throws Exception {
		return "/system/resource/forSelect/selectTravelKa";
	}

	@RequestMapping(value = "/selectMustBuy")
	public String selectMustBuy() throws Exception {
		return "/system/resource/forSelect/selectMustBuy";
	}

	/**
	 * @Title: listMustBuy
	 * @Description:(获取买必推荐列表)
	 * @author create by yushengwei @ 2015年12月27日 下午4:23:41
	 * @param @param regionIntroduceQuery
	 * @param @return
	 * @param @throws Exception
	 * @return ResponseVo 返回类型
	 * @throws
	 */
	@RequestMapping(value = "/listMustBuy")
	public @ResponseBody ResponseVo listMustBuy(RegionIntroduceQuery query,Integer pageNumber,Integer pageSize) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		} else {
			query.setPageNo(BaseQuery.DEFAULT_PAGE);
		}
		if(pageSize!= null) {
			query.setPageSize(pageSize);
		} else{
			query.setPageSize(BaseQuery.DEFAULT_SIZE);
		}
		PageVO<RegionIntroduceDO> pageVo = tripService.getPageRegionIntroduceDO(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	@RequestMapping(value = "/selectLive")
	public String selectLive() throws Exception {
		return "/system/resource/forSelect/selectLive";
	}
	
	@RequestMapping(value = "/listLive")
	public  @ResponseBody ResponseVo listLive(SubjectInfoDTO query,Integer pageNumber,Integer pageSize) throws Exception {
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		} else {
			query.setPageNo(BaseQuery.DEFAULT_PAGE);
		}
		if(pageSize!= null) {
			query.setPageSize(pageSize);
		} else{
			query.setPageSize(BaseQuery.DEFAULT_SIZE);
		}
		
		PageVO<SnsSubjectDO> pageVo = tripService.getPageSnsSubjectDO(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	/**
	 * 选择单个旅游产品
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneTravelProduct")
	public String selectOneTravelProduct() throws Exception {
		return "/system/resource/forSelect/selectOneTravelProduct";
	}


	/**
	 *
	 * 查询旅游产品
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTravelProductForSelect")
	public @ResponseBody ResponseVo queryTravelProductForSelect(LinePageQuery query) throws Exception {
		Integer pageNumber = getInteger("pageNumber");
		if (pageNumber != null) {
			query.setPageNo(pageNumber);
		}
//		PageVO<TravelKaVO> pageVo = userService.getTravelKaListByPage(query);
		PageVO<LineDO> pageVo = commTravelService.pageQueryLine(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}


	/**
	 * 选择单个活动
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectOneActivity")
	public String selectOneActivity() throws Exception {
		return "/system/resource/forSelect/selectOneActivity";
	}

	/**
	 * 查询活动
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryActivityForSelect")
	public @ResponseBody ResponseVo queryActivityForSelect(ActivityListQuery query) throws Exception {
		Integer pageNumber = getInteger("pageNumber");
		if (pageNumber != null) {
			query.setPageNumber(pageNumber);
		}
		int totalCount = 0;
		ActivityQueryDTO activityInfoDTO = convertQuery(query);
		PageVO pageVo = null;
		BasePageResult<SnsActivityDO> ret = activityService.getList(activityInfoDTO);
		if(ret != null & ret.isSuccess()){
			pageVo = new PageVO(activityInfoDTO.getPageNo(), query.getPageSize(), totalCount, ret.getList());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageVo", pageVo);
		result.put("query", query);
		return new ResponseVo(result);
	}

	private ActivityQueryDTO convertQuery(ActivityListQuery query) {
		ActivityQueryDTO activityQueryDTO = new ActivityQueryDTO();
		if (query == null) {
			return activityQueryDTO;
		}
		if (query.getPageNumber() != null) {
			activityQueryDTO.setPageNo(query.getPageNumber());
		}
		if (query.getPageSize() != null) {
			activityQueryDTO.setPageSize(query.getPageSize());
		}
		activityQueryDTO.setTitle(query.getTitle());
//		activityQueryDTO.set
		if (query.getProductId() != null) {
			activityQueryDTO.setClubId(query.getProductId());
		}
		if (StringUtils.isNotBlank(query.getActivityBeginDate())) {
			Date startTime = DateUtil.parseDate(query.getActivityBeginDate());
			activityQueryDTO.setActivityStartTime(startTime);
		}
		if (StringUtils.isNotBlank(query.getActivityEndDate())) {
			Date endTime = DateUtil.parseDate(query.getActivityEndDate());
			activityQueryDTO.setActivityEndTime(DateUtil.add23Hours(endTime));
		}
		if (StringUtils.isNotBlank(query.getCreateStartTime())) {
			Date startTime = DateUtil.parseDate(query.getCreateStartTime());
			activityQueryDTO.setStartTime(startTime);
		}
		if (StringUtils.isNotBlank(query.getCreateEndTime())) {
			Date endTime = DateUtil.parseDate(query.getCreateEndTime());
			activityQueryDTO.setEndTime(DateUtil.add23Hours(endTime));
		}
		if (query.getStatus() != null) {
			activityQueryDTO.setState(query.getStatus());
		}
		return activityQueryDTO;
	}


}
