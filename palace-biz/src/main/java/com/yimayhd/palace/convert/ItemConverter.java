package com.yimayhd.palace.convert;

import java.util.Arrays;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.domain.RoomDO;
import com.yimayhd.ic.client.model.domain.ScenicDO;
import com.yimayhd.ic.client.model.domain.TicketDO;
import com.yimayhd.ic.client.model.domain.item.ItemDTO;
import com.yimayhd.ic.client.model.domain.item.ItemInfo;
import com.yimayhd.ic.client.model.domain.person.IcMerchantInfo;
import com.yimayhd.ic.client.model.enums.ItemStatus;
import com.yimayhd.ic.client.model.param.item.ItemQryDTO;
import com.yimayhd.palace.model.ItemListQuery;
import com.yimayhd.palace.model.item.*;
import com.yimayhd.palace.util.ItemUtil;

/**
 * 商品转换器
 * 
 * @author yebin
 *
 */
public class ItemConverter {

	public static ItemQryDTO toItemQryDTO(ItemListQuery query) {
		if (query == null) {
			return null;
		}
		ItemQryDTO itemQryDTO = new ItemQryDTO();
		itemQryDTO.setName(query.getName());
		if (query.getItemId() != null) {
			itemQryDTO.setId(query.getItemId());
		}
		if (query.getStatus() != null) {
			itemQryDTO.setStatus(Arrays.asList(query.getStatus()));
		} else {
			itemQryDTO.setStatus(Arrays.asList(ItemStatus.create.getValue(), ItemStatus.invalid.getValue(),
					ItemStatus.valid.getValue()));
		}
		if (query.getItemType() != null) {
			itemQryDTO.setItemType(query.getItemType());
		}
		itemQryDTO.setBeginDate(query.getBeginDate());
		itemQryDTO.setEndDate(query.getEndDate());
		itemQryDTO.setPageNo(query.getPageNumber());
		itemQryDTO.setPageSize(query.getPageSize());
		itemQryDTO.setMerchantName(query.getMerchantName());
		return itemQryDTO;
	}

	public static ItemInfoVO toItemInfoVO(ItemInfo itemInfo) {
		if (itemInfo == null) {
			return null;
		}
		
		ItemDTO itemDTO = itemInfo.getItemDTO();
		ItemVO itemVO = new ItemVO();
		if(itemDTO != null){
			itemVO.setId(itemDTO.getId());
			List<String> itemMainPics = itemDTO.getItemMainPics();
			if (CollectionUtils.isNotEmpty(itemMainPics)) {
				itemVO.setPicture(itemMainPics.get(0));
			}
			itemVO.setName(itemDTO.getTitle());
			itemVO.setCode(itemDTO.getCode());
			itemVO.setPrice(itemDTO.getPrice());
			itemVO.setType(itemDTO.getItemType());
			itemVO.setStatus(itemDTO.getStatus());
			itemVO.setOperates(ItemUtil.getItemOperates(itemDTO.getItemType(), itemDTO.getStatus()));
		}
		
		IcMerchantInfo icMerchantInfo = itemInfo.getIcMerchantInfoInfo();
		IcMerchantVO icMerchantVO = new IcMerchantVO();
		if(icMerchantInfo != null){
			icMerchantVO.setUserId(icMerchantInfo.getUserId());
			icMerchantVO.setUserNick(icMerchantInfo.getUserNick());
			icMerchantVO.setUserAvatar(icMerchantInfo.getUserAvatar());
			
			icMerchantVO.setMerchantId(icMerchantInfo.getMerchantId());
			icMerchantVO.setMerchantName(icMerchantInfo.getMerchantName());
			icMerchantVO.setMerchantLogo(icMerchantInfo.getMerchantLogo());
		}
		ItemInfoVO itemInfoVo = new ItemInfoVO();


		//酒店信息
		HotelDO hotelDO= itemInfo.getHotelDO();
		RoomDO roomDO= itemInfo.getRoomDO();
		TicketDO ticketDO= itemInfo.getTicketDO();
		if(hotelDO!=null){
			itemVO.setPicture(hotelDO.getLogoUrl());
			itemInfoVo.setHotelVO(new HotelVO().setName(hotelDO.getName()));
		}
		//房间型号
		if(roomDO!=null){
			itemInfoVo.setRoomVO(new RoomVO().setName(roomDO.getName()));
		}
		//景区门票信息
		if(ticketDO!=null){
			itemInfoVo.setTicketVO(new TicketVO().setTitle(ticketDO.getTitle()));
		}
		// 景区信息
		ScenicDO scenicDO =  itemInfo.getScenicDO();
		if(scenicDO!=null){
			itemInfoVo.setScenicVO(new ScenicVO().setName(scenicDO.getName()));
		}
		itemInfoVo.setItemVO(itemVO);
		itemInfoVo.setIcMerchantVO(icMerchantVO);
		return itemInfoVo;
	}
}
