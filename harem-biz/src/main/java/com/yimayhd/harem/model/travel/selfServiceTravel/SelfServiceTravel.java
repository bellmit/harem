package com.yimayhd.harem.model.travel.selfServiceTravel;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.share_json.FlightDetail;
import com.yimayhd.ic.client.model.domain.share_json.FlightInfo;
import com.yimayhd.ic.client.model.param.item.LinePublishDTO;
import com.yimayhd.ic.client.model.result.item.LineResult;

/**
 * 自由行
 * 
 * @author yebin
 *
 */
public class SelfServiceTravel extends BaseTravel {

	private TripPackageInfo tripPackageInfo;// 套餐信息

	@Override
	protected void parseTripInfo(LineResult lineResult) {
		this.tripPackageInfo = new TripPackageInfo(lineResult);
	}

	public TripPackageInfo getTripPackageInfo() {
		return tripPackageInfo;
	}

	public void setTripPackageInfo(TripPackageInfo tripPackageInfo) {
		this.tripPackageInfo = tripPackageInfo;
	}

	@Override
	public void setRouteInfo(LinePublishDTO dto) {
		List<FlightDetail> flightDetails = this.tripPackageInfo.getFlightDetails();
		if (CollectionUtils.isNotEmpty(flightDetails)) {
			FlightDetail flightDetail = flightDetails.get(0);
			FlightInfo flightInfo = new FlightInfo();
			flightInfo.setForwardArriveDate(flightDetail.getForwardArriveDate());
			flightInfo.setForwardArriveCity(flightDetail.getForwardArriveCity());
			// flightInfo.setForwardArriveTime(flightDetail.getForwardArriveTime());
			flightInfo.setForwardDepartDate(flightDetail.getForwardDepartDate());
			flightInfo.setForwardDepartCity(flightDetail.getForwardDepartCity());
			// flightInfo.setForwardDepartTime(flightDetail.getForwardDepartTime());
			flightInfo.setReturnArriveDate(flightDetail.getReturnArriveDate());
			flightInfo.setReturnArriveCity(flightDetail.getReturnArriveCity());
			// flightInfo.setReturnArriveTime(flightDetail.getReturnArriveTime());
			flightInfo.setReturnDepartDate(flightDetail.getReturnDepartDate());
			flightInfo.setReturnDepartCity(flightDetail.getReturnDepartCity());
			// flightInfo.setReturnDepartTime(flightDetail.getReturnDepartTime());
			flightInfo.setMemo("");
			dto.getLineDO().setFlights(JSON.toJSONString(flightInfo));
		}
		dto.getLineDO().setFlightDetail(JSON.toJSONString(flightDetails));
		dto.getLineDO().setHotels(JSON.toJSONString(this.tripPackageInfo.getHotels()));
	}

	@Override
	public ItemDO getItemDO() {
		// TODO Auto-generated method stub
		return null;
	}
}
