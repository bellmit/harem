package com.yimayhd.palace.model.guide;

import com.yimayhd.ic.client.model.domain.guide.GuideFocusDO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by haozhu on 16/8/31.
 */
public class GuideAttractionVO  {
    /**
     * 景点id
     */
    private long id;

    /**
     * 导览id
     */
    private long guideId;

    /**
     * 景点图片
     */
    private String attrImg;

    /**
     * 景点名称
     */
    private String name;

    /**
     * 游览时间
     */
    private String tourTime;

    /**
     * 标题
     */
    private String title;


    /**
     * 标题
     */
    private String subTitle;

    private int isDel;

    /**
     * 看点顺序json
     */
    private String focusOrder;

    /**
     * 权重
     */
    private int weights;

    private Date gmtCreated;

    private Date gmtModified;

    /**
     * 景点编号
     */
    private int attrNo;

    private static final long serialVersionUID = 1L;

    public int getAttrNo() {
        return attrNo;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setAttrNo(int attrNo) {
        this.attrNo = attrNo;
        return this;
    }

    public long getId() {
        return id;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setId(long id) {
        this.id = id;
        return this;
    }

    public long getGuideId() {
        return guideId;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setGuideId(long guideId) {
        this.guideId = guideId;
        return this;
    }

    public String getAttrImg() {
        return attrImg;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setAttrImg(String attrImg) {
        this.attrImg = attrImg;
        return this;
    }

    public String getName() {
        return name;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setName(String name) {
        this.name = name;
        return this;
    }

    public String getTourTime() {
        return tourTime;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setTourTime(String tourTime) {
        this.tourTime = tourTime;
        return this;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setTitle(String title) {
        this.title = title;
        return this;
    }


    public int getIsDel() {
        return isDel;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setIsDel(int isDel) {
        this.isDel = isDel;
        return this;
    }

    public String getFocusOrder() {
        return focusOrder;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setFocusOrder(String focusOrder) {
        this.focusOrder = focusOrder;
        return this;
    }

    public int getWeights() {
        return weights;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setWeights(int weights) {
        this.weights = weights;
        return this;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public com.yimayhd.palace.model.guide.GuideAttractionVO setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    @Override
    public String toString() {
        return "GuideAttraction [id=" + id + ", guideId=" + guideId + ", attrImg=" + attrImg + ", name=" + name + ", tourTime=" + tourTime + ", title=" + title + ", isDel=" + isDel + ", focusOrder=" + focusOrder + ", weights=" + weights + ", gmtCreated=" + gmtCreated + ", gmtModified=" + gmtModified + "]";
    }

}
