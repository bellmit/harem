package com.yimayhd.model;

import com.yimayhd.base.BaseModel;
import com.yimayhd.model.query.TradeListQuery;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/27.
 */
public class Trade extends BaseModel {
    private String tradNO;//交易编号
    private String userName;//会员姓名
    private String phone;//手机号
    private double shouldMoney;//应付金额
    private double tradeMoney;//实付金额
    private double reduceMoney;//优惠金额
    private double usePoint;//使用积分
    private double sendPoint;//赠送积分
    private double point;//剩余积分
    private int tradeStatus;//交易状态
    private Date tradeTime;//交易时间
    private String terminalName;//终端
    private String cashierName;//收银员
    private String remark;//备注

    private TradeListQuery tradeListQuery;//查询条件

    public String getTradNO() {
        return tradNO;
    }

    public void setTradNO(String tradNO) {
        this.tradNO = tradNO;
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

    public double getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(double shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public double getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(double tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public double getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(double reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public double getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(double usePoint) {
        this.usePoint = usePoint;
    }

    public double getSendPoint() {
        return sendPoint;
    }

    public void setSendPoint(double sendPoint) {
        this.sendPoint = sendPoint;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public TradeListQuery getTradeListQuery() {
        return tradeListQuery;
    }

    public void setTradeListQuery(TradeListQuery tradeListQuery) {
        this.tradeListQuery = tradeListQuery;
    }
}
