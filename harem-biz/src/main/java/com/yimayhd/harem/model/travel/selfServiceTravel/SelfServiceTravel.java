package com.yimayhd.harem.model.travel.selfServiceTravel;

import com.yimayhd.harem.model.travel.BaseTravel;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
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
		// TODO Auto-generated method stub

	}

	@Override
	public ItemDO getItemDO() {
		// TODO Auto-generated method stub
		return null;
	}
}
