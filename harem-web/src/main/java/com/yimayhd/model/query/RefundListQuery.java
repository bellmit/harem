package com.yimayhd.model.query;

import com.yimayhd.base.BaseQuery;

/**
 * Created by Administrator on 2015/10/27.
 */
public class RefundListQuery extends BaseQuery {
    private String refundNO;//交易号
    private String phone;//手机号
    private String userName;//会员名
    private String terminalName;//终端编号

    public String getTradNO() {
        return refundNO;
    }

    public void setTradNO(String refundNO) {
        this.refundNO = refundNO;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }
}
