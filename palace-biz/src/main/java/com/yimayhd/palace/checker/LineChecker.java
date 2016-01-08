package com.yimayhd.palace.checker;

import java.util.List;

import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.model.travel.BaseInfo;
import com.yimayhd.palace.model.travel.PriceInfo;
import com.yimayhd.palace.model.travel.flightHotelTravel.FlightHotelTravel;
import com.yimayhd.palace.model.travel.flightHotelTravel.TripPackageInfo;
import com.yimayhd.palace.model.travel.groupTravel.GroupTravel;
import com.yimayhd.palace.model.travel.groupTravel.TripDay;

/**
 * 线路checker
 * 
 * @author yebin
 *
 */
public class LineChecker {
	public static CheckResult checkForSave(GroupTravel gt) {
		CheckResult checkBaseInfoForSave = checkBaseInfoForSave(gt.getBaseInfo());
		if(!checkBaseInfoForSave.isSuccess()) {
			return checkBaseInfoForSave;
		}
		CheckResult checkTripInfoForSave = checkTripInfoForSave(gt.getTripInfo());
		if(!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		CheckResult checkPriceInfoForSave = checkPriceInfoForSave(gt.getPriceInfo());
		if(!checkPriceInfoForSave.isSuccess()) {
			return checkPriceInfoForSave;
		}
		return CheckResult.success();
	}

	public static CheckResult checkForUpdate(FlightHotelTravel fht) {
		CheckResult checkBaseInfoForUpdate = checkBaseInfoForUpdate(fht.getBaseInfo());
		if(!checkBaseInfoForUpdate.isSuccess()) {
			return checkBaseInfoForUpdate;
		}
		CheckResult checkTripPackageInfoForUpdate = checkTripPackageInfoForUpdate(fht.getTripPackageInfo());
		if(!checkTripPackageInfoForUpdate.isSuccess()) {
			return checkTripPackageInfoForUpdate;
		}
		CheckResult checkPriceInfoForUpdate = checkPriceInfoForUpdate(fht.getPriceInfo());
		if(!checkPriceInfoForUpdate.isSuccess()) {
			return checkPriceInfoForUpdate;
		}
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForSave(BaseInfo baseInfo) {
		baseInfo.getType();
		baseInfo.getName();
		baseInfo.getProductImage();
		baseInfo.getTripImage();
		baseInfo.getOrderImage();
		baseInfo.getTags();
		baseInfo.getClass();
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForUpdate(BaseInfo baseInfo) {
		
		return CheckResult.success();
	}
	
	public static CheckResult checkPriceInfoForSave(PriceInfo priceInfo) {
		return CheckResult.success();
	}

	public static CheckResult checkPriceInfoForUpdate(PriceInfo priceInfo) {
		return CheckResult.success();
	}
	
	public static CheckResult checkTripInfoForSave(List<TripDay> tripInfo) {
		return CheckResult.success();
	}
	
	public static CheckResult checkTripInfoForUpdate(List<TripDay> tripInfo) {
		return CheckResult.success();
	}
	
	public static CheckResult checkTripPackageInfoForSave(TripPackageInfo tripPackageInfo) {
		return CheckResult.success();
	}
	
	public static CheckResult checkTripPackageInfoForUpdate(TripPackageInfo tripPackageInfo) {
		return CheckResult.success();
	}
}
