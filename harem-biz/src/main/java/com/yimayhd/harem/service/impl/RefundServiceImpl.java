package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.model.Refund;
import com.yimayhd.harem.model.query.RefundListQuery;
import com.yimayhd.harem.service.RefundService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.tradecenter.client.model.domain.imall.IMallRefundRecordDO;
import com.yimayhd.tradecenter.client.model.query.IMallRefundRecordQuery;
import com.yimayhd.tradecenter.client.model.result.TCPageResult;
import com.yimayhd.tradecenter.client.service.imall.IMallHaremService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by czf on 2015/10/27.
 */
public class RefundServiceImpl implements RefundService {
    @Autowired
    private IMallHaremService iMallHaremServiceRef;
    @Override
    public PageVO<IMallRefundRecordDO> getList(RefundListQuery refundListQuery) throws Exception{
        IMallRefundRecordQuery tcRefundRecordQuery = new IMallRefundRecordQuery();
        //TODO 商家ID
        tcRefundRecordQuery.setSellerId((long) 1);
        if(!StringUtils.isEmpty(refundListQuery.getTradeId())){
            tcRefundRecordQuery.setTradeId(refundListQuery.getTradeId());
        }
        if(!StringUtils.isEmpty(refundListQuery.getBeginDate())) {
            tcRefundRecordQuery.setGmtCreatedStart(DateUtil.formatMaxTimeForDate(refundListQuery.getBeginDate()));
        }
        if(!StringUtils.isEmpty(refundListQuery.getEndDate())) {
            tcRefundRecordQuery.setGmtCreatedEnd(DateUtil.formatMaxTimeForDate(refundListQuery.getEndDate()));
        }
        tcRefundRecordQuery.setCurrentPage(refundListQuery.getPageNumber());
        tcRefundRecordQuery.setPageSize(refundListQuery.getPageSize());

        TCPageResult<IMallRefundRecordDO> tcPageResult = iMallHaremServiceRef.queryRefundRecords(tcRefundRecordQuery);
        PageVO<IMallRefundRecordDO> pageVO = null;
        if(null != tcPageResult && tcPageResult.isSuccess()) {
            pageVO = new PageVO<IMallRefundRecordDO>(refundListQuery.getPageNumber(), refundListQuery.getPageSize(), (int) tcPageResult.getTotalCount(), tcPageResult.getList());
        }
        return pageVO;
    }

    @Override
    public Refund getById(long refundId) throws Exception {
        Refund refundData = new Refund();
        refundData.setRefundNO("2008100109");//交易编号
        refundData.setUserName("张三");
        refundData.setPhone("18618162075");
        refundData.setRefundMoney(BigDecimal.valueOf(488.88));
        refundData.setShouldRefundPoint(BigDecimal.valueOf(400));
        refundData.setAvailablePoint(BigDecimal.valueOf(200));
        refundData.setDeductMoneyOffsetPoint(BigDecimal.valueOf(20));
        refundData.setFactRefundMoney(BigDecimal.valueOf(468.88));
        refundData.setRefundTime(new Date());
        refundData.setRefundStatus(1);
        refundData.setOperatorName("李四");
        refundData.setRemark("重复下单");
        return refundData;
    }

}
