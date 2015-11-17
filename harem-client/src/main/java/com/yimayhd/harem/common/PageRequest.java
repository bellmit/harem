package com.yimayhd.harem.common;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页请求信息 其中范型<T>为filters的类型
 * 
 * @author badqiu
 */
public class PageRequest implements Serializable {

	/**
	 * 过滤参数
	 */
	private Map<String, Object> filters;
	/**
	 * 页号码,页码从1开始
	 */
	private int pageNumber;
	/**
	 * 分页大小
	 */
	private int pageSize;
	/**
	 * 排序的多个列,如: username desc
	 */
	private String sortColumns;

	public PageRequest() {
		this(0, 0);
	}

	public PageRequest(Map<String, Object> filters) {
		this(0, 0, filters);
	}

	public PageRequest(int pageNumber, int pageSize) {
		this(pageNumber, pageSize, (Map<String, Object>) null);
	}

	public PageRequest(int pageNumber, int pageSize, Map<String, Object> filters) {
		this(pageNumber, pageSize, filters, null);
	}

	public PageRequest(int pageNumber, int pageSize, String sortColumns) {
		this(pageNumber, pageSize, null, sortColumns);
	}

	public PageRequest(int pageNumber, int pageSize, Map<String, Object> filters, String sortColumns) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		setFilters(filters);
		setSortColumns(sortColumns);
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortColumns() {
		return sortColumns;
	}

	/**
	 * 排序的列,可以同时多列,使用逗号分隔,如 username desc,age asc
	 * 
	 * @return
	 */
	public void setSortColumns(String sortColumns) {
		this.sortColumns = sortColumns;
	}
}
