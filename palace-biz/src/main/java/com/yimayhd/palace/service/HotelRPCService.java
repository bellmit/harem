package com.yimayhd.palace.service;

import java.util.List;

import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.param.item.RoomDTO;
import com.yimayhd.ic.client.model.param.item.RoomStatusDTO;
import com.yimayhd.ic.client.model.result.ICResult;
import com.yimayhd.ic.client.model.result.ICResultSupport;
import com.yimayhd.palace.base.PageVO;
import com.yimayhd.palace.model.HotelFacilityVO;
import com.yimayhd.palace.model.HotelVO;
import com.yimayhd.palace.model.RoomVO;
import com.yimayhd.palace.model.line.pictxt.PictureTextVO;
import com.yimayhd.palace.model.query.HotelListQuery;

public interface HotelRPCService {

	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery)throws Exception;

	public ICResult<Boolean> updateHotelStatus(HotelDO hotelDO)throws Exception;

	public ICResult<Boolean> addHotel(HotelVO hotelVO)throws Exception;
	
	public ICResult<HotelVO> addHotelV2(HotelVO hotelVO)throws Exception;

	public ICResult<Boolean> updateHotel(HotelVO hotelVO)throws Exception;
	
	public ICResult<Boolean> updateHotelV2(HotelVO hotelVO)throws Exception;

	public HotelVO getHotel(long id)throws Exception;
	
	public HotelVO getHotelV2(long id)throws Exception;

	public List<HotelFacilityVO> queryFacilities(int type)throws Exception;
	
	public List<HotelFacilityVO> queryFacilitiesV2(int type)throws Exception;

	void setHotelStatusList(List<Long> idList, int hotelStatus) throws Exception;

	void setHotelStatus(long id, int hotelStatus) throws Exception;
	
	/**
	 * 获取酒店所属的房型列表
	 */
	public ICResult<List<RoomDO>> queryAllRoom(long hotelId) throws Exception;
	
    /**
     * 房型
     * @param roomDO
     * @return
     */
    public ICResult<RoomDO> addRoom(RoomVO roomVO) throws Exception;
    
    /**
     * 更新房型
     * @param roomDTO
     * @return
     */
    public ICResultSupport updateRoom(RoomVO roomVO) throws Exception;
    
    /**
	 * 获取房型详情
	 */
	public ICResult<RoomVO> getRoom(long id) throws Exception;
	
	/**
     * 更新房型状态
     * @param roomStatusDTO
     * @return
     */
    public ICResultSupport updateRoomStatus(RoomVO roomVO) throws Exception;
    
	/**
	 * 保存景区图文详情（资源）
	 * 
	 * @return
	 * @throws
	 */
	void savePictureText(long id, PictureTextVO pictureTextVO) throws Exception;
}
