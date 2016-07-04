package com.yimayhd.palace.controller;

import java.io.Serializable;
import java.util.List;

import com.yimayhd.palace.model.SockpuppetDO;

/**
 * 运营小号
 * @author xmn
 *
 */
public class SockPuppetPageQuery extends SockpuppetDO implements Serializable {

    private static final long serialVersionUID = 482986871546699027L;

    private List<Long> userIdList;

    private int pageNo = 1;

    private int pageSize = 20;

    private Long startIndex;

    private List<Long> optionsList;

    public List<Long> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Long> userIdList) {
        this.userIdList = userIdList;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Long startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public String toString() {
        return "UserDOPageQuery{" +
                "userIdList=" + userIdList +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", startIndex=" + startIndex +
                ", optionsList=" + optionsList +
                '}';
    }

    public List<Long> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Long> optionsList) {
        this.optionsList = optionsList;
    }

}
