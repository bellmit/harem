package com.yimayhd.harem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.query.HotelListQuery;
import com.yimayhd.harem.service.HotelRPCService;
import com.yimayhd.ic.client.model.domain.HotelDO;
import com.yimayhd.ic.client.model.query.HotelPageQuery;
import com.yimayhd.ic.client.model.result.ICPageResult;
import com.yimayhd.ic.client.service.item.ItemQueryService;

public class HotelRPCServiceImpl implements HotelRPCService {

    @Autowired
    private ItemQueryService itemQueryService;
	
	@Override
	public PageVO<HotelDO> pageQueryHotel(HotelListQuery hotelListQuery) {
		
		HotelPageQuery hotelPageQuery = new HotelPageQuery();
    	hotelPageQuery.setNeedCount(true);
		
		if (hotelListQuery.getPageNumber() != null) {

			int pageNumber = hotelListQuery.getPageNumber();
			int pageSize = hotelListQuery.getPageSize();
			int pageBegin = (pageNumber - 1) * pageSize;
			hotelListQuery.setPageBegin(pageBegin);
			hotelPageQuery.setPageBegin(pageBegin);
			hotelPageQuery.setPageSize(pageSize);

		}
		
		//酒店名称
		if (StringUtils.isNotEmpty(hotelListQuery.getName())) {			
			hotelPageQuery.setTags(hotelListQuery.getName());			
		}
		
		//酒店状态
		if (hotelListQuery.getHotelStatus() != 0) {			
			hotelPageQuery.setStatus(hotelListQuery.getHotelStatus());			
		}
		
		//区域
		
		//联系人
		if (StringUtils.isEmpty(hotelListQuery.getHotelNameOrTel())) {			
			hotelPageQuery.setPersonOrPhone(hotelListQuery.getHotelNameOrTel());			
		}
		
		//开始时间
		if (StringUtils.isEmpty(hotelListQuery.getBeginDate())) {			
			hotelPageQuery.setStartTime(hotelListQuery.getBeginDate());			
		}
		
		//结束时间
		if (StringUtils.isEmpty(hotelListQuery.getEndDate())) {			
			hotelPageQuery.setEndTime(hotelListQuery.getEndDate());			
		}
		
		ICPageResult<HotelDO> icPageResult = itemQueryService.pageQueryHotel(hotelPageQuery);
    	List<HotelDO> hotelDOList = icPageResult.getList();
    	
    	PageVO<HotelDO> pageVo = new PageVO<HotelDO>(hotelPageQuery.getPageNo(), hotelPageQuery.getPageSize(), icPageResult.getTotalCount(), hotelDOList);
		
		return pageVo;
	}

	
	
}
