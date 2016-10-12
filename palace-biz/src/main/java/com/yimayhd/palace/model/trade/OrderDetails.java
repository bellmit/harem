package com.yimayhd.palace.model.trade;

import com.yimayhd.lgcenter.client.domain.ExpressVO;
import com.yimayhd.palace.model.vo.AdjustFeeVO;
import com.yimayhd.tradecenter.client.model.domain.order.FullGiveInfo;
import com.yimayhd.tradecenter.client.model.domain.order.PromotionInfo;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class OrderDetails {
    private MainOrder mainOrder;
    // 联系人
    private ContactUser contacts;
    // 游客
    private List<ContactUser> tourists;

    private String buyerName;

    private String buyerNiceName;

    private String buyerPhoneNum;

    private String buyerMemo;

    private long totalFee;

    private long actualTotalFee;

    private String payChannel;

    private String orderFrom;

    private String closeReason;

    private Date consignTime;

    private ExpressVO express;

    private long orderPromotionFee;

    private long orderVoucherFee;

    private PromotionInfo orderPromotionInfo;

    private FullGiveInfo giftPromotionInfo;

    private String promotionInfoDesc;

    //订单改价
    private List<AdjustFeeVO> listAdjustFeeVO;
    private long oldFee;//原订单价格
    private boolean hasAdjustFee;

    public long getOldFee() {
        return oldFee;
    }

    public void setOldFee(long oldFee) {
        this.oldFee = oldFee;
    }

    public boolean isHasAdjustFee() {
        return hasAdjustFee;
    }

    public void setHasAdjustFee(boolean hasAdjustFee) {
        this.hasAdjustFee = hasAdjustFee;
    }

    public List<AdjustFeeVO> getListAdjustFeeVO() {
        return listAdjustFeeVO;
    }

    public void setListAdjustFeeVO(List<AdjustFeeVO> listAdjustFeeVO) {
        this.listAdjustFeeVO = listAdjustFeeVO;
    }

    public long getOrderPromotionFee() {
        return orderPromotionFee;
    }

    public void setOrderPromotionFee(long orderPromotionFee) {
        this.orderPromotionFee = orderPromotionFee;
    }

    public long getOrderVoucherFee() {
        return orderVoucherFee;
    }

    public void setOrderVoucherFee(long orderVoucherFee) {
        this.orderVoucherFee = orderVoucherFee;
    }

    public PromotionInfo getOrderPromotionInfo() {
        return orderPromotionInfo;
    }

    public void setOrderPromotionInfo(PromotionInfo orderPromotionInfo) {
        this.orderPromotionInfo = orderPromotionInfo;
    }

    public String getPromotionInfoDesc() {
        return promotionInfoDesc;
    }

    public void setPromotionInfoDesc(String promotionInfoDesc) {
        this.promotionInfoDesc = promotionInfoDesc;
    }

    public MainOrder getMainOrder() {
        return mainOrder;
    }

    public void setMainOrder(MainOrder mainOrder) {
        this.mainOrder = mainOrder;
    }

    public ContactUser getContacts() {
        return contacts;
    }

    public void setContacts(ContactUser contacts) {
        this.contacts = contacts;
    }

    public List<ContactUser> getTourists() {
        return tourists;
    }

    public void setTourists(List<ContactUser> tourists) {
        this.tourists = tourists;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNiceName() {
        return buyerNiceName;
    }

    public void setBuyerNiceName(String buyerNiceName) {
        this.buyerNiceName = buyerNiceName;
    }

    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getActualTotalFee() {
        return actualTotalFee;
    }

    public void setActualTotalFee(long actualTotalFee) {
        this.actualTotalFee = actualTotalFee;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public ExpressVO getExpress() {
        return express;
    }

    public void setExpress(ExpressVO express) {
        this.express = express;
    }

    public FullGiveInfo getGiftPromotionInfo() {
        return giftPromotionInfo;
    }

    public void setGiftPromotionInfo(FullGiveInfo giftPromotionInfo) {
        this.giftPromotionInfo = giftPromotionInfo;
    }
}
