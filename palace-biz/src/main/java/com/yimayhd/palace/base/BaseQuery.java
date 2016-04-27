package com.yimayhd.palace.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author czf
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseQuery implements Serializable {

	private static final long serialVersionUID = 7184354135734117464L;
	public static final int DEFAULT_SIZE = 10;
	public static final int DEFAULT_PAGE = 1;
	public static final int PAGING_YES = 1;
	public static final int PAGING_NO = 0;

	public Integer pageNumber = DEFAULT_PAGE;
	protected Integer pageSize = DEFAULT_SIZE;

	private long boothId;//it
	private String boothCode;//code
	private String boothName;//name

	public long getBoothId() {
		return boothId;
	}

	public void setBoothId(long boothId) {
		this.boothId = boothId;
	}

	public String getBoothCode() {
		return boothCode;
	}

	public void setBoothCode(String boothCode) {
		this.boothCode = boothCode;
	}

	public String getBoothName() {
		return boothName;
	}

	public void setBoothName(String boothName) {
		this.boothName = boothName;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
