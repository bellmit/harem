package com.yimayhd.harem.model.trade;

import com.yimayhd.harem.model.Contact;
import com.yimayhd.harem.model.Tourist;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;

import java.util.List;

/**
 * Created by zhaozhaonan on 2015/12/22.
 */
public class OrderDetails {
    private BizOrderDO bizOrderDO;

    private List<SubOrder> subOrderList;

    // 联系人
    private List<Contact> contacts;
    // 游客
    private List<Tourist> tourists;

    private String buyerName;

    private String buyerNiceName;

    private String buyerPhoneNum;


    public BizOrderDO getBizOrderDO() {
        return bizOrderDO;
    }

    public void setBizOrderDO(BizOrderDO bizOrderDO) {
        this.bizOrderDO = bizOrderDO;
    }

    public List<SubOrder> getSubOrderList() {
        return subOrderList;
    }

    public void setSubOrderList(List<SubOrder> subOrderList) {
        this.subOrderList = subOrderList;
    }


    public OrderDetails(BizOrderDO bizOrderDO, List<SubOrder> subOrderList) {
        this.bizOrderDO = bizOrderDO;
        this.subOrderList = subOrderList;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Tourist> getTourists() {
        return tourists;
    }

    public void setTourists(List<Tourist> tourists) {
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
}
