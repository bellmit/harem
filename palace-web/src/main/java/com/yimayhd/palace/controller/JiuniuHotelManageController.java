package com.yimayhd.palace.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.item.HotelFeature;
import com.yimayhd.ic.client.model.enums.FacilityIconType;
import com.yimayhd.ic.client.model.enums.RoomExtraBed;
import com.yimayhd.ic.client.model.enums.RoomNetwork;
import com.yimayhd.ic.client.model.enums.RoomWindow;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.palace.base.AreaService;
import com.yimayhd.palace.base.BaseController;
import com.yimayhd.palace.base.BaseException;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.base.ResponseVo;
import com.yimayhd.palace.constant.Constant;
import com.yimayhd.palace.constant.ResponseStatus;
import com.yimayhd.palace.model.AreaVO;
import com.yimayhd.palace.model.DestinationVO;
import com.yimayhd.palace.model.HotelFacilityVO;
import com.yimayhd.palace.model.HotelVO;
import com.yimayhd.palace.model.RoomVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.DestinationQuery;
import com.yimayhd.palace.model.query.HotelListQuery;
import com.yimayhd.palace.service.DestinationRPCService;
import com.yimayhd.palace.service.HotelRPCService;
import com.yimayhd.palace.tair.TcCacheManager;
import com.yimayhd.resourcecenter.domain.DestinationDO;
import com.yimayhd.resourcecenter.model.enums.DestinationLevel;
import com.yimayhd.resourcecenter.model.result.RcResult;
import com.yimayhd.user.client.enums.DestinationOutType;
import com.yimayhd.user.session.manager.SessionManager;

/**
 * 酒店管理（资源）
 * 
 * @author ghf
 */
@Controller
@RequestMapping("/jiuniu/hotelManage")
public class JiuniuHotelManageController extends BaseController {

	@Autowired
	private HotelRPCService hotelRPCService;
	
	@Autowired
	private DestinationRPCService destinationRPCService;
	
	@Autowired
	private TcCacheManager	tcCacheManager;
	@Autowired
	private SessionManager sessionManager;

	/**
	 * 酒店（资源）列表
	 * 
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HotelListQuery hotelListQuery) throws Exception {
		
		hotelListQuery.setDomain(Constant.DOMAIN_JIUXIU);
		PageVO<HotelDO> pageVo = hotelRPCService.pageQueryHotel(hotelListQuery);
		List<HotelDO> hotelDOList = pageVo.getItemList();

		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", hotelDOList);
		return "/system/hotel/list-jiuniu";
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
		List<HotelFacilityVO> roomFacilityList = hotelRPCService.queryFacilities(1);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService.queryFacilities(2);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService.queryFacilities(3);
		//省
		List<AreaVO> provinceList= AreaService.getInstance().getAreaByIDAndType("PROVINCE", null);

		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("UUIDHotel", UUID.randomUUID().toString());
		model.addAttribute("UUIDPicText", UUID.randomUUID().toString());
		
		return "/system/hotel/edit-jiuniu";
	}

	/**
	 * 新增酒店（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo add(HotelVO hotelVO, String uuidHotel) throws Exception {
		
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuidHotel;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {
				hotelVO.setDomain(Constant.DOMAIN_JIUXIU);
				ICResult<HotelVO> result = hotelRPCService.addHotelV2(hotelVO);
				return  ResponseVo.success(result.getModule());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ResponseVo.error(e);
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}

	/**
	 * 酒店（资源）状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatus(@PathVariable("id") long id, int hotelStatus) throws Exception {

		HotelDO hotelDO = new HotelDO();
		hotelDO.setId(id);
		hotelDO.setStatus(hotelStatus);

		ICResult<Boolean> icResult = hotelRPCService.updateHotelStatus(hotelDO);

		ResponseVo responseVo = new ResponseVo();
		if (icResult == null || !icResult.getModule()) {
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
	 * @return 酒店（资源）编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String toEdit(Model model, @PathVariable(value = "id") long id) throws Exception {

		HotelVO hotelVO = hotelRPCService.getHotelV2(id);
		if (hotelVO == null) {
			return "/system/hotel/edit-jiuniu";
		}
		
		/**
		long roomFacility = hotelVO.getRoomFacility();
		long hotelFacility = hotelVO.getHotelFacility();
		long roomService = hotelVO.getRoomService();
		**/
		
		HotelFeature feature = hotelVO.getFeature();
		
		if(feature != null && feature.getTradeArea() != null){
			hotelVO.setTradeAreaList(feature.getTradeArea());
		}
		
		List<Long> list = null;
		long roomFacility = 0;
		if(feature != null){
			list = feature.getRoomFacility();
			if(list != null && list.size() > 0){
				roomFacility = list.get(0);
			}
		}
		
		long hotelFacility = 0;
		if(feature != null){
			list = feature.getHotelFacility();
			if(list != null && list.size() > 0){
				hotelFacility = list.get(0);
			}
		}
		
		long roomService = 0;
		if(feature != null){
			list = feature.getRoomFacility();
			if(list != null && list.size() > 0){
				roomService = list.get(0);
			}
		}

		/**
		 * 处理酒店设施 开始
		 */
		String roomFacilityStr = Long.toBinaryString(roomFacility);
		String hotelFacilityStr = Long.toBinaryString(hotelFacility);
		String roomServiceStr = Long.toBinaryString(roomService);

		roomFacilityStr = new StringBuffer(roomFacilityStr).reverse().toString();
		hotelFacilityStr = new StringBuffer(hotelFacilityStr).reverse().toString();
		roomServiceStr = new StringBuffer(roomServiceStr).reverse().toString();
		/**
		 * 处理酒店设施 结束
		 */

		char[] roomFacilityArr = roomFacilityStr.toCharArray();
		char[] hotelFacilityArr = hotelFacilityStr.toCharArray();
		char[] roomServiceArr = roomServiceStr.toCharArray();

		// 房间设施
		List<HotelFacilityVO> roomFacilityList = hotelRPCService.queryFacilitiesV2(FacilityIconType.ROOM_FACILITY.getType());
		Collections.sort(roomFacilityList);
		// 特色服务
		List<HotelFacilityVO> roomServiceList = hotelRPCService.queryFacilitiesV2(FacilityIconType.HOTEL_SERVICE.getType());
		Collections.sort(roomServiceList);
		// 酒店设施
		List<HotelFacilityVO> hotelFacilityList = hotelRPCService.queryFacilitiesV2(FacilityIconType.HOTEL_FACILITY.getType());
		Collections.sort(hotelFacilityList);

		checkInit(roomFacilityList, roomFacilityArr);
		checkInit(roomServiceList, roomServiceArr);
		checkInit(hotelFacilityList, hotelFacilityArr);

		//省
		List<AreaVO> provinceList= AreaService.getInstance().getAreaByIDAndType("PROVINCE", null);
		//市
		List<AreaVO> cityList= AreaService.getInstance().getAreaByIDAndType("CITY", String.valueOf(hotelVO.getLocationProvinceId()));
		//区
		List<AreaVO> townList= AreaService.getInstance().getAreaByIDAndType("COUNTY", String.valueOf(hotelVO.getLocationCityId()));

		model.addAttribute("hotel", hotelVO);
		model.addAttribute("roomFacilityList", roomFacilityList);
		model.addAttribute("roomServiceList", roomServiceList);
		model.addAttribute("hotelFacilityList", hotelFacilityList);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("townList", townList);
		model.addAttribute("pictureList", hotelVO.getPictureList());
		model.addAttribute("UUIDHotel", UUID.randomUUID().toString());
		model.addAttribute("UUIDPicText", UUID.randomUUID().toString());
		return "/system/hotel/edit-jiuniu";
	}


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
	public String selectList(Model model, HotelListQuery hotelListQuery, int multiSelect) throws Exception {
		PageVO<HotelDO> pageVo = hotelRPCService.pageQueryHotel(hotelListQuery);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("hotelListQuery", hotelListQuery);
		model.addAttribute("hotelDOList", pageVo.getItemList());
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
	@ResponseBody
	public ResponseVo edit(HotelVO hotelVO, @PathVariable("id") long id, String uuidHotel) throws Exception {
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuidHotel;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {
				hotelVO.setId(id);
				hotelVO.setDomain(Constant.DOMAIN_JIUXIU);
				hotelRPCService.updateHotelV2(hotelVO);
				return  ResponseVo.success(hotelVO);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ResponseVo.error(e);
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}

	/**
	 * 酒店状态变更(批量)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setHotelStatusList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setHotelStatusList(@RequestParam("hotelIdList[]") ArrayList<Long> hotelIdList, int hotelStatus)
			throws Exception {
		hotelRPCService.setHotelStatusList(hotelIdList, hotelStatus);
		return new ResponseVo();
	}
	
	/**
	 * 根据城市获得商圈
	 * @param areaType
	 * @param areaParentCode
	 * @return
	 */
	@RequestMapping(value = "/destination", method = RequestMethod.GET)
	@ResponseBody
	public ResponseVo getDestination(DestinationQuery destinationQuery) {
		
		try {
			destinationQuery.setLevel(DestinationLevel.COMMERCIAL_AREA.getCode());
			destinationQuery.setOutType(DestinationOutType.HOTEL.getCode());
			RcResult<List<DestinationDO>> result = destinationRPCService.queryDestinationList(destinationQuery);
			
			List<DestinationVO> VOList = new ArrayList<DestinationVO>();
			if(result != null){
				VOList = DestinationVO.getDestinationVO(result.getT());
			}
			return  ResponseVo.success(VOList);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseVo.error(e);
		}
	}
	
	/**
	 * 酒店（资源）列表
	 * 
	 * @return 酒店（资源）列表
	 * @throws Exception
	 */
	@RequestMapping(value = "/roomList/{hotelId}", method = RequestMethod.GET)
	public String roomList(Model model, @PathVariable(value = "hotelId") long hotelId) throws Exception {
				
		ICResult<List<RoomDO>> result = hotelRPCService.queryAllRoom(hotelId);
		if(result != null){
			model.addAttribute("roomList", result.getModule());
		}
		model.addAttribute("hotelId", hotelId);
		return "/system/hotel/room-list";
	}
	
	/**
	 * 新增酒店（资源）
	 * 
	 * @return 酒店（资源）详情
	 * @throws Exception
	 */
	@RequestMapping(value = "/toAddRoom", method = RequestMethod.GET)
	public String toAddRoom(Model model, long hotelId) throws Exception {
		
		model.addAttribute("hotelId", hotelId);
		model.addAttribute("network", RoomNetwork.values());
		model.addAttribute("window", RoomWindow.values());
		model.addAttribute("extraBed", RoomExtraBed.values());
		model.addAttribute("UUID", UUID.randomUUID().toString());
		
		return "/system/hotel/room-edit";
	}
	
	/**
	 * 新增酒店房型（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addRoom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo addRoom(RoomVO roomVO, String uuid) throws Exception {
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuid;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {
				roomVO.setDomain(Constant.DOMAIN_JIUXIU);
				ICResult<RoomDO> result = hotelRPCService.addRoom(roomVO);
				return  ResponseVo.success(result.getModule());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ResponseVo.error(e);
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}
	
	/**
	 * 编辑酒店（资源）
	 * 
	 * @return 酒店（资源）编辑
	 * @throws Exception
	 */
	@RequestMapping(value = "/editRoom/{id}", method = RequestMethod.GET)
	public String toEditRoom(Model model, @PathVariable(value = "id") long id) throws Exception {
		
		model.addAttribute("network", RoomNetwork.values());
		model.addAttribute("window", RoomWindow.values());
		model.addAttribute("extraBed", RoomExtraBed.values());
		model.addAttribute("UUID", UUID.randomUUID().toString());
		
		ICResult<RoomVO> result = hotelRPCService.getRoom(id);
		
		if(result.isSuccess()){
			model.addAttribute("room", result.getModule());
			model.addAttribute("hotelId", result.getModule().getHotelId());
		}
		
		return "/system/hotel/room-edit";
	}
	
	/**
	 * 编辑酒店房型（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editRoom/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo editRoom(RoomVO roomVO, @PathVariable("id") long id, String uuid) throws Exception {
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuid;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {	
				roomVO.setId(id);
				ICResultSupport result = hotelRPCService.updateRoom(roomVO);
				return  ResponseVo.success(result.getResultMsg());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ResponseVo.error(e);
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}
	
	/**
	 * 酒店房型（资源）状态变更
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/setRoomStatus/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo setRoomStatus(@PathVariable("id") long id, int status) throws Exception {

		RoomVO roomVO = new RoomVO();
		roomVO.setId(id);
		roomVO.setStatus(status);

		ICResultSupport icResult = hotelRPCService.updateRoomStatus(roomVO);

		ResponseVo responseVo = new ResponseVo();
		if (icResult == null || !icResult.isSuccess()) {
			responseVo.setStatus(ResponseStatus.ERROR.VALUE);
		}

		return responseVo;
	}
	
	/**
	 * 保存景区图文详情（资源）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/savePictureText/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo savePictureText(@PathVariable("id") long id, String json, String uuidPicText) throws Exception {
		
		String key = Constant.APP+"_repeat_"+sessionManager.getUserId() + uuidPicText;
		boolean rs = tcCacheManager.addToTair(key, true , 2, 24*60*60);
		if(rs){
			try {
				
				if (StringUtils.isBlank(json)) {
					log.warn("json is null");
					return ResponseVo.error();
				}
				
				json = json.replaceAll("\\s*\\\"\\s*", "\\\"");
				PictureTextVO pictureTextVO = (PictureTextVO) JSONObject.parseObject(json, PictureTextVO.class);
				
				hotelRPCService.savePictureText(id, pictureTextVO);
				return  ResponseVo.success();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return ResponseVo.error(e);
			}
		}
		return ResponseVo.error(new BaseException(Constant.UN_REPEAT_SUBMIT));
	}
}
