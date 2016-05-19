package com.yimayhd.palace.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yimayhd.palace.error.PalaceReturnCode;
import com.yimayhd.palace.result.BizResult;
import com.yimayhd.pay.client.model.domain.order.PayOrderDO;
import com.yimayhd.pay.client.model.param.pay.QuerySingleDTO;
import com.yimayhd.pay.client.model.query.PayOrderQuery;
import com.yimayhd.pay.client.model.result.PayResultDTO;
import com.yimayhd.pay.client.service.QueryPayOrderService;

public class PayRepo {
	private static final Logger logger = LoggerFactory.getLogger("PayRepo");

	@Autowired
	private QueryPayOrderService queryPayOrderService;

	public BizResult<PayOrderDO> getPayOrderList(long bizOrderId, int domainId) {
		BizResult<PayOrderDO> result = new BizResult<PayOrderDO>();
		if( bizOrderId <=0 ){
			result.setPalaceReturnCode(PalaceReturnCode.PARAM_ERROR);
			return result ;
		}
		QuerySingleDTO dto = new QuerySingleDTO() ;
		dto.setBizOrderId(bizOrderId);
		dto.setDomain(domainId);
		PayResultDTO<PayOrderDO> queryResult = queryPayOrderService.querySinglePayOrder(dto) ;
		if (null == queryResult || !queryResult.isSuccess() ) {
			logger.error("querySinglePayOrder failed! dto={}, result={} ", JSON.toJSONString(dto), JSON.toJSONString(queryResult) );
			result.setPalaceReturnCode(PalaceReturnCode.REMOTE_CALL_FAILED);
			return result ;
		}
		PayOrderDO payOrderDO = queryResult.getModule();
		result.setValue(payOrderDO);
		return result;
	}
}
