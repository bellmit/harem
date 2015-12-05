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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by czf on 2015/10/27.
 */
public class RefundServiceImpl implements RefundService {
    @Autowired
    private IMallHaremService iMallHaremServiceRef;
    @Override
    public PageVO<IMallRefundRecordDO> getList(long sellerId,RefundListQuery refundListQuery) throws Exception{
        //查询条件
        IMallRefundRecordQuery tcRefundRecordQuery = new IMallRefundRecordQuery();
        tcRefundRecordQuery.setSellerId(sellerId);
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
        //返回结果
        PageVO<IMallRefundRecordDO> pageVO = null;
        if(null != tcPageResult && tcPageResult.isSuccess()) {
            pageVO = new PageVO<IMallRefundRecordDO>(refundListQuery.getPageNumber(), refundListQuery.getPageSize(), (int) tcPageResult.getTotalCount(), tcPageResult.getList());
        }
        return pageVO;
    }

    @Override
    public List<IMallRefundRecordDO> exportRefundList(long sellerId, RefundListQuery refundListQuery) throws Exception {
        //返回结果
        List<IMallRefundRecordDO> iMallRefundRecordDOList = null;
        //查询条件
        IMallRefundRecordQuery tcRefundRecordQuery = new IMallRefundRecordQuery();
        tcRefundRecordQuery.setSellerId(sellerId);
        if(!StringUtils.isEmpty(refundListQuery.getTradeId())){
            tcRefundRecordQuery.setTradeId(refundListQuery.getTradeId());
        }
        if(!StringUtils.isEmpty(refundListQuery.getBeginDate())) {
            tcRefundRecordQuery.setGmtCreatedStart(DateUtil.formatMaxTimeForDate(refundListQuery.getBeginDate()));
        }
        if(!StringUtils.isEmpty(refundListQuery.getEndDate())) {
            tcRefundRecordQuery.setGmtCreatedEnd(DateUtil.formatMaxTimeForDate(refundListQuery.getEndDate()));
        }
        tcRefundRecordQuery.setCurrentPage(1);
        tcRefundRecordQuery.setPageSize(10000);
        TCPageResult<IMallRefundRecordDO> tcPageResult = iMallHaremServiceRef.queryRefundRecords(tcRefundRecordQuery);
        if(null != tcPageResult && tcPageResult.isSuccess()) {
            iMallRefundRecordDOList = tcPageResult.getList();
        }
        return iMallRefundRecordDOList;
    }
}
