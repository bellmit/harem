package com.yimayhd.palace.model.query;

import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

import com.yimayhd.palace.base.BaseQuery;
import com.yimayhd.palace.util.DateUtil;
import com.yimayhd.pay.client.model.query.audit.AuditOrderQuery;
import com.yimayhd.pay.client.model.query.audit.AuditProgressQuery;
import com.yimayhd.pay.client.model.query.audit.AuditResultQuery;

public class AuditQuery extends BaseQuery {

	private static final long serialVersionUID = 1L;

	/**对账日期*/
    private String auditDate;

	/**对账渠道*/
    private String auditType;

    /**对账状态 */
    private String auditStatus;
    
    /**对账结果*/
    private String auditResultStatus;

	public static AuditProgressQuery getAuditProgressQuery(AuditQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	AuditProgressQuery queryDO = new AuditProgressQuery();
    	
    	if(StringUtils.isNotEmpty(query.getAuditDate())){
    		queryDO.setAuditDate(DateUtil.convertStringToDate(DateUtil.DEFAULT_DATE_FORMAT, query.getAuditDate()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditType())){
    		queryDO.setAuditType(Integer.parseInt(query.getAuditType()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditStatus())){
    		queryDO.setAuditResultStatus(Integer.parseInt(query.getAuditStatus()));
    	}
    	
    	return queryDO;
    }
    
    public static AuditResultQuery getAuditResultQuery(AuditQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	AuditResultQuery queryDO = new AuditResultQuery(); 
    	
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
			queryDO.setPageNo(pageNumber);
			queryDO.setPageSize(pageSize);
		}
    	
    	if(StringUtils.isNotEmpty(query.getAuditDate())){
    		queryDO.setAuditDate(DateUtil.convertStringToDate(DateUtil.DEFAULT_DATE_FORMAT, query.getAuditDate()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditType())){
    		queryDO.setAuditType(Integer.parseInt(query.getAuditType()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditResultStatus())){
    		queryDO.setAuditResultStatus(Integer.parseInt(query.getAuditResultStatus()));
    	}
    	
    	return queryDO;
    }
    
    public static AuditOrderQuery getAuditOrderQuery(AuditQuery query) throws ParseException{
    	
    	if(query == null){
    		return null;
    	}
    	
    	AuditOrderQuery queryDO = new AuditOrderQuery();
    	
    	if(query.getPageNumber() != null){
			int pageNumber =query.getPageNumber();
			int pageSize = query.getPageSize();
			queryDO.setPageNo(pageNumber);
			queryDO.setPageSize(pageSize);
		}
    	
    	if(StringUtils.isNotEmpty(query.getAuditDate())){
    		queryDO.setAuditDate(DateUtil.convertStringToDate(DateUtil.DEFAULT_DATE_FORMAT, query.getAuditDate()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditType())){
    		queryDO.setAuditType(Integer.parseInt(query.getAuditType()));
    	}
    	
    	if(StringUtils.isNotEmpty(query.getAuditResultStatus())){
    		queryDO.setAuditOrderStatus(Integer.parseInt(query.getAuditResultStatus()));
    	}
    	
    	return queryDO;
    }
    
    public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public String getAuditResultStatus() {
		return auditResultStatus;
	}

	public void setAuditResultStatus(String auditResultStatus) {
		this.auditResultStatus = auditResultStatus;
	}

}
