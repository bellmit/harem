package com.yimayhd.harem.model;

import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.BizOrderFeatureKey;
import com.yimayhd.tradecenter.client.util.BizOrderUtil;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Created by Administrator on 2015/12/8.
 */
public class BizOrderExportVO extends BizOrderDO {
    private static final long serialVersionUID = -1178403102272771239L;
    private long usePoint;//使用积分
    private long givePoint;//赠送积分
    private int payChannel;//
    private String payChannelName;//支付方式名称
    private String dt;//部门
    private String jn;//工号
    private String dc;//终端
    private String pn;//
    private long stt;//
    private String no;//

    public static BizOrderExportVO getBizOrderExportVO(BizOrderDO bizOrderDO) throws Exception{
        BizOrderExportVO bizOrderExportVO = new BizOrderExportVO();
        BeanUtils.copyProperties(bizOrderExportVO, bizOrderDO);
        BeanUtils.copyProperties(bizOrderExportVO,BizOrderUtil.getIMallInfo(bizOrderDO) );
        bizOrderExportVO.setUsePoint(BizOrderUtil.getUsePointNum(bizOrderDO));
        bizOrderExportVO.setGivePoint(BizOrderUtil.getLong(bizOrderDO, BizOrderFeatureKey.GIVE_POINT));
        bizOrderExportVO.payChannel = BizOrderUtil.getInt(bizOrderDO, BizOrderFeatureKey.PAY_CHANNEL);
        return bizOrderExportVO;
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

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getJn() {
        return jn;
    }

    public void setJn(String jn) {
        this.jn = jn;
    }

    public String getDc() {
        return dc;
    }

    public void setDc(String dc) {
        this.dc = dc;
    }

    public String getPn() {
        return pn;
    }

    public void setPn(String pn) {
        this.pn = pn;
    }

    public long getStt() {
        return stt;
    }

    public void setStt(long stt) {
        this.stt = stt;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
