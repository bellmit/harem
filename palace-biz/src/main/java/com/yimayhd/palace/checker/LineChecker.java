package com.yimayhd.palace.checker;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yimayhd.ic.client.model.domain.share_json.MasterRecommend;
import com.yimayhd.ic.client.model.enums.LineType;
import com.yimayhd.palace.checker.result.CheckResult;
import com.yimayhd.palace.model.travel.BaseInfo;
import com.yimayhd.palace.model.travel.BaseTravel;
import com.yimayhd.palace.model.travel.PackageBlock;
import com.yimayhd.palace.model.travel.PackageDay;
import com.yimayhd.palace.model.travel.PackageInfo;
import com.yimayhd.palace.model.travel.PackageMonth;
import com.yimayhd.palace.model.travel.PriceInfo;
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
	public static <T extends BaseTravel> CheckResult checkForSave(T travel) {
		if (travel instanceof GroupTravel) {
			CheckResult checkGroupTravelForSave = checkGroupTravelForSave((GroupTravel) travel);
			if (!checkGroupTravelForSave.isSuccess()) {
				return checkGroupTravelForSave;
			}
		}
		return CheckResult.success();
	}

	public static <T extends BaseTravel> CheckResult checkForUpdate(T travel) {
		if (travel instanceof GroupTravel) {
			CheckResult checkGroupTravelForUpdate = checkGroupTravelForUpdate((GroupTravel) travel);
			if (!checkGroupTravelForUpdate.isSuccess()) {
				return checkGroupTravelForUpdate;
			}
		}
		return CheckResult.success();
	}

	public static CheckResult checkGroupTravelForSave(GroupTravel gt) {
		CheckResult checkBaseInfoForSave = checkBaseInfoForSave(gt.getBaseInfo());
		if (!checkBaseInfoForSave.isSuccess()) {
			return checkBaseInfoForSave;
		}
		CheckResult checkTripInfoForSave = checkTripInfoForSave(gt.getTripInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		CheckResult checkPriceInfoForSave = checkPriceInfoForSave(gt.getPriceInfo());
		if (!checkPriceInfoForSave.isSuccess()) {
			return checkPriceInfoForSave;
		}
		return CheckResult.success();
	}

	public static CheckResult checkGroupTravelForUpdate(GroupTravel gt) {
		CheckResult checkBaseInfoForUpdate = checkBaseInfoForUpdate(gt.getBaseInfo());
		if (!checkBaseInfoForUpdate.isSuccess()) {
			return checkBaseInfoForUpdate;
		}
		CheckResult checkTripInfoForSave = checkTripInfoForUpdate(gt.getTripInfo());
		if (!checkTripInfoForSave.isSuccess()) {
			return checkTripInfoForSave;
		}
		CheckResult checkPriceInfoForUpdate = checkPriceInfoForUpdate(gt.getPriceInfo());
		if (!checkPriceInfoForUpdate.isSuccess()) {
			return checkPriceInfoForUpdate;
		}
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForSave(BaseInfo baseInfo) {
		int type = baseInfo.getType();
		if (LineType.getByType(type) == null) {
			return CheckResult.error("未知线路类型");
		}
		if (StringUtils.isBlank(baseInfo.getName())) {
			return CheckResult.error("线路名称不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getProductImage())) {
			return CheckResult.error("产品封面图不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getTripImage())) {
			return CheckResult.error("行程封面图不能为空");
		}
		if (StringUtils.isBlank(baseInfo.getOrderImage())) {
			return CheckResult.error("商品订单图不能为空");
		}
		if (CollectionUtils.isEmpty(baseInfo.getTags())) {
			return CheckResult.error("产品标签不能为空");
		}
		if (baseInfo.getPublisherId() <= 0) {
			return CheckResult.error("无效发布者");
		}
		if (baseInfo.getPublisherType() <= 0) {
			return CheckResult.error("发布者类型不能为空");
		}
		if (baseInfo.getToId() <= 0) {
			return CheckResult.error("无效目的地");
		}
		if (StringUtils.isBlank(baseInfo.getToName())) {
			return CheckResult.error("目的地名称不能为空");
		}
		if (baseInfo.getMemberPrice() < 0) {
			return CheckResult.error("无效会员价");
		}
		if (baseInfo.getPrice() < 0) {
			return CheckResult.error("无效会员价");
		}
		String highlights = baseInfo.getHighlights();
		if (StringUtils.isBlank(highlights)) {
			return CheckResult.error("线路设计亮点不能为空");
		} else if (highlights.length() > 800) {
			return CheckResult.error("线路设计亮点不能超过800个字符");
		}
		MasterRecommend recommond = baseInfo.getRecommond();
		if (StringUtils.isBlank(recommond.getTitle())) {
			return CheckResult.error("推荐理由标题不能为空");
		}
		if (StringUtils.isBlank(recommond.getDescription())) {
			return CheckResult.error("推荐理由内容不能为空");
		}
		if (recommond.getUserId() <= 0) {
			return CheckResult.error("无效线路推荐人");
		}
		if (StringUtils.isBlank(recommond.getName())) {
			return CheckResult.error("线路推荐人名称不能为空");
		}
		return CheckResult.success();
	}

	public static CheckResult checkBaseInfoForUpdate(BaseInfo baseInfo) {
		if (baseInfo.getId() <= 0) {
			return CheckResult.error("无效线路ID");
		}
		return checkBaseInfoForSave(baseInfo);
	}

	public static CheckResult checkPriceInfoForSave(PriceInfo priceInfo) {
		List<PackageInfo> tcs = priceInfo.getTcs();
		if (CollectionUtils.isEmpty(tcs)) {
			return CheckResult.error("线路套餐不能为空");
		}
		for (PackageInfo tc : tcs) {
			CheckResult packageCheckResult = checkPackageInfoForSave(tc);
			if (!packageCheckResult.isSuccess()) {
				return packageCheckResult;
			}
		}
		if (priceInfo.getLimit() <= 0) {
			return CheckResult.error("无效提前报名天数");
		}
		return CheckResult.success();
	}

	public static CheckResult checkPackageInfoForSave(PackageInfo tc) {
		List<PackageMonth> months = tc.getMonths();
		if (StringUtils.isBlank(tc.getName())) {
			return CheckResult.error("线路套餐名称不能为空");
		}
		if (CollectionUtils.isEmpty(months)) {
			return CheckResult.error("套餐月份不能为空");
		}
		for (PackageMonth packageMonth : months) {
			CheckResult packageMonthCheckResult = checkPackageMonthForSave(packageMonth);
			if (!packageMonthCheckResult.isSuccess()) {
				return packageMonthCheckResult;
			}
		}
		return PropertyChecker.checkProperty(tc.getPId(), tc.getPType(), tc.getPTxt());
	}

	public static CheckResult checkPackageMonthForSave(PackageMonth month) {
		if (month.getTime() <= 0) {
			return CheckResult.error("无效套餐月份时间");
		}
		List<PackageDay> days = month.getDays();
		if (CollectionUtils.isEmpty(days)) {
			return CheckResult.error("套餐日期项不能为空");
		}
		for (PackageDay packageDay : days) {
			CheckResult packageDayCheckResult = checkPackageDayForSave(packageDay);
			if (!packageDayCheckResult.isSuccess()) {
				return packageDayCheckResult;
			}
		}
		return CheckResult.success();
	}

	public static CheckResult checkPackageDayForSave(PackageDay day) {
		if (day.getTime() <= 0) {
			return CheckResult.error("无效套餐日期项时间");
		}
		List<PackageBlock> blocks = day.getBlocks();
		if (CollectionUtils.isEmpty(blocks)) {
			return CheckResult.error("套餐sku不能为空");
		}
		for (PackageBlock packageBlock : blocks) {
			CheckResult checkPackageBlockForSave = checkPackageBlockForSave(packageBlock);
			if (!checkPackageBlockForSave.isSuccess()) {
				return checkPackageBlockForSave;
			}
		}
		return PropertyChecker.checkProperty(day.getPId(), day.getPType(), day.getPTxt());
	}

	public static CheckResult checkPackageBlockForSave(PackageBlock block) {
		return CheckResult.success();
	}

	public static CheckResult checkPriceInfoForUpdate(PriceInfo priceInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripInfoForSave(List<TripDay> tripInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripInfoForUpdate(List<TripDay> tripInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripPackageInfoForSave(TripPackageInfo tripPackageInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}

	public static CheckResult checkTripPackageInfoForUpdate(TripPackageInfo tripPackageInfo) {
		// TODO YEBIN
		return CheckResult.success();
	}
}
