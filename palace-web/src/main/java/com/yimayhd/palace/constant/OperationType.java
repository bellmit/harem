package com.yimayhd.palace.constant;


/**
 * Created by weiwenbin on 2015/6/26.
 */
public enum OperationType {
    //    LINK_URL("LINK_URL","url链接"),
//    TEXT("TEXT","文本信息"),
    RESOURCE("RESOURCE","资源位"),
    //    H5("H5","外链url"),
//    POSTS("POSTS","帖子"),
//    ACTIVITY_LIST("ACTIVITY_LIST","活动"),
//    LINE_DETAIL("LINE_DETAIL","线路详情"),
//    LINE_LIST("LINE_LIST","线路列表"),
//    TRALVER_JUR_DETAIL("TRALVER_JUR_DETAIL","旅游日记"),
//    NONE("NONE","无操作"),
//    USER_INFO("USER_INFO","用户信息"),
    ITEM("ITEM","商品"),
    SKU("SKU","商品下SKU"),
    //    PACKAGE_TOUR("PACKAGE_TOUR","跟团游首页"),
//    AROUND_FUN("AROUND_FUN","周边玩乐首页"),
//    CITY_ACTIVITY("CITY_ACTIVITY","同城活动首页"),
//    FREE_TOUR("FREE_TOUR","自由行首页"),
//    JIUXIU_CLUB("JIUXIU_CLUB","九休club首页"),
//    JIUXIU_MASTER("JIUXIU_MASTER","达人的首页"),
//    JIUXIU_FOOD("JIUXIU_FOOD","九休美食首页"),
//    JIUXIU_BUY("JIUXIU_BUY","九休必买首页"),
    CITY_ACTIVITY_DETAIL("CITY_ACTIVITY_DETAIL","活动详情"),
//    MASTER_DETAIL("MASTER_DETAIL","达人详情"),
    FREE_TOUR_LIST("FREE_TOUR_LIST","自由行列表页"),
    AROUND_FUN_LIST("AROUND_FUN_LIST","周边玩乐列表页"),
    PACKAGE_TOUR_LIST("PACKAGE_TOUR_LIST","跟团游列表"),
    //    TAG("TAG","标签"),
//    SHOP_HOME_PAGE("SHOP_HOME_PAGE","店铺首页"),
    FREE_TOUR_DETAIL("FREE_TOUR_DETAIL","自由行线路详情"),
    PACKAGE_TOUR_DETAIL("PACKAGE_TOUR_DETAIL","跟团游详情"),
    JIUXIU_BUY_DETAIL("JIUXIU_BUY_DETAIL","实物商品详情"),
    AROUND_FUN_DETAIL("AROUND_FUN_DETAIL","周边玩乐详情"),
    //    MUST_BUY_LIST("MUST_BUY_LIST","必买商品列表"),
    CITY_ACTIVITY_LIST("CITY_ACTIVITY_LIST","同城活动列表"),


    //yusw -add
    SCENIC_HOME("SCENIC_HOME","景区首页"),
    HOTEL_HOME("HOTEL_HOME","酒店首页"),

    HOTEL_DETAIL("HOTEL_DETAIL","酒店详情"),
    SCENIC_DETAIL("SCENIC_DETAIL","景区详情"),

    SCENIC_TAG_LIST("SCENIC_TAG_LIST","景区列表"),

    JIUXIU_FOOD_DETAIL("JIUXIU_FOOD_DETAIL","美食店铺页"),
    MASTER_DETAIL("MASTER_DETAIL","达人详情"),
   /* MASTER_LIST("MASTER_LIST","达人列表"),*/
    MASTER_LIST("MASTER_LIST","达人专题列表"),//带达人的服务描述，如，出行服务
    JIUXIU_MASTER("JIUXIU_MASTER","达人列表")
    ;





    private String value;
    private String desc;

    OperationType(String value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
