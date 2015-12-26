package com.yimayhd.harem.service;

import java.util.List;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.HotelFacilityVO;
import com.yimayhd.harem.model.HotelVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.result.ICResult;

public interface HotelRPCService {

	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery)throws Exception;

	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO)throws Exception;

	public ICResult<Boolean> addHotel(HotelVO hotelVO)throws Exception;

	public ICResult<Boolean> updateHotel(HotelVO hotelVO)throws Exception;

	public HotelVO getHotel(long id)throws Exception;

	public List<HotelFacilityVO> queryFacilities(int type)throws Exception;

	void setHotelStatusList(List<Long> idList, int hotelStatus) throws Exception;

	void setHotelStatus(long id, int hotelStatus) throws Exception;
}
