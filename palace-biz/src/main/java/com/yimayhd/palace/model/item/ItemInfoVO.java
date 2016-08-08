package com.yimayhd.palace.model.item;


/**
 * 商品信息
 * 
 * @author hognfei.guo
 *
 */
public class ItemInfoVO {
	
	private ItemVO itemVO;
	private IcMerchantVO icMerchantVO;

	private HotelVO hotelVO;//酒店vo
	private ScenicVO scenicVO;//景区vo
	private RoomVO roomVO;//酒店房间
	private TicketVO ticketVO;//景区门票


	public RoomVO getRoomVO() {
		return roomVO;
	}

	public ItemInfoVO setRoomVO(RoomVO roomVO) {
		this.roomVO = roomVO;
		return this;
	}

	public TicketVO getTicketVO() {
		return ticketVO;
	}

	public ItemInfoVO setTicketVO(TicketVO ticketVO) {
		this.ticketVO = ticketVO;
		return this;
	}

	public HotelVO getHotelVO() {
		return hotelVO;
	}

	public ItemInfoVO setHotelVO(HotelVO hotelVO) {
		this.hotelVO = hotelVO;
		return this;
	}

	public ScenicVO getScenicVO() {
		return scenicVO;
	}

	public ItemInfoVO setScenicVO(ScenicVO scenicVO) {
		this.scenicVO = scenicVO;
		return this;
	}

	public ItemVO getItemVO() {
		return itemVO;
	}
	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}
	public IcMerchantVO getIcMerchantVO() {
		return icMerchantVO;
	}
	public void setIcMerchantVO(IcMerchantVO icMerchantVO) {
		this.icMerchantVO = icMerchantVO;
	}
}
