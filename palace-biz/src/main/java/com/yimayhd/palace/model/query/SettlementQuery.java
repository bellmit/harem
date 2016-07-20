package com.yimayhd.palace.model.query;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.pay.client.model.param.settlement.SettlementDTO;

public class SettlementQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	/** 结算日期-开始日期,格式：yyyy-MM-dd */
    private String reqDateStart;

    /** 结算日期-结束日期,格式：yyyy-MM-dd */
    private String reqDateEnd;
    
    /**结算类型**/
    private String accountType;
    
    public static SettlementDTO getSettlementDTO(SettlementQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	SettlementDTO queryDO = new SettlementDTO();
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
		}
    	
    	if(StringUtils.isNotEmpty(query.getReqDateStart())){
    		queryDO.setReqDateStart(query.getReqDateStart());
    	}
    	if(StringUtils.isNotEmpty(query.getReqDateEnd())){
    		queryDO.setReqDateEnd(query.getReqDateEnd());
    	}
    	if(StringUtils.isNotEmpty(query.getAccountType())){
    		queryDO.setAccountType(Integer.parseInt(query.getAccountType()));
    	}
    	return queryDO;
    }
    
    public String getReqDateStart() {
		return reqDateStart;
	}

	public void setReqDateStart(String reqDateStart) {
		this.reqDateStart = reqDateStart;
	}

	public String getReqDateEnd() {
		return reqDateEnd;
	}

	public void setReqDateEnd(String reqDateEnd) {
		this.reqDateEnd = reqDateEnd;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
    
}
