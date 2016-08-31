package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.enums.GuideStatus;

import javax.validation.constraints.NotNull;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicVO {
    @NotNull(message = "导览Id不能为空")
    private Long guideId;//导览id
    @NotNull(message = "景区Id不能为空")
    private Long scenicId;//景区id
    private String scenicName;//景区名称
    private String scenicResourceNum;//景区资源编号
    private int status;// 状态 0: 下架  1：上架
    private int weight;//权重
    @NotNull(message = "电子地图图片不能为空")
    private String guideImg;//电子地图图片

    @NotNull(message = "导览头图不能为空")
    private String listImg;//导览头图
    /**
     * 开场语音时长
     */
    @NotNull(message = "开场语音时长不能为空")
    private Integer audioTime;
    private String audioTimeStr;
    /**
     * 导览开场语音
     */
    @NotNull(message = "导览开场语音不能为空")
    private String guideAudio;
    //实用锦囊
    @NotNull(message = "实用锦囊不能为空")
    private Long guideTipsId;//实用锦囊 id
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


    private ScenicVO scenicVO;

    public ScenicVO getScenicVO() {
        return scenicVO;
    }

    public GuideScenicVO setScenicVO(ScenicVO scenicVO) {
        this.scenicVO = scenicVO;
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

    public Long getScenicId() {
        return scenicId;
    }

    public GuideScenicVO setScenicId(Long scenicId) {
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


    public int getWeight() {
        return weight;
    }

    public GuideScenicVO setWeight(Integer weight) {
        this.weight = weight;
        return this;
    }


    public Integer getAudioTime() {
        return audioTime==null?0:audioTime;
    }

    public void setAudioTime(Integer audioTime) {
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


    public Long getGuideTipsId() {
        return guideTipsId==null?0:guideTipsId;
    }

    public void setGuideTipsId(Long guideTipsId) {
        this.guideTipsId = guideTipsId;
    }

    public Long getGuideId() {
        return guideId==null?0:guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }
}
