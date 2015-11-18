package com.yimayhd.harem.model.query.base;

import java.io.Serializable;

import com.yimayhd.harem.base.BaseQuery;

/**
 * 
 * 分页请求信息：其中范型<T>为filters的类型
 * 
 * @author yebin
 *
 */
public class PageRequest<T extends BaseQuery> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8793134703133688121L;
	/**
	 * 过滤参数
	 */
	private T query;
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

	public PageRequest(T query) {
		this(0, 0, query);
	}

	public PageRequest(int pageNumber, int pageSize) {
		this(pageNumber, pageSize, (T) null);
	}

	public PageRequest(int pageNumber, int pageSize, T query) {
		this(pageNumber, pageSize, query, null);
	}

	public PageRequest(int pageNumber, int pageSize, String sortColumns) {
		this(pageNumber, pageSize, null, sortColumns);
	}

	public PageRequest(int pageNumber, int pageSize, T query, String sortColumns) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		setQuery(query);
		setSortColumns(sortColumns);
	}

	public T getQuery() {
		return query;
	}

	public void setQuery(T query) {
		this.query = query;
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
