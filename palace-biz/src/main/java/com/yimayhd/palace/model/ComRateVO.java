package com.yimayhd.palace.model;

import com.yimayhd.commentcenter.client.domain.ComRateDO;
import com.yimayhd.commentcenter.client.result.DimensionInfoResult;
import com.yimayhd.ic.client.model.domain.item.ItemDO;
import com.yimayhd.ic.client.model.domain.item.ItemSkuDO;
import com.yimayhd.user.client.domain.UserDO;

import java.util.Date;
import java.util.List;

/**
 * Created by p on 7/18/16.
 */
public class ComRateVO {

    private long id;
    private long skuId;
    private long itemId;
    private long sellerId;
    private long orderId;
    private long outId;
    private String outType;
    private long userId;
    private String content;
    private List<String> picUrls;
    private long score;
    private int rateStatus;
    private List<DimensionInfoResult> dimensionInfoResultList;
    private Date backTime;
    private String backContent;
    private List<String> backPics;
    private Date addTime;
    private String addContent;
    private List<String> addPics;
    private Date gmtCreated;
    private int status;

    private ItemDO itemDO;
    private UserDO userDO;
    private String itemSkuTitle;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSkuId() {
        return this.skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getItemId() {
        return this.itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSellerId() {
        return this.sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOutId() {
        return this.outId;
    }

    public void setOutId(long outId) {
        this.outId = outId;
    }

    public String getOutType() {
        return this.outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getPicUrls() {
        return this.picUrls;
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    public long getScore() {
        return this.score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public int getRateStatus() {
        return this.rateStatus;
    }

    public void setRateStatus(int rateStatus) {
        this.rateStatus = rateStatus;
    }

    public List<DimensionInfoResult> getDimensionInfoResultList() {
        return this.dimensionInfoResultList;
    }

    public void setDimensionInfoResultList(List<DimensionInfoResult> dimensionInfoResultList) {
        this.dimensionInfoResultList = dimensionInfoResultList;
    }

    public String getBackContent() {
        return this.backContent;
    }

    public void setBackContent(String backContent) {
        this.backContent = backContent;
    }

    public String getAddContent() {
        return this.addContent;
    }

    public void setAddContent(String addContent) {
        this.addContent = addContent;
    }

    public List<String> getBackPics() {
        return this.backPics;
    }

    public void setBackPics(List<String> backPics) {
        this.backPics = backPics;
    }

    public List<String> getAddPics() {
        return this.addPics;
    }

    public void setAddPics(List<String> addPics) {
        this.addPics = addPics;
    }

    public Date getBackTime() {
        return this.backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getGmtCreated() {
        return this.gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public UserDO getUserDO() {
        return this.userDO;
    }

    public void setItemDO(ItemDO itemDO) {
        this.itemDO = itemDO;
    }

    public ItemDO getItemDO() {
        return this.itemDO;
    }

    public String getItemSkuTitle() {
        return itemSkuTitle;
    }

    public void setItemSkuTitle(String itemSkuTitle) {
        this.itemSkuTitle = itemSkuTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}