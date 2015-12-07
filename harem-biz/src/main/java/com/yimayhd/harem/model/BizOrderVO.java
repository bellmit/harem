package com.yimayhd.harem.model;

import com.yimayhd.tradecenter.client.model.domain.imall.IMallInfo;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import com.yimayhd.user.client.domain.UserDO;
import org.apache.commons.beanutils.BeanUtils;


public class BizOrderVO extends BizOrderDO {
    private static final long serialVersionUID = 8182585991216318684L;
    private UserDO userDO;
    private IMallInfo mallInfo;

    private long usePoint;

    private long givePoint;

    private int payChannel;

    public UserDO getUserDO() {
        return userDO;
    }

    public void setUserDO(UserDO userDO) {
        this.userDO = userDO;
    }

    public static BizOrderVO getBizOrderVO(BizOrderDO bizOrderDO) throws Exception{
        BizOrderVO bizOrderVO = new BizOrderVO();
        BeanUtils.copyProperties(bizOrderVO,bizOrderDO);
        bizOrderVO.setUsePoint(BizOrderUtil.getUsePointNum(bizOrderDO));
        bizOrderVO.setGivePoint(BizOrderUtil.getLong(bizOrderDO, BizOrderFeatureKey.GIVE_POINT));
        bizOrderVO.payChannel = BizOrderUtil.getInt(bizOrderDO, BizOrderFeatureKey.PAY_CHANNEL);
        return bizOrderVO;
    }
    public static BizOrderDO getBizOrderDO(BizOrderVO bizOrderVO){
        return bizOrderVO;
    }

    public IMallInfo getMallInfo() {
        return mallInfo;
    }

    public void setMallInfo(IMallInfo mallInfo) {
        this.mallInfo = mallInfo;
    }

    public long getUsePoint() {
        return usePoint;
    }

    public void setUsePoint(long usePoint) {
        this.usePoint = usePoint;
    }

    public long getGivePoint() {
        return givePoint;
    }

    public void setGivePoint(long givePoint) {
        this.givePoint = givePoint;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }
}
