package com.yimayhd.palace.model.guide;

/**
 * Created by xushubing on 2016/8/18.
 */
public class GuideScenicVO {
    private long guideid;//导览id
    private long scenicId;//景区id
    private String scenicName;//景区名称
    private String scenicResourceNum;//景区资源编号
    private int status;// 状态 0: 下架  1：上架
    private String location;//位置
    private int weight;//权重
    private int level;//等级
    private String guideImg;//电子地图图片

    private String listImg;//导览头图


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

    public long getGuideid() {
        return guideid;
    }

    public GuideScenicVO setGuideid(long guideid) {
        this.guideid = guideid;
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
}
