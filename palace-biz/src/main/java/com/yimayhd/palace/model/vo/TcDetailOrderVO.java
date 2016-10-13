package com.yimayhd.palace.model.vo;

import com.yimayhd.tradecenter.client.model.result.order.create.TcBizOrder;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menhaihao372 on 2014/9/8.
 *
 */
public class TcDetailOrderVO implements Serializable {
    private static final long serialVersionUID = 5051070522168473251L;
    /** 子订单基本信息 */
    private TcBizOrder bizOrder;
    /** 商品id */
    private long itemId;
    /** 商品标题 */
    private String itemTitle;
    /** 商品子标题 */
    private String itemSubTitle;
    /** sku标题 */
    private String skuTitle;
    /** 商品图 */
    private String itemPic;
    /** 单价 */
    private long itemPrice;
    /** 主订单id */
    private long parentId;
    /** 出发日期 */
    private Date startDate;
    /** 线路套餐 */
    private String linePackage;
    /** 人员类型 */
    private String personType;
    /** 商品编码 */
    private String itemCode;
    /** 商品Sku编码 */
    private String itemSkuCode;
    /** 同城活动活动内容 */
    private String activityContent;
    /** 同城活动活动时间--文本 */
    private String activityTime;
    /** 子订单实付总金额**/

    private long subOrderActualFee;

//    private long rechargeGoldNum;
//    private DoctorAppointmentInfo doctorAppointmentInfo;

    public TcBizOrder getBizOrder() {
        return bizOrder;
    }

    public void setBizOrder(TcBizOrder bizOrder) {
        this.bizOrder = bizOrder;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemSubTitle() {
        return itemSubTitle;
    }

    public void setItemSubTitle(String itemSubTitle) {
        this.itemSubTitle = itemSubTitle;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public long getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(long itemPrice) {
        this.itemPrice = itemPrice;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLinePackage() {
        return linePackage;
    }

    public void setLinePackage(String linePackage) {
        this.linePackage = linePackage;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemSkuCode() {
        return itemSkuCode;
    }

    public void setItemSkuCode(String itemSkuCode) {
        this.itemSkuCode = itemSkuCode;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public long getSubOrderActualFee() {
        return subOrderActualFee;
    }

    public void setSubOrderActualFee(long subOrderActualFee) {
        this.subOrderActualFee = subOrderActualFee;
    }
}
