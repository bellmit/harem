package com.yimayhd.palace.util;

import org.apache.commons.lang3.math.NumberUtils;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;

public class OrderUtil {

	public static long getOrderFee(BizOrderDO bizOrderDO, BizOrderFeatureKey bizOrderFeatureKey) {
		if (bizOrderDO == null || bizOrderFeatureKey == null) {
			return 0;
		}
		String fee = bizOrderDO.getFeature(bizOrderFeatureKey);
		if (NumberUtils.isNumber(fee)) {
			return NumberUtils.toLong(fee);
		}
		return 0;
	}
}
