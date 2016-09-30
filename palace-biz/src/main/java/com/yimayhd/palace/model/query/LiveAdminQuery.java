package com.yimayhd.palace.model.query;

import com.yimayhd.live.client.enums.LiveOrder;
import com.yimayhd.live.client.query.PageQuery;
import com.yimayhd.palace.base.BaseQuery;

import java.util.Date;

/**
 * Created by haozhu on 16/9/23.
 */
public class LiveAdminQuery extends BaseQuery {
    private static final long serialVersionUID = -8003588527594636468L;
    /**
     * 主播ID
     */
    private Long userId;
    /**
     * 主播昵称
     */
    private String nickName;
    /**
     * 直播间ID
     */
    private Long liveRoomId;
    /**
     * 直播分类
     */
    private Long liveCategory;
    /**
     * 直播状态
     */
    private int liveStatus;
    /**
     * 直播CityCode
     */
    private String locationCityCode;
    /**
     * 直播城市名
     */
    private String locationCityName;
    /**
     * 直播开始时间
     */
    private Date startDate;
    /**
     * 直播开始时间
     */
    private Date endDate;
    /**
     * 直播人气
     */
    private int viewCount;
    /**
     * 在线人数
     */
    private int onlineCount;
    /**
     * 直播开始时间秒数
     */
    private int startSecondTime;
    /**
     * 回放时间秒数
     */
    private int replaySecondTime;

    /**
     * 排序方式
     */
    private Long liveOrder;

    private Date gmtCreated;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(Long liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public Long getLiveCategory() {
        return liveCategory;
    }

    public void setLiveCategory(Long liveCategory) {
        this.liveCategory = liveCategory;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getLocationCityCode() {
        return locationCityCode;
    }

    public void setLocationCityCode(String locationCityCode) {
        this.locationCityCode = locationCityCode;
    }

    public String getLocationCityName() {
        return locationCityName;
    }

    public void setLocationCityName(String locationCityName) {
        this.locationCityName = locationCityName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getStartSecondTime() {
        return startSecondTime;
    }

    public void setStartSecondTime(int startSecondTime) {
        Date now = new Date();
        this.gmtCreated = new Date(now.getTime() - startSecondTime * 1000);
        this.startSecondTime = startSecondTime;
    }

    public int getReplaySecondTime() {
        return replaySecondTime;
    }

    public void setReplaySecondTime(int replaySecondTime) {
        this.replaySecondTime = replaySecondTime;
    }

    public Long getLiveOrder() {
        return liveOrder;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setLiveOrder(Long liveOrder) {
        this.liveOrder = liveOrder;
    }
}
