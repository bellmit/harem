package com.yimayhd.palace.helper;

import java.util.ArrayList;
import java.util.List;

import com.yimayhd.promotion.client.enums.PromotionType;

public class PromotionHelper {
	public static List<PromotionType> getAvaiableItemPromotionTypes(){
		List<PromotionType> promotionTypes = new ArrayList<PromotionType>() ;
//		promotionTypes.add(PromotionType.SUM_REDUCE);
		promotionTypes.add(PromotionType.DIRECT_REDUCE);
		return promotionTypes ;
	}
}
