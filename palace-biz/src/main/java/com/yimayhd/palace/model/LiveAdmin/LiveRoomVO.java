package com.yimayhd.palace.model.LiveAdmin;

import com.yimayhd.live.client.enums.LiveFeatureKey;
import com.yimayhd.live.client.util.FeatureUtil;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by haozhu on 16/9/22.
 */
public class LiveRoomVO implements Serializable {
    /**
     * 直播间ID
     */
    private long id;
    /**
     * 直播标题
     */
    private String liveTitle;
    /**
     * 达人ID
     */
    private long userId;
    /**
     * 达人信息
     */
    private UserDO userDO;
    /**
     * 正在直播中的记录ID
     */
    private long livingRecord;
    /**
     * 直播分类
     */
    private long liveCategory;
    /**
     * 直播分类标签
     */
    private String liveCategoryString;
    /**
     * 直播公告
     */
    private String roomNotice;
    /**
     * 直播间排序
     */
    private int roomOrder;
    /**
     * 额外属性String
     */
    private String feature;
    /**
     * 直播间状态
     */
    private int status;
    /**
     * 创建时间
     */
    private Date gmtCreated;
    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 额外属性Map
     */
    private Map<String, String> featureMap;

    private static final long serialVersionUID = 1L;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLiveTitle() {
        return liveTitle;
    }

    public void setLiveTitle(String liveTitle) {
        this.liveTitle = liveTitle;
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

    public String getRoomNotice() {
        return roomNotice;
    }

    public void setRoomNotice(String roomNotice) {
        this.roomNotice = roomNotice;
    }

    public int getRoomOrder() {
        return roomOrder;
    }

    public void setRoomOrder(int roomOrder) {
        this.roomOrder = roomOrder;
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

    public long getLivingRecord() {
        return livingRecord;
    }

    public void setLivingRecord(long livingRecord) {
        this.livingRecord = livingRecord;
    }

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
}