package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HotelFacilityVO;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.result.ICResult;

public interface HotelRPCService {

	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery);
	
	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO);
	
	public ICResult<Boolean> addHotel(HotelVO hotelVO);
	
	public ICResult<Boolean> updateHotel(HotelVO hotelVO);
	
	public HotelVO getHotel(long id);
	
	public List<HotelFacilityVO> queryFacilities(int type);
}
