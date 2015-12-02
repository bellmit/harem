package com.yimayhd.harem.service.impl;

import com.yimayhd.harem.base.BaseException;
import com.yimayhd.harem.base.PageVO;
import com.yimayhd.harem.exception.NoticeException;
import com.yimayhd.harem.model.Trade;
import com.yimayhd.harem.model.query.PayListQuery;
import com.yimayhd.harem.model.query.TradeListQuery;
import com.yimayhd.harem.service.TradeService;
import com.yimayhd.harem.util.DateUtil;
import com.yimayhd.harem.util.excel.JxlFor2003;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.query.PayOrderQuery;
import com.yimayhd.pay.client.model.result.PayPageResultDTO;
import com.yimayhd.pay.client.service.QueryPayOrderService;
import com.yimayhd.tradecenter.client.model.domain.order.BizOrderDO;
import com.yimayhd.tradecenter.client.model.enums.OrderBizType;
import com.yimayhd.tradecenter.client.model.param.order.OrderQueryDTO;
import com.yimayhd.tradecenter.client.model.result.order.BatchQueryResult;
import com.yimayhd.tradecenter.client.service.trade.TcQueryService;
import org.apache.http.message.BasicNameValuePair;
import org.apache.mina.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/27.
 */
public class TradeServiceImpl implements TradeService {
    @Autowired
    private QueryPayOrderService QueryPayOrderServiceRef;
    @Autowired
    private TcQueryService tcQueryServiceRef;
    @Override
    public PageVO<BizOrderDO> getOrderList(TradeListQuery tradeListQuery) throws Exception{
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        //TODO 商家ID
        orderQueryDTO.setSellerId((long) 1);
        //TODO 缺少交易号、买家电话、中段考条件
        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null != tradeListQuery.getBeginDate() && "".equals(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(null != tradeListQuery.getEndDate() && "".equals(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setPageNo(tradeListQuery.getPageNumber());
        orderQueryDTO.setPageSize(tradeListQuery.getPageSize());
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);
        PageVO<BizOrderDO> pageVO = null;
        if(null != batchQueryResult && batchQueryResult.isSuccess()) {
            pageVO = new PageVO<BizOrderDO>(tradeListQuery.getPageNumber(), tradeListQuery.getPageSize(), (int) batchQueryResult.getTotalCount(), batchQueryResult.getBizOrderDOList());
        }else{
            //返回的结果错误
            throw new BaseException(batchQueryResult);
        }
        return pageVO;
    }

    @Override
    public void exportOrderList(HttpServletResponse response, TradeListQuery tradeListQuery) throws Exception {
        OrderQueryDTO orderQueryDTO = new OrderQueryDTO();
        //TODO 商家ID
        orderQueryDTO.setSellerId((long) 1);
        //TODO 缺少交易号、买家电话、中段考条件
        orderQueryDTO.setOrderBizTypes(new int[]{OrderBizType.MEMBER_RECHARGE.getBizType(), OrderBizType.BUSINESS_OUT.getBizType()});
        if(null != tradeListQuery.getBeginDate() && "".equals(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setStartDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getBeginDate()));
        }
        if(null != tradeListQuery.getEndDate() && "".equals(tradeListQuery.getBeginDate())) {
            orderQueryDTO.setEndDate(DateUtil.formatMaxTimeForDate(tradeListQuery.getEndDate()));
        }
        orderQueryDTO.setPageNo(tradeListQuery.getPageNumber());
        orderQueryDTO.setPageSize(tradeListQuery.getPageSize());
        BatchQueryResult batchQueryResult = tcQueryServiceRef.queryOrderForSeller(orderQueryDTO);
        PageVO<BizOrderDO> pageVO = null;
        if(null != batchQueryResult && batchQueryResult.isSuccess()) {
            //TODO
        }else{
            //返回的结果错误
            throw new BaseException(batchQueryResult);
        }
    }

    @Override
    public PageVO<PayOrderDO> getPayOrderList(PayListQuery payListQuery)throws Exception{
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        //TODO SessionUtil商家ID
        payOrderQuery.setSellerId(10000000);
        payOrderQuery.setPageSize(payListQuery.getPageSize());
        payOrderQuery.setPageNo(payListQuery.getPageNumber());
        if(null != payListQuery.getBeginDate() && "".equals(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if(null != payListQuery.getEndDate() && "".equals(payListQuery.getBeginDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        PageVO<PayOrderDO> pageVO = null;
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            pageVO = new PageVO<PayOrderDO>(payListQuery.getPageSize(),payPageResultDTO.getTotalCount(),payPageResultDTO.getPageNo(),payPageResultDTO.getList());
        }else{
            //返回的结果错误
            throw new BaseException(payPageResultDTO);
        }

        return pageVO;
    }

    @Override
    public void exportPayOrderList(HttpServletResponse response,PayListQuery payListQuery) throws Exception {
        PayOrderQuery payOrderQuery = new PayOrderQuery();
        payOrderQuery.setBizOrderId(payListQuery.getBizOrderId());
        //TODO SessionUtil商家ID
        payOrderQuery.setSellerId(10000000);
        //导出10000条
        payOrderQuery.setPageSize(10000);
        if(null != payListQuery.getBeginDate() && "".equals(payListQuery.getBeginDate())) {
            payOrderQuery.setStartDate(DateUtil.formatMaxTimeForDate(payListQuery.getBeginDate()));
        }
        if (null != payListQuery.getEndDate() && "".equals(payListQuery.getBeginDate())) {
            payOrderQuery.setEndDate(DateUtil.formatMaxTimeForDate(payListQuery.getEndDate()));
        }
        PayPageResultDTO<PayOrderDO> payPageResultDTO = QueryPayOrderServiceRef.getPayOrderResult(payOrderQuery);
        if(null != payPageResultDTO && payPageResultDTO.isSuccess()) {
            List<BasicNameValuePair> headList = new ArrayList<BasicNameValuePair>();
            headList.add(new BasicNameValuePair("tradeNo", "交易号"));
            headList.add(new BasicNameValuePair("id", "商户订单号"));
            headList.add(new BasicNameValuePair("subject", "商品信息"));
            headList.add(new BasicNameValuePair("buyerAccount", "对方账号"));
            headList.add(new BasicNameValuePair("totalAmount", "交易金额"));
            headList.add(new BasicNameValuePair("payStatus", "状态"));
            JxlFor2003.exportExcel(response, "payList.xls", payPageResultDTO.getList(), headList);

        }else{
            //返回的结果错误
            throw new BaseException(payPageResultDTO);
        }

    }
}
