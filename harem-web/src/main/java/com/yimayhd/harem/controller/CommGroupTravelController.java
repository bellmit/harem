package com.yimayhd.harem.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yimayhd.harem.base.BaseTravelController;
import com.yimayhd.harem.base.ResponseVo;
import com.yimayhd.harem.constant.B2CConstant;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.travel.groupTravel.GroupTravel;
import com.yimayhd.harem.model.travel.groupTravel.TripDay;
import com.yimayhd.harem.model.travel.groupTravel.TripTraffic;
import com.yimayhd.harem.service.CommTravelService;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.harem.service.PictureRPCService;
import com.yimayhd.harem.service.RestaurantRPCService;
import com.yimayhd.harem.service.ScenicService;
import com.yimayhd.harem.service.TfsService;
import com.yimayhd.ic.client.model.domain.PicturesDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.share_json.RouteItemDetail;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.ic.client.model.enums.PictureOutType;
import com.yimayhd.ic.client.model.enums.RouteItemType;

/**
 * 商品-跟团游
 * 
 * @author yebin 2015年11月23日
 *
 */
@Controller
@RequestMapping("/B2C/comm/groupTravel")
public class CommGroupTravelController extends BaseTravelController {
	private static final int PICTURE_MAX_SIZE = 6;
	@Autowired
	private CommTravelService travelService;
	@Autowired
	private TfsService tfsService;
	@Autowired
	private RestaurantRPCService restaurantRPCService;
	@Autowired
	private ScenicService scenicService;
	@Autowired
	private HotelRPCService hotelRPCService;
	@Autowired
	private PictureRPCService pictureRPCService;

	/**
	 * 详细信息页
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(long categoryId, @PathVariable(value = "id") long id) throws Exception {
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		if (id > 0) {
			GroupTravel gt = travelService.getById(id, GroupTravel.class);
			put("product", gt);
			String importantInfos = tfsService.readHtml5(gt.getPriceInfo().getImportantInfosCode());
			put("importantInfos", importantInfos);
			String extraInfos = tfsService.readHtml5(gt.getBaseInfo().getNeedKnow().getExtraInfoUrl());
			put("extraInfos", extraInfos);
			put("lineType", LineType.getByType(gt.getBaseInfo().getType()));
		}
		return "/system/comm/travel/detail";
	}

	/**
	 * 创建跟团游
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(long categoryId) throws Exception {
		initBaseInfo();
		initLinePropertyTypes(categoryId);
		put("lineType", LineType.REGULAR_LINE);
		return "/system/comm/travel/detail";
	}

	/**
	 * 批量录入
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/batchInsert")
	public String batchInsert(long categoryId) throws Exception {
		initLinePropertyTypes(categoryId);
		return "/system/comm/travel/batchInsert";
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save")
	public @ResponseBody ResponseVo save(String json, String importantInfos, String extraInfos) throws Exception {
		GroupTravel gt = (GroupTravel) JSONObject.parseObject(json, GroupTravel.class);
		if (StringUtils.isNotBlank(importantInfos)) {
			String importantInfosCode = tfsService.publishHtml5(importantInfos);
			gt.getPriceInfo().setImportantInfosCode(importantInfosCode);
		} else {
			gt.getPriceInfo().setImportantInfosCode(B2CConstant.DEFAULT_CONTRACT_TFS_CODE);
		}
		if (StringUtils.isNotBlank(extraInfos)) {
			String extraInfosCode = tfsService.publishHtml5(extraInfos);
			gt.getBaseInfo().getNeedKnow().setExtraInfoUrl(extraInfosCode);
		}
		// 详情
		for (TripDay tripDay : gt.getTripInfo()) {
			long rId = tripDay.getRestaurantDetailId();
			if (rId > 0) {
				RestaurantDO restaurant = restaurantRPCService.getRestaurantById(rId);
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(restaurant.getId());
				detail.setType(RouteItemType.RESTAURANT.name());
				detail.setName(restaurant.getName());
				detail.setShortDesc(restaurant.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRPCService.queryTopPictureList(PictureOutType.RESTAURANT, rId,
						PICTURE_MAX_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setRestaurantDetail(detail);
			}
			long sId = tripDay.getScenicDetailId();
			if (sId > 0) {
				ScenicDO scenic = scenicService.getById(sId).getScenic();
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(scenic.getId());
				detail.setType(RouteItemType.SCENIC.name());
				detail.setName(scenic.getName());
				detail.setShortDesc(scenic.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRPCService.queryTopPictureList(PictureOutType.SCENIC, sId,
						PICTURE_MAX_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setScenicDetail(detail);
			}
			long hId = tripDay.getHotelDetailId();
			if (hId > 0) {
				HotelVO hotel = hotelRPCService.getHotel(hId);
				RouteItemDetail detail = new RouteItemDetail();
				detail.setId(hotel.getId());
				detail.setType(RouteItemType.HOTEL.name());
				detail.setName(hotel.getName());
				detail.setShortDesc(hotel.getOneword());
				List<String> pics = new ArrayList<String>();
				List<PicturesDO> pictures = pictureRPCService.queryTopPictureList(PictureOutType.HOTEL, hId,
						PICTURE_MAX_SIZE);
				for (PicturesDO pictureDO : pictures) {
					pics.add(pictureDO.getPath());
				}
				detail.setPics(pics);
				tripDay.setHotelDetail(detail);
			}
		}
		long id = travelService.publishLine(gt);
		return new ResponseVo(id);
	}

	/**
	 * 选择交通
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectTraffic")
	public String selectTraffic() throws Exception {
		put("ways", TripTraffic.ways());
		return "/system/comm/travel/groupTravel/selectTraffic";
	}
}
