package com.yimayhd.palace.model.query;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.palace.base.BaseQuery;

public class SettlementQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;
	
	/** 结算日期-开始日期,格式：yyyy-MM-dd */
    private String reqDateStart;

    /** 结算日期-结束日期,格式：yyyy-MM-dd */
    private String reqDateEnd;
    
    /**结算类型**/
    private String accountType;
    
    /** 结算单号 */
    private String settlementId;

	/** 回盘时间,格式：yyyy-MM-dd HH:mm:ss */
    private String backTime;

    /** 结算日期,格式：yyyy-MM-dd */
    private String reqDate;
    
    /** 批次号 */
    private String batchNo;
    
    public static com.yimayhd.pay.client.model.query.settlement.SettlementQuery getSettlementDTO(SettlementQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	com.yimayhd.pay.client.model.query.settlement.SettlementQuery queryDO = new com.yimayhd.pay.client.model.query.settlement.SettlementQuery();
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
			queryDO.setPageNo(pageNumber);
			queryDO.setPageSize(pageSize);
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
    	if(StringUtils.isNotEmpty(query.getSettlementId())){
    		queryDO.setSettlementId(Long.parseLong(query.getSettlementId()));
    	}
    	if(StringUtils.isNotEmpty(query.getBackTime())){
    		queryDO.setBackTime(query.getBackTime());
    	}
    	if(StringUtils.isNotEmpty(query.getReqDate())){
    		queryDO.setReqDate(query.getReqDate());
    	}
    	if(StringUtils.isNotEmpty(query.getBatchNo())){
    		queryDO.setBatchNo(Integer.parseInt(query.getBatchNo()));
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
    
	public String getSettlementId() {
		return settlementId;
	}

	public void setSettlementId(String settlementId) {
		this.settlementId = settlementId;
	}

	public String getBackTime() {
		return backTime;
	}

	public void setBackTime(String backTime) {
		this.backTime = backTime;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
