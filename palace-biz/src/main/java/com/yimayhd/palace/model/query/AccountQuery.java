package com.yimayhd.palace.model.query;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.pay.client.model.enums.eleaccount.UserType;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBalanceQuery;
import com.yimayhd.pay.client.model.query.eleaccount.EleAccBillDetailQuery;

public class AccountQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	/**编号*/
    private String userId;
	/**用户姓名*/
    private String userName;
    /**用户类型*/
    private String userType;
    
    /**业务流水号*/
    private String payOrderId;
	/**交易开始时间*/
    private String transStartDate;
    /**交易结束时间*/
    private String transEndDate;
    /**交易类型**/
    private String transType;
    
    private String outSn;
    
	public static EleAccBalanceQuery getEleAccBalanceQuery(AccountQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	EleAccBalanceQuery queryDO = new EleAccBalanceQuery();
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
			queryDO.setPageNo(pageNumber);
			queryDO.setPageSize(pageSize);
		}
    	
    	if(StringUtils.isNotEmpty(query.getUserId())){
    		queryDO.setUserId(Long.parseLong(query.getUserId().trim()));
    	}
    	
    	int userType = 0;
    	if(StringUtils.isNotEmpty(query.getUserType())){
    		userType = Integer.parseInt(query.getUserType());
    		queryDO.setUserType(userType);
    	}
    	
    	if(StringUtils.isNotEmpty(query.getUserName())){
    		if(userType == UserType.COMPANY_USER.getType()){
    			queryDO.setCorpName(query.getUserName().trim());
    		}else if(userType == UserType.NORMAL_USER.getType() || userType == UserType.EXPERT_USER.getType()){
    			queryDO.setUserName(query.getUserName().trim());
    		}else{
    			queryDO.setCorpName(query.getUserName().trim());
    			queryDO.setUserName(query.getUserName().trim());
    		}
    	}
    	
    	queryDO.setNeedTotalAmount(true);
		queryDO.setNeedCount(true);
    	
    	return queryDO;
    }
    
    public static EleAccBillDetailQuery getEleAccBillDetailQuery(AccountQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	EleAccBillDetailQuery queryDO = new EleAccBillDetailQuery(); 
    	
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
			queryDO.setPageNo(pageNumber);
			queryDO.setPageSize(pageSize);
		}
    	
    	if(StringUtils.isNotEmpty(query.getTransStartDate())){
    		queryDO.setTransStartDate((DateUtil.convertStringToDate(DateUtil.DEFAULT_DATE_FORMAT, query.getTransStartDate())));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getTransEndDate())){
    		queryDO.setTransEndDate((DateUtil.convertStringToDate(DateUtil.DEFAULT_DATE_FORMAT, query.getTransEndDate())));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getPayOrderId())){
    		queryDO.setPayOrderId(Long.parseLong(query.getPayOrderId().trim()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getTransType())){
    		queryDO.setTransType(Integer.parseInt(query.getTransType()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getUserId())){
    		queryDO.setUserId(Long.parseLong(query.getUserId().trim()));
    	}
    	if(StringUtils.isNotEmpty(query.getOutSn())){
    		queryDO.setOutSn(Long.parseLong(query.getOutSn().trim()));
    	}
    	
    	queryDO.setNeedCount(true);
    	return queryDO;
    }
    
    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getTransStartDate() {
		return transStartDate;
	}

	public void setTransStartDate(String transStartDate) {
		this.transStartDate = transStartDate;
	}

	public String getTransEndDate() {
		return transEndDate;
	}

	public void setTransEndDate(String transEndDate) {
		this.transEndDate = transEndDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getPayOrderId() {
		return payOrderId;
	}

	public void setPayOrderId(String payOrderId) {
		this.payOrderId = payOrderId;
	}
	
	public String getOutSn() {
		return outSn;
	}

	public void setOutSn(String outSn) {
		this.outSn = outSn;
	}
}
