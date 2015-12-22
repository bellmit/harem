package com.yimayhd.harem.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.base.BaseController;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.ResponseStatus;
import com.yimayhd.harem.model.HotelFacilityVO;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.CommodityService;
import com.yimayhd.harem.service.FacilityIconService;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.harem.service.RegionService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.domain.share_json.NeedKnow;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

/**
 * 酒店管理（资源）
 * 
 * @author czf
 */
@Controller
@RequestMapping("/B2C/hotelManage")
public class HotelManageController extends BaseController {
	private final static int ROOMFACILITY_TYPE = 1;
	private final static int ROOMSERVICELIST_TYPE = 2;
	private final static int HOTELFACILITYLIST_TYPE = 3;

	@Autowired
	private ItemQueryService itemQueryService;

	@Autowired
	private HotelService hotelService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private FacilityIconService facilityIconService;
	@Autowired
	private CommodityService commodityService;

	@Autowired
	private HotelRPCService hotelRPCService;

	/**
	 * 酒店（资源）列表
	 * 
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HotelListQuery hotelListQuery)
			throws Exception {

		PageVO<HotelDO> pageVo = hotelRPCService.pageQueryHotel(hotelListQuery);
		List<HotelDO> hotelDOList = pageVo.getItemList();

		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", hotelDOList);
		return "/system/hotel/list";
	}

	/**
	 * 新增酒店（资源）
	 * 
	 * @return 酒店（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public String toAdd(Model model) throws Exception {

		// 房间设施
		List<HotelFacilityVO> roomFacilityList = hotelRPCService
				.queryFacilities(1);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService
				.queryFacilities(2);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService
				.queryFacilities(3);

		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);
		return "/system/hotel/add3";
	}
	
	/**
	 * 新增酒店（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HotelVO hotelVO, String roomFacilityStr,
			String roomServiceStr, String hotelFacilityStr,
			MasterRecommend recommend,
			NeedKnow needKnow,
			String name2) throws Exception {
		
		long roomFacility = Long.parseLong(new StringBuilder(roomFacilityStr).reverse().toString(), 2);
		long roomService = Long.parseLong(new StringBuilder(roomServiceStr).reverse().toString(), 2);
		long hotelFacility = Long.parseLong(new StringBuilder(hotelFacilityStr).reverse().toString(), 2);
		
		recommend.setName(name2);
		String jsonString = JSON.toJSONString(recommend);
		
		String jsonNeedKnow = JSON.toJSONString(needKnow);
		
		//hotelVO.setNeedKnow(jsonNeedKnow);
		hotelVO.setRecommend(jsonString);
		hotelVO.setRoomFacility(roomFacility);
		hotelVO.setRoomService(roomService);
		hotelVO.setHotelFacility(hotelFacility);
				
		hotelRPCService.addHotel(hotelVO);

		return "/success";
	}

	/**
	 * 酒店（资源）状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatus(@PathVariable("id") long id,
			int hotelStatus) throws Exception {

		HotelDO hotelDO = new HotelDO();
		hotelDO.setId(id);
		hotelDO.setStatus(hotelStatus);

		ICResult<Boolean> icResult = hotelRPCService.updateHotelStatus(hotelDO);

		ResponseVo responseVo = new ResponseVo();
		if (!icResult.getModule()) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}

	/**
	 * 检查设施是否被选中
	 */
	private void checkInit(List<HotelFacilityVO> list, char[] arr) {

		for (int i = 0; i < list.size(); i++) {

			if (i <= arr.length - 1) {

				char tempChar = arr[i];
				boolean tempResult = (tempChar == '1');
				list.get(i).setChecked(tempResult);

			} else {
				continue;
			}

		}

	}

	/**
	 * 编辑酒店（资源）
	 * 
	 * @return 酒店（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id)
			throws Exception {

		HotelVO hotelVO = hotelRPCService.getHotel(id);
		MasterRecommend recommend = hotelVO.getMasterRecommend();
		long roomFacility = hotelVO.getRoomFacility();
		long hotelFacility = hotelVO.getHotelFacility();
		long roomService = hotelVO.getRoomService();

		/**
		 * 处理酒店设施 开始
		 */
		String roomFacilityStr = Long.toBinaryString(roomFacility);
		String hotelFacilityStr = Long.toBinaryString(hotelFacility);
		String roomServiceStr = Long.toBinaryString(roomService);

		roomFacilityStr = new StringBuffer(roomFacilityStr).reverse()
				.toString();
		hotelFacilityStr = new StringBuffer(hotelFacilityStr).reverse()
				.toString();
		roomServiceStr = new StringBuffer(roomServiceStr).reverse().toString();
		/**
		 * 处理酒店设施 结束
		 */

		char[] roomFacilityArr = roomFacilityStr.toCharArray();
		char[] hotelFacilityArr = hotelFacilityStr.toCharArray();
		char[] roomServiceArr = roomServiceStr.toCharArray();

		// 房间设施
		List<HotelFacilityVO> roomFacilityList = hotelRPCService
				.queryFacilities(1);
		Collections.sort(roomFacilityList);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService
				.queryFacilities(2);
		Collections.sort(roomServiceList);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService
				.queryFacilities(3);
		Collections.sort(hotelFacilityList);

		checkInit(roomFacilityList, roomFacilityArr);
		checkInit(roomServiceList, roomServiceArr);
		checkInit(hotelFacilityList, hotelFacilityArr);

		/*
		 * List<FacilityIconDO> roomFacilityList = facilityIconService
		 * .getListByType(ROOMFACILITY_TYPE); List<FacilityIconDO>
		 * roomServiceList = facilityIconService
		 * .getListByType(ROOMSERVICELIST_TYPE); List<FacilityIconDO>
		 * hotelFacilityList = facilityIconService
		 * .getListByType(HOTELFACILITYLIST_TYPE);
		 */
		model.addAttribute("hotel", hotelVO);
		// model.addAttribute("pictureList", hotelVO.getPictureList());
		model.addAttribute("recommend", recommend);
		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);

		return "/system/hotel/edit";
	}

	/**
	 * ------------------------------未添加的--------------------------------------
	 * --------------
	 */

	/**
	 * 酒店（资源）列表
	 * 
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	/*
	 * @RequestMapping(value = "/list", method = RequestMethod.GET) public
	 * String list(Model model,HotelListQuery hotelListQuery) throws Exception {
	 * List<HotelDO> hotelDOList = hotelService.getList(hotelListQuery); PageVO
	 * pageVo = new PageVO(1,10,300); model.addAttribute("pageVo", pageVo);
	 * model.addAttribute("hotelListQuery", hotelListQuery);
	 * model.addAttribute("hotelDOList", hotelDOList); return
	 * "/system/hotel/list"; }
	 */
	/**
	 * 选择酒店列表
	 * 
	 * @param model
	 * @param hotelListQuery
	 * @param multiSelect
	 *            是否多选（1：单选；2：多选）
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	public String selectList(Model model, HotelListQuery hotelListQuery,
			int multiSelect) throws Exception {
		List<HotelDO> hotelDOList = hotelService.getList(hotelListQuery);
		PageVO pageVo = new PageVO(1, 10, 300);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", hotelDOList);
		model.addAttribute("multiSelect", multiSelect);
		return "/system/hotel/selectList";
	}

	/**
	 * 编辑酒店（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
	public String edit(HotelVO hotelVO, @PathVariable("id") long id)
			throws Exception {
		hotelVO.setId(id);
		hotelService.modify(hotelVO);
		return "/success";
	}

	/**
	 * 酒店状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatusList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatusList(
			@RequestParam("hotelIdList[]") ArrayList<Long> hotelIdList,
			int hotelStatus) throws Exception {
		hotelService.setHotelStatusList(hotelIdList, hotelStatus);
		return new ResponseVo();
	}

	// TODO
	/**
	 * 添加图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/picture/add/{hotelId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseVo addHotelPicture(@PathVariable("hotelId") long id,
			ArrayList<String> pictureList) throws Exception {
		return new ResponseVo();
	}

	/**
	 * 删除图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/picture/delete/{hotelId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo delHotelPicture(@PathVariable("hotelId") long id)
			throws Exception {
		return new ResponseVo();
	}

	/**
	 * 置顶图片
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/picture/top/{hotelId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo topHotelPicture(@PathVariable("hotelId") long id)
			throws Exception {
		return new ResponseVo();
	}

}
