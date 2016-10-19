package com.yimayhd.palace.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BasePageQuery implements Serializable {
	private static final long serialVersionUID = -5909461212581464469L;
	public static final int DEFAULT_SIZE = 10;
	public static final int DEFAULT_PAGE = 1;


	public Integer pageNumber = DEFAULT_PAGE;
	protected Integer pageSize = DEFAULT_SIZE;




	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		if( pageSize <=0 ){
			pageSize = 10 ;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
