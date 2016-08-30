package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.enums.GuideStatus;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicVO {
    private long guideId;//导览id
    private long scenicId;//景区id
    private String scenicName;//景区名称
    private String scenicResourceNum;//景区资源编号
    private int status;// 状态 0: 下架  1：上架
    private String location;//位置
    private int weight;//权重
    private int level;//等级
    private String guideImg;//电子地图图片

    private String listImg;//导览头图
    /**
     * 开场语音时长
     */
    private int audioTime;
    private String audioTimeStr;
    /**
     * 导览开场语音
     */
    private String guideAudio;
    //实用锦囊
    private long guideTipsId;//实用锦囊 id
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

    //
    private String statusStr;

    private long subjectId;
    private String address;


    public long getSubjectId() {
        return subjectId;
    }

    public GuideScenicVO setSubjectId(long subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public GuideScenicVO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAudioTimeStr() {
        return audioTime / 60 + "分" + audioTime % 60 + "秒";
    }

    public GuideScenicVO setAudioTimeStr(String audioTimeStr) {
        this.audioTimeStr = audioTimeStr;
        return this;
    }

    private static GuideStatus getStatusByValue(int status) {
        for (GuideStatus guideStatus : GuideStatus.values()) {
            if (guideStatus.getCode() == status) {
                return guideStatus;
            }
        }
        return null;
    }

    public String getStatusStr() {
        GuideStatus guideStatus = getStatusByValue(status);
        if (guideStatus == null) {
            return null;
        }
        return guideStatus.getDesc();
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public long getScenicId() {
        return scenicId;
    }

    public GuideScenicVO setScenicId(long scenicId) {
        this.scenicId = scenicId;
        return this;
    }

    public String getGuideImg() {
        return guideImg;
    }

    public GuideScenicVO setGuideImg(String guideImg) {
        this.guideImg = guideImg;
        return this;
    }

    public String getListImg() {
        return listImg;
    }

    public GuideScenicVO setListImg(String listImg) {
        this.listImg = listImg;
        return this;
    }


    public String getScenicName() {
        return scenicName;
    }

    public GuideScenicVO setScenicName(String scenicName) {
        this.scenicName = scenicName;
        return this;
    }

    public String getScenicResourceNum() {
        return scenicResourceNum;
    }

    public GuideScenicVO setScenicResourceNum(String scenicResourceNum) {
        this.scenicResourceNum = scenicResourceNum;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public GuideScenicVO setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public GuideScenicVO setLocation(String location) {
        this.location = location;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public GuideScenicVO setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public GuideScenicVO setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(int audioTime) {
        this.audioTime = audioTime;
    }

    public String getGuideAudio() {
        return guideAudio;
    }

    public void setGuideAudio(String guideAudio) {
        this.guideAudio = guideAudio;
    }

    public String getTicketInfo() {
        return ticketInfo;
    }

    public void setTicketInfo(String ticketInfo) {
        this.ticketInfo = ticketInfo;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCares() {
        return cares;
    }

    public void setCares(String cares) {
        this.cares = cares;
    }

    public String getHaveTo() {
        return haveTo;
    }

    public void setHaveTo(String haveTo) {
        this.haveTo = haveTo;
    }


    public long getGuideTipsId() {
        return guideTipsId;
    }

    public void setGuideTipsId(long guideTipsId) {
        this.guideTipsId = guideTipsId;
    }

    public long getGuideId() {
        return guideId;
    }

    public void setGuideId(long guideId) {
        this.guideId = guideId;
    }
}
