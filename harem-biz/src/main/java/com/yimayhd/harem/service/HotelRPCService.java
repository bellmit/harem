package com.yimayhd.harem.service;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.ic.client.model.domain.HotelDO;

public interface HotelRPCService {

	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery);
	
}
