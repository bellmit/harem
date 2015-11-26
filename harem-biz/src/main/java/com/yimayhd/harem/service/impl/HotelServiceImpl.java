package com.yimayhd.harem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RestaurantDO;

/**
 * Created by Administrator on 2015/11/18.
 */
public class HotelServiceImpl implements HotelService {
	private List<HotelDO> table = new ArrayList<HotelDO>();

	public HotelServiceImpl() {
		for (long i = 0; i < 100; i++) {
			try {
				table.add(getById(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<HotelDO> getList(HotelListQuery hotelListQuery) throws Exception {
		List<HotelDO> hotelDOList = new ArrayList<HotelDO>();
		for (int i = 0; i < 10; i++) {
			HotelDO hotelDOData = new HotelDO();
			hotelDOData.setId(i);
			hotelDOData.setName("温德姆至尊豪廷大酒店");
			hotelDOData.setOneword("是个不错的酒店");
			hotelDOData.setDescription("至尊豪，真的是个不错的酒店");
			hotelDOData.setPhoneNum("[\"18618161816\",\"18618161817\",\"18618161819\"]");
			hotelDOData.setLevel(4);
			hotelDOData.setRecommend("希望更豪华");
			// hotelDOData.setRoomFacility("1,2,3");
			// hotelDOData.setRoomService("2,3");
			// hotelDOData.setHotelFacility("1,2,3");
			hotelDOData.setLogoUrl("123.png");
			hotelDOData.setPictures("[\"123.png\",\"2.png\",\"3.png\"]");
			hotelDOData.setLocationX(1000.555);
			hotelDOData.setLocationY(2000.666);
			hotelDOData.setLocationText("海淀区");
			hotelDOData.setLocationProvinceId(1);
			hotelDOData.setLocationProvinceName("云南");
			hotelDOData.setLocationCityId(11);
			hotelDOData.setLocationCityName("昆明");
			hotelDOData.setGmtCreated(new Date());
			hotelDOData.setGmtModified(new Date());
			hotelDOData.setStatus(1);
			hotelDOData.setPrice(5600);
			hotelDOData.setContactPerson("张三");
			hotelDOData.setContactPhone("18039262076");
			hotelDOData.setLogoUrl("logo.png");
			hotelDOData.setCoverUrl("main.png");
			hotelDOList.add(hotelDOData);
		}
		return hotelDOList;
	}

	@Override
	public HotelVO getById(long id) throws Exception {
		HotelDO hotelDOData = new HotelDO();
		hotelDOData.setId(id);
		hotelDOData.setName("温德姆至尊豪廷大酒店" + id);
		hotelDOData.setOneword("是个不错的酒店");
		hotelDOData.setDescription("至尊豪，真的是个不错的酒店");
		hotelDOData.setPhoneNum("[\"18618161816\",\"18618161817\",\"18618161819\"]");
		hotelDOData.setLevel(2);
		hotelDOData.setRecommend("希望更豪华");
		// hotelDOData.setRoomFacility("1,2,3");
		// hotelDOData.setRoomService("2,3");
		// hotelDOData.setHotelFacility("1,2,3");
		hotelDOData.setLogoUrl("123.png");
		hotelDOData.setPictures("[\"123.png\",\"2.png\",\"3.png\"]");
		hotelDOData.setLocationX(1000.555);
		hotelDOData.setLocationY(2000.666);
		hotelDOData.setLocationText("海淀区");
		hotelDOData.setLocationProvinceId(10);
		hotelDOData.setLocationProvinceName("云南");
		hotelDOData.setLocationCityId(100);
		hotelDOData.setLocationCityName("昆明");
		hotelDOData.setGmtCreated(new Date());
		hotelDOData.setGmtModified(new Date());
		hotelDOData.setStatus(1);
		hotelDOData.setPrice(5600);
		hotelDOData.setOpenTime("[8,12,20]");
		hotelDOData.setContactPerson("张三");
		hotelDOData.setContactPhone("18039262076");
		hotelDOData.setLogoUrl("logo.png");
		hotelDOData.setCoverUrl("main.png");
		hotelDOData.setRecommend(
				"{\"name\":\"tini\",\"description\":\"很棒的酒店\",\"id\":1,\"user_id\":2,\"user_pic\":\"pic.png\"}");
		return HotelVO.getHotelVO(hotelDOData);
	}

	@Override
	public HotelDO add(HotelVO hotelVO) throws Exception {
		return null;
	}

	@Override
	public void modify(HotelVO hotelVO) throws Exception {

	}

	@Override
	public void setHotelStatusList(List<Long> idList, int hotelStatus) throws Exception {

	}

	@Override
	public void setHotelStatus(long id, int hotelStatus) throws Exception {

	}

	@Override
	public PageVO<HotelDO> getListByPage(HotelListQuery query) throws Exception {
		int totalCount = count(query);
		PageVO<HotelDO> page = new PageVO<HotelDO>(query.getPageNumber(), query.getPageSize(), totalCount);
		List<HotelDO> itemList = find(query);
		page.setItemList(itemList);
		return page;
	}

	protected Integer count(HotelListQuery query) throws Exception {
		return query(table, query).size();
	}

	protected List<HotelDO> find(HotelListQuery query) throws Exception {
		int fromIndex = query.getPageSize() * (query.getPageNumber() - 1);
		int toIndex = query.getPageSize() * query.getPageNumber();
		List<HotelDO> result = query(table, query);
		if (result.size() > 0) {
			if (toIndex > result.size()) {
				toIndex = result.size();
			}
			result = result.subList(fromIndex, toIndex);
		}
		return result;
	}

	private List<HotelDO> query(List<HotelDO> hotels, HotelListQuery query) {
		List<HotelDO> result = new ArrayList<HotelDO>();
		for (HotelDO hotel : hotels) {
			if (StringUtils.isNotBlank(query.getHotelName())) {
				if (hotel.getName().indexOf(query.getHotelName()) != -1) {
					result.add(hotel);
				}
			} else {
				result.add(hotel);
			}
		}
		return result;
	}
}
