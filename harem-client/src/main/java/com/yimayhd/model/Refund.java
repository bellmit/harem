package com.yimayhd.model;

import com.yimayhd.base.BaseModel;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Refund extends BaseModel {
    private String refundNO;//交易编号
    private String userName;//用户姓名
    private String phone;//手机号
    private double refundMoney;//退款金额
    private double shouldRefundPoint;//需返还积分
    private double availablePoint;//可返还积分
    private double deductMoneyOffsetPoint;//超出积分扣现
    private double factRefundMoney;//实际退款金额
    private Date refundTime;//退款时间
    private int refundStatus;//退款状态
    private String operatorName;//操作员
    private String remark;//备注

    public double getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(double refundMoney) {
        this.refundMoney = refundMoney;
    }

    public double getShouldRefundPoint() {
        return shouldRefundPoint;
    }

    public void setShouldRefundPoint(double shouldRefundPoint) {
        this.shouldRefundPoint = shouldRefundPoint;
    }

    public double getAvailablePoint() {
        return availablePoint;
    }

    public void setAvailablePoint(double availablePoint) {
        this.availablePoint = availablePoint;
    }

    public double getDeductMoneyOffsetPoint() {
        return deductMoneyOffsetPoint;
    }

    public void setDeductMoneyOffsetPoint(double deductMoneyOffsetPoint) {
        this.deductMoneyOffsetPoint = deductMoneyOffsetPoint;
    }

    public double getFactRefundMoney() {
        return factRefundMoney;
    }

    public void setFactRefundMoney(double factRefundMoney) {
        this.factRefundMoney = factRefundMoney;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRefundNO() {
        return refundNO;
    }

    public void setRefundNO(String refundNO) {
        this.refundNO = refundNO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }
}
