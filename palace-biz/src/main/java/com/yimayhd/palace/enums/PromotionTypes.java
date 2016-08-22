package com.yimayhd.palace.enums;

/**
 * @author create by yushengwei on 2016/5/24
 * @Description
 * @return $returns
 */
public enum PromotionTypes {
    SUM_DISCOUNT("满","折", 1),
    SUM_REDUCE("满","减", 2),
    COUNT_DISCOUNT("满","个折", 3),
    COUNT_REDUCE("满个","减", 4),
    PER_SUM_REDUCE("每满","减", 5),
    DIRECT_REDUCE("直降"," ", 6);

    private String desc;
    private String desc_suffix;
    private int type;

    PromotionTypes(String desc, String desc_suffix, int type) {
        this.desc = desc;
        this.desc_suffix = desc_suffix;
        this.type = type;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getType() {
        return this.type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_suffix() {
        return desc_suffix;
    }

    public void setDesc_suffix(String desc_suffix) {
        this.desc_suffix = desc_suffix;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static PromotionTypes getByType(int type) {
        PromotionTypes[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            PromotionTypes userType = arr$[i$];
            if(userType.getType() == type) {
                return userType;
            }
        }

        return null;
    }


}
