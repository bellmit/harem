package com.yimayhd.service.impl;

import com.yimayhd.model.Refund;
import com.yimayhd.service.RefundService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class RefundServiceImpl implements RefundService {
    @Override
    public List<Refund> getList(Refund refund) throws Exception{
        List<Refund> refundList = new ArrayList<Refund>();
        int j;
        //是否有查询条件
        if(refund.getRefundListQuery()==null){
            j=10;
        }else{
            j=5;
        }
        for (int i = 0;i <= j;i++){
            Refund refundData = new Refund();
            refundData.setRefundNO("2008100109" + i);//交易编号
            refundData.setUserName("张三" + i);
            refundData.setPhone("18618162075");
            refundData.setRefundMoney(488.88);
            refundData.setShouldRefundPoint(400);
            refundData.setAvailablePoint(200);
            refundData.setDeductMoneyOffsetPoint(20);
            refundData.setFactRefundMoney(468.88);
            refundData.setRefundTime(new Date());
            refundData.setRefundStatus(1);
            refundData.setOperatorName("李四");
            refundData.setRemark("重复下单");
            refundList.add(refundData);
        }
        return refundList;
    }
}
