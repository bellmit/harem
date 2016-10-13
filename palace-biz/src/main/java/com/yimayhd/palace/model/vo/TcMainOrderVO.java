package com.yimayhd.palace.model.vo;

import com.yimayhd.palace.repo.MerchantRepo;
import com.yimayhd.tradecenter.client.model.domain.order.LogisticsOrderDO;
import com.yimayhd.tradecenter.client.model.domain.order.PayOrderDO;
import com.yimayhd.tradecenter.client.model.domain.person.ContactUser;
import com.yimayhd.tradecenter.client.model.domain.person.TcMerchantInfo;
import com.yimayhd.tradecenter.client.model.result.order.create.Address;
import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;
import com.yimayhd.tradecenter.client.model.result.order.create.TcDetailOrder;
import com.yimayhd.user.client.domain.UserDO;
import com.yimayhd.user.client.dto.MerchantUserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangdi on 16/10/9.
 */
public class TcMainOrderVO implements Serializable {

    private static final long serialVersionUID = -1778838755630011353L;
    /** 订单基本信息 */
    private TcBizOrder bizOrder;

    private PayOrderDO payOrderDO;

    private LogisticsOrderDO logisticsOrderDO;
    /** 子订单列表 */
    private List<TcDetailOrderVO> detailOrders;
//    private ButtonStatus buttonStatus;
    //private LgOrderList lgOrderList;
    //private LogisticsOrder logisticsOrder;
    /** 订单总金额，单位为分 */
    private long totalFee;
    /** 订单完成时间，交易关闭时间 */
    private long completionTime;
    /** 发货时间 */
    private long deliveryTime;

    //景区入园时间
    private long scenicEnterTime;
    //线路出行时间
    private long departTime;
    //酒店入住时间
    private long checkInTime;
    //酒店离店时间
    private long checkOutTime;
    //酒店最晚到店时间
    private String latestArriveTime;
    //购买房间数
    private int roomAmount;
    //参加活动ID
    private long activityId;


    //游客信息列表
    private List<ContactUser> touristList;
    //联系人信息
    private ContactUser contactInfo;
    //邮箱
    private String email;
    //收货地址
    private Address address;
    //买家留言，其他要求
    private String otherInfo;
    //客服电话
    private String servicePhone;
    //关闭订单原因
    private String closeReason;
    //付款方式，使用TcPayChannel枚举
    private int payChannel;
    //商户信息
    private TcMerchantInfo merchantInfo;

    private String hotelTitle;

    private String roomTitle;

    private String scenicTitle;

    private String ticketTitle;
    //卖家留言
    private String sellerMemo;
    //取消订单来源
    private int closeOrderSource;
    //完成订单来源
    private int finishOrderSource;

    private UserDO userDO;
    private String userNick;
    private String merchantName;
    private long requirement;
    private long reValue;
    public PayOrderDO getPayOrderDO() {
        return payOrderDO;
    }

    public void setPayOrderDO(PayOrderDO payOrderDO) {
        this.payOrderDO = payOrderDO;
    }

    public LogisticsOrderDO getLogisticsOrderDO() {
        return logisticsOrderDO;
    }

    public void setLogisticsOrderDO(LogisticsOrderDO logisticsOrderDO) {
        this.logisticsOrderDO = logisticsOrderDO;
    }

    public TcBizOrder getBizOrder() {
        return bizOrder;
    }

    public void setBizOrder(TcBizOrder bizOrder) {
        this.bizOrder = bizOrder;
    }

    public List<TcDetailOrderVO> getDetailOrders() {
        return detailOrders;
    }

    public void setDetailOrders(List<TcDetailOrderVO> detailOrders) {
        this.detailOrders = detailOrders;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(long completionTime) {
        this.completionTime = completionTime;
    }

    public long getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(long deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getCloseReason() {
        return closeReason;
    }

    public void setCloseReason(String closeReason) {
        this.closeReason = closeReason;
    }

    public long getScenicEnterTime() {
        return scenicEnterTime;
    }

    public void setScenicEnterTime(long scenicEnterTime) {
        this.scenicEnterTime = scenicEnterTime;
    }

    public long getDepartTime() {
        return departTime;
    }

    public void setDepartTime(long departTime) {
        this.departTime = departTime;
    }

    public long getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(long checkInTime) {
        this.checkInTime = checkInTime;
    }

    public long getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(long checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getLatestArriveTime() {
        return latestArriveTime;
    }

    public void setLatestArriveTime(String latestArriveTime) {
        this.latestArriveTime = latestArriveTime;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

    public List<ContactUser> getTouristList() {
        return touristList;
    }

    public void setTouristList(List<ContactUser> touristList) {
        this.touristList = touristList;
    }

    public ContactUser getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactUser contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public TcMerchantInfo getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(TcMerchantInfo merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public void setHotelTitle(String hotelTitle) {
        this.hotelTitle = hotelTitle;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getScenicTitle() {
        return scenicTitle;
    }

    public void setScenicTitle(String scenicTitle) {
        this.scenicTitle = scenicTitle;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public int getCloseOrderSource() {
        return closeOrderSource;
    }

    public void setCloseOrderSource(int closeOrderSource) {
        this.closeOrderSource = closeOrderSource;
    }

    public int getFinishOrderSource() {
        return finishOrderSource;
    }

    public void setFinishOrderSource(int finishOrderSource) {
        this.finishOrderSource = finishOrderSource;
    }

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public long getRequirement() {
        return requirement;
    }

    public void setRequirement(long requirement) {
        this.requirement = requirement;
    }

    public long getReValue() {
        return reValue;
    }

    public void setReValue(long reValue) {
        this.reValue = reValue;
    }
}
