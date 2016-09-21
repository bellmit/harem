package com.yimayhd.palace.model.guide;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicTipsVO {
    /**
     * 实用锦囊id
     */
    private long id;

    /**
     * 导览id
     */
    private long guideId;

    /**
     * 景区门票信息
     */
    private String ticketInfo;

    /**
     * 营业时间
     */
    private String openTime;

    /**
     * 交通
     */
    private String traffic;

    /**
     * 温馨提示
     */
    private String tips;

    /**
     * 注意事项
     */
    private String cares;

    /**
     * 必玩必吃之事
     */
    private String haveTo;

    public long getId() {
        return id;
    }

    public GuideScenicTipsVO setId(long id) {
        this.id = id;
        return this;
    }

    public long getGuideId() {
        return guideId;
    }

    public GuideScenicTipsVO setGuideId(long guideId) {
        this.guideId = guideId;
        return this;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public GuideScenicTipsVO setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
        return this;
    }

    public String getOpenTime() {
        return openTime;
    }

    public GuideScenicTipsVO setOpenTime(String openTime) {
        this.openTime = openTime;
        return this;
    }

    public String getTraffic() {
        return traffic;
    }

    public GuideScenicTipsVO setTraffic(String traffic) {
        this.traffic = traffic;
        return this;
    }

    public String getTips() {
        return tips;
    }

    public GuideScenicTipsVO setTips(String tips) {
        this.tips = tips;
        return this;
    }

    public String getCares() {
        return cares;
    }

    public GuideScenicTipsVO setCares(String cares) {
        this.cares = cares;
        return this;
    }

    public String getHaveTo() {
        return haveTo;
    }

    public GuideScenicTipsVO setHaveTo(String haveTo) {
        this.haveTo = haveTo;
        return this;
    }
}
