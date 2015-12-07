package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.harem.model.travel.IdNamePair;
import com.yimayhd.ic.client.model.domain.LineDO;
import com.yimayhd.ic.client.model.domain.share_json.HotelInfo;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 跟团游
 * 
 * @author yebin
 *
 */
public class SelfServiceTravel extends BaseTravel {

	private TripPackageInfo tripPackageInfo;// 套餐信息

	@Override
	protected void parseTripInfo(LineResult lineResult) {
		LineDO line = lineResult.getLineDO();
		// 航班
		this.tripPackageInfo.setHasFlight(StringUtils.isNotBlank(line.getFlightDetail()) ? 1 : 0);
		// 酒店
		List<IdNamePair> hotels = new ArrayList<IdNamePair>();
		List<HotelInfo> hotelInfos = JSON.parseArray(line.getHotels(), HotelInfo.class);
		if (CollectionUtils.isNotEmpty(hotelInfos)) {
			for (HotelInfo hotelInfo : hotelInfos) {
				hotels.add(new IdNamePair(hotelInfo.getId(), hotelInfo.getName()));
			}
		}
	}

	public TripPackageInfo getTripPackageInfo() {
		return tripPackageInfo;
	}

	public void setTripPackageInfo(TripPackageInfo tripPackageInfo) {
		this.tripPackageInfo = tripPackageInfo;
	}

}
