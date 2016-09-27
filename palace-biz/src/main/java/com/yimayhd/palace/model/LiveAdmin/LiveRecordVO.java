package com.yimayhd.palace.model.LiveAdmin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yimayhd.live.client.enums.LiveFeatureKey;
import com.yimayhd.live.client.enums.WangSuInfo;
import com.yimayhd.live.client.util.FeatureUtil;
import org.apache.commons.lang3.StringUtils;
import com.yimayhd.user.client.domain.UserDO;


import java.io.Serializable;
import java.util.*;

/**
 * Created by haozhu on 16/9/22.
 */
public class LiveRecordVO implements Serializable {
    /**
     * 直播记录ID
     */
    private long id;
    /**
     * 主播ID
     */
    private long userId;
    /**
     * 主播信息
     */
    private UserDO userDO;
    /**
     * 直播间ID
     */
    private long liveRoom;
    /**
     * 直播分类标签
     */
    private String liveCategoryString;
    /**
     * 直播分类ID
     */
    private long liveCategory;
    /**
     * 直播标题
     */
    private String liveTitle;
    /**
     * 直播描述
     */
    private String liveDes;
    /**
     * 直播封面
     */
    private String liveCover;
    /**
     * 直播状态
     */
    private int liveStatus;
    /**
     * 主播CityCode
     */
    private String locationCityCode;
    /**
     * 主播城市名
     */
    private String locationCityName;
    /**
     * 额外属性String
     */
    private String feature;
    /**
     * 直播开始时间
     */
    private Date startDate;
    /**
     * 直播结束时间
     */
    private Date endDate;
    /**
     * 在线观看人数
     */
    private int onlineCount;
    /**
     * 观看人次
     */
    private int viewCount;
    /**
     * 观看峰值
     */
    private int peakCount;
    /**
     * 回放秒数
     */
    private int replaySecond;
    /**
     * 直播排序
     */
    private int liveOrder;
    /**
     * 直播记录状态
     */
    private int status;
    /**
     * 直播记录创建时间
     */
    private Date gmtCreated;
    /**
     * 直播记录更新时间
     */
    private Date gmtModified;
    /**
     * 额外属性Map
     */
    private Map<String, String> featureMap;

    private static final long serialVersionUID = 1L;

    public String getFeature() {
        return FeatureUtil.toString(featureMap);
    }

    public void setFeature(String featureString) {
        this.featureMap = FeatureUtil.fromString(featureString);
        this.feature = featureString;
    }

    public void addFeature(LiveFeatureKey liveRecordFeatureKey, String value) {
        if (liveRecordFeatureKey == null || StringUtils.isBlank(value)) {
            return;
        }
        if (featureMap == null) {
            featureMap = new HashMap<String, String>();
        }
        featureMap.put(liveRecordFeatureKey.getCode(), value);
        this.feature = FeatureUtil.toString(featureMap);
    }

    public String getFeature(LiveFeatureKey key) {
        if (feature == null || key == null) {
            return null;
        }
        return featureMap.get(key.getCode());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public long getLiveCategory() {
        return liveCategory;
    }

    public void setLiveCategory(long liveCategory) {
        this.liveCategory = liveCategory;
    }

    public String getLiveCategoryString() {
        return liveCategoryString;
    }

    public void setLiveCategoryString(String liveCategoryString) {
        this.liveCategoryString = liveCategoryString;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
    }

    public String getLiveDes() {
        return liveDes;
    }

    public void setLiveDes(String liveDes) {
        this.liveDes = liveDes;
    }

    public String getLiveCover() {
        return liveCover;
    }

    public void setLiveCover(String liveCover) {
        this.liveCover = liveCover;
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

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLiveOrder() {
        return liveOrder;
    }

    public void setLiveOrder(int liveOrder) {
        this.liveOrder = liveOrder;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public long getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(long liveRoom) {
        this.liveRoom = liveRoom;
    }

    public int getReplaySecond() {
        return replaySecond;
    }

    public void setReplaySecond(int replaySecond) {
        this.replaySecond = replaySecond;
    }

    public int getPeakCount() {
        return peakCount;
    }

    public void setPeakCount(int peakCount) {
        this.peakCount = peakCount;
    }

    /**
     * 获取回放URL
     *
     * @return
     */
    public List<String> getReplayUrl() {
        List<String> urls = new ArrayList<String>();
        try {
            if (featureMap == null || featureMap.get(LiveFeatureKey.WS_RECORD_END_INFO.getCode()) == null) {
                return urls;
            }
            String recordInfo = featureMap.get(LiveFeatureKey.WS_RECORD_END_INFO.getCode());
            JSONObject recordInfoMap = JSON.parseObject(recordInfo);
            JSONArray array = (JSONArray) recordInfoMap.get("urls");
            if (array != null && array.size() > 0) {
                for (int i = 0; i < array.size(); i++) {
                    urls.add(array.get(i).toString());
                }
            }
        } catch (Exception e) {
            return urls;
        }
        return urls;
    }

    /**
     * 获取流名
     *
     * @return
     */
    public String getStreamName() {
        String streamName = null;
        if (featureMap == null || featureMap.get(LiveFeatureKey.WS_STREAM_START_INFO.getCode()) == null) {
            return null;
        }
        String recordInfo = featureMap.get(LiveFeatureKey.WS_STREAM_START_INFO.getCode());
        JSONObject recordInfoMap = JSON.parseObject(recordInfo);
        streamName = recordInfoMap.getString("id");
        return streamName;
    }
}
