package com.yimayhd.palace.controller.vo;

import com.yimayhd.commentcenter.client.enums.TagType;
import com.yimayhd.ic.client.model.enums.ItemType;

/**
 * @author create by yushengwei on 2016/4/18
 * @Description
 * @return $returns
 */
public class OperationParamConstant {
	/**
	 * 同城活动主题
	 */
	public static final String THEM_ACTIVITY = TagType.CITYACTIVITY.name();
	/**
	 * 周边 跟团 自由都属于线路，公用线路主题
	 */
	public static final String THEM_LINE = TagType.LINETAG.name();
	/**
	 * 关键字
	 */
	public static final String KEYWORD = "KEYWORD";
	/**
	 * 选择目的地
	 */
    public static final String CHOOSE_DESTINATION = "CHOOSE_DESTINATION";
    /**
     * 商品详情页
     */
    public static final String ITEM_DETAIL = "ITEM_DETAIL";
	/*TOUR_LINE(21,"跟团游"),
	FREE_LINE(22,"自由行"),
	CITY_ACTIVITY(23, "同城活动"),*/
	/*public static final String ITEM_DETAIL_TOUR_LINE = ItemType.TOUR_LINE.name();//跟团游
	public static final String ITEM_DETAIL_FREE_LINE = ItemType.FREE_LINE.name();//自由行
	public static final String ITEM_DETAIL_CITY_ACTIVITY = ItemType.CITY_ACTIVITY.name();//同城活动*/

	public static final String ITEM_DETAIL_TOUR_LINE = "TOUR_LINE";//跟团游
	public static final String ITEM_DETAIL_FREE_LINE = "FREE_LINE";//自由行
	public static final String ITEM_DETAIL_CITY_ACTIVITY = "CITY_ACTIVITY";//同城活动
	public static final String ITEM_DETAIL_NORMAL = "NORMAL";//实物商品
	public static final String ITEM_DETAIL_HOTEL = "HOTEL";//酒店商品
	public static final String ITEM_DETAIL_SPOTS = "SPOTS";//景区门票


}
